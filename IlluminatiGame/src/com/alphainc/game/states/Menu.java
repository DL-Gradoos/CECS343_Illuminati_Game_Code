package com.alphainc.game.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import com.alphainc.game.Main;

public class Menu extends BasicGameState implements ComponentListener, MouseListener 
{
	
	/** ID of the state */
	private static int mID;
	/** Number of players, data to be transferred to PLAY state */
	public static int players;
	/** Images for background, title, and menu */
	private Image bg, title, menu[];
	/** Images for Start tile */
	private Image startGame;
	/** Allows for highlighting of menu items */
	private MouseOverArea menuMouseOver[], plusButton, minusButton, startGameButton;
	/** The container the game is in, received from init */
	private GameContainer container;
	/** A level (state) of the main game, received from init */
	private StateBasedGame game;
	/** Frames for Options and Start*/
	private Image optionsMenuFrame, startGamePlayerNum;
	/** Checks for which menu buttons were clicked */
	private boolean menuButtonsClicked[];
	private int numPlayers;
	private Image centerNum[];
	private boolean start_game_menu, options_menu;
	private Image minusImage, plusImage;
	
	/**
	 * Constructor, this is the menu state of the game.
	 * 
	 * @param id The ID of the current state passed in
	 */
	public Menu(int id) {
		mID = id;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		this.container = container;
		this.game = game;
		//playerCountData = PlayerCountSingleton.getInstance();
		numPlayers = 1;

		try {
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
			minusImage = new Image("res/playercounter/minus_icon.png").getScaledCopy(48,48);
			plusImage = new Image("res/playercounter/plus_icon.png").getScaledCopy(48,48);
			plusButton = new MouseOverArea(container, plusImage, 546 + minusImage.getWidth() + (192/2), 370);
			minusButton = new MouseOverArea(container, minusImage, 546, 370);
			startGame = new Image("/src/com/alphainc/res/go_PLACEHOLDER.png");
			startGameButton = new MouseOverArea(container, startGame, 566, 420);
		} catch(SlickException se) {
			System.err.println("UNABLE TO LOAD TITLE SCREEN IMAGES");
			se.printStackTrace();
		}
		/* Allows for light up menu options */
		menuMouseOver = new MouseOverArea[4];
		/* Checker for if a menu option is clicked */
		menuButtonsClicked = new boolean[4];
		centerNum = new Image[8];
	
			
		for(int ii = 0; ii < 4; ii++) {
			menuMouseOver[ii] = new MouseOverArea(container, menu[ii], 40, 300 + (ii * 50), menu[ii].getWidth(), menu[ii].getHeight(), this);
			menuMouseOver[ii].setNormalColor(new Color(1, 1, 1, 0.5F));
			menuMouseOver[ii].setMouseOverColor(new Color(1, 1, 1, 1.0F));
			
			menuButtonsClicked[ii] = false;
			
		}
		for(int ii = 0; ii < 8; ii++)
		{
			centerNum[ii] = new Image("res/playercounter/playercounter" + (ii + 1) + ".png").getScaledCopy(192/2, 48);
		}
		//initStartGame();
		startGamePlayerNum = new Image("res/options_menu_c.png").getScaledCopy(0.4F);
	
		startGame = new Image("res/go_PLACEHOLDER.png");
		
		startGameButton.setNormalColor(new Color(1, 1, 1, 0.5F));
		startGameButton.setMouseOverColor(new Color(1, 1, 1, 1.0F));
		
		/* The frame */
		optionsMenuFrame = new Image("res/options_menu_c.png").getScaledCopy(0.8F);

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		renderMenuScreen(container, g);
		if(start_game_menu)
		{
			g.drawImage(startGamePlayerNum, 20 + container.getWidth() / 4.0F, -20 + container.getHeight() / 4.0F);
			g.drawImage(centerNum[numPlayers-1], 546 + minusButton.getWidth(), 370);
			startGameButton.render(container, g);
			minusButton.render(container, g);
			plusButton.render(container, g);
		}
		else if (options_menu)
		{
			g.setColor(new Color(0, 0, 0, 0.5F));
			g.drawImage(optionsMenuFrame, 150, 15);
			//System.out.println("options display");
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
	
	@Override
	public void mousePressed(int button, int x, int y) {

		if(start_game_menu )
		{
			if(x >= plusButton.getX() &&
					x <= (plusButton.getWidth() + plusButton.getX()) &&
					y >= plusButton.getY() &&
					y <= (plusButton.getHeight() + plusButton.getY()))
			{
				if(numPlayers < 8)
				{
					numPlayers ++;
				}
			}
			else if(x >= minusButton.getX() &&
					x <= (minusButton.getWidth() + minusButton.getX()) &&
					y >= minusButton.getY() &&
					y <= (minusButton.getY() + minusButton.getHeight()))
			{
				if(numPlayers > 1)
				{
					numPlayers --;
				}
			}
			else if(x >= startGameButton.getX() &&
					x <= (startGameButton.getWidth() + startGameButton.getX()) &&
					y >= startGameButton.getY() &&
					y <= (startGameButton.getY() + startGameButton.getHeight()))
			{
				//Enters game state
				players = numPlayers;
				System.out.println(players);
				try {
					game.getState(Main.GAME).init(container, game);
				} catch (SlickException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				game.enterState(Main.GAME);
			}
		}
		
		
		if(x >= menuMouseOver[0].getX() 
				&& x<= (menuMouseOver[0].getWidth()+menuMouseOver[0].getX())
				&& y >= (menuMouseOver[0].getY()) 
				&& y <= (menuMouseOver[0].getHeight() + menuMouseOver[0].getY()))
		{
			start_game_menu = true;
			options_menu = false;
			System.out.println("Menu item one clicked.");
		}
		else if(x >= menuMouseOver[1].getX() 
				&& x<= (menuMouseOver[1].getWidth() + menuMouseOver[1].getX())
				&& y >= (menuMouseOver[1].getY()) 
				&& y <= (menuMouseOver[1].getHeight() + menuMouseOver[1].getY()))
		{
			start_game_menu = false;
			options_menu = true;
			System.out.println("Menu item two clicked.");
		}
		else if(x >= menuMouseOver[2].getX() 
				&& x<= (menuMouseOver[2].getWidth()+menuMouseOver[2].getX())
				&& y >= (menuMouseOver[2].getY()) 
				&& y <= (menuMouseOver[2].getHeight() + menuMouseOver[2].getY()))
		{
			start_game_menu = false;
			options_menu = false;
			System.out.println("Menu item three clicked.");
		}
		else if (x >= menuMouseOver[3].getX() 
				&& x<= (menuMouseOver[3].getWidth()+menuMouseOver[3].getX())
				&& y >= (menuMouseOver[3].getY()) 
				&& y <= (menuMouseOver[3].getHeight() + menuMouseOver[3].getY()))
		{
			System.out.println("Menu item four clicked.");
			container.exit();
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
			menuMouseOver[ii].render(container, g);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}


}