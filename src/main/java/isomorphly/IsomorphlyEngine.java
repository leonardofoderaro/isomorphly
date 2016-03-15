package isomorphly;

import isomorphly.reflect.IsomorphlyRegistry;
import isomorphly.reflect.scanners.PackageScanner;

import java.util.ArrayList;
import java.util.List;


public class IsomorphlyEngine {

  // elements annotated with IsomorphlyPlugin
  private List<Class<?>> isomorphlyPluginAnnotatedElements;

  private List<Class<?>> componentAnnotations;

  private String[] packageNames;

  private boolean initialized;

  private PackageScanner packageScanner;
  
  private IsomorphlyRegistry registry;

  private List<Class<?>> methodCallContexts;

  private List<Class<?>> pluginsDefinitions;

  public IsomorphlyEngine(String[] packageNames) {

    this.initialized = false;

    this.packageNames = packageNames;

    isomorphlyPluginAnnotatedElements = new ArrayList<>();

    componentAnnotations = new ArrayList<>();

    methodCallContexts = new ArrayList<>();

    pluginsDefinitions = new ArrayList<>();
    

  }

  public void init() throws IsomorphlyValidationException {

    this.packageScanner = new PackageScanner(this.packageNames);

    scanAnnotatedElements();

    boolean isomorphlyPluginsIsValid = !this.isomorphlyPluginAnnotatedElements.isEmpty();

    boolean componentAnnotationIsValid = !this.componentAnnotations.isEmpty();

    this.initialized = isomorphlyPluginsIsValid && componentAnnotationIsValid;

    if (!isomorphlyPluginsIsValid) {
      throw new IsomorphlyValidationException("no valid @Group Annotations found.");
    }

    if (!componentAnnotationIsValid) {
      throw new IsomorphlyValidationException("no valid @Component Annotations found.");
    }

    this.registry = new IsomorphlyRegistry(this.packageScanner.getValidMethodsMap());

  }

  private void scanAnnotatedElements() throws IsomorphlyValidationException {

    this.isomorphlyPluginAnnotatedElements.addAll(this.packageScanner.getIsomorphlyPlugins());

    this.componentAnnotations.addAll(this.packageScanner.getComponentsDefinitions());

    this.methodCallContexts.addAll(this.packageScanner.getMethodCallContexts());

    this.pluginsDefinitions.addAll(this.packageScanner.getPluginsDefinitions());
  }

  public final List<Class<?>> getIsomorphlyPluginAnnotatedElements() {
    return isomorphlyPluginAnnotatedElements;
  }

  public final List<Class<?>> getIsomorphlyComponents() {
    return componentAnnotations;
  }

  public final List<Class<?>> getIsomorphlyPluginsDefinitions() {
    return pluginsDefinitions;
  }

  public boolean isInitialized() {
    return this.initialized;
  }
  
  public void invokeMethod() {
    
  }


}
