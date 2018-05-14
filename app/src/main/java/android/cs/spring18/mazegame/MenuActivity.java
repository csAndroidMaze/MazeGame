package android.cs.spring18.mazegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



        mButton = (Button) findViewById(R.id.go_button);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //nothing else yet
    }

    public void goToMaze(View v){
        Intent i = new Intent(MenuActivity.this,mazeController.class);
        startActivity(i);
    }


}
