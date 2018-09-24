package app.module.sample.refund;

public class Refund {

    String marketplaceOrderId;
    long returnAmount;
    String returnAcceptedDate;
    String returnShipmentReceivedDate;
    String refundDate;
    String returnType;

    public String getMarketplaceOrderId() {
        return marketplaceOrderId;
    }

    public void setMarketplaceOrderId(String marketplaceOrderId) {
        this.marketplaceOrderId = marketplaceOrderId;
    }

    public long getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(long returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getReturnAcceptedDate() {
        return returnAcceptedDate;
    }

    public void setReturnAcceptedDate(String returnAcceptedDate) {
        this.returnAcceptedDate = returnAcceptedDate;
    }

    public String getReturnShipmentReceivedDate() {
        return returnShipmentReceivedDate;
    }

    public void setReturnShipmentReceivedDate(String returnShipmentReceivedDate) {
        this.returnShipmentReceivedDate = returnShipmentReceivedDate;
    }

    public String getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(String refundDate) {
        this.refundDate = refundDate;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public Refund(){

    }



    /*"marketplaceOrderId": "<marketplaceorderid>",
            "returnAmount": 100000,
            "returnAcceptedDate"will : "2017-07-11T11:06:46Z",
            "returnShipmentReceivedDate": "2017-07-13T11:06:46Z",
            "refundDate": "2017-07-13T11:06:46Z",
            "returnType": "full"*/

}
