package international_trade;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Conversions {

    private ArrayList<Rate> rates;

    public Conversions(ArrayList<Rate> startingRates) {
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

    private ArrayList<Rate> withInverseRates(ArrayList<Rate> startingRates) {
        ArrayList<Rate> newRates = new ArrayList<Rate>(startingRates.size());
        Rate inverseRate;
        for (Rate rate: startingRates) {
            newRates.add(rate);
            inverseRate = rate.toInverseRate();
            if (! hasRate(newRates, inverseRate))
                newRates.add(inverseRate);
        }
        return newRates;
    }

    private boolean hasRate(ArrayList<Rate> rates, Rate rate) {
        for (Rate r : rates)
            if (r != null && isSameRate(r, rate))
                return true;
        return false;
    }

    private boolean isSameRate(Rate rate1, Rate rate2) {
        return rate1.getFrom() == rate2.getFrom() && rate1.getTo() == rate2.getTo();
    }

    private int deriveRates(ArrayList<Rate> rates, String from, String to) {
        ArrayList<Rate> derivedRates = new ArrayList<Rate>(rates.size());
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
                                derivedRates.add(derivedRate);
                                index += 1;
                            }
                        }
                    }
                }
            }
        }
        if (index > 0) {
            ArrayList<Rate> newRates = new ArrayList<Rate>(rates.size());
            int nIndex = 0;
            for (Rate rate : rates) {
                if (rate != null) {
                    newRates.add(rate);
                    nIndex += 1;
                }
            }
            for (Rate derivedRate : derivedRates) {
                if (derivedRate != null) {
                    newRates.add(derivedRate);
                    nIndex += 1;
                }
            }
            this.rates = newRates;
        }

        return index;
    }

}
