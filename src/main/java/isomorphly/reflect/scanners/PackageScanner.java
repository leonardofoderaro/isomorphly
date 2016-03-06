package isomorphly.reflect.scanners;

import isomorphly.annotations.Group;

import java.util.Set;

import org.reflections.Reflections;

public class PackageScanner {
	private Reflections reflections;
	
	private Set<Class<?>> groups;
	
	private String packageNames[];
	
	public PackageScanner(String packageNames[]) {
		this.packageNames = packageNames;
	}
	
	public void scan() {
		for (String s : packageNames) {
		    reflections = new Reflections(s);
		    groups.addAll(reflections.getTypesAnnotatedWith(Group.class));
		}
	}

}
