package android.cs.spring18.mazegame;

import android.content.Context;
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

public class mazeController extends AppCompatActivity {
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
    RelativeLayout.LayoutParams params;
    RelativeLayout mainLayout;

    Drawable charImage;
    protected RelativeLayout layout;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maze_box);
        treasureView=(ImageView)findViewById(R.id.cheese);
        Drawable drawable = getResources().getDrawable(R.drawable.evil_rat_image);
        charDrawableWidth = drawable.getIntrinsicWidth();
        charDrawableHeight = drawable.getIntrinsicHeight();

        //mainLayout = (RelativeLayout) findViewById(R.id.main);
        pCharacterView=(ImageView)findViewById(R.id.charImage);


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = (int)convertPixelsToDp(Resources.getSystem().getDisplayMetrics().heightPixels, this);
        int correctedScreenHeight=(int)(screenHeight*2.5);

        pCharacter=new MazeCharacter(0,correctedScreenHeight);
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
        Log.d("Treasure X:", ""+treasureXPos);
        Log.d("Treasure Y:", ""+treasureYPos);
        if(charReachedTreasure()){
            Log.d("Treasure reached", "true");
            Toast.makeText(this, "Treasure reached", Toast.LENGTH_SHORT).show();
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
}
