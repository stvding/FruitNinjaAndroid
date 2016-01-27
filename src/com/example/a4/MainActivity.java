/**
 * CS349 Winter 2014
 * Assignment 4 Demo Code
 * Jeff Avery
 */
package com.example.a4;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a4complete.R;

public class MainActivity extends Activity {
	private Model model;
	private MainView mainView;
	private TitleView titleView;
	public static Point displaySize;

    public final int SCREEN_WIDTH = 720;
    public final int SCREEN_HEIGHT = 1280;

	// Resources res= this.getResources();

	Button restartButton, pauseButton, resumeButton;
	TextView endGameTextView, comboTextView, scoreTextView, timeTextView;
	ImageView life1, life2, life3, life4, life5;

	// Timer
	Handler timerHandler = new Handler();
	Runnable timerRunnable = new Runnable() {
		@Override
		public void run() {
			timerHandler.postDelayed(this, 20);

			if (model.getCombo() > 1) {
				Log.d("combo", "combo = " + model.getCombo());
				comboTextView.setVisibility(View.VISIBLE);
				model.setTimeComboToZero();
				// comboTextView.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,
				// android.R.anim.bounce_interpolator));
				comboTextView.setText(model.getCombo()
						+ " FRUITS COMBO!!!!\n    +" + model.getCombo()
						* model.getCombo());
			}

			if (model.getTimeSinceLastCombo() > 1) {
				comboTextView.setVisibility(View.INVISIBLE);
			}

			model.clearCombo();
			model.setHighScore(model.getScore());

			Log.d("running", "run");
			ArrayList<Fruit> fruitToRemove = new ArrayList<Fruit>();
			for (Fruit f : model.getShapes()) {
				if (Graphics2D.getTranslateValues(f.getTransform())[1] > SCREEN_WIDTH + 200) {
					fruitToRemove.add(f);
				}
			}

			for (Fruit ff : fruitToRemove) {
				model.remove(ff);
				if (ff.isSliced == false && ff.subPiece == false) {
					model.loseLife();
				}
			}

			switch (model.getLife()) {
			case 5:
				life1.setVisibility(View.VISIBLE);
				life2.setVisibility(View.VISIBLE);
				life3.setVisibility(View.VISIBLE);
				life4.setVisibility(View.VISIBLE);
				life5.setVisibility(View.VISIBLE);
				break;

			case 4:
				life1.setVisibility(View.VISIBLE);
				life2.setVisibility(View.VISIBLE);
				life3.setVisibility(View.VISIBLE);
				life4.setVisibility(View.VISIBLE);
				life5.setVisibility(View.INVISIBLE);
				break;

			case 3:
				life1.setVisibility(View.VISIBLE);
				life2.setVisibility(View.VISIBLE);
				life3.setVisibility(View.VISIBLE);
				life4.setVisibility(View.INVISIBLE);
				life5.setVisibility(View.INVISIBLE);
				break;

			case 2:
				life1.setVisibility(View.VISIBLE);
				life2.setVisibility(View.VISIBLE);
				life3.setVisibility(View.INVISIBLE);
				life4.setVisibility(View.INVISIBLE);
				life5.setVisibility(View.INVISIBLE);
				break;

			case 1:
				life1.setVisibility(View.VISIBLE);
				life2.setVisibility(View.INVISIBLE);
				life3.setVisibility(View.INVISIBLE);
				life4.setVisibility(View.INVISIBLE);
				life5.setVisibility(View.INVISIBLE);
				break;

			case 0:
				life1.setVisibility(View.INVISIBLE);
				life2.setVisibility(View.INVISIBLE);
				life3.setVisibility(View.INVISIBLE);
				life4.setVisibility(View.INVISIBLE);
				life5.setVisibility(View.INVISIBLE);
			default:
				break;
			}

			scoreTextView.setText("Score = " + model.getScore());

			if (model.getMin() < 10) {
				if (model.getSec() < 10) {
					timeTextView.setText("Time = 0" + model.getMin() + ":0"
							+ model.getSec());
				} else {
					timeTextView.setText("Time = 0" + model.getMin() + ":"
							+ model.getSec());
				}
			} else {
				if (model.getSec() < 10) {
					timeTextView.setText("Time = " + model.getMin() + ":0"
							+ model.getSec());
				} else {
					timeTextView.setText("Time = " + model.getMin() + ":"
							+ model.getSec());
				}
			}

			mainView.update(null, null);
			if (model.getLife() <= 0) {
				Log.d("running", "not run");
				timerHandler.removeCallbacks(timerRunnable);
				fruitHandler.removeCallbacks(fruitRunnable);
				timerH.removeCallbacks(timerR);

				comboTextView.setVisibility(View.INVISIBLE);
				endGameTextView.setText("GAME OVER!\n" + "SCORE: "
						+ model.getScore() + "\n" + "BEST SCORE: "
						+ model.getHighScore());
				model.removeAll();
				model.clearLife();
				model.clearCombo();
				model.clearTime();
				resumeButton.setVisibility(View.INVISIBLE);
				pauseButton.setVisibility(View.INVISIBLE);
				restartButton.setVisibility(View.VISIBLE);
				endGameTextView.setVisibility(View.VISIBLE);
			}
		}
	};

	// fruit timer
	Handler fruitHandler = new Handler();
	Runnable fruitRunnable = new Runnable() {
		@Override
		public void run() {

			// if (model.getLife() > 0) {
			fruitHandler.postDelayed(this, 3000);
			Log.d("running", "creating fruits");

			int numOfFruits = 1 + (int) (Math.random() * ((3 - 1) + 1));
			int rand;
			int fruitType;
			for (int i = 0; i < numOfFruits; i++) {
				rand = 0 + (int) (Math.random() * ((SCREEN_HEIGHT - 0) + 1));
				fruitType = 0 + (int) (Math.random() * ((1 - 0) + 1));
				Fruit f;
				if (fruitType == 0) {
					f = new Fruit(new double[] { 1, 43, 2, 46, 3, 49, 4, 51, 5,
							53, 6, 54, 7, 56, 8, 57, 9, 58, 10, 59, 11, 60, 12,
							61, 13, 62, 14, 63, 15, 63, 16, 64, 17, 65, 18, 65,
							19, 66, 20, 66, 21, 67, 22, 67, 23, 67, 24, 68, 25,
							68, 26, 68, 27, 69, 28, 69, 29, 69, 30, 69, 31, 69,
							32, 69, 33, 69, 34, 69, 35, 70, 36, 69, 37, 69, 38,
							69, 39, 69, 40, 69, 41, 69, 42, 69, 43, 69, 44, 68,
							45, 68, 46, 68, 47, 67, 48, 67, 49, 67, 50, 66, 51,
							66, 52, 65, 53, 65, 54, 64, 55, 63, 56, 63, 57, 62,
							58, 61, 59, 60, 60, 59, 61, 58, 62, 57, 63, 56, 64,
							54, 65, 53, 66, 51, 67, 49, 68, 46, 69, 43, 70, 35,
							69, 27, 68, 24, 67, 21, 66, 19, 65, 17, 64, 16, 63,
							14, 62, 13, 61, 12, 60, 11, 59, 10, 58, 9, 57, 8,
							56, 7, 55, 7, 54, 6, 53, 5, 52, 5, 51, 4, 50, 4,
							49, 3, 48, 3, 47, 3, 46, 2, 45, 2, 44, 2, 43, 1,
							42, 1, 41, 1, 40, 1, 39, 1, 38, 1, 37, 1, 36, 1,
							35, 0, 34, 1, 33, 1, 32, 1, 31, 1, 30, 1, 29, 1,
							28, 1, 27, 1, 26, 2, 25, 2, 24, 2, 23, 3, 22, 3,
							21, 3, 20, 4, 19, 4, 18, 5, 17, 5, 16, 6, 15, 7,
							14, 7, 13, 8, 12, 9, 11, 10, 10, 11, 9, 12, 8, 13,
							7, 14, 6, 16, 5, 17, 4, 19, 3, 21, 2, 24, 1, 43 },
							false, -1);
				} else {
					f = new Fruit(
							new double[] { 16, 64, 17, 65, 18, 65, 19, 66, 20,
									66, 21, 67, 22, 67, 23, 67, 24, 68, 25, 68,
									26, 68, 27, 69, 28, 69, 29, 69, 30, 69, 31,
									69, 32, 69, 33, 69, 34, 69, 35, 70, 36, 69,
									37, 69, 38, 69, 39, 69, 40, 69, 41, 69, 42,
									69, 43, 69, 44, 68, 45, 68, 46, 68, 47, 67,
									48, 67, 49, 67, 50, 66, 51, 66, 52, 65, 53,
									65, 54, 64, 55, 63, 56, 63, 57, 62, 58, 61,
									59, 60, 60, 59, 61, 58, 62, 57, 63, 56, 64,
									54, 65, 53, 66, 51, 67, 49, 68, 46, 69, 43,
									70, 35, 69, 27, 68, 24, 67, 21, 66, 19, 65,
									17, 64, 16, 63, 14, 62, 13, 61, 12, 60, 11,
									59, 10, 58, 9, 57, 8, 56, 7, 40,40,16,64}, false, -1);

				}
				// f.res = res;
				f.scale(2, 2);
				f.translate(rand, SCREEN_WIDTH + 0
						+ (int) (Math.random() * ((200 - 0) + 1)));
				f.vx = (rand <= SCREEN_HEIGHT / 2) ? 5 : -5;
				model.add(f);
			}
			mainView.update(null, null);
		}
	};

	Handler timerH = new Handler();
	Runnable timerR = new Runnable() {
		public void run() {
			timerH.postDelayed(this, 1000);
			model.addTime();
			model.addTimeSinceLastCombo();
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setTitle("CS349 A4 Demo");

		// save display size
		Display display = getWindowManager().getDefaultDisplay();
		displaySize = new Point();
		display.getSize(displaySize);

		// initialize model
		model = new Model();

		// set view
		setContentView(R.layout.main);

		timerHandler.post(timerRunnable);
		fruitHandler.post(fruitRunnable);
		timerH.post(timerR);

		restartButton = (Button) findViewById(R.id.Restart);
		pauseButton = (Button) findViewById(R.id.Pause);
		resumeButton = (Button) findViewById(R.id.Resume);
		endGameTextView = (TextView) findViewById(R.id.endGameText);
		life1 = (ImageView) findViewById(R.id.life1);
		life2 = (ImageView) findViewById(R.id.life2);
		life3 = (ImageView) findViewById(R.id.life3);
		life4 = (ImageView) findViewById(R.id.life4);
		life5 = (ImageView) findViewById(R.id.life5);
		comboTextView = (TextView) findViewById(R.id.combo);
		scoreTextView = (TextView) findViewById(R.id.score);
		timeTextView = (TextView) findViewById(R.id.time);

		restartButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				restartButton.setVisibility(View.INVISIBLE);
				endGameTextView.setVisibility(View.INVISIBLE);
				life1.setVisibility(View.VISIBLE);
				life2.setVisibility(View.VISIBLE);
				life3.setVisibility(View.VISIBLE);
				life4.setVisibility(View.VISIBLE);
				life5.setVisibility(View.VISIBLE);
				resumeButton.setVisibility(View.INVISIBLE);
				pauseButton.setVisibility(View.VISIBLE);
				model.clearScore();
				model.reBorn();
				timerHandler.post(timerRunnable);
				fruitHandler.post(fruitRunnable);
				timerH.post(timerR);
			}
		});

		pauseButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("Button", "Pause");
				timerHandler.removeCallbacks(timerRunnable);
				fruitHandler.removeCallbacks(fruitRunnable);
				timerH.removeCallbacks(timerR);
				pauseButton.setVisibility(View.INVISIBLE);
				resumeButton.setVisibility(View.VISIBLE);
				endGameTextView.setVisibility(View.VISIBLE);
				endGameTextView.setText("PAUSE");
				endGameTextView.setTextSize(96);
			}
		});

		resumeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				timerH.post(timerR);
				timerHandler.post(timerRunnable);
				fruitHandler.post(fruitRunnable);
				pauseButton.setVisibility(View.VISIBLE);
				resumeButton.setVisibility(View.INVISIBLE);
				endGameTextView.setVisibility(View.INVISIBLE);
				endGameTextView.setTextSize(32);
			}
		});

		endGameTextView.setTextSize(32);
		endGameTextView.setTextColor(Color.YELLOW);

		scoreTextView.setTextSize(20);
		scoreTextView.setTextColor(Color.YELLOW);

		comboTextView.setTextSize(32);
		comboTextView.setTextColor(Color.CYAN);

		timeTextView.setTextSize(20);
		timeTextView.setTextColor(Color.YELLOW);

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// create the views and add them to the main activity
		titleView = new TitleView(this.getApplicationContext(), model);
		ViewGroup v1 = (ViewGroup) findViewById(R.id.main_1);
		v1.addView(titleView);

		mainView = new MainView(this.getApplicationContext(), model);
		ViewGroup v2 = (ViewGroup) findViewById(R.id.main_2);
		v2.addView(mainView);

		// notify all views
		model.initObservers();
	}
}
