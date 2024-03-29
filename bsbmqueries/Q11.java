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

public class Q11 extends BerlinQuery {

	public static void main(String[] args) {
		BerlinQuery Q = new Q11(args[1]);
		Q.inst = new InstanceReader(2,args[1],11,Integer.parseInt(args[0]));
		Q.runExperiment();
		Q.close();
	}

	public Q11(String path) {
		super(path);
	}

	public void runQuery() {

		r = new ResultGenerator();
		Vertex iNode;
		Edge rel;
		GraphIterator<Edge> it;

		iNode = g.getVertexURI(bsbminst+"dataFromVendor"+inst.get(0)+"/Offer"+inst.get(1));
		if (iNode == null) return;
		it = iNode.getEdgesOut();
		while (it.hasNext()) {
			rel = it.next();
			// bsbminst:dataFromVendor215/Offer423241 ?property ?hasValue
			(r.newResult(rel.getURI(),rel.getEnd().getAny(),"")).print();
		}
		it.close();

		// UNION

		it = iNode.getEdgesIn();
		while (it.hasNext()) {
			rel = it.next();
			// ?isValueOf ?property bsbminst:dataFromVendor215/Offer423241
			(r.newResult(rel.getURI(),"",rel.getStart().getAny())).print();
		}
		it.close();
	}
}
