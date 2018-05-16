package android.cs.spring18.mazegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
                //Log.d("which maze?", ""+Maze.mMazes.length);
                Intent intent = MazeActivity.newIntent(MenuActivity.this, Maze.mazeLayouts[0]);
                //Intent intent = new Intent(MenuActivity.this, LevelCompletedActivity.class);//this was test
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
