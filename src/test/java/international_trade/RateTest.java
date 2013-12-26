package international_trade;

import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RateTest {

    @Test
    public void testAttributes() {
        Rate rate = new Rate("foo", "bar", new BigDecimal("5.5"));
        assertEquals("foo", rate.getFrom());
        assertEquals("bar", rate.getTo());
        assertEquals(new BigDecimal("5.5"), rate.getConversion());
    }

    @Test
    public void makesInverseRate() {
        Rate rate = new Rate("foo", "bar", new BigDecimal("5.5"));
        Rate inverseRate = rate.toInverseRate();
        assertEquals("bar", inverseRate.getFrom());
        assertEquals("foo", inverseRate.getTo());
        BigDecimal one = new BigDecimal("1.0");
        assertEquals(one.divide(new BigDecimal("5.5"), Rate.MC), inverseRate.getConversion());
    }

}
