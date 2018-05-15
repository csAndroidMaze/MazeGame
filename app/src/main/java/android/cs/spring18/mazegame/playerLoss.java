package android.cs.spring18.mazegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class playerLoss extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_loss);
    }
    public void tryAgainClicked(View v){
        Intent i = new Intent(this, MazeActivity.class);
        //Put extras in like this
        //i.putExtra("enteredUsername",enteredUsername);
        startActivity(i);
    }
    public void quitClicked(View v){
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
    }
}
