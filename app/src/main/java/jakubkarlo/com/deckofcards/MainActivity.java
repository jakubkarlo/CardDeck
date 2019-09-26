package jakubkarlo.com.deckofcards;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private EditText numberOfDecks;
    private Button confirmButton;

    private int decks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberOfDecks = (EditText)findViewById(R.id.numberOfDecks);
        confirmButton = (Button)findViewById(R.id.decksConfirm);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDecks(Integer.parseInt(String.valueOf(numberOfDecks.getText())));
                if(validateDecks()){
                    Intent intent = new Intent(MainActivity.this, CardsActivity.class);
                    intent.putExtra("DECKS_NO", Integer.toString(getDecks()));
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Wrong number of decks! Put number between 1 and 5", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean validateDecks(){
        return getDecks() > 0 && getDecks() < 6;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public int getDecks() {
        return decks;
    }

    public void setDecks(int decks) {
        this.decks = decks;
    }
}
