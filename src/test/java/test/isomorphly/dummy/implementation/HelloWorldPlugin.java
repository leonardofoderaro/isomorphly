package test.isomorphly.dummy.implementation;

import test.isomorphly.dummy.annotations.Function;
import test.isomorphly.dummy.annotations.Plugin;

@Plugin
public class HelloWorldPlugin {

	@Function
	private void hello(MethodContextObject obj) {
		System.out.println("hello! - " + obj.n);
	}
}
