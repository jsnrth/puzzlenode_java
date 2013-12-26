package international_trade;

public class ConvertsRates {

    private Rate[] rates;

    public ConvertsRates(Rate[] startingRates) {
        rates = withInverseRates(startingRates);
    }

    public Double getConversion(String from, String to) {
        Double conversion = null;

        for(Rate rate : rates) {
            if (rate != null && rate.getFrom() == from && rate.getTo() == to) {
                conversion = rate.getConversion();
                break;
            }
        }

        if (conversion == null) {
            int countNewRates = deriveRates(rates, from, to);
            if (countNewRates > 0)
                conversion = getConversion(from, to);
        }

        return conversion;
    }

    private Rate[] withInverseRates(Rate[] startingRates) {
        Rate[] newRates = new Rate[startingRates.length * 2];
        Rate inverseRate;
        int index = 0;
        for (Rate rate: startingRates) {
            newRates[index] = rate;
            inverseRate = rate.toInverseRate();
            if (! hasRate(newRates, inverseRate))
                newRates[index += 1] = inverseRate;
            index += 1;
        }
        return newRates;
    }

    private boolean hasRate(Rate[] rates, Rate rate) {
        for (Rate r : rates)
            if (r != null && r.isRate(rate))
                return true;
        return false;
    }

    private int deriveRates(Rate[] rates, String from, String to) {
        Rate[] derivedRates = new Rate[rates.length];
        int index = 0;
        for (Rate fromRate : rates) {
            if (fromRate != null && fromRate.getFrom() == from) {
                for (Rate toRate : rates) {
                    if (toRate != null && fromRate.getTo() == toRate.getFrom()) {
                        String newFrom = fromRate.getFrom();
                        String newTo = toRate.getTo();
                        if (newFrom != newTo) {
                            Double newConversion = fromRate.getConversion() * toRate.getConversion();
                            Rate derivedRate = new Rate(newFrom, newTo, newConversion);
                            if (!hasRate(rates, derivedRate) && !hasRate(derivedRates, derivedRate)) {
                                derivedRates[index] = derivedRate;
                                index += 1;
                            }
                        }
                    }
                }
            }
        }
        if (index > 0) {
            Rate[] newRates = new Rate[rates.length * 2];
            int nIndex = 0;
            for (Rate rate : rates) {
                if (rate != null) {
                    newRates[nIndex] = rate;
                    nIndex += 1;
                }
            }
            for (Rate derivedRate : derivedRates) {
                if (derivedRate != null) {
                    newRates[nIndex] = derivedRate;
                    nIndex += 1;
                }
            }
            this.rates = newRates;
        }

        return index;
    }

}
