/**
 *  @file GameView.java
 *  @author ALSO wu
 *  @description: 1) 
 *  ANY BUGS PLEASE CONNECT ME: alsoblack222#gmail.com
 */
package com.android.myapp.game;

import java.util.Deque;
import java.util.LinkedList;

import android.os.Handler;

public class Game {
	
	private int mode =0;
	private int mGameWidth = 0;
	private int mGameHeight = 0;
	
	public static final int SCALE_SMALL = 8;
    public static final int SCALE_MEDIUM = 10;
    public static final int SCALE_LARGE = 11;
    
    public static final int NORMAL_MOD = 0;
    public static final int EASY_MOD = 1;
    public static final int HARD_MOD = 2;
    
    public static final int ROLLBACK_NUM = 2;
 
    int[][] mGameMap = null; 
    Deque<Coordinate> mActions ;
	
    Player player = null;
   
    private Handler mNotify;
    
    
    public Game(Handler handle, Player me){
    	this(handle, me, SCALE_MEDIUM, SCALE_MEDIUM);
    }
    
    public Game(Handler handle, Player me, int width, int height){
    	this.player = me;
    	this.mNotify = handle;
    	this.mGameWidth = width;
    	this.mGameHeight = height;
    	this.mGameMap = new int[mGameHeight][mGameWidth];
        mActions = new LinkedList<Coordinate>();
        obstacleCircleInit();
    }
    
    public void setMode(int mode){
    	this.mode = mode;
    }
    
    public int getMode(){
    	return this.mode;
    }
    
    public int getBoardWidth(){
    	return mGameWidth;
    }
    
    public int getBoardHeight(){
    	return mGameHeight;
    }
    
    public int[][] getGameMap(){
    	return mGameMap;
    }
    	
    public Deque<Coordinate> getActions(){
    	return mActions;
    }
    
    // reset the game
    public void reset(){
    	mGameMap = new int[mGameHeight][mGameWidth];
    	mActions.clear();
    	obstacleCircleInit();
    	
    }
    
    private boolean isGameEnd(){
    	//@todo
    	return false;
    }
    
    private void senGameResult(){
    	// @todo
    }
    
    public void addObstacleCircle(int x, int y){
    	// @todo 
    }
    
    // 
    private void obstacleCircleInit(){
    	// TODO
    }
}
