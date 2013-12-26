package international_trade;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ConvertsRatesTest {

    @Test
    public void fetchesAGivenRate(){
        Rate[] rates = new Rate[] {
            new Rate("FOO", "BAR", new BigDecimal("1.1")),
            new Rate("BAR", "BAZ", new BigDecimal("0.9"))
        };
        ConvertsRates converter = new ConvertsRates(rates);
        assertEquals(new BigDecimal("1.1"), converter.getConversion("FOO", "BAR"));
        assertEquals(new BigDecimal("0.9"), converter.getConversion("BAR", "BAZ"));
        assertNull(converter.getConversion("FOO", "WAT"));
    }

    @Test
    public void derivesInverseRates() {
        Rate[] rates = new Rate[]{
            new Rate("FOO", "BAR", new BigDecimal("1.1"))
        };
        ConvertsRates converter = new ConvertsRates(rates);
        assertEquals(new BigDecimal("0.90909"), converter.getConversion("BAR", "FOO"));
    }

    @Test
    public void derivesMissingRates() {
        Rate fooBarRate = new Rate("FOO", "BAR", new BigDecimal(1.1));
        Rate barBazRate = new Rate("BAR", "BAZ", new BigDecimal(1.2));
        Rate bazBatRate = new Rate("BAZ", "BAT", new BigDecimal(1.3));
        Rate quxBatRate = new Rate("QUX", "BAT", new BigDecimal(1.4));
        Rate[] rates = new Rate[] { fooBarRate, barBazRate, bazBatRate, quxBatRate };
        ConvertsRates converter = new ConvertsRates(rates);

        BigDecimal fooBazConversion = fooBarRate.getConversion().multiply(barBazRate.getConversion(), Rate.MC);
        assertEquals("derives missing", fooBazConversion, converter.getConversion("FOO", "BAZ"));

        BigDecimal fooBatConversion = fooBazConversion.multiply(bazBatRate.getConversion(), Rate.MC);
        assertEquals("through several", fooBatConversion, converter.getConversion("FOO", "BAT"));

        BigDecimal fooQuxConversion = fooBatConversion.multiply(quxBatRate.toInverseRate().getConversion(), Rate.MC);
        assertEquals("through inverse", fooQuxConversion, converter.getConversion("FOO", "QUX"));
    }
}
