package com.alphainc.game.components;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;
/**
 * This class is for creating a counter with plus and minus buttons
 * @author Daniel Lee
 *
 */
public class PlayerCounter extends AbstractComponent {
	/** In order, the images for the minus button, plus button, default center image, and 8 center images */
	private Image minusImage, plusImage, centerDefault, centerNum[];
	/** TODO: Currently unused, will implement animations later*/
	private MouseOverArea minusArea, plusArea;
	/** x, y locations, width and height, number of players wanted */
	private int mX, mY, mWidth, mHeight, mPlayerCounter;
	
	/**
	 * Creates a counter at the specified location with specified width
	 * and height.
	 * 
	 * @param container The game container
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param width width
	 * @param height height
	 */
	public PlayerCounter(GUIContext container, int x, int y, int width, int height) {
		super(container);
		mX = x;
		mY = y;
		mWidth = width;
		mHeight = height;
		/** Used to evenly draw the image, the image is actually 3 pieces split
		 * into 4 even sections: 1 for minus, 2 for center, 1 for plus.
		 * 
		 * Note: The width should ALWAYS be 4 * height or else the images will come
		 * 		 out stretched. Also, all numbers must be divisible by 4, evenly.
		 */
		int sections = width / 4;
		try {
			//0.0625, 0.0625, 0.128
			minusImage = new Image("res/playercounter/minus_icon.png").getScaledCopy(height, height);
			plusImage = new Image("res/playercounter/plus_icon.png").getScaledCopy(height, height);
			centerDefault = new Image("res/playercounter/counter_icon.png").getScaledCopy(sections * 2, height);
			centerNum = new Image[8];
			for(int ii = 0; ii < 8; ii++) {
				centerNum[ii] = new Image("res/playercounter/playercounter" + (ii + 1) + ".png").getScaledCopy(sections * 2, height);
			}
		} catch(SlickException se) {
			System.err.println("Exception loading PlayerCounter minus and plus images");
			se.printStackTrace();
		}
		mPlayerCounter = 1;
	}
	
	public PlayerCounter(GUIContext container) {
		this(container, 0, 0, 512, 64);
	}
	
	public PlayerCounter(GUIContext container, int x, int y) {
		this(container, x, y, 512, 64);
	}
	/**
	 * Gets current player count
	 * @return player count
	 */
	public int getPlayerCount() {
		return mPlayerCounter;
	}
	/**
	 * Increases player count
	 */
	public void increaseCount() {
			mPlayerCounter++;
			if(mPlayerCounter > 8)
				mPlayerCounter = 8;
	}
	/**
	 * Decreases player count
	 */
	public void decreaseCount() {
			mPlayerCounter--;
			if(mPlayerCounter < 1)
				mPlayerCounter = 1;
	}
	/**
	 * Gets the width of the minus button
	 * @return width of the minus button
	 */
	public int getMinusButtonWidth() {
		return minusImage.getWidth();
	}
	/**
	 * Gets the width of the plus button
	 * @return width of the plus button
	 */
	public int getPlusButtonWidth() {
		return plusImage.getWidth();
	}
	/**
	 * Gets the height of the minus button
	 * @return height of the minus button
	 */
	public int getMinusButtonHeight() {
		return minusImage.getHeight();
	}
	/**
	 * Gets the height of the plus button
	 * @return height of the plus button
	 */
	public int getPlusButtonHeight() {
		return plusImage.getHeight();
	}
	/**
	 * Gets the bounds of the minus button
	 * @param choice the side of the bounds
	 * @return minus button bound
	 */
	public int getMinusBounds(int choice) {
		switch(choice) {
			case 0:
				//Left
				return mX;
			case 1:
				//Top
				return mY;
			case 2:
				//Right
				return mX + minusImage.getWidth();
			case 3:
				//Bottom
				return mY + minusImage.getHeight();
			default:
				System.out.println("lmao try again");
				return 0;
		}
	}
	/**
	 * Gets the bounds of the plus button
	 * @param choice the side of the bounds
	 * @return plus button bound
	 */
	public int getPlusBounds(int choice) {
		switch(choice) {
			case 0:
				//Left
				return mX + minusImage.getWidth() + centerDefault.getWidth();
			case 1:
				//Top
				return mY;
			case 2:
				//Right
				return mX + minusImage.getWidth() + centerDefault.getWidth() + plusImage.getWidth();
			case 3:
				//Bottom
				return mY + plusImage.getHeight();
			default:
				System.out.println("lmao try again");
				return 0;
		}
	}

	@Override
	public void render(GUIContext container, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.drawImage(minusImage, mX, mY);
		g.drawImage(plusImage, mX + minusImage.getWidth() + centerDefault.getWidth(), mY);
		renderCenterImage(g, mPlayerCounter);
		//g.drawImage(centerDefault, mX + minusImage.getWidth(), mY);
	}
	/**
	 * Depending on the mPlayerCounter, will render the appropriate center image
	 * @param g Graphics object
	 * @param count the current player count
	 */
	private void renderCenterImage(Graphics g, int count) {
		if(count != 0)
			g.drawImage(centerNum[count - 1], mX + minusImage.getWidth(), mY);
		else
			g.drawImage(centerDefault, mX + minusImage.getWidth(), mY);
	}

	@Override
	public void setLocation(int x, int y) {}

	@Override
	public int getX() {
		return mX;
	}

	@Override
	public int getY() {
		return mY;
	}

	@Override
	public int getWidth() {
		return mWidth;
	}

	@Override
	public int getHeight() {
		return mHeight;
	}
	/**
	 * Used as a getter for abstractcomponent
	 * @return MouseOverArea for plus button
	 */
	public MouseOverArea getPlusComponent() {
		return plusArea;
	}
	/**
	 * Used as a getter for abstractcomponent
	 * @return MouseOverArea for minus button
	 */
	public MouseOverArea getMinusComponent() {
		return minusArea;
	}
	
	@Override
	public String toString() {
		return "Current Players: " + mPlayerCounter;
	}

}
