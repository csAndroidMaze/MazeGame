package android.cs.spring18.mazegame;

import android.view.View;

import java.util.List;


public class MazeCharacter {

    private int xPos;
    private int yPos;

    private int xMoveDist=5;
    public int yMoveDist=5;

    public int directionFaced=0;

    public MazeCharacter(int startX,int startY) {
        xPos=startX;
        yPos=startY;
    }


    public void setDirectionFaced(int direction){
        //Must be 0,1,2, or 3
        directionFaced=direction;
    }
    public void setPosition(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    public int getXPos() {
        return this.xPos;
    }

    public int getYPos() {
        return this.yPos;
    }

    public int getDirectionFaced(){
        return directionFaced;
    }
    public void move() {
        //Facing up
        if(directionFaced==0){
            yPos+=yMoveDist;
        }
        //Facing down
        else if(directionFaced==1){
            yPos-=yMoveDist;
        }
        //Facing right
        else if(directionFaced==2){
            xPos+=xMoveDist;
        }
        //Facing left
        else if(directionFaced==3){
            xPos-=xMoveDist;
        }

    }
}
