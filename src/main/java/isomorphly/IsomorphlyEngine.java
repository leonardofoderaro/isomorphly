package isomorphly;

import isomorphly.reflect.scanners.PackageScanner;

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

  private PackageScanner packageScanner;

  private List<Class<?>> methodCallContexts;

  public IsomorphlyEngine(String[] packageNames) {

    this.initialized = false;

    this.packageNames = packageNames;

    groupAnnotations = new ArrayList<>();

    componentAnnotations = new ArrayList<>();
    
    methodCallContexts = new ArrayList<>();

    clientGroups = new ArrayList<>();

  }

  public void init() throws IsomorphlyValidationException {

    this.packageScanner = new PackageScanner(this.packageNames);

    scanAnnotatedElements();

    scanAnnotatedImplementations();

    boolean groupAnnotationIsValid = !this.groupAnnotations.isEmpty();

    boolean componentAnnotationIsValid = !this.componentAnnotations.isEmpty();

    this.initialized = groupAnnotationIsValid && componentAnnotationIsValid;

    if (!groupAnnotationIsValid) {
      throw new IsomorphlyValidationException("no valid @Group Annotations found.");
    }

    if (!componentAnnotationIsValid) {
      throw new IsomorphlyValidationException("no valid @Component Annotations found.");
    }

  }

  private void scanAnnotatedElements() throws IsomorphlyValidationException {

    this.groupAnnotations.addAll(this.packageScanner.getGroupDefinitions());

    this.componentAnnotations.addAll(this.packageScanner.getComponentsDefinitions());

    this.methodCallContexts.addAll(this.packageScanner.getMethodCallContexts());
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
    return this.initialized;
  }


}
