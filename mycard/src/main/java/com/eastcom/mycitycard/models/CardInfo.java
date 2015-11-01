package com.eastcom.mycitycard.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rockgarden on 15/10/31.
 */
public class CardInfo implements Parcelable{// 可考虑用Serializable
    private String cardName;
    private String cardNumber;// 可考虑用BigInteger但传入时强制转换

    public String getCardNumber(){
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardName(){
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public CardInfo(String cardName, String cardNumber){
        this.cardName=cardName;
        this.cardNumber=cardNumber;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getCardName());
        dest.writeString(getCardNumber());
    }

    public static final Creator<CardInfo> CREATOR=new Creator<CardInfo>() {
        @Override
        public CardInfo createFromParcel(Parcel source) {
            //source.writeBundle();
            return new CardInfo(source.readString(),source.readString());
        }

        @Override
        public CardInfo[] newArray(int size) {
            return new CardInfo[size];
        }
    };
}
