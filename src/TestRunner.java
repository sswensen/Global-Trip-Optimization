<<<<<<< HEAD
=======
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
>>>>>>> master
/**
 * Created by SummitDrift on 2/13/17.
 */

<<<<<<< HEAD
import Model.TestLocationFactory;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(JunitTestSuite.class);
=======
public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestTripCo.class);
>>>>>>> master

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}
