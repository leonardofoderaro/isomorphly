package isomorphly.reflect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class IsomorphlyRegistry {

  private Map<String, Map<String, Method>> methodsRegistry;

  public IsomorphlyRegistry() {
    this.methodsRegistry = new HashMap<>();
  }

  public void addMethod(Method method) {
    Map<String, Method> methodsMap = methodsRegistry.get(method.getClass().getName());

    if (methodsMap == null) {
      methodsMap = new HashMap<>();
    }

    Method m = methodsMap.get(method.getName());

    if (m != null) {
      // TODO method already exists. so what?!
    }

    methodsMap.put(method.getName(), method);

    methodsRegistry.put(method.getClass().getName(), methodsMap);
  }
}
