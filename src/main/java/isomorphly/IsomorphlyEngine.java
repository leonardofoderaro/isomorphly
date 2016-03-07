package isomorphly;

import isomorphly.reflect.scanners.PackageScanner;


public class IsomorphlyEngine {
	private PackageScanner packageScanner;
	
	public IsomorphlyEngine(String packageNames[]) {
		this.packageScanner = new PackageScanner(packageNames);
	}
	
	public void init() {
		this.packageScanner.scan();
	}
}
