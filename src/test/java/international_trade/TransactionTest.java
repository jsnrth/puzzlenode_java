package international_trade;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;

public class TransactionTest {

    @Test
    public void attributes(){
        Transaction t = new Transaction("FOO", "BAR", new BigDecimal("5.1"), "BAT");
        assertEquals("FOO", t.getStore());
        assertEquals("BAR", t.getSku());
        assertEquals(new BigDecimal("5.1"), t.getAmount());
        assertEquals("BAT", t.getCurrency());
    }
}
