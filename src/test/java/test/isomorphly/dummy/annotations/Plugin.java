package test.isomorphly.dummy.annotations;

import isomorphly.annotations.IsomorphlyPlugin;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IsomorphlyPlugin
@Retention(RetentionPolicy.RUNTIME)
public @interface Plugin {


}
