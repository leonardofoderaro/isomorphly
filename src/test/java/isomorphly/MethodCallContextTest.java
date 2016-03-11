package isomorphly;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class MethodCallContextTest extends TestCase {

  public static Test suite()
  {
    return new TestSuite( MethodCallContextTest.class );
  }

  public MethodCallContextTest( String testName )
  {
    super( testName );
  }

  public void testInvalidMethodCallContextAnnotation() {
    IsomorphlyEngine engine;

    String packageNames[] = {"test.isomorphly.dummy.invalid.four.annotations"};

    engine = new IsomorphlyEngine(packageNames);	

    try {
      engine.init();
    } catch (IsomorphlyValidationException e) {
      assertFalse("isInitialized should be False", engine.isInitialized());
      return;
    }

    assertTrue("It should raise an IsomorphlyValidationException", false);
  }

  public void testValidMethodCallContextAnnotation() throws IsomorphlyValidationException {
    IsomorphlyEngine engine;

    String packageNames[] = {"test.isomorphly.dummy.annotations"};

    engine = new IsomorphlyEngine(packageNames);        

    engine.init();

  }

}
