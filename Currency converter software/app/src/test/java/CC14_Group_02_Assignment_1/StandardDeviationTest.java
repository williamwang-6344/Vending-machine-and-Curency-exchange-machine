package CC14_Group_02_Assignment_1;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class StandardDeviationTest {

    @Test
    public void calculateSDTestValid() {
        ArrayList<Float> numArray = new ArrayList<>() {
            {
                add(1f);
                add(2f);
                add(3f);
                add(4f);
                add(5f);
            }
        };
        StandardDeviation standardDeviation = new StandardDeviation(numArray);
        assertNotNull(standardDeviation);
        assertEquals(1.4142135f, StandardDeviation.calculateSD(numArray));
    }

}
