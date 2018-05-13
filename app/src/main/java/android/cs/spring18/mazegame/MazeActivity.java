package android.cs.spring18.mazegame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MazeActivity extends AppCompatActivity {

    private static int mCurrentMaze;
    private Button upButton;
    private Button downButton;
    private Button leftButton;
    private Button rightButton;


    public static Intent newIntent(Context packageContext, Maze maze) {
        Intent intent = new Intent(packageContext, MazeActivity.class);
        mCurrentMaze = maze.getMazeLayout();
        Log.d("what's the layout", ""+ maze.getMazeLayout());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("are we getting here?", "yes");
        super.onCreate(savedInstanceState);
        setContentView(mCurrentMaze);

        //character movement stuff goes in here
        /*upButton = (Button) findViewById(R.id.up_button);
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if moving this way doesn't hit a barrier
                //move character up
            }
        });

        downButton = (Button) findViewById(R.id.down_button);
        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if moving this way doesn't hit a barrier
                //move character down
            }
        });

        leftButton = (Button) findViewById(R.id.left_button);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if moving this way doesn't hit a barrier
                //move character left
            }
        });

        rightButton = (Button) findViewById(R.id.right_button);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if moving this way doesn't hit a barrier
                //move character right
            }
        });*/

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //would probably want to save the position of the character here as well
    }
}
