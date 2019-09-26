package jakubkarlo.com.deckofcards;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.path;

public class CardsActivity extends AppCompatActivity {

    private final String cardsDrawn = "5";

    private TextView cardsAvailable;
    private Button reshuffleButton, drawDeckButton;
    private Deck deck;
    private ImageView card1, card2, card3, card4, card5;
    private Score score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        cardsAvailable = (TextView)findViewById(R.id.cardsAvailable);
        reshuffleButton = (Button)findViewById(R.id.reshuffleButton);
        drawDeckButton = (Button)findViewById(R.id.drawCardsButton);

        card1 = (ImageView)findViewById(R.id.card1);
        card2 = (ImageView)findViewById(R.id.card2);
        card3 = (ImageView)findViewById(R.id.card3);
        card4 = (ImageView)findViewById(R.id.card4);
        card5 = (ImageView)findViewById(R.id.card5);

        final CardsService service = new CardsService();
        getDecks(service);

        drawDeckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call call = service.getService().drawCards(deck.getDeckId(), cardsDrawn);

                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {

                        if (response.body() != null) {
                            deck = (Deck)response.body();
                            updateDeckRemaining();
                            if (deck.getCards() != null && !deck.getCards().isEmpty()) {
                                updateCardsDrawables();
                                score = validateWin();
                                Toast.makeText(getApplicationContext(), "You scored : " + score, Toast.LENGTH_LONG).show();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                        t.printStackTrace();
                        Log.e("Cards error", t.getMessage());

                    }
                });
            }
        });

        reshuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call call = service.getService().reshuffle(deck.getDeckId());
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if (response.body() != null) {
                            deck = (Deck) response.body();
                            updateDeckRemaining();
                            Toast.makeText(getApplicationContext(), "The deck has been reshuffled!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                });
            }
        });

    }



    private void getDecks(CardsService service){

        String decksNo = getIntent().getStringExtra("DECKS_NO");

        Call call = service.getService().getDecks(decksNo);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                if (response.body() != null) {
                    deck = (Deck) response.body();
                    updateDeckRemaining();
                    Log.i("Deck", "Deck remaining:" + deck.getRemaining());
                } else {
                    Log.e("Deck error", "Deck error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

                t.printStackTrace();
                Log.e("Deck error", t.getMessage());
                /*
                Error callback
                */
            }
        });
    }

    public void updateDeckRemaining(){
        if (deck.getRemaining() == 0){
            cardsAvailable.setText("All cards are gone. Please reshuffle.");
        } else {
            cardsAvailable.setText("Cards available: " + deck.getRemaining());
        }
    }

    public void updateCardsDrawables() {
        //totally hardcoded
        List<Card> cards = deck.getCards();
        for (int i=0; i < cards.size(); i++){
            final int finalI = i;
            Glide.with(this)
                    .load(cards.get(i).getImageRes())
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            if (finalI ==0) card1.setImageBitmap(resource);
                            else if (finalI ==1) card2.setImageBitmap(resource);
                            else if (finalI ==2) card3.setImageBitmap(resource);
                            else if (finalI ==3) card4.setImageBitmap(resource);
                            else if (finalI ==4) card5.setImageBitmap(resource);
                        }
                    });
        }
    }


    public Score validateWin(){

        List<Card> cards = deck.getCards();
        int suitCounter=0, valueCounter=0, shapeCounter=0, stairsCounter=0;
        for(int i = 0; i < cards.size(); i++){
            for (int j = i+1; j < cards.size(); j++) {

                    if (Objects.equals(cards.get(i).getSuit(), cards.get(j).getSuit()))
                        suitCounter++;
                    if (Objects.equals(cards.get(i).getValue(), cards.get(j).getValue()))
                        valueCounter++;

                    if (valueCounter >= 3) return Score.TWINS;
                    if (suitCounter >= 3) return Score.COLOR;
                }

            if (cards.get(i).getValue().equals("KING") || cards.get(i).getValue().equals("QUEEN") || cards.get(i).getValue().equals("JACK"))
                shapeCounter++;
            if (shapeCounter >= 3) return Score.SHAPE;
        }

    return Score.NOTHING;
    }

}
