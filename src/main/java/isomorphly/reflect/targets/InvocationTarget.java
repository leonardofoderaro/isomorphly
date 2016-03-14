package isomorphly.reflect.targets;

import java.lang.reflect.Method;

public class InvocationTarget {

  private Object instance;

  private Method method;

  public InvocationTarget(Object obj, Method method) {
    this.instance = obj;
    this.method = method;
  }

  public Object getInstance() {
    return instance;
  }

  public Method getMethod() {
    return method;
  }

}