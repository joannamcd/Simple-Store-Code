public class Receipt {
    private int orderID;
    private String receiptTXT;

    public int getOrderID(){
        return orderID;
    }

    public void setOrderID(int orderID){
        this.orderID = orderID;
    }

    public String getReceiptTXT(){
        return receiptTXT;
    }

    public void setReceiptTXT(String receiptTXT){
        this.receiptTXT = receiptTXT;
    }
}
