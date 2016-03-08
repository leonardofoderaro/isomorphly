package isomorphly;

import isomorphly.annotations.Component;
import isomorphly.annotations.Group;

import java.lang.annotation.Annotation;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for IsomorphlyEngine.
 */
public class IsomorphlyEngineTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
	private IsomorphlyEngine engine;
	
    public IsomorphlyEngineTest( String testName )
    {
        super( testName );
        String packageNames[] = {"test.isomorphly.dummy.annotations", "test.isomorphly.dummy.implementation"};
        engine = new IsomorphlyEngine(packageNames);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( IsomorphlyEngineTest.class );
    }

    public void testIsomorphlyEngineInstance()
    {
        assertTrue( engine != null );
    }
    
    public void testEngineInit() throws IsomorphlyValidationException {
        engine.init();

        assertTrue("engine not properly initialized.", engine.isInitialized());
    }

    public void testGroupScan() throws IsomorphlyValidationException
    {
		engine.init();
    	
    	List<Class<?>> groups = engine.getIsomorphlyGroups();
    	
    	assertNotNull("Groups should not null. Is engine's initialization ok?", groups);

    	assertFalse("Groups should not be empty. Is engine's initialization ok?", groups.isEmpty());
    	
    	for (Class<?> cls : groups) {
    		assertTrue(cls.isAnnotation());
    		assertTrue(cls.isAnnotationPresent(Group.class));
    	}

    	assertTrue(true);
    	assertTrue("isInitalized should be true", engine.isInitialized());
    }
    
    public void testComponentsScan() throws IsomorphlyValidationException
    {
		engine.init();
    	
    	List<Class<?>> components = engine.getIsomorphlyComponents();
    	
    	assertNotNull("Components should not null. Is engine's initialization ok?", components);

    	assertTrue("Components should not be empty. Is engine's initialization ok?", components.size() > 0);
    	
    	for (Class<?> cls : components) {
    		assertTrue(cls.isAnnotation());
    		assertTrue(cls.isAnnotationPresent(Component.class));
    	}

    	assertTrue(true);
    	assertTrue("isInitalized should be true", engine.isInitialized());
    }
    
    public void testPluginsScan() throws IsomorphlyValidationException
    {
		engine.init();
    	
    	List<Class<?>> annotatedClientGroups = engine.getIsomorphlyClientGroups();

    	assertFalse("AnnotatedClientGroups should not be empty. Is engine's initialization ok?", annotatedClientGroups.isEmpty());
    
    	for (Class<?> cls : annotatedClientGroups) {
    		assertFalse("AnnotatedClient classes cannot be Interfaces", cls.isInterface());
    		assertFalse("AnnotatedClient classes cannot be Annotations", cls.isAnnotation());
    		
    		boolean foundValidParentGroupAnnotation = false;
    		
    		for (Annotation a : cls.getAnnotations()) {
    			foundValidParentGroupAnnotation |= a.annotationType().isAnnotationPresent(Group.class);
    		} 
    		
    		assertTrue("Unable to find a valid @Group Annotation in " + cls.getName(), foundValidParentGroupAnnotation);
    		
    	}

    	assertTrue(engine != null);
    	assertTrue("isInitalized should be true", engine.isInitialized());
    }
  
    public void functionScanTest() throws IsomorphlyValidationException
    {
		engine.init();
    	
    	List<Class<?>> annotatedClientGroups = engine.getIsomorphlyClientGroups();

    	assertFalse("AnnotatedClientGroups should not be empty. Is engine's initialization ok?", annotatedClientGroups.isEmpty());
    
    	for (Class<?> cls : annotatedClientGroups) {
    		assertFalse("AnnotatedClient classes cannot be Interfaces", cls.isInterface());
    		assertFalse("AnnotatedClient classes cannot be Annotations", cls.isAnnotation());
    		
    		boolean foundValidParentGroupAnnotation = false;
    		
    		for (Annotation a : cls.getAnnotations()) {
    			foundValidParentGroupAnnotation |= a.annotationType().isAnnotationPresent(Group.class);
    		} 
    		
    		assertTrue("Unable to find a valid @Group Annotation in " + cls.getName(), foundValidParentGroupAnnotation);
    		
    	}

    	assertTrue(engine != null);
    	assertTrue("isInitalized should be true", engine.isInitialized());
    }
    

    
    
    
}
