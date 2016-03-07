package isomorphly;

import isomorphly.annotations.Component;
import isomorphly.annotations.Group;
import isomorphly.reflect.scanners.PackageScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;


public class IsomorphlyEngine {

	private PackageScanner packageScanner;

	private List<Class<?>> groupAnnotations;

	private List<Class<?>> componentAnnotations;

	private String[] packageNames;

	boolean initialized;

	public IsomorphlyEngine(String[] packageNames) {

		this.initialized = false;

		this.packageScanner = new PackageScanner(this, packageNames);

		this.packageNames = packageNames;

		groupAnnotations = new ArrayList<>();

		componentAnnotations = new ArrayList<>();

	}

	public void init() {
		this.packageScanner.scan();

		scanAnnotatedElements();

		this.initialized = true;
	}

	private void scanAnnotatedElements() {
		for (String pkgName : this.packageNames) {
			Reflections reflections = new Reflections(pkgName);

			Set<Class<?>> groups = reflections.getTypesAnnotatedWith(Group.class);
			groupAnnotations.addAll(groups);

			Set<Class<?>> components = reflections.getTypesAnnotatedWith(Component.class);
			componentAnnotations.addAll(components);
		}
	}
	
	public final List<Class<?>> getIsomorphlyGroups() {
		return groupAnnotations;
	}

	public final List<Class<?>> getIsomorphlyComponents() {
		return componentAnnotations;
	}

	public boolean isInitialized() {
		return this.initialized &&
				this.groupAnnotations != null &&
				this.groupAnnotations.size() > 0 &&
				this.componentAnnotations != null &&
				this.componentAnnotations.size() > 0;
	}


}
