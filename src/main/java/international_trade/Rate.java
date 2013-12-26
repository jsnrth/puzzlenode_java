package international_trade;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Rate {

    private String from;
    private String to;
    private BigDecimal conversion;

    public static final MathContext MC = new MathContext(5, RoundingMode.HALF_EVEN);

    public Rate(String startFrom, String startTo, BigDecimal startConversion) {
        from = startFrom;
        to = startTo;
        conversion = startConversion;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public BigDecimal getConversion() {
        return conversion;
    }

    public Rate toInverseRate() {
        BigDecimal one = new BigDecimal("1.0");
        return new Rate(to, from, one.divide(conversion, MC));
    }
}
