package international_trade;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RateTest {

    @Test
    public void testAttributes() throws Exception {
        Rate rate = new Rate("foo", "bar", 5.5);
        assertEquals("foo", rate.getFrom());
        assertEquals("bar", rate.getTo());
        assertEquals((Object) 5.5, rate.getConversion());
    }

}
