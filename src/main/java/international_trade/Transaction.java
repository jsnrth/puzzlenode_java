package international_trade;

import java.math.BigDecimal;

public class Transaction {

    private String store;
    private String sku;
    private BigDecimal amount;
    private String currency;

    public Transaction(String store, String sku, BigDecimal amount, String currency) {
        this.store = store;
        this.sku = sku;
        this.amount = amount;
        this.currency = currency;
    }

    public String getStore() {
        return store;
    }

    public String getSku() {
        return sku;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}
