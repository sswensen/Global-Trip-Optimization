//import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
/**
 * Created by SummitDrift on 2/13/17.
 */
public class TestTripCo {
    @Before
    public void initialize() {
    }



    @Test
    public void printTest() {
        String expected = "Negro";
        String result = TripCo.printTest("FUCK");
        assertEquals(expected,result);
    }
}