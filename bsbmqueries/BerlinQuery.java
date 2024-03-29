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

public abstract class BerlinQuery {

	Graphium g;
	ResultGenerator r;
	InstanceReader inst;

	public BerlinQuery(String path) {
		g = GraphiumLoader.open(path);
	}

	public void runExperiment() {
		// Cold Cache
		long startTime = System.currentTimeMillis();
		this.runQuery();
		System.err.print((System.currentTimeMillis()-startTime) + "\t");
		
		// Warm Cache
		startTime = System.currentTimeMillis();
		this.runQuery();
		System.err.println(System.currentTimeMillis()-startTime);

		this.close();
	}

	// PREFIX
	public final String bsbm = "http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/vocabulary/";
	public final String bsbminst = "http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/";
	public final String bsbmexport = "http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/vocabulary/export/";
	public final String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	public final String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
	public final String dc = "http://purl.org/dc/elements/1.1/";
	public final String rev = "http://purl.org/stuff/rev#";
	public final String foaf = "http://xmlns.com/foaf/0.1/";
	public final String xsd = "http://www.w3.org/2001/XMLSchema#";

	public abstract void runQuery();
	public void close() {
		g.close();
	}
}
