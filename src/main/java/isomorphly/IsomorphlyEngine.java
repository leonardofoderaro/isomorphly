package isomorphly;

import isomorphly.annotations.Component;
import isomorphly.annotations.Group;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;


public class IsomorphlyEngine {

	private List<Class<?>> groupAnnotations;

	private List<Class<?>> componentAnnotations;

	private String[] packageNames;

	private boolean initialized;

	private List<Class<?>> clientGroups;

	public IsomorphlyEngine(String[] packageNames) {

		this.initialized = false;

		this.packageNames = packageNames;

		groupAnnotations = new ArrayList<>();

		componentAnnotations = new ArrayList<>();

		clientGroups = new ArrayList<>();

	}

	public void init() throws IsomorphlyValidationException {
		scanAnnotatedElements();

		scanAnnotatedImplementations();
		
		if (this.getIsomorphlyClientGroups().isEmpty()) {
			throw new IsomorphlyValidationException("Group emtpy!");
		}


		this.initialized = true;
	}

	private void scanAnnotatedElements() throws IsomorphlyValidationException {
		for (String pkgName : this.packageNames) {
			Reflections reflections = new Reflections(pkgName);

			Set<Class<?>> groups = reflections.getTypesAnnotatedWith(Group.class);
			for (Class<?> c : groups) {
				if (c.isAnnotation()) {
			        groupAnnotations.add(c);
				} else {
					throw new IsomorphlyValidationException("@Group Annotation can be used only on Annotations.");
				}
			}

			Set<Class<?>> components = reflections.getTypesAnnotatedWith(Component.class);
			for (Class<?> c : components) {
			    if (c.isAnnotation()) {
			        componentAnnotations.add(c);	
			    } else {
					throw new IsomorphlyValidationException("@Group Annotation can be used only on Annotations.");
			    }
			}
		}
	}

	private void scanAnnotatedImplementations() {
		for (String pkgName : this.packageNames) {
			Reflections reflections = new Reflections(pkgName);

			for (Class<?> groupAnnotation : groupAnnotations) {
				@SuppressWarnings("unchecked")
				Set<Class<?>> annotatedClientGroups = reflections.getTypesAnnotatedWith((Class<? extends Annotation>) groupAnnotation);
				clientGroups.addAll(annotatedClientGroups);
			}
		}

	}

	public final List<Class<?>> getIsomorphlyGroups() {
		return groupAnnotations;
	}

	public final List<Class<?>> getIsomorphlyComponents() {
		return componentAnnotations;
	}

	public final List<Class<?>> getIsomorphlyClientGroups() {
		return clientGroups;
	}

	public boolean isInitialized() {
		boolean groupAnnotationIsValid = this.groupAnnotations != null && !this.groupAnnotations.isEmpty();

		boolean componentAnnotationIsValid = this.componentAnnotations != null && !this.componentAnnotations.isEmpty();

		return this.initialized && groupAnnotationIsValid && componentAnnotationIsValid;
	}


}
