/**
 * CS349 Winter 2014
 * Assignment 4 Demo Code
 * Jeff Avery & Michael Terry
 */
package com.example.a4;

import android.util.Log;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/*
 * Class the contains a list of fruit to display.
 * Follows MVC pattern, with methods to add observers,
 * and notify them when the fruit list changes.
 */
public class Model extends Observable {
    // List of fruit that we want to display
    private ArrayList<Fruit> shapes = new ArrayList<Fruit>();

    private int count = 0;
    private int life = 5;
    private int time = 0;
    private double g = 3;
    private static int score = 0;
    private int highScore = 0;
    private int combo = 0;
    private int timeSinceLastCombo = 1000;
    
    // Constructor
    Model() {
        shapes.clear();
    }

    // Model methods
    // You may need to add more methods here, depending on required functionality.
    // For instance, this sample makes to effort to discard fruit from the list.
    public void remove(Fruit s){
    	if(shapes.remove(s)){
    		setChanged();
        	notifyObservers();
    	}else{
    		System.out.println("can't remove");
    	}
    }
    
    public void removeAll(){
    	shapes.removeAll(shapes);
    	setChanged();
    	notifyObservers();
    }
    
    
    public void add(Fruit s) {
        shapes.add(s);
        setChanged();
        notifyObservers();
    }

    public ArrayList<Fruit> getShapes() {
        return (ArrayList<Fruit>) shapes.clone();
    }

    // MVC methods
    // Basic MVC methods to bind view and model together.
    public void addObserver(Observer observer) {
        super.addObserver(observer);
    }

    // a helper to make it easier to initialize all observers
    public void initObservers() {
        setChanged();
        notifyObservers();
    }

    @Override
    public synchronized void deleteObserver(Observer observer) {
        super.deleteObserver(observer);
        setChanged();
        notifyObservers();
    }

    @Override
    public synchronized void deleteObservers() {
        super.deleteObservers();
        setChanged();
        notifyObservers();
    }
    
    //Game method
    public int getCount(){
    	return count;
    }
    
    public void addCount(){
    	this.count++;
    }
    
    public int getLife(){
    	return life;
    }
    
	public void loseLife() {
		
		life--;
	}
	
	public void reBorn(){
		life = 5;
	}
	
    public int getTimeInSec(){
    	return time;	
    }
    
    public int getSec(){
		return this.time%60;
    }
    
    public int getMin() {
		return this.time/60;
	}
    
    public double getG(){
    	return g;
    }

	public boolean getPoint() {
		Log.d("combo","score before = "+this.score);
		if(this.combo > 1){
			score--;
			score += combo * 2;
		}else{
			score ++;
		}
		Log.d("combo","score after = "+this.score);
		return true;
	}

	public void clearCombo() {
		this.combo = 0;
	}

	public void addCombo() {
		this.combo++;
	}
	
	public int getCombo(){
		return this.combo;
	}

	public int getScore() {
		return this.score;
	}

	public void clearScore() {
		this.score = 0;
	}
	
	public int getHighScore(){
		return this.highScore;
	}
	
	public void setHighScore(int newScore){
		if(newScore > this.highScore){
			this.highScore = newScore;
		}
	}
	
	public void addTime() {
		this.time++;
	}

	public void clearTime() {
		this.time = 0;
	}

	public void clearLife() {
		// TODO Auto-generated method stub
		this.life = 0;
	}

	public int getTimeSinceLastCombo() {
		// TODO Auto-generated method stub
		return this.timeSinceLastCombo;
	}
	
	public void addTimeSinceLastCombo(){
		this.timeSinceLastCombo++;
	}

	public void setTimeComboToZero() {
		// TODO Auto-generated method stub
		this.timeSinceLastCombo = 0;
	}
}
