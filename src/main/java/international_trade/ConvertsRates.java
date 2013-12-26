package international_trade;

public class ConvertsRates {

    private Rate[] rates;

    public ConvertsRates(Rate[] startingRates) {
        rates = startingRates;
    }

    public Double getConversion(String from, String to) {
        for(Rate rate : rates) {
            if (rate.getFrom() == from && rate.getTo() == to) {
                return rate.getConversion();
            }
        }
        return null;
    }

}
