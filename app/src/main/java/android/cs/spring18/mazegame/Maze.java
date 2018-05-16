package android.cs.spring18.mazegame;


import android.view.View;

public class Maze {

    private boolean mMazeSolved;
    private static int mMazeLayout;

    public static int mCurrentMazeIndex = 0;


   /* public static Maze[] mMazes = new Maze[] {
            new Maze(R.layout.maze_box, false),
            //new Maze(R.layout.maze_box1, false)
    };*/

    public static int[] mazeLayouts = new int[] {
            R.layout.maze_box,
            //R.layout.maze_box1
    };


    public Maze(int layout, boolean isMazeSolved) {
        this.mMazeLayout = layout;
        this.mMazeSolved = isMazeSolved;

    }

    public boolean isMazeSolved() {
        return this.mMazeSolved;
    }

    public void setMazeSolved(boolean isMazeSolved) {
        this.mMazeSolved = isMazeSolved;
    }

    public int getMazeLayout() {

        return this.mMazeLayout;
    }



}
