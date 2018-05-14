package android.cs.spring18.mazegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.logging.Level;

public class MenuActivity extends AppCompatActivity {

    private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        mButton = (Button) findViewById(R.id.go_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start maze Activity
                //need to have a class with information about the mazes, put the different Maze layouts in an Array to choose from
                Intent intent = MazeActivity.newIntent(MenuActivity.this, Maze.mMazes[Maze.mCurrentMazeIndex]);
                //Intent intent = new Intent(MenuActivity.this, LevelMenuActivity.class);this was test
                startActivity(intent);
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //nothing else yet


    }




}
