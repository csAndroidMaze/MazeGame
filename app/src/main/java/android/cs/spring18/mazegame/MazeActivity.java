package android.cs.spring18.mazegame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MazeActivity extends AppCompatActivity {
    protected MazeCharacter pCharacter;
    protected monsterLevelOne monsterObj;
    protected ImageView pCharacterView;
    protected ImageView treasureView;
    protected ImageView monsterView;
    protected int xPos;
    protected int yPos;
    protected int charDrawableWidth;
    protected int charDrawableHeight;
    protected int monsterDrawableWidth;
    protected int monsterDrawableHeight;
    protected int screenHeight;
    private boolean stopThreadLoop=false;
    public int treasureXPos;
    public int treasureYPos;
    public int monsterXPos;
    public int monsterYPos;
    RelativeLayout.LayoutParams params;
    RelativeLayout mainLayout;

    Drawable charImage;

    //We include 1 monster in this level
    Drawable monsterImage;

    protected RelativeLayout layout;
    private static int mCurrentMaze;

    private static Coords[] mBarriers = new Coords[] {
            //every index will hold a list of barriers for the maze at
            // that respective position

    };

    private static int[] mViews = new int[] {
            //array filled with all the views in a layout
    };


    public static Intent newIntent(Context packageContext, int mazeLayout) {
        Intent intent = new Intent(packageContext, MazeActivity.class);
        mCurrentMaze = mazeLayout;
        //Log.d("what's the layout", ""+ maze.getMazeLayout());
        return intent;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mCurrentMaze);
        treasureView=(ImageView)findViewById(R.id.treasure);
        Drawable drawable = getResources().getDrawable(R.drawable.maze_square);
        charDrawableWidth = drawable.getIntrinsicWidth();
        charDrawableHeight = drawable.getIntrinsicHeight();

        drawable = getResources().getDrawable(R.drawable.monster_one);
        monsterDrawableWidth = drawable.getIntrinsicWidth();
        monsterDrawableHeight = drawable.getIntrinsicHeight();

        //mainLayout = (RelativeLayout) findViewById(R.id.main);
        pCharacterView=(ImageView)findViewById(R.id.charImage);
        monsterView=(ImageView)findViewById(R.id.monsterImage);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = (int)convertPixelsToDp(Resources.getSystem().getDisplayMetrics().heightPixels, this);
        int correctedScreenHeight=(int)(screenHeight*2);

        monsterObj=new monsterLevelOne(0,0);
        pCharacter=new MazeCharacter(0,correctedScreenHeight);

        //Sets the starting params so not 0,0
        params=new RelativeLayout.LayoutParams(charDrawableWidth, charDrawableHeight);
        pCharacterView.setLayoutParams(params);
        xPos=pCharacter.getXPos();
        yPos=pCharacter.getYPos();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) pCharacterView
                .getLayoutParams();
        layoutParams.leftMargin = xPos;
        layoutParams.topMargin =yPos;

        final Handler handler = new Handler();
        final int delay = 1000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                moveMonster();
                handler.postDelayed(this, delay);
                if(stopThreadLoop){
                    handler.removeCallbacks(this);
                }
            }
        }, delay);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //would probably want to save the position of the character here as well
    }

    public static Point getLocationOnScreen(View view){
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new Point(location[0], location[1]);
    }

    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    private boolean charHitByMonster(){
        Log.d("Monster x in bool:", ""+monsterXPos);
         Log.d("Monster y in bool:", ""+monsterYPos);
         Log.d("Char x in m bool:", ""+xPos);
         Log.d("Char y in m bool:", ""+yPos);
        //Eventually we will need to do this with a list of monsters
        return ((xPos + 100 > monsterXPos) && (xPos-100 <monsterXPos)
                && (yPos + 100>monsterYPos) && (yPos - 100<monsterYPos));
    }
    private boolean charReachedTreasure(){
        Log.d("Treasure x in bool:", ""+treasureXPos);
        Log.d("Treasure y in bool:", ""+treasureYPos);
        Log.d("Char x in t bool:", ""+xPos);
        Log.d("Char y in t bool:", ""+yPos);
        return ((xPos + 100 > treasureXPos) && (xPos-100 <treasureXPos)
                && (yPos + 100>treasureYPos-236) && (yPos - 100<treasureYPos-236));
    }
    private int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
    private void moveMonster(){
        monsterObj.move();
        params=new RelativeLayout.LayoutParams(monsterDrawableWidth, monsterDrawableHeight);
        monsterView.setLayoutParams(params);
        monsterXPos=monsterObj.getXPos();
        monsterYPos=monsterObj.getYPos();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) monsterView
                .getLayoutParams();
        layoutParams.leftMargin = monsterXPos;
        layoutParams.topMargin =monsterYPos;

        monsterView.setLayoutParams(params);

        if(charHitByMonster()){
            Log.d("Char hit by monster","yes");
            goToPlayerLossPage();
        }
    }
    private void generalMove(){
        pCharacter.move();
        params=new RelativeLayout.LayoutParams(charDrawableWidth, charDrawableHeight);
        pCharacterView.setLayoutParams(params);
        xPos=pCharacter.getXPos();
        yPos=pCharacter.getYPos();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) pCharacterView
                .getLayoutParams();
        layoutParams.leftMargin = xPos;
        layoutParams.topMargin =yPos;

        pCharacterView.setLayoutParams(params);
        Point treasureXY=getLocationOnScreen(treasureView);
        treasureXPos=treasureXY.x;
        treasureYPos=treasureXY.y;
        Log.d("Treasure X:", ""+treasureXPos);
        Log.d("Treasure Y:", ""+treasureYPos);
        if(charReachedTreasure()){
            goToLevelCompletedPage();
            //This code fails to change to the new activity
            //newIntent(this, new Maze(R.layout.maze_box, true));

            //Log.d("Treasure reached", "true");
            //Toast.makeText(this, "Treasure reached", Toast.LENGTH_SHORT).show();
        }
        if(charHitByMonster()){
            goToPlayerLossPage();
        }
    }
    public void goToLevelCompletedPage(){
        stopThreadLoop=true;
        Intent i = new Intent(this, LevelCompletedActivity.class);
        //Put extras in like this
        //i.putExtra("enteredUsername",enteredUsername);
        startActivity(i);
    }
    public void goToPlayerLossPage(){
        stopThreadLoop=true;
        Intent i = new Intent(this, playerLoss.class);
        startActivity(i);
    }
    public void moveRight(View v){
        //o=down, 1=up, 2=right, 3=left
        pCharacter.setDirectionFaced(2);
        generalMove();
    }
    public void moveLeft(View v){
        pCharacter.setDirectionFaced(3);
        generalMove();
    }

    public void moveUp(View v){
        pCharacter.setDirectionFaced(1);
        generalMove();
    }
    public void moveDown(View v){
        pCharacter.setDirectionFaced(0);
        generalMove();
    }

    class Coords {
        int x;
        int y;

        public boolean equals(Object o) {
            Coords c = (Coords) o;
            return c.x == x && c.y == y;
        }

        public Coords(int x, int y) {
            super();
            this.x = x;
            this.y = y;
        }
    }

}
