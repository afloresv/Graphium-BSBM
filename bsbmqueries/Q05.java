/*
 *  Copyright (C) 2014, Universidad Simon Bolivar
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package bsbmqueries;

import java.util.*;
import java.lang.*;
import java.io.*;

import ve.usb.ldc.graphium.core.*;

public class Q05 extends BerlinQuery {

	public static void main(String[] args) {
		BerlinQuery Q = new Q05(args[1]);
		Q.inst = new InstanceReader(2,args[1],5,Integer.parseInt(args[0]));
		Q.runExperiment();
		Q.close();
	}

	public Q05(String path) {
		super(path);
	}

	public void runQuery() {

		r = new ResultGenerator(1);
		Vertex pNode, vNode;
		Edge rel;
		GraphIterator<Edge> it;
		RDFobject relURI, product;

		HashSet<Long>
			setOP1 = new HashSet<Long>(),
			setOP2 = new HashSet<Long>();
		HashSet<Vertex>
			setPF = new HashSet<Vertex>(),
			setProd = new HashSet<Vertex>();

		// FILTER (?p = bsbminst:dataFromProducer408/Product20183)
		pNode = g.getVertexURI(bsbminst+"dataFromProducer"
			+inst.get(0)+"/Product"+inst.get(1));
		if (pNode == null) return;
		it = pNode.getEdgesOut();
		while (it.hasNext()) {
			rel = it.next();
			relURI = rel.getURI();
			if (relURI.equals(bsbm+"productFeature")) {
				// ?p bsbm:productFeature ?prodFeature .
				setPF.add(rel.getEnd());
			} else if (relURI.equals(bsbm+"productPropertyNumeric1")) {
				// ?p bsbm:productPropertyNumeric1 ?origProperty1 .
				setOP1.add(rel.getEnd().getLong());
			} else if (relURI.equals(bsbm+"productPropertyNumeric2")) {
				// ?p bsbm:productPropertyNumeric2 ?origProperty2 .
				setOP2.add(rel.getEnd().getLong());
			}
		}
		it.close();

		for (Vertex nodePF : setPF) {
			it = nodePF.getEdgesIn();
			while (it.hasNext()) {
				rel = it.next();
				vNode = rel.getStart();
				// ?product bsbm:productFeature ?prodFeature .
				// FILTER (bsbminst:dataFromProducer408/Product20183 != ?product)
				if (!pNode.equals(vNode) && rel.getURI().equals(bsbm+"productFeature"))
					setProd.add(vNode);
			}
			it.close();
		}

		ArrayList<ResultTuple> results = new ArrayList<ResultTuple>();

		for (Vertex nodeProd : setProd) {
			product = nodeProd.getAny();

			HashSet<Long>
				setSP1 = new HashSet<Long>(),
				setSP2 = new HashSet<Long>();
			HashSet<RDFobject> setPL = new HashSet<RDFobject>();

			it = nodeProd.getEdgesOut();
			while (it.hasNext()) {
				rel = it.next();
				relURI = rel.getURI();
				if (relURI.equals(rdfs+"label")) {
					// ?product rdfs:label ?productLabel .
					setPL.add(rel.getEnd().getAny());
				} else if (relURI.equals(bsbm+"productPropertyNumeric1")) {
					// ?product bsbm:productPropertyNumeric1 ?simProperty1 .
					setSP1.add(rel.getEnd().getLong());
				} else if (relURI.equals(bsbm+"productPropertyNumeric2")) {
					// ?product bsbm:productPropertyNumeric2 ?simProperty2 .
					setSP2.add(rel.getEnd().getLong());
				}
			}
			it.close();

			// FILTER (?simProperty1 < (?origProperty1 + 120)
			// && ?simProperty1 > (?origProperty1 - 120))
			boolean passFilter = false;
			filter1:
			for (Long origProperty1 : setOP1) {
				for (Long simProperty1 : setSP1) {
					if (simProperty1 < (origProperty1 + 170)
						&& simProperty1 > (origProperty1 - 170))
						passFilter = true;
						break filter1;
				}
			}

			// FILTER (?simProperty2 < (?origProperty2 + 170)
			// && ?simProperty2 > (?origProperty2 - 170))
			if (passFilter) {
				passFilter = false;
				filter2:
				for (Long origProperty2 : setOP2) {
					for (Long simProperty2 : setSP2) {
						if (simProperty2 < (origProperty2 + 120)
							&& simProperty2 > (origProperty2 - 120)) {
							passFilter = true;
							break filter2;
						}
					}
				}
			}

			if (passFilter)
				for (RDFobject productLabel : setPL)
					results.add(r.newResult(product,productLabel));
		}

		// ORDER BY ?productLabel
		Collections.sort(results);

		// LIMIT 5
		for (int i=0 ; i<5 && i<results.size() ; i++)
			results.get(i).print();
	}
}
