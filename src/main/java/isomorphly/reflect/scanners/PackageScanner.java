package isomorphly.reflect.scanners;

import isomorphly.IsomorphlyValidationException;
import isomorphly.annotations.CallContext;
import isomorphly.annotations.Component;
import isomorphly.annotations.IsomorphlyPlugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

public class PackageScanner {

  private String[] packageNames;

  private Map<String, Class<?>> isomorphlyPlugins;

  private Map<String, Class<?>> components;

  private Set<Class<?>> methodCallContexts;

  private Set<Class<?>> pluginClasses;

  // TODO explain better
  // key: classname, value: list of methods
  private Map<String, Map<String, Method>> validMethodsMap;

  public PackageScanner(String[] packageNames) throws IsomorphlyValidationException {
    this.packageNames = packageNames;

    this.isomorphlyPlugins = new HashMap<>();

    this.components = new HashMap<>();

    this.methodCallContexts = new HashSet<>();

    this.pluginClasses = new HashSet<>();

    this.validMethodsMap = new HashMap<>();

    // step 1 - annotations used to identify plugins in the guest environment
    loadIsomorphlyPluginElements();

    // step 2 - annotations used to identify functions in the guest environment 
    loadComponentsDefinitions();

    // step 3 - classes encapsulating all the guest-specific plugin execution parameters
    loadMethodCallContexts();

    // step 4 - classes in the guest-space annotated with annotations defined at step 1
    loadPluginsImplementations();

    /* step 5 - methods in the guest-space belongings to classes identified at step 4
       annotated with the annotations defined at step 2 */
    loadAnnotatedMethods();
  }


  private void loadIsomorphlyPluginElements() throws IsomorphlyValidationException {


    for (String pkgName : packageNames) {

      Reflections reflections = new Reflections(pkgName);

      Set<Class<?>> foundPlugins = reflections.getTypesAnnotatedWith(IsomorphlyPlugin.class);

      for (Class<?> c : foundPlugins) {
        if (c.isAnnotation()) {
          isomorphlyPlugins.put(c.getName(), c);
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
          components.put(c.getName(), c);
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

    Map<String, Class<?>> tmpPlugins = new HashMap<>();

    for (String pkgName : this.packageNames) {
      Reflections reflections = new Reflections(pkgName);

      for (String groupAnnotationKey : this.isomorphlyPlugins.keySet()) {
        @SuppressWarnings("unchecked")
        Set<Class<?>> foundPluginClasses = reflections.getTypesAnnotatedWith((Class<? extends Annotation>) this.isomorphlyPlugins.get(groupAnnotationKey));
        for (Class<?> cls : foundPluginClasses) {
          tmpPlugins.put(cls.getName(), cls);
        }
      }
    }

    for (String k : tmpPlugins.keySet()) {
      Class<?> cls = tmpPlugins.get(k);
      if (!cls.isInterface()) {
        if (!cls.isAnnotation()) {
          pluginClasses.add(cls);
        }
      } else {
        throw new IsomorphlyValidationException("Plugins must be implemented in classes.");
      }
    }
  }


  private void loadAnnotatedMethods() throws IsomorphlyValidationException {

    Map<String, Method> tmpMethodsMap = null;
    for (Class<?> c : this.pluginClasses) {
      // TODO add a cache to avoid rescanning same un-annotated methods for every class (e.g. methods from Object class)
      tmpMethodsMap = this.validMethodsMap.get(c.getName());

      if (tmpMethodsMap == null) {
        tmpMethodsMap = new HashMap<String, Method>();
      }

      for (Method m : c.getMethods()) {
        Annotation[] methodAnnotations = m.getAnnotations();
        for (Annotation a : methodAnnotations) {
          // TODO Component annotation must be refactored (as per IsomorphlyPlugin)
          if (a.annotationType().isAnnotationPresent(Component.class)) {
            //TODO first method's param MUST be a MethodCallContext object
            Parameter[] methodParams = m.getParameters();

            if (methodParams.length==0) {
              throw new IsomorphlyValidationException("annotated methods must accept at least 1 parameter of type callcontext");
            }
            
            Class<?> firstParamType = methodParams[0].getType();
            
            Annotation[] firstParamTypeAnnotations = firstParamType.getAnnotations();
            
            if (firstParamTypeAnnotations.length == 0) {
              throw new IsomorphlyValidationException("the first parameter of an annotated method must be of a type annotate with CallContext annotation.");
            }

            boolean foundValidParamTypeAnnotation = false;
            for (Annotation methodAnnotation : firstParamTypeAnnotations) {
              Class<?> annotationType = methodAnnotation.annotationType();
              
              if (annotationType.isAnnotationPresent(CallContext.class)) {
                foundValidParamTypeAnnotation = true;
              }
            }
            
            if (!foundValidParamTypeAnnotation) {
              throw new IsomorphlyValidationException("the first parameter of an annotated method must be of a type annotated with CallContext annotation.");
            }

            tmpMethodsMap.put(m.getName(), m);
          }
        }

      }
      
      this.validMethodsMap.put(c.getName(), tmpMethodsMap);
    }
  }

  public Map<String, Class<?>> getIsomorphlyPlugins() {
    return this.isomorphlyPlugins;
  }

  public Map<String, Class<?>> getComponentsDefinitions() {
    return this.components;
  }

  public Set<Class<?>> getMethodCallContexts() {
    return this.methodCallContexts;
  }

  public Set<Class<?>> getPluginsDefinitions() {
    return this.pluginClasses;
  }
  
  public Map<String, Map<String, Method>> getValidMethodsMap() {
    return this.validMethodsMap;
  }

}
