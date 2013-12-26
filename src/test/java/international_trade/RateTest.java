package international_trade;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RateTest {

    @Test
    public void testAttributes() {
        Rate rate = new Rate("foo", "bar", 5.5d);
        assertEquals("foo", rate.getFrom());
        assertEquals("bar", rate.getTo());
        assertEquals((Double) 5.5d, rate.getConversion());
    }

    @Test
    public void makesInverseRate() {
        Rate rate = new Rate("foo", "bar", 5.5d);
        Rate inverseRate = rate.toInverseRate();
        assertEquals("bar", inverseRate.getFrom());
        assertEquals("foo", inverseRate.getTo());
        assertEquals((Double) (1.0d / 5.5d), inverseRate.getConversion());
    }

}
