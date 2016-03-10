package isomorphly.reflect.scanners;

import isomorphly.IsomorphlyValidationException;
import isomorphly.annotations.Component;
import isomorphly.annotations.Group;

import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;

public class PackageScanner {

  private String[] packageNames;

  private Set<Class<?>> groups;

  private Set<Class<?>> components;

  public PackageScanner(String[] packageNames) throws IsomorphlyValidationException {
    this.packageNames = packageNames;

    this.groups = new HashSet<>();

    this.components = new HashSet<>();

    loadGroupsDefinitions();

    loadComponentsDefinitions();
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

  public Set<Class<?>> getGroupDefinitions() {
    return this.groups;
  }

  public Set<Class<?>> getComponentsDefinitions() {
    return this.components;
  }

}
