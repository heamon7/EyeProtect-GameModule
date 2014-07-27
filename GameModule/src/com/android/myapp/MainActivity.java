package com.android.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.myapp.game.Game;
import com.android.myapp.game.GameView;
import com.android.myapp.game.Player;

public class MainActivity extends Activity implements OnClickListener {

    boolean stop = true;
    
    private GameView mGameView;
    private Game mGame;
    private Player me; //user
    
    private Button restart;
    private Button undo;
    
	private Handler mRefreshHandler;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initGame();
        initCat();
    }
    
    private void initView(){
    	mGameView = (GameView) findViewById(R.id.game_view);
        restart = (Button) findViewById(R.id.restart_button);
        undo = (Button) findViewById(R.id.undo_button);
        restart.setOnClickListener(this);
        undo.setOnClickListener(this);
    }
    
    private void initGame(){
        mGame = new Game(mRefreshHandler, me);
        mGameView.setGame(mGame);
    	
    }
    
    private void initCat(){
    	
    }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	}
}