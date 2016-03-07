package isomorphly;

import isomorphly.annotations.Component;
import isomorphly.annotations.Group;

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
        String packageNames[] = {"test.isomorphly.dummy.annotations"};
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
    
    public void testEngineInit() {
        engine.init();
        assertTrue("engine not properly initialized.", engine.isInitialized());
    }

    public void testGroupScan()
    {
    	engine.init();
    	
    	List<Class<?>> groups = engine.getIsomorphlyGroups();
    	
    	assertNotNull("Groups should not null. Is engine's initialization ok?", groups);

    	assertTrue("Groups should not be empty. Is engine's initialization ok?", groups.size() > 0);
    	
    	for (Class<?> cls : groups) {
    		assertTrue(cls.isAnnotation());
    		assertTrue(cls.isAnnotationPresent(Group.class));
    	}

    	assertTrue(true);
    }
    
    public void testComponentsScan()
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
    }
    
    public void testMethodsScan()
    {
    	/*
    	engine.init();
    	
    	List<Class<?>> groups = engine.getIsomorphlyGroups();
    	
    	for (Class<?> cls : groups) {
    		assertTrue(cls.isAnnotation());
    		assertTrue(cls.isAnnotationPresent(Group.class));
    	}
*/
    	assertTrue(engine != null);
    }
    
    
    
}
