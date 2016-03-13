package isomorphly;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for PackageScanner
 */
public class PackageScannerTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
	private IsomorphlyEngine engine;
	
    public PackageScannerTest( String testName )
    {
        super( testName );
        String packageNames[] = {"test.isomorphly.dummy.annotations", "test.isomorphly.dummy.implementation"};
        engine = new IsomorphlyEngine(packageNames);
    }

    public static Test suite()
    {
        return new TestSuite( PackageScannerTest.class );
    }
    
    public void testGeneric() {
    	assertTrue("should be != null", this.engine != null);
    }

    
    
}
