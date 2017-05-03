package com.alphainc.game;
/**
 * Class to track what the top left corner of the screen is after translation
 * @author Daniel
 *
 */
public class Camera {
	
	/** Current (0, 0) coords after potential shift */
	private int mTopLeftX, mTopLeftY;
	
	public Camera() {
		mTopLeftX = mTopLeftY = 0;
	}
	
	public void setCurrentTopLeftCoords(int x, int y) {
		mTopLeftX = x;
		mTopLeftY = y;
	}
	
	public int getTopLeftX() {
		return mTopLeftX;
	}
	
	public int getTopLeftY() {
		return mTopLeftY;
	}
}