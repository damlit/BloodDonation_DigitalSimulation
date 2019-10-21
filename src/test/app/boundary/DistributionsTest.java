package app.boundary;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DistributionsTest {

    private static final int FIRST_VALUE = 1234;
    private static final double FIRST_RESULT = 0.34;

    private static final int SECOND_VALUE = 712371323;
    private static final double SECOND_RESULT = 0.23;

    private static final double RELATIVE_ERROR = 0.01;

    @Test
    public void shouldCalculateUniformFromValue(){
        assertEquals(FIRST_RESULT, Distributions.getUniformFromValue(FIRST_VALUE), RELATIVE_ERROR);
        assertEquals(SECOND_RESULT, Distributions.getUniformFromValue(SECOND_VALUE), RELATIVE_ERROR);
    }
}
