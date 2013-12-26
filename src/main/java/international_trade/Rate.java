package international_trade;

public class Rate {
    private String from;
    private String to;
    private Double conversion;

    public Rate(String startFrom, String startTo, Double startConversion) {
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

    public Double getConversion() {
        return conversion;
    }

    public Rate toInverseRate() {
        return new Rate(to, from, (1.0d / conversion));
    }
}
