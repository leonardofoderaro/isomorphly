package test.isomorphly.invalid.six.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import isomorphly.annotations.Component;

@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Function {

}