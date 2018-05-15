package android.cs.spring18.mazegame;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class MazeActivity extends AppCompatActivity {
    protected MazeCharacter pCharacter;
    protected ImageView pCharacterView;
    protected ImageView treasureView;
    protected int xPos;
    protected int yPos;
    protected int charDrawableWidth;
    protected int charDrawableHeight;
    protected int screenHeight;
    public int treasureXPos;
    public int treasureYPos;
    public int viewXPos;
    public int viewYPos;
    RelativeLayout.LayoutParams params;
    RelativeLayout mainLayout;

    Drawable charImage;
    protected RelativeLayout layout;
    private static Maze mCurrentMaze;

    private View rectangle4;

    private static Coords[] mBarriers = new Coords[] {
            //every index will hold a list of barriers for the maze at
            // that respective position


    };

    private static int[] mViews = new int[] {
            //array filled with all the views in a layout
    };


    public static Intent newIntent(Context packageContext, Maze maze) {
        Intent intent = new Intent(packageContext, MazeActivity.class);
        mCurrentMaze = maze;
        //Log.d("what's the layout", ""+ maze.getMazeLayout());
        return intent;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("what maze?", ""+mCurrentMaze.getMazeLayout());
        setContentView(mCurrentMaze.getMazeLayout());
        treasureView=(ImageView)findViewById(R.id.cheese);
        Drawable drawable = getResources().getDrawable(R.drawable.evil_rat_image);
        charDrawableWidth = drawable.getIntrinsicWidth();
        charDrawableHeight = drawable.getIntrinsicHeight();

        rectangle4 = (View) findViewById(R.id.Rectangle4);
        Log.d("rectangle 4 coords", ""+ getLocationOnScreen(rectangle4));

        //mainLayout = (RelativeLayout) findViewById(R.id.main);
        pCharacterView=(ImageView)findViewById(R.id.charImage);


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = (int)convertPixelsToDp(Resources.getSystem().getDisplayMetrics().heightPixels, this);
        int correctedScreenHeight=(int)(screenHeight*2.5);

        pCharacter=new MazeCharacter(0,correctedScreenHeight);
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

    private boolean charTouchedView(){
        Log.d("where am I", ""+viewXPos+", "+viewYPos );
        return ((xPos + 50 > viewXPos) && (xPos - 50 < viewXPos) && (yPos +50 > viewYPos - 236) &&
                (yPos - 50 < viewYPos - 236));
    }

    private boolean charReachedTreasure(){
        Log.d("Treasure x in bool:", ""+treasureXPos);
        Log.d("Treasure y in bool:", ""+treasureYPos);
        Log.d("Char x in bool:", ""+xPos);
        Log.d("Char y in bool:", ""+yPos);
        return ((xPos + 50 > treasureXPos) && (xPos-50 <treasureXPos)
                && (yPos + 50>treasureYPos-236) && (yPos - 50<treasureYPos-236));
    }
    private int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
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
        Point viewXY = getLocationOnScreen(rectangle4);
        viewXPos = viewXY.x;
        viewYPos = viewXY.y;
        Log.d("Treasure X:", ""+treasureXPos);
        Log.d("Treasure Y:", ""+treasureYPos);
        if(charReachedTreasure()){
            Log.d("Treasure reached", "true");
            Toast.makeText(this, "Treasure reached", Toast.LENGTH_SHORT).show();
        }

        if(charTouchedView()) {
            Log.d("View touched", "true");
            Toast.makeText(this, "Ouch!", Toast.LENGTH_SHORT).show();
        }
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
