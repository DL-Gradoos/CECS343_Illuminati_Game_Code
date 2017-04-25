package com.alphainc.game.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.BasicGameState;

public class Options extends BasicGameState {
	private static int mID;
	private TextField tf;
	
	public Options(int id) {
		mID = id;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		//tf.render(container, g);
		g.setColor(new Color(1, 1, 1, 1F));
		g.fillRect(0, 0, container.getWidth(), container.getHeight());
		g.setColor(new Color(0, 0, 0, 1F));
		g.drawString("GIACALONE, MORE LIKE JACK ALONE AMIRITE", 100, 100);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return mID;
	}
}
