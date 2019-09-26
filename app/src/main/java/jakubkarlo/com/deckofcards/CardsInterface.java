package jakubkarlo.com.deckofcards;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Jakub on 25.09.2019.
 */

public interface CardsInterface {

    @GET("deck/new/shuffle/")
    Call<Deck> getDecks(@Query("deck_count") String deckCount);

    @GET("deck/{deck_id}/shuffle/")
    Call <Deck> reshuffle(@Path("deck_id") String deckId);

    @GET("deck/{deck_id}/draw/")
    Call <Deck> drawCards(@Path("deck_id") String deckId, @Query("count") String count);



}
