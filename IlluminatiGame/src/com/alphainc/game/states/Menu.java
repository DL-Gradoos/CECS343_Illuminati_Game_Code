package com.alphainc.game.states;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.alphainc.game.Main;

public class Menu extends BasicGameState implements ComponentListener {
	
	private static int mID;
	private TextField tf;
	private Image bg, title, menu[];
	private MouseOverArea menuMouseOver[];
	private AngelCodeFont font;
	private java.awt.Font f;
	private TrueTypeFont x;
	private Shape line;
	private GameContainer container;
	private StateBasedGame game;
	
	
	public Menu(int id) {
		mID = id;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.container = container;
		this.game = game;
		bg = new Image("res/Pyramid-Abstract-Wallpapers-1.jpg").getScaledCopy(container.getWidth(), container.getHeight());
		title = new Image("res/title2.png").getScaledCopy(0.5F);
		menu = new Image[4];
		menu[0] = new Image("res/start.png").getScaledCopy(0.5F);
		menu[1] = new Image("res/options.png").getScaledCopy(0.5F);
		menu[2] = new Image("res/usermanual.png").getScaledCopy(0.5F);
		menu[3] = new Image("res/quit.png").getScaledCopy(0.5F);
		menuMouseOver = new MouseOverArea[4];
		for(int ii = 0; ii < 4; ii++) {
			menuMouseOver[ii] = new MouseOverArea(container, menu[ii], 40, 300 + (ii * 50), menu[ii].getWidth(), menu[ii].getHeight(), this);
			menuMouseOver[ii].setNormalColor(new Color(1, 1, 1, 0.5F));
			menuMouseOver[ii].setMouseOverColor(new Color(1, 1, 1, 1.0F));
		}
		//font = new AngelCodeFont();
		System.out.printf("WIDTH: %s HEIGHT: %s\n", container.getWidth(), container.getHeight());
		//font = new AngelCodeFont();
		f = new java.awt.Font(/*"Segoe UI Light"*/"Arial", java.awt.Font.BOLD, 36);
		x = new TrueTypeFont(f, false);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawImage(bg, 0, 0);
		g.drawImage(title, 40, 50);
		for(int ii = 0; ii < 4; ii++) {
			//g.drawImage(menu[ii], 40, 300 + (ii * 50));
			menuMouseOver[ii].render(container, g);
		}
		//g.setFont(x);
		//g.drawString("Start", 40, 500);
		//g.setFont((Font) f);
		//g.drawString("Start", 40, 500);
		
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
		if(source == menuMouseOver[0]) {
			game.enterState(Main.GAME);
		} else if (source == menuMouseOver[1]) {
			game.enterState(Main.OPTIONS);
		}
		if(source == menuMouseOver[3])
			container.exit();
	}
}