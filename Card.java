import java.math.BigInteger;

public class Card {
    private int cardID;
    private long cardNum;
    private String expiryMonth;
    private int expiryYear;
    private int cvv;
    private String cardName;

    public int getCardID(){
        return cardID;
    }

    public void setCardID(int cardID){
        this.cardID = cardID;
    }

    public long getCardNum(){
        return cardNum;
    }

    public void setCardNum(long cardNum){
        this.cardNum = cardNum;
    }

    public String getExpiryMonth(){
        return expiryMonth;
    }

    public void setExpiryMonth(String expiryMonth){
        this.expiryMonth = expiryMonth;
    }

    public int getExpiryYear(){
        return expiryYear;
    }

    public void setExpiryYear(int expiryYear){
        this.expiryYear = expiryYear;
    }

    public int getCvv(){
        return cvv;
    }

    public void setCvv(int cvv){
        this.cvv = cvv;
    }

    public String getCardName(){
        return cardName;
    }

    public void setCardName(String cardName){
        this.cardName = cardName;
    }
}
