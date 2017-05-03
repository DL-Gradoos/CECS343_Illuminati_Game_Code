package com.alphainc.game.states;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.MouseListener;
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
import com.alphainc.game.PlayerCountSingleton;
import com.alphainc.game.components.PlayerCounter;

public class Menu extends BasicGameState implements ComponentListener, MouseListener {
	
	/** ID of the state */
	private static int mID;
	/** Number of players, data to be transferred to PLAY state */
	public static int players;
	/** Images for background, title, and menu */
	private Image bg, title, menu[];
	/** Images for Start tile */
	private Image startGame;
	/** Allows for highlighting of menu items */
	private MouseOverArea menuMouseOver[], startGameMouseOver;
	/** UNUSED */
	private AngelCodeFont font;
	/** UNUSED */
	private java.awt.Font f;
	/** UNUSED */
	private TrueTypeFont x;
	/** The container the game is in, received from init */
	private GameContainer container;
	/** A level (state) of the main game, received from init */
	private StateBasedGame game;
	/** Frames for Options and Start*/
	private Image optionsMenuFrame, startGamePlayerNum;
	/** Checks for which menu buttons were clicked */
	private boolean menuButtonsClicked[];
	/** UNUSED */
	private static long timePassed;
	/** Custom module to display number of players */
	private PlayerCounter pc;
	/** Singleton used to transfer data to PLAY state */
	public static PlayerCountSingleton playerCountData;
	/** Paths to images 0: cards, 1: playercounter, 2: titlescreen*/
	private String imagePath[] = {
			"src/com/alphainc/res/cards/",
			"src/com/alphainc/res/playercounter/",
			"src/com/alphainc/res/titlescreen/"
	};
	
	/**
	 * Constructor, this is the menu state of the game.
	 * 
	 * @param id The ID of the current state passed in
	 */
	public Menu(int id) {
		mID = id;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.container = container;
		this.game = game;
		playerCountData = PlayerCountSingleton.getInstance();
		initTitleScreen();
		initOptionsMenu();
		initStartGame();
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
		//timePassed += 1;
		if((menuButtonsClicked[1] == true) && /*(timePassed % 100 == 0) &&*/ (optionsMenuFrame.getAlpha() <= 1.0F)) {
			optionsMenuFrame.setAlpha(optionsMenuFrame.getAlpha() + 0.2F);
		} else if((menuButtonsClicked[1] == false) && (optionsMenuFrame.getAlpha() >= 0F)) {
			optionsMenuFrame.setAlpha(optionsMenuFrame.getAlpha() - 0.2F);
		}
		/*if(timePassed % 100 == 0) {
			System.out.println("players: " + players);
		}*/
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return mID;
	}
	/**
	 * Initializes title screen elements
	 */
	private void initTitleScreen() {
		try {
			/* Menu background image */
			bg = new Image(imagePath[2] + "Pyramid-Abstract-Wallpapers-1.jpg").getScaledCopy(container.getWidth(), container.getHeight());
			/* The title and subheading in the top right corner */
			title = new Image("res/title2.png").getScaledCopy(0.5F);
			/* Menu images */
			menu = new Image[4];
			menu[0] = new Image(imagePath[2] + "start.png").getScaledCopy(0.5F);
			menu[1] = new Image(imagePath[2] + "options.png").getScaledCopy(0.5F);
			menu[2] = new Image(imagePath[2] + "usermanual.png").getScaledCopy(0.5F);
			menu[3] = new Image(imagePath[2] + "quit.png").getScaledCopy(0.5F);
		} catch(SlickException se) {
			System.err.println("UNABLE TO LOAD TITLE SCREEN IMAGES");
			se.printStackTrace();
		}
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
	}
	
	@Override
	public void componentActivated(AbstractComponent source) {
		for(int ii = 0; ii < 4; ii++) {
			if(source == menuMouseOver[ii])
				menuButtonsClicked[ii] = !menuButtonsClicked[ii];
		}
		if(source == startGameMouseOver) {
			playerCountData.setPlayerCount(pc.getPlayerCount());
			System.out.println(playerCountData.getPlayerCount());
			try {
				/** Reinitializes the Game state */
				game.getState(Main.GAME).init(container, game);
			} catch (SlickException e) {
				System.err.println("REINIT OF GAME STATE FAILED");
				e.printStackTrace();
			}
			game.enterState(Main.GAME);
		}
		/* TODO:  if source == gotoplayscreen, set players = pc.getPlayerCount()*/
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		//System.out.println(x + " " + y);
		if(menuButtonsClicked[0] == true && button == 0 && x <= pc.getMinusBounds(2) && x >= pc.getMinusBounds(0) 
				&& y <= pc.getMinusBounds(3) && y >= pc.getMinusBounds(1))
			pc.decreaseCount();
		if(menuButtonsClicked[0] == true && button == 0 && x <= pc.getPlusBounds(2) && x >= pc.getPlusBounds(0) 
				&& y <= pc.getPlusBounds(3) && y >= pc.getPlusBounds(1))
			pc.increaseCount();
		//System.out.println(pc.getPlayerCount());
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
		optionsMenuFrame = new Image(imagePath[2] + "options_menu_c.png").getScaledCopy(0.8F);
		//optionsMenuFrame = new Image("res/cards/thenetwork.png").getScaledCopy(0.5F);
		optionsMenuFrame.setAlpha(0F);
	}
	
	/**
	 * A "pop up" menu that is rendered when 'Options' is clicked
	 * @param g The graphics object used to render
	 */
	private void renderOptionsMenu(Graphics g) {
		g.setColor(new Color(0, 0, 0, 0.5F));
		g.drawImage(optionsMenuFrame, 150, 15);
	}
	/**
	 * @deprecated
	 * Was supposed to be used to derender Options frame, unneeded now.
	 * 
	 * @param g Graphics object
	 */
	private void derenderOptionsMenu(Graphics g) {
		if(optionsMenuFrame.getAlpha() <= 0) {
			
		}
	}
	
	/**
	 * This method checks for whether anything was clicked, responds
	 * accordingly.
	 * @param g The graphics object used to render
	 */
	private void checkClicked(Graphics g) throws SlickException {
		if(menuButtonsClicked[0]) {
			//menuButtonsClicked[0] = false;
			//game.enterState(Main.PLAYER_CHOICE);
			renderStartGame(g);
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
	 * Initializes the variables needed for creating the mini popup window
	 * that asks the user for the number of players wanted. Also contains
	 * buttons to either 1) read the user manual 2) move on to play the game
	 * or 3) exit from the window.
	 * 
	 * @throws SlickException A general exception
	 */
	private void initStartGame() throws SlickException {
		startGamePlayerNum = new Image(imagePath[2] + "options_menu_c.png").getScaledCopy(0.4F);
		pc = new PlayerCounter(container, 546, 370, 192, 48);
		startGame = new Image(imagePath[2] + "go_PLACEHOLDER.png");
		startGameMouseOver = new MouseOverArea(container, startGame, 546, 400, startGame.getWidth(), startGame.getHeight(), this);
		startGameMouseOver.setNormalColor(new Color(1, 1, 1, 0.5F));
		startGameMouseOver.setMouseOverColor(new Color(1, 1, 1, 1.0F));
	}
	/**
	 * Renders the images for the Start popup menu
	 * 
	 * @param g Graphics object
	 * @throws SlickException A general exception
	 */
	private void renderStartGame(Graphics g) throws SlickException {
		g.drawImage(startGamePlayerNum, 20 + container.getWidth() / 4.0F, -20 + container.getHeight() / 4.0F);
		pc.render(container, g);
		startGameMouseOver.render(container, g);
		//playerCount.render(container, g);
	}
	
	/**
	 * @deprecated
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