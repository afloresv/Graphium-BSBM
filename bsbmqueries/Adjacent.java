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

public class Adjacent extends BerlinQuery {

	public static void main(String[] args) {
		BerlinQuery Q = new Adjacent(args[0]);
		Q.runExperiment();
		Q.close();
	}

	public Adjacent(String path) {
		super(path);
	}

	public void runQuery() {

		r = new ResultGenerator();
		Vertex srcNode;
		GraphIterator<Edge> it;

		srcNode = g.getVertexURI(bsbm+"Product");
		if (srcNode == null) return;
		it = srcNode.getEdgesIn();
		while (it.hasNext()) {
			// ?product ?p bsbm:Product .
			r.newResult(it.next().getStart().getAny()).print();
		}
		it.close();
	}
}
