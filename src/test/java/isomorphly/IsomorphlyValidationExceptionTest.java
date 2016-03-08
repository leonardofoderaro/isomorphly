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
    
    public void testEmptyAnnotations() {
        try {
			engine.init();
		} catch (IsomorphlyValidationException e) {
            assertFalse("isInitialized should be False", engine.isInitialized());
			return;
		}
        
        assertTrue("It should raise an IsomorphlyValidationException", false);
    }

}
