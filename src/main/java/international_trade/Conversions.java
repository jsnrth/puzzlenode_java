package international_trade;

import java.math.BigDecimal;

public class Conversions {

    private Rate[] rates;

    public Conversions(Rate[] startingRates) {
        rates = withInverseRates(startingRates);
    }

    public BigDecimal getConversion(String from, String to) {
        BigDecimal conversion = null;

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

        if (conversion != null) {
            return conversion;
        }
        else {
            throw new ConversionRateNotFound();
        }
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
            if (r != null && isSameRate(r, rate))
                return true;
        return false;
    }

    private boolean isSameRate(Rate rate1, Rate rate2) {
        return rate1.getFrom() == rate2.getFrom() && rate1.getTo() == rate2.getTo();
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
                            BigDecimal newConversion = fromRate.getConversion().multiply(toRate.getConversion(), Rate.MC);
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
