package android.cs.spring18.mazegame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class mazeData extends MazeActivity {
    private ArrayList<Integer> levelOneViewIds= new ArrayList<Integer>();

    private static ArrayList<ArrayList> currentWallLocations=new ArrayList<ArrayList>();

    //Assume that current level starts as 1
    private int currentLevel=1;

    //Keeps track of the view names for level one of maze
    protected void addLevelOneIds(){
        levelOneViewIds.add(R.id.hBlock1);
        levelOneViewIds.add(R.id.hBlock2);
        levelOneViewIds.add(R.id.hBlock3);
        levelOneViewIds.add(R.id.hBlock4);
        levelOneViewIds.add(R.id.hBlock5);
        levelOneViewIds.add(R.id.hBlock6);
        levelOneViewIds.add(R.id.hBlock7);
        levelOneViewIds.add(R.id.hBlock8);
        levelOneViewIds.add(R.id.hBlock9);
        levelOneViewIds.add(R.id.hBlock10);
        levelOneViewIds.add(R.id.hBlock11);
        levelOneViewIds.add(R.id.hBlock12);
        levelOneViewIds.add(R.id.hBlock13);
        levelOneViewIds.add(R.id.hBlock14);
        levelOneViewIds.add(R.id.hBlock15);
        levelOneViewIds.add(R.id.hBlock16);
        levelOneViewIds.add(R.id.hBlock17);
        levelOneViewIds.add(R.id.hBlock18);
        levelOneViewIds.add(R.id.hBlock19);
        levelOneViewIds.add(R.id.hBlock20);

        levelOneViewIds.add(R.id.vBlock1);
        levelOneViewIds.add(R.id.vBlock2);
        levelOneViewIds.add(R.id.vBlock3);
        levelOneViewIds.add(R.id.vBlock4);
        levelOneViewIds.add(R.id.vBlock5);
        levelOneViewIds.add(R.id.vBlock6);
        levelOneViewIds.add(R.id.vBlock7);
        levelOneViewIds.add(R.id.vBlock8);
        levelOneViewIds.add(R.id.vBlock9);
        levelOneViewIds.add(R.id.vBlock10);
        levelOneViewIds.add(R.id.vBlock11);
        levelOneViewIds.add(R.id.vBlock12);
        levelOneViewIds.add(R.id.vBlock13);
        levelOneViewIds.add(R.id.vBlock14);
        levelOneViewIds.add(R.id.vBlock15);
        levelOneViewIds.add(R.id.vBlock16);
        levelOneViewIds.add(R.id.vBlock17);
        levelOneViewIds.add(R.id.vBlock18);
        levelOneViewIds.add(R.id.vBlock19);
        levelOneViewIds.add(R.id.vBlock20);
        levelOneViewIds.add(R.id.vBlock21);
        levelOneViewIds.add(R.id.vBlock22);
        levelOneViewIds.add(R.id.vBlock23);
        levelOneViewIds.add(R.id.vBlock24);
        levelOneViewIds.add(R.id.vBlock25);
        levelOneViewIds.add(R.id.vBlock26);
        levelOneViewIds.add(R.id.vBlock27);
        levelOneViewIds.add(R.id.vBlock28);
        levelOneViewIds.add(R.id.vBlock29);
        levelOneViewIds.add(R.id.vBlock30);
        levelOneViewIds.add(R.id.vBlock31);
        levelOneViewIds.add(R.id.vBlock32);
    }
    public void incCurrLevel(){
        currentLevel+=1;
    }
    public void decCurrentLevel(){
        currentLevel-=1;
    }
    public void setCurrentLevel(int newLevel){
        currentLevel=newLevel;
    }
    public ArrayList getCurrWallLocations(){
        return currentWallLocations;
    }

    public ArrayList getLevelOneIdsList(){
        return levelOneViewIds;
    }



}
