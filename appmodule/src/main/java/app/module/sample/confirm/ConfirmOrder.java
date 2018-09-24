package app.module.sample.confirm;

public class ConfirmOrder {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPaylater() {
        return paylater;
    }

    public void setPaylater(boolean paylater) {
        this.paylater = paylater;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMarketPlaceOrderId() {
        return marketPlaceOrderId;
    }

    public void setMarketPlaceOrderId(String marketPlaceOrderId) {
        this.marketPlaceOrderId = marketPlaceOrderId;
    }

    /* "id" : 10001001,   //ePayLaterOrderId
                "amount" : 100000,
                "currencyCode" : "INR",
                "date" : "2017-07-07T11:06:46Z",
                "paylater" : true,
                "status" : "delivered",
                "marketplaceOrderId":"12038474633"*/
    String id;
    long amount;
    String currencyCode = "INR";
    String date;
    boolean paylater = true;
    String status;
    String marketPlaceOrderId;
}
