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
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MazeActivity extends AppCompatActivity {
    protected MazeCharacter pCharacter;
    protected monsterLevelOne monsterObj;
    protected monsterLevelOne monster2Obj;
    protected monsterLevelOne monster3Obj;
    protected ImageView pCharacterView;
    protected ImageView treasureView;
    protected ImageView treasureView2;
    protected boolean treasure1Found=false;
    protected boolean treasure2Found=false;
    protected ImageView monsterView;
    protected ImageView monster2View;
    protected ImageView monster3View;
    protected int xPos;
    protected int yPos;
    protected int charDrawableWidth;
    protected int charDrawableHeight;
    protected int monsterDrawableWidth;
    protected int monsterDrawableHeight;
    protected int treasureDrawableWidth;
    protected int treasureDrawableHeight;
    protected int screenHeight;
    protected mazeData mazeDataObject;
    private boolean stopThreadLoop=false;
    private Random randObject=new Random();
    private ArrayList<ArrayList> wallLocations;
    private ArrayList<ArrayList> wallWidthsAndHeights;
    public int treasureXPos;
    public int treasureYPos;
    public int treasure2XPos;
    public int treasure2YPos;
    public int monsterXPos;
    public int monsterYPos;

    public int monster2XPos;
    public int monster2YPos;

    public int monster3XPos;
    public int monster3YPos;
    RelativeLayout.LayoutParams params;
    RelativeLayout mainLayout;

    Drawable charImage;

    //We include 1 monster in this level
    Drawable monsterImage;

    protected RelativeLayout layout;
    private static Maze mCurrentMaze;

    /**private static Coords[] mBarriers = new Coords[] {
            //every index will hold a list of barriers for the maze at
            // that respective position

    };**/

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
        setContentView(mCurrentMaze.getMazeLayout());
        treasureView=(ImageView)findViewById(R.id.treasure);

        /**treasureView2=(ImageView)findViewById(R.id.treasure2);
        params=new RelativeLayout.LayoutParams(treasureDrawableWidth, treasureDrawableHeight);
        treasureView2View.setLayoutParams(params);
        monster3XPos=monster3Obj.getXPos();
        monster3YPos=monster3Obj.getYPos();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) monsterView
                .getLayoutParams();
        layoutParams.leftMargin = treasure2XPos;
        layoutParams.topMargin =monsterYPos;**/

        Drawable drawable = getResources().getDrawable(R.drawable.maze_square);
        charDrawableWidth = drawable.getIntrinsicWidth();
        charDrawableHeight = drawable.getIntrinsicHeight();

        drawable = getResources().getDrawable(R.drawable.monster_one);
        monsterDrawableWidth = drawable.getIntrinsicWidth();
        monsterDrawableHeight = drawable.getIntrinsicHeight();

        drawable = getResources().getDrawable(R.drawable.treasure_image);
        treasureDrawableWidth = drawable.getIntrinsicWidth();
        treasureDrawableHeight = drawable.getIntrinsicHeight();

        //mainLayout = (RelativeLayout) findViewById(R.id.main);
        pCharacterView=(ImageView)findViewById(R.id.charImage);
        monsterView=(ImageView)findViewById(R.id.monsterImage);
        monster2View=(ImageView)findViewById(R.id.monster2Image);
        monster3View=(ImageView)findViewById(R.id.monster3Image);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = (int)convertPixelsToDp(Resources.getSystem().getDisplayMetrics().heightPixels, this);
        int correctedScreenHeight=(int)(screenHeight*1.5);

        //Point initialUserXY=getLocationOnScreen(pCharacterView);
        //xPos=initialUserXY.x;
        //yPos=initialUserXY.y;
        //int correctedScreenHeight=500;

        monsterObj=new monsterLevelOne(0,correctedScreenHeight/2-100);
        //monsterObj.setXMoveSpeed(50);
        //monsterObj.setYMoveSpeed(50);

        //For some reason we can set speeds on others but setting this one causes freeze
        monster2Obj=new monsterLevelOne(500,correctedScreenHeight/2-100);
        //monster2Obj.setXMoveSpeed(50);
        //monster2Obj.setYMoveSpeed(50);

        monster3Obj=new monsterLevelOne(800,correctedScreenHeight/2-100);
        //monster3Obj.setXMoveSpeed(75);
        //monster3Obj.setYMoveSpeed(75);

        pCharacter=new MazeCharacter(0,correctedScreenHeight+300);

        //Sets the starting params so not 0,0
        params=new RelativeLayout.LayoutParams(monsterDrawableWidth, monsterDrawableHeight);

        monsterView.setLayoutParams(params);
        monsterXPos=monsterObj.getXPos();
        monsterYPos=monsterObj.getYPos();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) monsterView
                .getLayoutParams();
        layoutParams.leftMargin = monsterXPos;
        layoutParams.topMargin =monsterYPos;

        params=new RelativeLayout.LayoutParams(monsterDrawableWidth, monsterDrawableHeight);
        monster2View.setLayoutParams(params);
        monster2XPos=monster2Obj.getXPos();
        monster2YPos=monster2Obj.getYPos();
        layoutParams = (RelativeLayout.LayoutParams) monster2View
                .getLayoutParams();
        layoutParams.leftMargin = monster2XPos;
        layoutParams.topMargin =monster2YPos;

        params=new RelativeLayout.LayoutParams(monsterDrawableWidth, monsterDrawableHeight);
        monster3View.setLayoutParams(params);
        monster3XPos=monster3Obj.getXPos();
        monster3YPos=monster3Obj.getYPos();
        layoutParams = (RelativeLayout.LayoutParams) monster3View
                .getLayoutParams();
        layoutParams.leftMargin = monster3XPos;
        layoutParams.topMargin =monster3YPos;

        //Sets the starting params so not 0,0
        params=new RelativeLayout.LayoutParams(charDrawableWidth, charDrawableHeight);
        pCharacterView.setLayoutParams(params);
        xPos=pCharacter.getXPos();
        yPos=pCharacter.getYPos();
        layoutParams = (RelativeLayout.LayoutParams) pCharacterView
                .getLayoutParams();
        layoutParams.leftMargin = xPos;
        layoutParams.topMargin =yPos;

        final Handler handler = new Handler();
        //final int delay = 1000; //milliseconds
        final int delay = 500; //milliseconds

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

    private void getWallLocations(int mazeLevel){
        mazeDataObject.setCurrentLevel(mazeLevel);
        setWallLocations(mazeDataObject.getLevelOneIdsList());
    }
    public boolean monsterCollidesWithWall(ArrayList<Integer>xYChange, ImageView mView){


        int futureXChange=xYChange.get(0);
        int futureYChange=xYChange.get(1);

        int currWallX;
        int currWallY;

        int currWallHeight;
        int currWallWidth;
        int wallSegCount=0;
        ArrayList currSizePiece;

        for(ArrayList wallPieceLoc:wallLocations){
            currSizePiece=wallWidthsAndHeights.get(wallSegCount);
            currWallWidth=(int)currSizePiece.get(0);
            currWallHeight=(int)currSizePiece.get(1);
            currWallX=(int)wallPieceLoc.get(0);
            currWallY=(int)wallPieceLoc.get(1);
            //Assumes that coordinates of wall pieces are centered
            //We make two different options because the width on vert pieces
            //and height on hor pieces are tiny so the character can easily
            //move past their radius if both are treated the same.
            //Char pos seems to be top left corner?
            Point monstXY=getLocationOnScreen(mView);
            int monstViewXPos=monstXY.x;
            int monstViewYPos=monstXY.y;
            if(monstViewXPos<0){
                monstViewXPos=0;
            }
            if(monstViewYPos<0){
                monstViewYPos=0;
            }
            int wallCenterX=currWallX+currWallWidth/2;
            int wallCenterY=currWallY+currWallHeight/2;
            int monstCenterX=monstViewXPos+monsterDrawableWidth/2+futureXChange;
            int monstCenterY=monstViewYPos+monsterDrawableHeight/2+futureYChange;

            int monstLeftX=monstCenterX-monsterDrawableWidth/2;
            int monstRightX=monstCenterX+monsterDrawableWidth/2;
            int monstUpY=monstCenterY-monsterDrawableHeight/2;
            int monstDownY=monstCenterY+monsterDrawableHeight/2;

            int wallLeftX=wallCenterX-currWallWidth/2;
            int wallRightX=wallCenterX+currWallWidth/2;
            int wallUpY=wallCenterY-currWallHeight/2;
            int wallDownY=wallCenterY+currWallHeight/2;

            boolean xInRange=false;
            boolean yInRange=false;

            if(!(monstLeftX>=wallRightX&& monstRightX>=wallRightX || monstLeftX<=wallLeftX&& monstRightX<=wallLeftX)){
                xInRange=true;
            }
            if(!(monstDownY>=wallDownY && monstUpY>=wallDownY) && !(monstDownY<=wallUpY && monstUpY<=wallUpY) ){
                yInRange=true;
            }

            if(xInRange && yInRange){
                return true;
            }
            wallSegCount+=1;

        }
        return false;
    }

    public boolean charCollidesWithWall(ArrayList<Integer>xYChange){

        int futureXChange=xYChange.get(0);
        int futureYChange=xYChange.get(1);

        int currWallX;
        int currWallY;

        int currWallHeight;
        int currWallWidth;
        int wallSegCount=0;
        ArrayList currSizePiece;

        for(ArrayList wallPieceLoc:wallLocations){
            currSizePiece=wallWidthsAndHeights.get(wallSegCount);
            currWallWidth=(int)currSizePiece.get(0);
            currWallHeight=(int)currSizePiece.get(1);
            currWallX=(int)wallPieceLoc.get(0);
            currWallY=(int)wallPieceLoc.get(1);
            //Assumes that coordinates of wall pieces are centered
            //We make two different options because the width on vert pieces
            //and height on hor pieces are tiny so the character can easily
            //move past their radius if both are treated the same.
            //Char pos seems to be top left corner?
            Point charXY=getLocationOnScreen(pCharacterView);
            int charViewXPos=charXY.x;
            int charViewYPos=charXY.y;
            if(charViewXPos<0){
                charViewXPos=0;
            }
            if(charViewYPos<0){
                charViewYPos=0;
            }
            int wallCenterX=currWallX+currWallWidth/2;
            int wallCenterY=currWallY+currWallHeight/2;
            int charCenterX=charViewXPos+charDrawableWidth/2+futureXChange;
            int charCenterY=charViewYPos+charDrawableHeight/2+futureYChange;

            int charLeftX=charCenterX-charDrawableWidth/2;
            int charRightX=charCenterX+charDrawableWidth/2;
            int charUpY=charCenterY-charDrawableHeight/2;
            int charDownY=charCenterY+charDrawableHeight/2;

            int wallLeftX=wallCenterX-currWallWidth/2;
            int wallRightX=wallCenterX+currWallWidth/2;
            int wallUpY=wallCenterY-currWallHeight/2;
            int wallDownY=wallCenterY+currWallHeight/2;

            boolean xInRange=false;
            boolean yInRange=false;

            if(!(charLeftX>=wallRightX&& charRightX>=wallRightX || charLeftX<=wallLeftX&& charRightX<=wallLeftX)){
                xInRange=true;
            }
            if(!(charDownY>=wallDownY && charUpY>=wallDownY) && !(charDownY<=wallUpY && charUpY<=wallUpY) ){
                yInRange=true;
            }

            if(xInRange && yInRange){
                return true;
            }
            wallSegCount+=1;

        }
        return false;
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

    private boolean charHitByMonster(ImageView mView){
        /**Log.d("Monster x in bool:", ""+monsterXPos);
         Log.d("Monster y in bool:", ""+monsterYPos);
         Log.d("Char x in m bool:", ""+xPos);
         Log.d("Char y in m bool:", ""+yPos);**/
        //Eventually we will need to do this with a list of monsters
        Point charXY=getLocationOnScreen(pCharacterView);
        int charViewXPos=charXY.x;
        int charViewYPos=charXY.y;
        if(charViewXPos<0){
            charViewXPos=0;
        }
        if(charViewYPos<0){
            charViewYPos=0;
        }
        Point monsterXY=getLocationOnScreen(mView);
        int monsterViewXPos=monsterXY.x;
        int monsterViewYPos=monsterXY.y;

        int monsterCenterX=monsterViewXPos+monsterDrawableWidth/2;
        int monsterCenterY=monsterViewYPos+monsterDrawableHeight/2;
        int charCenterX=charViewXPos+charDrawableWidth/2;
        int charCenterY=charViewYPos+charDrawableHeight/2;

        int charLeftX=charCenterX-charDrawableWidth/2;
        int charRightX=charCenterX+charDrawableWidth/2;
        int charUpY=charCenterY-charDrawableHeight/2;
        int charDownY=charCenterY+charDrawableHeight/2;

        boolean xInRange=false;
        boolean yInRange=false;

        int monsterLeftX=monsterCenterX-monsterDrawableWidth/2;
        int monsterRightX=monsterCenterX+monsterDrawableWidth/2;
        int monsterUpY=monsterCenterY-monsterDrawableHeight/2;
        int monsterDownY=monsterCenterY+monsterDrawableHeight/2;

        if(!(charLeftX>=monsterRightX&& charRightX>=monsterRightX || charLeftX<=monsterLeftX&& charRightX<=monsterLeftX)){
            xInRange=true;
        }
        if(!(charDownY>=monsterDownY && charUpY>=monsterDownY) && !(charDownY<=monsterUpY && charUpY<=monsterUpY) ){
            yInRange=true;
        }

        if(xInRange && yInRange){
            return true;
        }
        return false;
        /**return (((xPos + 25 > monsterXPos) && (xPos-25 <monsterXPos)
                && (yPos + 25>monsterYPos) && (yPos - 25<monsterYPos))||
                ((xPos + 25 > monster2XPos) && (xPos-25 <monster2XPos)
                        && (yPos + 25>monster2YPos) && (yPos - 25<monster2YPos)));**/
    }
    private boolean charReachedTreasure(){
        /**Log.d("Treasure x in bool:", ""+treasureXPos);
        Log.d("Treasure y in bool:", ""+treasureYPos);
        Log.d("Char x in t bool:", ""+xPos);
        Log.d("Char y in t bool:", ""+yPos);**/
        Point charXY=getLocationOnScreen(pCharacterView);
        int charViewXPos=charXY.x;
        int charViewYPos=charXY.y;
        if(charViewXPos<0){
            charViewXPos=0;
        }
        if(charViewYPos<0){
            charViewYPos=0;
        }
        Point treasureXY=getLocationOnScreen(treasureView);
        int treasureViewXPos=treasureXY.x;
        int treasureViewYPos=treasureXY.y;

        int treasureCenterX=treasureViewXPos+treasureDrawableWidth/2;
        int treasureCenterY=treasureViewYPos+treasureDrawableHeight/2;
        int charCenterX=charViewXPos+charDrawableWidth/2;
        int charCenterY=charViewYPos+charDrawableHeight/2;

        int charLeftX=charCenterX-charDrawableWidth/2;
        int charRightX=charCenterX+charDrawableWidth/2;
        int charUpY=charCenterY-charDrawableHeight/2;
        int charDownY=charCenterY+charDrawableHeight/2;

        boolean xInRange=false;
        boolean yInRange=false;

        int treasureLeftX=treasureCenterX-treasureDrawableWidth/2;
        int treasureRightX=treasureCenterX+treasureDrawableWidth/2;
        int treasureUpY=treasureCenterY-treasureDrawableHeight/2;
        int treasureDownY=treasureCenterY+treasureDrawableHeight/2;
        if(!(charLeftX>=treasureRightX&& charRightX>=treasureRightX || charLeftX<=treasureLeftX&& charRightX<=treasureLeftX)){
            xInRange=true;
        }
        if(!(charDownY>=treasureDownY && charUpY>=treasureDownY) && !(charDownY<=treasureUpY && charUpY<=treasureUpY) ){
            yInRange=true;
        }

        if(xInRange && yInRange){
            return true;
        }
        return false;
    }
    private int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
    private void moveMonster(){
        if(wallLocations==null) {
            mazeDataObject = new mazeData();
            mazeDataObject.addLevelOneIds();
            Log.d("Wall loc in move:", ""+wallLocations);
            //For now we assume that current level is 1
            getWallLocations(1);
        }

        monsterObj.move();
        monster2Obj.move();
        monster3Obj.move();
        //Needs to be here since we reset direction faced within monster move
        ArrayList<Integer> xYChange=monsterObj.getXYChangeOnMove();
        //Keep trying to move until a proper direction is found. Warning: if monster
        //gets trapped this will create an infinite loop.
        int directionFaced;
        while(monsterCollidesWithWall(xYChange,monsterView)){
            monsterObj.reverseMove();
            directionFaced=randObject.nextInt(3 - 0 + 1) + 0;
            monsterObj.setDirectionFaced(directionFaced);
            monsterObj.move();
            xYChange=monsterObj.getXYChangeOnMove();
        }
        xYChange=monster2Obj.getXYChangeOnMove();
        //Keep trying to move until a proper direction is found. Warning: if monster
        //gets trapped this will create an infinite loop.
        while(monsterCollidesWithWall(xYChange,monster2View)){
            monster2Obj.reverseMove();
            directionFaced=randObject.nextInt(3 - 0 + 1) + 0;
            monster2Obj.setDirectionFaced(directionFaced);
            monster2Obj.move();
            xYChange=monster2Obj.getXYChangeOnMove();
        }

        xYChange=monster3Obj.getXYChangeOnMove();
        //Keep trying to move until a proper direction is found. Warning: if monster
        //gets trapped this will create an infinite loop.
        while(monsterCollidesWithWall(xYChange,monster3View)){
            monster3Obj.reverseMove();
            directionFaced=randObject.nextInt(3 - 0 + 1) + 0;
            monster3Obj.setDirectionFaced(directionFaced);
            monster3Obj.move();
            xYChange=monster3Obj.getXYChangeOnMove();
        }

        params=new RelativeLayout.LayoutParams(monsterDrawableWidth, monsterDrawableHeight);
        monsterView.setLayoutParams(params);
        monsterXPos=monsterObj.getXPos();
        monsterYPos=monsterObj.getYPos();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) monsterView
                .getLayoutParams();
        layoutParams.leftMargin = monsterXPos;
        layoutParams.topMargin =monsterYPos;

        monsterView.setLayoutParams(params);

        params=new RelativeLayout.LayoutParams(monsterDrawableWidth, monsterDrawableHeight);
        monster2View.setLayoutParams(params);
        monster2XPos=monster2Obj.getXPos();
        monster2YPos=monster2Obj.getYPos();
        layoutParams = (RelativeLayout.LayoutParams) monster2View
                .getLayoutParams();
        layoutParams.leftMargin = monster2XPos;
        layoutParams.topMargin =monster2YPos;

        monster2View.setLayoutParams(params);

        params=new RelativeLayout.LayoutParams(monsterDrawableWidth, monsterDrawableHeight);
        monster3View.setLayoutParams(params);
        monster3XPos=monster3Obj.getXPos();
        monster3YPos=monster3Obj.getYPos();
        layoutParams = (RelativeLayout.LayoutParams) monster3View
                .getLayoutParams();
        layoutParams.leftMargin = monster3XPos;
        layoutParams.topMargin =monster3YPos;

        monster3View.setLayoutParams(params);

        if(charHitByMonster(monsterView) || charHitByMonster(monster2View) || charHitByMonster(monster3View) ){
            Log.d("Char hit by monster","yes");
            goToPlayerLossMonstersPage();
        }
        //Forces view to update so we call collides after the params have been changed
        //getWindow().getDecorView().findViewById(android.R.id.content).invalidate();
    }
    private void generalMove(){
        Log.d("In char move:", "true");
        Log.d("Char x pos:", ""+xPos);
        Log.d("Char y pos:", ""+yPos);
        //We must do this here since views are null within onCreate
        if(wallLocations==null) {
            mazeDataObject = new mazeData();
            mazeDataObject.addLevelOneIds();
            Log.d("Wall loc in move:", ""+wallLocations);
            //For now we assume that current level is 1
            getWallLocations(1);
        }

        ArrayList<Integer> xYChange=pCharacter.getXYChangeOnMove();
        pCharacter.move();
        if(charCollidesWithWall(xYChange)){
            pCharacter.reverseNove();
        }
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
        /**Log.d("Treasure X:", ""+treasureXPos);
         Log.d("Treasure Y:", ""+treasureYPos);**/
        if(charReachedTreasure()){
            goToLevelCompletedPage();
            //This code fails to change to the new activity
            //newIntent(this, new Maze(R.layout.maze_box, true));

            //Log.d("Treasure reached", "true");
            //Toast.makeText(this, "Treasure reached", Toast.LENGTH_SHORT).show();
        }
        if(charHitByMonster(monsterView)|| charHitByMonster(monster2View)||charHitByMonster(monster3View)){
            goToPlayerLossMonstersPage();
        }
        //Forces view to update so we call collides after the params have been changed
        //getWindow().getDecorView().findViewById(android.R.id.content).invalidate();

    }
    private void setWallLocations(ArrayList<Integer>viewIdsInCurrLevel) {
        ImageView currentView;
        int cX;
        int cY;
        ArrayList currentWallSegment;
        ArrayList<Integer> currentWidthAndHeight;
        wallLocations=new ArrayList<ArrayList>();
        wallWidthsAndHeights=new ArrayList<ArrayList>();
        Log.d("C wall ids in setLoc:", ""+viewIdsInCurrLevel);
        for (int id : viewIdsInCurrLevel) {
            currentView = (ImageView) findViewById(id);
            Point wallXY=getLocationOnScreen(currentView);
            cX=wallXY.x;
            cY=wallXY.y;
            currentWallSegment=new ArrayList<Integer>();
            currentWidthAndHeight=new ArrayList<Integer>();
            currentWallSegment.add(cX);
            currentWallSegment.add(cY);
            //currentWidthAndHeight.add(currentView.getLayoutParams().width);
            //currentWidthAndHeight.add(currentView.getLayoutParams().height);

            //Seems to return twice the actual amount
            currentWidthAndHeight.add(currentView.getMeasuredWidth());
            currentWidthAndHeight.add(currentView.getMeasuredHeight());
            wallWidthsAndHeights.add(currentWidthAndHeight);
            wallLocations.add(currentWallSegment);
        }
        Log.d("C wall locs in setLoc:", ""+wallLocations);
    }

    public void goToLevelCompletedPage(){
        stopThreadLoop=true;
        Intent i = new Intent(this, LevelCompletedActivity.class);
        //Put extras in like this
        //i.putExtra("enteredUsername",enteredUsername);
        startActivity(i);
    }
    public void goToPlayerLossMonstersPage(){
        stopThreadLoop=true;
        Intent i = new Intent(this, playerLoss.class);
        startActivity(i);
    }
    //Otherwise, views go to 0,0 and thread gets collision between char and monsters
    //when back button is pressed, taking us back to character loss page.
    @Override
    public void onBackPressed() {
        stopThreadLoop=true;
        Intent i = new Intent(this, MenuActivity.class);
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

    /**class Coords {
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
    }**/

}
