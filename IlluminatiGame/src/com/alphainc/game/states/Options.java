package com.alphainc.game.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;

import com.alphainc.game.Main;

import org.newdawn.slick.state.BasicGameState;

public class Options extends BasicGameState implements ComponentListener {
	private static int mID;
	private GameContainer container;
	private StateBasedGame game;
	private TextField tf;
	private Image backButton;
	private MouseOverArea backButtonMO;
	
	public Options(int id) {
		mID = id;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.container = container;
		this.game = game;
		backButton = new Image("res/quit.png").getScaledCopy(0.5F);
		backButtonMO = new MouseOverArea(container, backButton, 40, 500, this);
		backButtonMO.setNormalColor(new Color(1, 1, 1, 0.5F));
		backButtonMO.setMouseOverColor(new Color(1, 1, 1, 1.0F));
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		//tf.render(container, g);
		g.setColor(new Color(1, 1, 1, 1F));
		//g.fillRect(0, 0, container.getWidth(), container.getHeight());
		//g.setColor(new Color(0, 0, 0, 1F));
		backButtonMO.render(container, g);
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

	@Override
	public void componentActivated(AbstractComponent source) {
		if(source == backButtonMO) {
			game.enterState(Main.MENU);
		}
	}
}
