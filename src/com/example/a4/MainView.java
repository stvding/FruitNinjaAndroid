/**
 * CS349 Winter 2014
 * Assignment 4 Demo Code
 * Jeff Avery & Michael Terry
 */
package com.example.a4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.*;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import com.example.a4complete.R;

/*
 * View of the main game area.
 * Displays pieces of fruit, and allows players to slice them.
 */
public class MainView extends View implements Observer {
    private final Model model;
    private final MouseDrag drag = new MouseDrag();
//    public final int SCREEN_WIDTH = 480;
//    public final int SCREEN_HEIGHT = 800;
    public final int SCREEN_WIDTH = 720;
    public final int SCREEN_HEIGHT = 1280;

    // Constructor
    MainView(Context context, Model m) {
        super(context);

        // register this view with the model
        model = m;
        model.addObserver(this);

        // TODO BEGIN CS349
        // test fruit, take this out before handing in!
//        Path path = new Path();
//        path.addCircle(50, 50, 50, null);
        
//        Fruit f1 = new Fruit(new float[] {0,60,60,0,120,0,180,60,180,120,120,180,60,180,0,120},false,-1);
//        f1.translate(100,700);
//        model.add(f1);
//        
//        Fruit f2 = new Fruit(new float[] {0,60,60,0,120,0,180,60,180,120,120,180,60,180,0,120},false,-1);
//        f2.translate(200, 1280);
//        model.add(f2);
        // TODO END CS349

        // add controller
        // capture touch movement, and determine if we intersect a shape
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Log.d(getResources().getString(R.string.app_name), "Touch down");
                        drag.start(event.getX(), event.getY());
                        break;

                    case MotionEvent.ACTION_UP:
                        // Log.d(getResources().getString(R.string.app_name), "Touch release");
                        drag.stop(event.getX(), event.getY());

                        // find intersected shapes
                        Iterator<Fruit> i = model.getShapes().iterator();
                        while(i.hasNext()) {
                            Fruit s = i.next();
                            if (s.intersects(drag.getStart(), drag.getEnd())) {
                            	model.remove(s);
                            	model.addCount();
                            	model.addCombo();
                            	if(model.getPoint()){
                            		//Log.d("combo","GET POINT");
                            	}
                            	
	
                                try {
                                    Fruit[] newFruits = s.split(drag.getStart(), drag.getEnd());

                                    // TODO BEGIN CS349
                                    // you may want to place the fruit more carefully than this
                                    newFruits[0].translate(0, -10);
                                    newFruits[1].translate(0, +10);
                                    // TODO END CS349
                                    model.add(newFruits[0]);
                                    model.add(newFruits[1]);

                                    // TODO BEGIN CS349
                                    // delete original fruit from model
                                    // TODO END CS349

                                } catch (Exception ex) {
                                    Log.e("fruit_ninja", "Error: " + ex.getMessage());
                                }
                            } else {
                                //s.setFillColor(Color.BLUE);
                            }
                            invalidate();
                        }
                        break;
                }
                return true;
            }
        });
    }

    // inner class to track mouse drag
    // a better solution *might* be to dynamically track touch movement
    // in the controller above
    class MouseDrag {
        private float startx, starty;
        private float endx, endy;

        protected PointF getStart() { return new PointF(startx, starty); }
        protected PointF getEnd() { return new PointF(endx, endy); }

        protected void start(float x, float y) {
            this.startx = x;
            this.starty = y;
        }

        protected void stop(float x, float y) {
            this.endx = x;
            this.endy = y;
        }
    }

    //@SuppressLint("DrawAllocation")
	@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        // draw background
        setBackgroundColor(Color.TRANSPARENT);
        //System.out.println("in draw");
        // draw all pieces of fruit
        for (Fruit s : model.getShapes()) {
        	
        	//System.out.println("y = " + Graphics2D.getTranslateValues(s.getTransform())[1]);
        	s.draw(canvas);
			//System.out.println("x = "+Graphics2D.getTranslateValues(s.getTransform())[0]);
        	s.translate(s.vx, s.vy);
        	
        	
        	if(Graphics2D.getTranslateValues(s.getTransform())[1] <= SCREEN_WIDTH){
				if (Graphics2D.getTranslateValues(s.getTransform())[0] <= 0) {
					s.vx = 10;
				} else if (Graphics2D.getTranslateValues(s.getTransform())[0] >= SCREEN_HEIGHT){
					s.vx = -10;
				}
        	}
			if (!s.subPiece) {
				if (Graphics2D.getTranslateValues(s.getTransform())[1] <= SCREEN_WIDTH && 
						Graphics2D.getTranslateValues(s.getTransform())[1] >0) {
					s.vy += 0.6 * model.getG();
				} else if (Graphics2D.getTranslateValues(s.getTransform())[1] <= 0 ){
					// s.vy = s.vyreal;
					s.vy += 5*model.getG(); 
				}
			} else {
				//Log.e("Fruit Fly","in sub piece");
				if (s.piece == 0) {
					s.vx = -10;
				} else if (s.piece == 1) {
					s.vx = 10;
				}
				s.vy = 15;
				s.vy += 2 * model.getG();
			}
        	
        	
        	
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        invalidate();
    }
}
