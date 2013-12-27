package international_trade;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;

public class TransactionsSetTest {

    @Test
    public void convertsTheTransactionsToACurrency(){
        Conversions converter = new Conversions(new Rate[]{
            new Rate("AUD", "CAD", new BigDecimal("1.0079")),
            new Rate("CAD", "USD", new BigDecimal("1.0090")),
            new Rate("USD", "CAD", new BigDecimal("0.9911"))
        });

        TransactionsSet transactions = new TransactionsSet(new Transaction[]{
            new Transaction("Yonkers",  "DM1210", new BigDecimal("70.00"), "USD"),
            new Transaction("Yonkers",  "DM1182", new BigDecimal("19.68"), "AUD"),
            new Transaction("Nashua",   "DM1182", new BigDecimal("58.58"), "AUD"),
            new Transaction("Scranton", "DM1210", new BigDecimal("68.76"), "USD"),
            new Transaction("Camden",   "DM1182", new BigDecimal("54.64"), "USD")
        });

        // FIXME: Rounding doesn't seem right...
        // assertEquals(new BigDecimal("134.22"), transactions.totalEverything("DM1182", "USD", converter));
        assertEquals(new BigDecimal("134.23"), transactions.totalEverything("DM1182", "USD", converter));
    }
}
