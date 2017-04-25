package com.alphainc.game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {
	
	public static final String NAME = "Illuminati, The Game";
	public static final int PLAY = 0;
	public static final int X_SIZE = 1280;
	public static final int Y_SIZE = 720;
	private static final int MENU = 0,
							 OPTIONS = 1,
							 USER_MANUAL = 2,
							 PLAYER_CHOICE = 3,
							 GAME = 4;
	
	public Main(String n) {
		super(n);
		this.addState(new Menu(MENU));
		this.addState(new Options(OPTIONS));
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(MENU).init(gc, this);
		this.getState(OPTIONS).init(gc, this);
		this.enterState(MENU);
	}
	
	public static void main(String[] args) {
		AppGameContainer agc;
		try {
			agc = new AppGameContainer(new Main(NAME));
			agc.setDisplayMode(X_SIZE, Y_SIZE, false);
			agc.setTargetFrameRate(60);
			agc.start();
		} catch(SlickException se) {
			se.printStackTrace();
		}
	}
}
