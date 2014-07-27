/**
 *  @file GameView.java
 *  @author ALSO wu
 *  @description: 1) draw the game board( 11 * 11 circles).
 *  			  2) handle the color change of these circles.
 *  			  3) deal with the animal movement(from one circle to another.
 *  ANY BUGS PLEASE CONNECT ME: alsoblack222#gmail.com
 *  :w
 */
package com.android.myapp.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	// for debug
	private static final String ALSO = "Game view";
	private static final boolean DEBUG = true;

	private SurfaceHolder myHolder = null;

	private Paint boardPaint = new Paint();
	private Paint circlePaint = new Paint();
	private Paint clear = new Paint(); // clear paint

	private int boardColor = 0;
	private int obCircleColor = 0;
	// private float boardWidth = 0.0f; // ±ß¿í
	// private float anchorWidth = 0.0f;
	private int circleSize = 0;
	private int cBoardWidth = 0;
	private int cBoardHeight = 0;

	private Coordinate focus = null;

	private Context mContext = null;

	private Bitmap cat;

	/*
	 * private Bitmap circle;
	 */
	private Game mGame;

	public int[][] circleArray = null; // these circles are actually stored with
										// a planar array

	public GameView(Context context) {
		this(context, null);
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		obCircleColor = Color.rgb(136, 134, 55);

		boardColor = Color.rgb(154, 205, 50);
		// boardWidth =
		// getResources().getDimensionPixelSize(R.dimen.boardWidth);
		// anchorWidth =
		// getResources().getDimensionPixelSize(R.dimen.anchorWidth);
		focus = new Coordinate();
		init();
	}

	public void init() {
		myHolder = this.getHolder();
		myHolder.addCallback(this);

		myHolder.setFormat(PixelFormat.TRANSLUCENT);
		setZOrderOnTop(true);
		circlePaint.setAntiAlias(true);
		circlePaint.setStyle(Style.FILL);
		circlePaint.setColor(obCircleColor);
		boardPaint.setStyle(Style.FILL);
		boardPaint.setColor(boardColor);
		clear.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
		setFocusable(true);
	}

	public void setGame(Game game) {
		mGame = game;
		requestLayout();
	}

	// background
	private void drawBoard() {
		Canvas canvas = myHolder.lockCanvas();
		if (myHolder == null || canvas == null) {
			return;
		}
		drawBgCircles(canvas);

		myHolder.unlockCanvasAndPost(canvas);
	}

	// background circles
	private void drawBgCircles(Canvas canvas) {
		int startX = (int) (circleSize / 1.25);
		int startY = (int) (circleSize * 1.5);
		float radius = circleSize / 2;

		for (int i = 0; i < cBoardWidth; ++i) {
			for (int j = 0; j < cBoardHeight; ++j) {
				float cx = startX + i * circleSize
						+ ((j % 2 == 1) ? radius : 0);
				float cy = startY + j * circleSize;
				canvas.drawCircle(cx, cy, radius, boardPaint);
			}
		}

	}

	// class Game will judge whether the circle has been changed
	private boolean canAdd(int newx, int newy, Coordinate focus) {
		return (newx < focus.x + circleSize / 2)
				&& (newx > focus.x - circleSize / 2)
				&& (newy < focus.y + circleSize / 2)
				&& (newy > focus.y - circleSize / 2);
	}

	private void addObstacleCircle(int x, int y) {
		if (mGame == null) {
			Log.d(ALSO, "game can not be null");
			return;
		}
		mGame.addObstacleCircle(x, y);
		drawGame();
	}

	private void drawObstacleCircles(Canvas canvas) {
		int[][] obMap = mGame.getGameMap();
		int startX = (int) (circleSize / 1.25);
		int startY = (int) (circleSize * 1.5);
		float radius = circleSize / 2;

		for (int x = 0; x < obMap.length; ++x) {
			for (int y = 0; y < obMap[0].length; ++y) {
				if (obMap[x][y] == 1) {
					float cx = startX + x * circleSize
							+ ((y % 2 == 1) ? radius : 0);
					float cy = startY + y * circleSize;
					canvas.drawCircle(cx, cy, radius, circlePaint);
				}
			}
		}

	}

	public void drawGame() {
		Canvas canvas = myHolder.lockCanvas();
		if (myHolder == null || canvas == null) {
			Log.d(ALSO, "mholde=" + myHolder + "  canvas=" + canvas);
			return;
		}

		// clear screen
		canvas.drawPaint(clear);
		drawBgCircles(canvas);
		drawObstacleCircles(canvas);
		myHolder.unlockCanvasAndPost(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		float x = event.getX();
		float y = event.getY();
		float radius = circleSize / 2;
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			focus.y = (int) (y / circleSize);
			focus.x = (int) (x / circleSize + (focus.y % 2 == 1 ? 0 : radius));
			drawGame();
			break;
		case MotionEvent.ACTION_UP:
			int newy = (int) (y / circleSize);
			int newx = (int) (x / circleSize + (focus.y % 2 == 0 ? 0 : radius));
			if (canAdd(newx, newy, focus) == true) {
				addObstacleCircle(focus.x, focus.y);
			}
			drawGame();
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// init the game board
		drawBoard();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = View.MeasureSpec.getSize(widthMeasureSpec);
		if (mGame != null) {
			if (width % mGame.getBoardWidth() == 0) {
				float scale = ((float) mGame.getBoardHeight())
						/ mGame.getBoardWidth();
				int height = (int) (width * scale);
				setMeasuredDimension(width, height);
			} else {
				width = width / mGame.getBoardWidth() * mGame.getBoardWidth();
				float scale = ((float) mGame.getBoardHeight())
						/ mGame.getBoardWidth();
				int height = (int) (width * scale);
				setMeasuredDimension(width, height);
			}
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		// debug
		if (DEBUG)
			Log.d(ALSO, "left=" + left + "  top=" + top + " right=" + right
					+ " bottom=" + bottom);
		if (mGame != null) {
			cBoardWidth = mGame.getBoardWidth();
			cBoardHeight = mGame.getBoardHeight();
			circleSize = (int) ((right - left) / (cBoardWidth + 1));
			Log.d(ALSO, "circleSize = " + circleSize + " cBoardHeight = "
					+ cBoardHeight + " cBoardWidth = " + cBoardWidth);
		}// if
	}

}
