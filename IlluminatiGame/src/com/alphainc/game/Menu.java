package com.alphainc.game;

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
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState {
	
	private static int mID;
	private TextField tf;
	private Image bg, title;
	private AngelCodeFont font;
	private java.awt.Font f;
	private TrueTypeFont x;
	private Shape line;
	
	
	public Menu(int id) {
		mID = id;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		bg = new Image("res/Pyramid-Abstract-Wallpapers-1.jpg").getScaledCopy(container.getWidth(), container.getHeight());
		title = new Image("res/title.png").getScaledCopy(0.5F);
		//font = new AngelCodeFont();
		System.out.printf("WIDTH: %s HEIGHT: %s\n", container.getWidth(), container.getHeight());
		//font = new AngelCodeFont();
		f = new java.awt.Font("Segoe UI Light", java.awt.Font.PLAIN, 48);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawImage(bg, 0, 0);
		g.drawImage(title, 40, 50);
		g.setFont(font);
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
}