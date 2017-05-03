package com.alphainc.game.cards;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

public class IlluminatiCard extends AbstractComponent/* implements Card*/ {
	/** Name of the card */
	private String mName;
	/** Path to the image for the card */
	private String mPath;
	/** Description of the card's special */
	private String mSpecialDesc;
	/**  */
	public IlluminatiCard(GameContainer container, String path) {
		super(container);
	}

	@Override
	public void render(GUIContext container, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocation(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
}
