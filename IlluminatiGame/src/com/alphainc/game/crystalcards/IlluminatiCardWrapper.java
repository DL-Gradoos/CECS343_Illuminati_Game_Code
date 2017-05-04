package com.alphainc.game.crystalcards;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;

public class IlluminatiCardWrapper extends AbstractComponent {
	
	private IlluminatiCard mCard;
	
	public IlluminatiCardWrapper(GameContainer container, IlluminatiCard card, ComponentListener listener) {
		super(container);
		mCard = card;
		addListener(listener);
	}

	@Override
	public void render(GUIContext container, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		if(mCard.isFlipped())
			mCard.getImage(1).draw(100, 100);
		else
			mCard.getImage(0).draw(100, 100);
	}
	
	public IlluminatiCard getCard() {
		return mCard;
	}

	@Override
	public void setLocation(int x, int y) {
		
		
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
