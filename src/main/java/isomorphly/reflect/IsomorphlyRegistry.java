package isomorphly.reflect;

import isomorphly.IsomorphlyValidationException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class IsomorphlyRegistry {

  private Map<String, Map<String, Method>> methods;
  
  private Map<String, Object> instances;

  public IsomorphlyRegistry(Map<String, Map<String, Method>> map) throws IsomorphlyValidationException {
    this.methods = new HashMap<>();
    
    this.instances = new HashMap<>();
    
    for (String className : map.keySet()) {
      try {
        Class<?> cls = Class.forName(className);
        Object obj = cls.newInstance();
        instances.put(className, obj);
      } catch (ClassNotFoundException e) {
        throw new IsomorphlyValidationException(e.getMessage());
      } catch (InstantiationException e) {
        throw new IsomorphlyValidationException(e.getMessage());
      } catch (IllegalAccessException e) {
        throw new IsomorphlyValidationException(e.getMessage());
      }
      
      Map<String, Method> annotatedMethods = map.get(className);

      // methods currently registered
      Map<String, Method> registeredMethods = methods.get(className);
      
      if (registeredMethods == null) {
        registeredMethods = new HashMap<>();
      }
      
      for (String methodName : annotatedMethods.keySet()) {
        registeredMethods.put(methodName, annotatedMethods.get(methodName));
      }
      
      methods.put(className, registeredMethods);

    }

  }

}
