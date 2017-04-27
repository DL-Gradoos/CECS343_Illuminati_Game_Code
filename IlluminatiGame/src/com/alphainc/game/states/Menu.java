package com.alphainc.game.states;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Rectangle;
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
	private Image optionsMenuFrame, optionsMenuShadow;
	private boolean menuButtonsClicked[];
	private static int timePassed;
	
	
	public Menu(int id) {
		mID = id;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.container = container;
		this.game = game;
		/* Menu background image */
		bg = new Image("res/Pyramid-Abstract-Wallpapers-1.jpg").getScaledCopy(container.getWidth(), container.getHeight());
		/* The title and subheading in the top right corner */
		title = new Image("res/title2.png").getScaledCopy(0.5F);
		/* Menu images */
		menu = new Image[4];
		menu[0] = new Image("res/start.png").getScaledCopy(0.5F);
		menu[1] = new Image("res/options.png").getScaledCopy(0.5F);
		menu[2] = new Image("res/usermanual.png").getScaledCopy(0.5F);
		menu[3] = new Image("res/quit.png").getScaledCopy(0.5F);
		/* Allows for light up menu options */
		menuMouseOver = new MouseOverArea[4];
		/* Checker for if a menu option is clicked */
		menuButtonsClicked = new boolean[4];
		for(int ii = 0; ii < 4; ii++) {
			menuMouseOver[ii] = new MouseOverArea(container, menu[ii], 40, 300 + (ii * 50), menu[ii].getWidth(), menu[ii].getHeight(), this);
			menuMouseOver[ii].setNormalColor(new Color(1, 1, 1, 0.5F));
			menuMouseOver[ii].setMouseOverColor(new Color(1, 1, 1, 1.0F));
			menuButtonsClicked[ii] = false;
		}
		initOptionsMenu();
		
		
		
		
		
		
		
		//font = new AngelCodeFont();
		System.out.printf("WIDTH: %s HEIGHT: %s\n", container.getWidth(), container.getHeight());
		//font = new AngelCodeFont();
		f = new java.awt.Font(/*"Segoe UI Light"*/"Arial", java.awt.Font.BOLD, 36);
		x = new TrueTypeFont(f, false);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		renderMenuScreen(container, g);
		checkClicked(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		timePassed += 1;
		if((menuButtonsClicked[1] == true) && /*(timePassed % 100 == 0) &&*/ (optionsMenuFrame.getAlpha() <= 1.0F)) {
			optionsMenuFrame.setAlpha(optionsMenuFrame.getAlpha() + 0.2F);
		} else if((menuButtonsClicked[1] == false) && (optionsMenuFrame.getAlpha() >= 0F)) {
			optionsMenuFrame.setAlpha(optionsMenuFrame.getAlpha() - 0.2F);
		}
		if(timePassed % 100 == 0) {
			System.out.println("Alpha: " + optionsMenuFrame.getAlpha());
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return mID;
	}
	
	@Override
	public void componentActivated(AbstractComponent source) {
		for(int ii = 0; ii < 4; ii++) {
			if(source == menuMouseOver[ii])
				menuButtonsClicked[ii] = !menuButtonsClicked[ii];
		}
	}
	
	/**
	 * Renders the background, title, and mouse over areas
	 * 
	 * @param container The current game container
	 * @param g The graphics object used to render
	 */
	private void renderMenuScreen(GameContainer container, Graphics g) {
		g.drawImage(bg, 0, 0);
		g.drawImage(title, 40, 50);
		for(int ii = 0; ii < 4; ii++) {
			//g.drawImage(menu[ii], 40, 300 + (ii * 50));
			menuMouseOver[ii].render(container, g);
		}
	}
	
	/**
	 * Initialize Options menu objects
	 */
	private void initOptionsMenu() throws SlickException {
		/* The frame */
		//optionsMenuFrame = new Rectangle(300, 160, 900, 470);
		optionsMenuFrame = new Image("res/options_menu.png").getScaledCopy(1.5F);
		optionsMenuFrame.setAlpha(0F);
		/* The shadow behind the frame */
		//optionsMenuShadow = new Rectangle(298, 158, 902, 472);
	}
	
	/**
	 * A "pop up" menu that is rendered when 'Options' is clicked
	 * @param g The graphics object used to render
	 */
	private void renderOptionsMenu(Graphics g) {
		g.setColor(new Color(0, 0, 0, 0.5F));
		g.drawImage(optionsMenuFrame, 300, 160);
		//g.fill(optionsMenuShadow);
		/*g.setColor(new Color(1, 1, 1, 1F));
		g.fill(optionsMenuFrame);*/
	}
	
	private void derenderOptionsMenu(Graphics g) {
		if(optionsMenuFrame.getAlpha() <= 0) {
			
		}
	}
	
	/**
	 * This method checks for whether anything was clicked, responds
	 * accordingly.
	 * @param g The graphics object used to render
	 */
	private void checkClicked(Graphics g) {
		if(menuButtonsClicked[0]) {
			menuButtonsClicked[0] = false;
			game.enterState(Main.PLAYER_CHOICE);
		} else if(menuButtonsClicked[1] || optionsMenuFrame.getAlpha() > 0) {
			renderOptionsMenu(g);
		} /*else if(!menuButtonsClicked[1]) {
			derenderOptionsMenu(g);
		}*/ else if(menuButtonsClicked[2]) {
			menuButtonsClicked[2] = false;
			game.enterState(Main.USER_MANUAL);
		}
		if(menuButtonsClicked[3]) {
			menuButtonsClicked[3] = false;
			container.exit();
		}
	}
	
	/**
	 * UNUSED
	 */
	private void unused() {
		/** init */
		/*
			
		 */
		/** render */
		/*
			g.setFont(x);
			g.drawString("Start", 40, 500);
			g.setFont((Font) f);
			g.drawString("Start", 40, 500);
			g.fillRect(300, 160, 900, 470);
		 */
		/** update */
		/*
		
		
		
		 */
	}
}