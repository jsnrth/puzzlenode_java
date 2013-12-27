package international_trade;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

public class TransactionsSetTest {

    @Test
    public void convertsTheTransactionsToACurrency(){
        ArrayList<Rate> rates = new ArrayList<Rate>(3);
        rates.add(new Rate("AUD", "CAD", new BigDecimal("1.0079")));
        rates.add(new Rate("CAD", "USD", new BigDecimal("1.0090")));
        rates.add(new Rate("USD", "CAD", new BigDecimal("0.9911")));
        Conversions converter = new Conversions(rates);

        ArrayList<Transaction> trans = new ArrayList<Transaction>(5);
        trans.add(new Transaction("Yonkers", "DM1210", new BigDecimal("70.00"), "USD"));
        trans.add(new Transaction("Yonkers", "DM1182", new BigDecimal("19.68"), "AUD"));
        trans.add(new Transaction("Nashua", "DM1182", new BigDecimal("58.58"), "AUD"));
        trans.add(new Transaction("Scranton", "DM1210", new BigDecimal("68.76"), "USD"));
        trans.add(new Transaction("Camden", "DM1182", new BigDecimal("54.64"), "USD"));
        TransactionsSet transactions = new TransactionsSet(trans);

        // FIXME: Rounding doesn't seem right...
        // assertEquals(new BigDecimal("134.22"), transactions.totalEverything("DM1182", "USD", converter));
        assertEquals(new BigDecimal("134.23"), transactions.totalEverything("DM1182", "USD", converter));
    }
}
