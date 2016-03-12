package isomorphly.reflect.scanners;

import isomorphly.IsomorphlyValidationException;
import isomorphly.annotations.CallContext;
import isomorphly.annotations.Component;
import isomorphly.annotations.Group;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;

public class PackageScanner {

  private String[] packageNames;

  private Set<Class<?>> groups;

  private Set<Class<?>> components;

  private Set<Class<?>> methodCallContexts;

  private Set<Class<?>> pluginClasses;

  public PackageScanner(String[] packageNames) throws IsomorphlyValidationException {
    this.packageNames = packageNames;

    this.groups = new HashSet<>();

    this.components = new HashSet<>();

    this.methodCallContexts = new HashSet<>();

    this.pluginClasses = new HashSet<>();

    loadGroupsDefinitions();

    loadComponentsDefinitions();

    loadMethodCallContexts();

    loadPluginsImplementations();
  }

  private void loadGroupsDefinitions() throws IsomorphlyValidationException {


    for (String pkgName : packageNames) {

      Reflections reflections = new Reflections(pkgName);

      Set<Class<?>> foundGroups = reflections.getTypesAnnotatedWith(Group.class);

      for (Class<?> c : foundGroups) {
        if (c.isAnnotation()) {
          groups.add(c);
        } else {
          throw new IsomorphlyValidationException("@Group Annotation can be used only on Annotations.");
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

      for (Class<?> groupAnnotation : this.groups) {
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

  public Set<Class<?>> getGroupDefinitions() {
    return this.groups;
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
