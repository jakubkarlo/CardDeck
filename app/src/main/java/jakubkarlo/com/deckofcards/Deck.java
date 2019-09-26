package jakubkarlo.com.deckofcards;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jakub on 25.09.2019.
 */

class Deck {

    @Expose
    @SerializedName("success")
    private boolean success;
    @Expose
    @SerializedName("deck_id")
    private String deckId;
    @Expose
    @SerializedName("shuffled")
    private boolean shuffled;
    @Expose
    @SerializedName("remaining")
    private int remaining;
    @Expose
    @SerializedName("cards")
    private List<Card> cards;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDeckId() {
        return deckId;
    }

    public void setDeckId(String deckId) {
        this.deckId = deckId;
    }

    public boolean isShuffled() {
        return shuffled;
    }

    public void setShuffled(boolean shuffled) {
        this.shuffled = shuffled;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
