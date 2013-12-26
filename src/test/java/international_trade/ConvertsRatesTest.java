package international_trade;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ConvertsRatesTest {

    @Test
    public void fetchesAGivenRate(){
        Rate[] rates = new Rate[] {
            new Rate("FOO", "BAR", 1.1d),
            new Rate("BAR", "BAZ", 0.9d)
        };
        ConvertsRates converter = new ConvertsRates(rates);
        assertEquals((Double) 1.1d, converter.getConversion("FOO", "BAR"));
        assertEquals((Double) 0.9d, converter.getConversion("BAR", "BAZ"));
        assertNull(converter.getConversion("FOO", "WAT"));
    }

    @Test
    public void derivesInverseRates() {
        Rate[] rates = new Rate[]{
            new Rate("FOO", "BAR", 1.1d)
        };
        ConvertsRates converter = new ConvertsRates(rates);
        assertEquals((Double) 1.1d, converter.getConversion("FOO", "BAR"));
        assertEquals((Double) 0.9090909090909091d, converter.getConversion("BAR", "FOO"));
    }
}
