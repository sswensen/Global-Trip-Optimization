/**
 * Created by SummitDrift on 2/13/17.
 */

import Model.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//JUnit Suite Test
@RunWith(Suite.class)

@Suite.SuiteClasses({
        TestModel.class, TestLocationFactory.class, TestLocation.class
})

public class JunitTestSuite {
}
