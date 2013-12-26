package international_trade;

public class ConvertsRates {

    private Rate[] rates;

    public ConvertsRates(Rate[] startingRates) {
        rates = deriveInverseRates(startingRates);
    }

    public Double getConversion(String from, String to) {
        Double conversion = null;

        for(Rate rate : rates) {
            if (rate.getFrom() == from && rate.getTo() == to) {
                conversion = rate.getConversion();
                break;
            }
        }

        return conversion;
    }

    private Rate[] deriveInverseRates(Rate[] startingRates) {
        Rate[] newRates = new Rate[startingRates.length * 2];
        int index = 0;
        for (Rate rate: startingRates) {
            newRates[index] = rate;
            newRates[index + 1] = rate.toInverseRate();
            index += 2;
        }
        return newRates;
    }

}
