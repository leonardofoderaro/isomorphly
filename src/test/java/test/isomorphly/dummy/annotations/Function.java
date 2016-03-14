package test.isomorphly.dummy.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import isomorphly.annotations.Component;

@Component
@Retention(RetentionPolicy.RUNTIME)
public @interface Function {

}