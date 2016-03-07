package test.isomorphly.dummy.annotations;

import isomorphly.annotations.Group;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Group
@Retention(RetentionPolicy.RUNTIME)
public @interface Plugin {

}
