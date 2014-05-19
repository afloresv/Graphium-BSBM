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

public class PrintQ11 {

	public static void main(String[] args) {
		InstanceReader inst = new InstanceReader(2,args[1],11,Integer.parseInt(args[0]));

		String q = "";
		q += "SELECT ?property ?hasValue ?isValueOf\n";
		q += "WHERE {\n";
		q += "  { <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/dataFromVendor"+inst.get(0)+"/Offer"+inst.get(1)+"> ?property ?hasValue }\n";
		q += "  UNION\n";
		q += "  { ?isValueOf ?property <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/dataFromVendor"+inst.get(0)+"/Offer"+inst.get(1)+"> }\n";
		q += "}\n";

		System.out.print(q);
	}
}
