/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahian.conway;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/** Unit test for simple App. */
public class ConwayTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ConwayTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(ConwayTest.class);
    }

    /** Rigourous Test :-) */
    public void testApp() {
        assertTrue(true);
    }
}
