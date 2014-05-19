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

public class PrintQ01 {

	public static void main(String[] args) {
		InstanceReader inst = new InstanceReader(4,args[1],1,Integer.parseInt(args[0]));

		String q = "";
		q += "PREFIX bsbminst: <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/>\n";
		q += "PREFIX bsbm: <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/vocabulary/>\n";
		q += "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n";
		q += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		q += "\n";
		q += "SELECT DISTINCT ?product ?label\n";
		q += "WHERE { \n";
		q += "    ?product rdfs:label ?label .\n";
		q += "    ?product a <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/ProductType"+inst.get(0)+"> .\n";
		q += "    ?product bsbm:productFeature <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/ProductFeature"+inst.get(1)+"> .\n";
		q += "    ?product bsbm:productFeature <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/ProductFeature"+inst.get(2)+"> . \n";
		q += "    ?product bsbm:productPropertyNumeric1 ?value1 . \n";
		q += "    FILTER (?value1 > "+inst.get(3)+") \n";
		q += "}\n";
		q += "ORDER BY ?label\n";
		q += "LIMIT 10";

		System.out.println(q);
	}
}
