package com.alphainc.game;
/**
 * Class to track what the top left corner of the screen is after translation
 * @author Daniel
 *
 */
public class Camera {
	
	/** Current (0, 0) coords after potential shift */
	private float mTopLeftX, mTopLeftY;
	
	public Camera() {
		mTopLeftX = mTopLeftY = 0;
	}
	
	public void setCurrentTopLeftCoords(float x, float y) {
		mTopLeftX = x;
		mTopLeftY = y;
	}
	
	public void adjustCoords(float x, float y) {
		mTopLeftX += x;
		mTopLeftY += y;
	}
	
	public float getTopLeftX() {
		return mTopLeftX;
	}
	
	public float getTopLeftY() {
		return mTopLeftY;
	}
}
