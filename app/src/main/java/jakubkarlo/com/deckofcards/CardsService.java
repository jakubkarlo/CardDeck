package jakubkarlo.com.deckofcards;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jakub on 25.09.2019.
 */

public class CardsService {

    private CardsInterface service;

    public CardsService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://deckofcardsapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        service = retrofit.create(CardsInterface.class);
    }

    public  CardsInterface getService() {
        return service;
    }

    public void setService(CardsInterface service) {
        this.service = service;
    }
}
