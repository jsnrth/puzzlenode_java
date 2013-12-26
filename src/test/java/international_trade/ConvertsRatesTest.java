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
        assertEquals((Double) 0.9090909090909091d, converter.getConversion("BAR", "FOO"));
    }

    @Test
    public void derivesMissingRates() {
        Rate[] rates = new Rate[] {
            new Rate("FOO", "BAR", 1.1d),
            new Rate("BAR", "BAZ", 1.2d),
            new Rate("BAZ", "BAT", 1.3d),
            new Rate("QUX", "BAT", 1.4d)
        };
        ConvertsRates converter = new ConvertsRates(rates);
        assertEquals("derives missing", (Double) (1.1d * 1.2d), converter.getConversion("FOO", "BAZ"));
        assertEquals("through several", (Double) (1.1d * 1.2d * 1.3d), converter.getConversion("FOO", "BAT"));
        assertEquals("through inverse", (Double) (1.1d * 1.2d * 1.3d * (1.0d / 1.4d)), converter.getConversion("FOO", "QUX"));
    }
}
