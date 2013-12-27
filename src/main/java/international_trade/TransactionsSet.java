package international_trade;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

public class TransactionsSet {

    private ArrayList<Transaction> transactions;

    public static final MathContext MC = new MathContext(5, RoundingMode.HALF_EVEN);

    public TransactionsSet(ArrayList<Transaction> ts){
        transactions = ts;
    }

    public BigDecimal totalEverything(String sku, String currency, Conversions converter) {
        BigDecimal total = new BigDecimal("0.0");
        for (Transaction t : transactions) {
            if(t != null && t.getSku() == sku) {
                if(t.getCurrency() == currency) {
                    total = total.add(t.getAmount(), MC);
                }
                else {
                    BigDecimal conversionRate = converter.getConversion(t.getCurrency(), currency);
                    BigDecimal convertedAmount = t.getAmount().multiply(conversionRate, Rate.MC);
                    total = total.add(convertedAmount, MC);
                }
            }
        }
        return total.round(MC);
    }
}
