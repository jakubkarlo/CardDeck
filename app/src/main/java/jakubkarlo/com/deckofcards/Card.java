package jakubkarlo.com.deckofcards;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jakub on 25.09.2019.
 */

public class Card {

    @Expose
    @SerializedName("image")
    private String imageRes;
    @Expose
    @SerializedName("value")
    private String value;
    @Expose
    @SerializedName("suit")
    private String suit;
    @Expose
    @SerializedName("code")
    private String code;

    public String getImageRes() {
        return imageRes;
    }

    public void setImageRes(String imageRes) {
        this.imageRes = imageRes;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
