/**
 * 
 */
package permafrost.hdf.basic.impl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestAbstractGroup.class,
    TestCleaner.class,
    TestFileBuilder.class,
    TestGroupIterator.class,
    TestAttributeIterator.class,
    TestAttribute.class,
    TestAttributeBuilder.class,
    TestDataset.class,
    TestDatasetBuilder.class,
    TestStructTypeBuilder.class
})
public class AllTests {
    // Nothing here.
}
