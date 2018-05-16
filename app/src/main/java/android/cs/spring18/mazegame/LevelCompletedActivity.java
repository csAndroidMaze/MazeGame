package android.cs.spring18.mazegame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LevelCompletedActivity extends AppCompatActivity {

    private static int mCurrentMaze;
    private Button mNextButton;
    private Button mPrevButton;
    private TextView mTextView;

    public static Intent newIntent(Context packageContext, int mazeLayout) {
        Intent intent = new Intent(packageContext, LevelCompletedActivity.class);
        mCurrentMaze = mazeLayout;
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_level);

        mTextView = (TextView) findViewById(R.id.congrats_text);
        if (Maze.mCurrentMazeIndex == (Maze.mazeLayouts.length - 1)) {
            mTextView.setText("Congrats, you've beat all the levels");
        }

        mNextButton = (Button) findViewById(R.id.next_maze_button);
        if (Maze.mCurrentMazeIndex == (Maze.mazeLayouts.length-1)) {
            mNextButton.setEnabled(false);
        }
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Maze.mCurrentMazeIndex += 1;
                Intent intent = MazeActivity.newIntent(LevelCompletedActivity.this, Maze.mazeLayouts[Maze.mCurrentMazeIndex]);
                startActivity(intent);
            }
        });

        mPrevButton = (Button) findViewById(R.id.prev_maze_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MazeActivity.newIntent(LevelCompletedActivity.this, Maze.mazeLayouts[Maze.mCurrentMazeIndex]);
                startActivity(intent);
            }
        });








    }
}
