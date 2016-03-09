package isomorphly.reflect.scanners;

import isomorphly.IsomorphlyEngine;

public class PackageScanner {
	
	private String[] packageNames;
	
	private IsomorphlyEngine engine;
	
	public PackageScanner(IsomorphlyEngine engine, String[] packageNames) {
		this.engine = engine;
		this.packageNames = packageNames;
	}
	
	public String[] getPackagesNames() {
		return this.packageNames;
	}
	
	public IsomorphlyEngine getEngine() {
		return this.engine;
	}
	
}
