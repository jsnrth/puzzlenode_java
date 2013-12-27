package international_trade;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ConversionsTest {

    @Test
    public void fetchesAGivenRate(){
        ArrayList<Rate> rates = new ArrayList<Rate>(2);
        rates.add(new Rate("FOO", "BAR", new BigDecimal("1.1")));
        rates.add(new Rate("BAR", "BAZ", new BigDecimal("0.9")));
        Conversions converter = new Conversions(rates);
        assertEquals(new BigDecimal("1.1"), converter.getConversion("FOO", "BAR"));
        assertEquals(new BigDecimal("0.9"), converter.getConversion("BAR", "BAZ"));
    }

    @Test
    public void derivesInverseRates() {
        ArrayList<Rate> rates = new ArrayList<Rate>(1);
        rates.add(new Rate("FOO", "BAR", new BigDecimal("1.1")));
        Conversions converter = new Conversions(rates);
        assertEquals(new BigDecimal("0.90909"), converter.getConversion("BAR", "FOO"));
    }

    @Test
    public void derivesMissingRates() {
        Rate fooBarRate = new Rate("FOO", "BAR", new BigDecimal(1.1));
        Rate barBazRate = new Rate("BAR", "BAZ", new BigDecimal(1.2));
        Rate bazBatRate = new Rate("BAZ", "BAT", new BigDecimal(1.3));
        Rate quxBatRate = new Rate("QUX", "BAT", new BigDecimal(1.4));
        ArrayList<Rate> rates = new ArrayList<Rate>();
        rates.add(fooBarRate);
        rates.add(barBazRate);
        rates.add(bazBatRate);
        rates.add(quxBatRate);
        Conversions converter = new Conversions(rates);

        BigDecimal fooBazConversion = fooBarRate.getConversion().multiply(barBazRate.getConversion(), Rate.MC);
        assertEquals("derives missing", fooBazConversion, converter.getConversion("FOO", "BAZ"));

        BigDecimal fooBatConversion = fooBazConversion.multiply(bazBatRate.getConversion(), Rate.MC);
        assertEquals("through several", fooBatConversion, converter.getConversion("FOO", "BAT"));

        BigDecimal fooQuxConversion = fooBatConversion.multiply(quxBatRate.toInverseRate().getConversion(), Rate.MC);
        assertEquals("through inverse", fooQuxConversion, converter.getConversion("FOO", "QUX"));
    }

    @Test(expected = ConversionRateNotFound.class)
    public void blowsUpWhenRateNotFound() {
        Conversions converter = new Conversions(new ArrayList<Rate>(0));
        converter.getConversion("FOO", "BAR");
    }

    @Test(expected = ConversionRateNotFound.class)
    public void blowsUpWhenRateImpossibleToDerive() {
        Rate fooBarRate = new Rate("FOO", "BAR", new BigDecimal(1.1));
        Rate barBazRate = new Rate("BAR", "BAZ", new BigDecimal(1.2));
        Rate quxBatRate = new Rate("QUX", "BAT", new BigDecimal(1.4));
        ArrayList<Rate> rates = new ArrayList<Rate>();
        rates.add(fooBarRate);
        rates.add(barBazRate);
        rates.add(quxBatRate);
        Conversions converter = new Conversions(rates);
        converter.getConversion("FOO", "QUX");
    }
}
