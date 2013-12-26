package international_trade;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

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

    @Test
    public void equality(){
        Rate rate1 = new Rate("FOO", "BAR", 1.0d);
        Rate rate2 = new Rate("FOO", "BAR", 1.1d);
        Rate rate3 = new Rate("BAR", "BAZ", 1.2d);
        assertTrue(rate1.isRate(rate2));
        assertFalse(rate2.isRate(rate3));
    }

}
