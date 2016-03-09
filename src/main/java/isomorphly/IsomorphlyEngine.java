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
		
		validateGroupAnnotatedClasses();
		
		validateComponentAnnotatedClasses();
		
		if (this.getIsomorphlyClientGroups().isEmpty()) {
			throw new IsomorphlyValidationException("Group emtpy!");
		}


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

	private void validateGroupAnnotatedClasses() throws IsomorphlyValidationException {
		List<Class<?>> groups = this.getIsomorphlyGroups();

		for (Class<?> cls : groups) {
			if (!cls.isAnnotation()) {
				throw new IsomorphlyValidationException("@Group Annotation is valid only when applied to another Annotation");
			}

			if (!cls.isAnnotationPresent(Group.class)) {
				throw new IsomorphlyValidationException("@Group Annotation not found in type " + cls.getName());
			}
		}
	}

	
	private void validateComponentAnnotatedClasses() throws IsomorphlyValidationException {
		List<Class<?>> groups = this.getIsomorphlyComponents();

		for (Class<?> cls : groups) {
			if (!cls.isAnnotation()) {
				throw new IsomorphlyValidationException("@Component Annotation is valid only when applied to another Annotation");
			}

			if (!cls.isAnnotationPresent(Component.class)) {
				throw new IsomorphlyValidationException("@Component Annotation not found in type " + cls.getName());
			}
		}
	}


}
