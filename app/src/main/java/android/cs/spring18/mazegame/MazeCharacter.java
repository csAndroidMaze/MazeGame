package android.cs.spring18.mazegame;

import android.view.View;

import java.util.List;


public class MazeCharacter {

    private int xPos;
    private int yPos;



  /*  public class MazeCharacter(View v) {
        use the given view v to set up the layout
        setPosition to wherever the start is

    }*/

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

    public void move(int x, int y) {
        setPosition(x, y);

    }

}
