/**
 * 
 */
package permafrost.hdf.libhdf;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestStringUtils.class,
    TestBufferUtils.class,
    TestH5.class,
    TestPropertiesLib.class,
    TestFileLib.class,
    TestLiteLib.class,
    TestImageLib.class,
    TestTableLib.class,
    TestPacketTableLib.class,    
    TestObjectLib.class,    
    TestAttributeLib.class,
    TestGroupLib.class,
    TestLinkLib.class,
    TestDatatypeBuiltins.class,
    TestDatatypeLib.class,
    TestDataspaceLib.class,
    TestDatasetLib.class,
    TestFilterLib.class,
    TestIdentifierLib.class,
    TestReferenceLib.class
})
public class AllTests {
    // Nothing here.
}
