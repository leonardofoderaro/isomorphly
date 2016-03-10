package isomorphly;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class IsomorphlyValidationExceptionTest extends TestCase {

	private IsomorphlyEngine engine;
	
    public static Test suite()
    {
        return new TestSuite( IsomorphlyValidationExceptionTest.class );
    }
	
    public IsomorphlyValidationExceptionTest( String testName )
    {
        super( testName );
        String packageNames[] = {"test.isomorphly.dummy.invalid.annotations"};
        engine = new IsomorphlyEngine(packageNames);	
    }
    
    public void testValidationException() {
        try {
			engine.init();
		} catch (IsomorphlyValidationException e) {
            assertFalse("isInitialized should be False", engine.isInitialized());
			return;
		}
        
        assertTrue("It should raise an IsomorphlyValidationException", false);
    }
    
    public void testEmptyAnnotations() throws Exception {
    	String[] packageNames = {"test.isomorphly.dummy.empty"};
    	IsomorphlyEngine emptyAnnotationsEngine = new IsomorphlyEngine(packageNames);
        try {
        	emptyAnnotationsEngine.init();
		} catch (IsomorphlyValidationException e) {
            assertFalse("isInitialized should be False", engine.isInitialized());
			return;
		}
        
        throw new Exception("It should raise an IsomorphlyValidationException");
    }
    
    public void testMissingAnnotations()
    {
        String packageNames[] = {"test.isomorphly.invalid.one.annotations"};
        engine = new IsomorphlyEngine(packageNames);	
        try {
			engine.init();
		} catch (IsomorphlyValidationException e) {
			assertFalse("it should be false", engine.isInitialized());
			return;
		}
        assertTrue("it should have raise an exception", false);
    }
    
    public void testMissingAnnotations2()
    {
        String packageNames[] = {"test.isomorphly.invalid.two"};
        engine = new IsomorphlyEngine(packageNames);	
        try {
			engine.init();
		} catch (IsomorphlyValidationException e) {
			assertFalse("it should be false", engine.isInitialized());
			return;
		}
        assertTrue("it should have raise an exception", false);
    }
    
    
    public void testMissingAnnotations3()
    {
        String packageNames[] = {"test.isomorphly.invalid.three"};
        engine = new IsomorphlyEngine(packageNames);	
        try {
			engine.init();
		} catch (IsomorphlyValidationException e) {
			assertFalse("it should be false", engine.isInitialized());
			return;
		}
        assertTrue("it should have raise an exception", false);
    }
    
    
    public void testMissingAnnotations4()
    {
        String packageNames[] = {"test.isomorphly.invalid.four"};
        engine = new IsomorphlyEngine(packageNames);	
        try {
			engine.init();
		} catch (IsomorphlyValidationException e) {
			assertFalse("it should be false", engine.isInitialized());
			return;
		}
        assertTrue("it should have raise an exception", false);
    }



}
