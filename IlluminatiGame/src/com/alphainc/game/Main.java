package com.alphainc.game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import com.alphainc.game.states.Game;
import com.alphainc.game.states.Menu;
import com.alphainc.game.states.Options;
import com.alphainc.game.states.PreGame;
import com.alphainc.game.states.UserManual;

public class Main extends StateBasedGame {
	
	public static final String NAME = "Illuminati, The Game";
	public static final int X_SIZE = 1280;
	public static final int Y_SIZE = 720;
	public static final int SPLASH_SCREEN = 0,
							 MENU = 1,
							 GAME = 2,
							 OPTIONS = 3,
							 USER_MANUAL = 4;
	
	public Main(String n) {
		super(n);
		this.addState(new PreGame(SPLASH_SCREEN));
		this.addState(new Menu(MENU));
		this.addState(new Game(GAME));
		this.addState(new Options(OPTIONS));
		this.addState(new UserManual(USER_MANUAL));
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(MENU).init(gc, this);
		this.getState(SPLASH_SCREEN).init(gc, this);
		this.getState(GAME).init(gc, this);
		this.getState(OPTIONS).init(gc, this);
		this.getState(USER_MANUAL).init(gc, this);
		
		this.enterState(SPLASH_SCREEN, null, new FadeInTransition());
	}
	
	public static void main(String[] args) {
		AppGameContainer agc;
		try {
			agc = new AppGameContainer(new Main(NAME));
			agc.setDisplayMode(X_SIZE, Y_SIZE, false);
			agc.setTargetFrameRate(60);
			agc.setShowFPS(false);
			agc.start();
		} catch(SlickException se) {
			se.printStackTrace();
		}
	}
}
