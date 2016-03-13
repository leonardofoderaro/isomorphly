package isomorphly.reflect.scanners;

import isomorphly.IsomorphlyValidationException;
import isomorphly.annotations.CallContext;
import isomorphly.annotations.Component;
import isomorphly.annotations.IsomorphlyPlugin;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;

public class PackageScanner {

  private String[] packageNames;

  private Set<Class<?>> isomorphlyPlugins;

  private Set<Class<?>> components;

  private Set<Class<?>> methodCallContexts;

  private Set<Class<?>> pluginClasses;

  public PackageScanner(String[] packageNames) throws IsomorphlyValidationException {
    this.packageNames = packageNames;

    this.isomorphlyPlugins = new HashSet<>();

    this.components = new HashSet<>();

    this.methodCallContexts = new HashSet<>();

    this.pluginClasses = new HashSet<>();

    loadIsomorphlyPluginElements();

    loadComponentsDefinitions();

    loadMethodCallContexts();

    loadPluginsImplementations();
  }

  private void loadIsomorphlyPluginElements() throws IsomorphlyValidationException {


    for (String pkgName : packageNames) {

      Reflections reflections = new Reflections(pkgName);

      Set<Class<?>> foundPlugins = reflections.getTypesAnnotatedWith(IsomorphlyPlugin.class);

      for (Class<?> c : foundPlugins) {
        if (c.isAnnotation()) {
          isomorphlyPlugins.add(c);
        } else {
          throw new IsomorphlyValidationException("@IsomorphlyPlugin Annotation can be used only on Annotations.");
        }
      }

    }

  }


  private void loadComponentsDefinitions() throws IsomorphlyValidationException {

    for (String pkgName : packageNames) {

      Reflections reflections = new Reflections(pkgName);

      Set<Class<?>> foundComponents = reflections.getTypesAnnotatedWith(Component.class);
      for (Class<?> c : foundComponents) {
        if (c.isAnnotation()) {
          components.add(c);
        } else {
          throw new IsomorphlyValidationException("@Group Annotation can be used only on Annotations.");
        }
      }

    }

  }

  private void loadMethodCallContexts() throws IsomorphlyValidationException {

    for (String pkgName : packageNames) {

      Reflections reflections = new Reflections(pkgName);

      Set<Class<?>> foundMethodCallContexts = reflections.getTypesAnnotatedWith(CallContext.class);
      for (Class<?> c : foundMethodCallContexts) {
        if (c.isAnnotation()) {
          methodCallContexts.add(c);
        } else {
          throw new IsomorphlyValidationException("@CallContext Annotation can be used only on Annotations.");
        }
      }

    }

  }

  private void loadPluginsImplementations() throws IsomorphlyValidationException {

    HashSet<Class<?>> tmpPlugins = new HashSet<>();

    for (String pkgName : this.packageNames) {
      Reflections reflections = new Reflections(pkgName);

      for (Class<?> groupAnnotation : this.isomorphlyPlugins) {
        @SuppressWarnings("unchecked")
        Set<Class<?>> foundPluginClasses = reflections.getTypesAnnotatedWith((Class<? extends Annotation>) groupAnnotation);
        tmpPlugins.addAll(foundPluginClasses);
      }
    }

    for (Class<?> cls : tmpPlugins) {
      if (!cls.isInterface()) {
        if (!cls.isAnnotation()) {
          pluginClasses.add(cls);
        }
      } else {
        throw new IsomorphlyValidationException("Plugins must be implemented in classes.");
      }
    }


  }

  public Set<Class<?>> getIsomorphlyPlugins() {
    return this.isomorphlyPlugins;
  }

  public Set<Class<?>> getComponentsDefinitions() {
    return this.components;
  }

  public Set<Class<?>> getMethodCallContexts() {
    return this.methodCallContexts;
  }

  public Set<Class<?>> getPluginsDefinitions() {
    return this.pluginClasses;
  }

}
