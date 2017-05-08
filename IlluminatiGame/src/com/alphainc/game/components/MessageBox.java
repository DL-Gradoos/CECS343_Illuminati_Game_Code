package com.alphainc.game.components;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;

import com.alphainc.game.states.Game;
import com.alphainc.game.states.Menu;
/**
 * Created to show system generated messages to all players
 * @author Daniel
 *
 */
public class MessageBox extends AbstractComponent {
	/** All messages present in the game, any message older than index 100 will be deleted. */
	private List<String> messages;
	/** The MessageBox background */
	private Image bg;
	/** Font */
	private Font segoeUIFont;
	/** Slick drawable font */
	private TrueTypeFont customFont;
	/** The Font color */
	private Color fontColor;
	/** The current place in the message box we're at */
	private int rowNum = 0;
	/** Space to place between each row of text */
	private int spacer;
	/** Amount of text to display in the screen */
	private static final int TEXT_AMOUNT = 5;
	/** Arrows for scrolling the MessageBox up and down */
	private MovableMouseOverArea arrow[];
	/** Images for arrows */
	private Image upArrow, downArrow;
	/** Static coordinates for the mouse over area */
	private int xMouseOver, yMouseOver;
	/** Changing coordinates for the image render */
	private int xRender, yRender;
	/** Listener */
	private ComponentListener listener;
	
	/**
	 * Constructor
	 */
	public MessageBox(GameContainer container, ComponentListener listener) {
		super(container);
		addListener(this.listener = listener);
		messages = new ArrayList<String>();
		initInitialText();
		initMessageBoxBackground();
		initItems();
		initFont();
	}
	
	/**
	 * Renders the MessageBox
	 * @param container The game container
	 * @param g The graphics content
	 */
	/*public void render(GameContainer container, Graphics g) {
		//Display background, display messages i -> i + 10, depends on how far the user scrolls
		bg.draw(0, 0);
		spacer = 0;
		for(int ii = rowNum; ii < rowNum + TEXT_AMOUNT; ii++) {
			customFont.drawString(300, 300 + spacer, messages.get(ii), Color.white);
			spacer += 20;
		}
	}*/
	
	/**
	 * Recursively adds a message to the list
	 * @param m The message to add
	 */
	public void addMessage(String m) {
		if(m.length() <= 30) {
			messages.add(messages.size() - 1, m);
			scrollDown();
		} else {
			String firstThirty = m.substring(0, 30);
			int lastSpace = firstThirty.lastIndexOf(" ");
			messages.add(messages.size() - 1, firstThirty.substring(0, lastSpace + 1));
			addMessage(" " + m.substring(lastSpace + 1));
		}
		scrollDown();
	}
	/**
	 * Scrolls message box up
	 */
	public void scrollUp() {
		if(rowNum - 1 < 0)
			rowNum = 0;
		else
			rowNum--;
	}
	
	/**
	 * Scrolls message box down
	 */
	public void scrollDown() {
		//System.out.println(rowNum + " " + messages.get(rowNum + 4).equals(""));
		if(!messages.get(rowNum + TEXT_AMOUNT).equals(""))
			rowNum++;
	}
	
	public MovableMouseOverArea getUpArrow() {
		return arrow[0];
	}
	
	public MovableMouseOverArea getDownArrow() {
		return arrow[1];
	}
	
	/**
	 * Initializes the MessageBox background
	 */
	private void initMessageBoxBackground() {
		try {
			bg = new Image("res/gui/messageboxbg.png");
		} catch (SlickException e) {
			System.err.println("COULD NOT LOAD MESSAGEBOX BACKGROUND");
			e.printStackTrace();
		}
	}
	
	/**
	 * Initalizes the font to be used
	 */
	private void initFont() {
		segoeUIFont = new Font("Segoe UI Light", Font.BOLD, 14);
		customFont = new TrueTypeFont(segoeUIFont, true);
		fontColor = new Color(Color.white);
	}
	
	/**
	 * Initalizes text
	 */
	private void initInitialText() {
		messages.add("Welcome to Illuminati, the");
		messages.add(" Card Game!");
		messages.add("If you haven't already, please");
		messages.add(" check out the User Manual.");
		messages.add("Here is the amount of chosen");
		messages.add(" players: " + Menu.playerCountData.getPlayerCount());
		messages.add("Players will now roll the dice.");
		messages.add("Rolling...");
		messages.add("");
	}
	
	private void initItems() {
		try {
			upArrow = new Image("res/messagebox/uparrow.png").getScaledCopy(0.4f);
			downArrow = new Image("res/messagebox/downarrow.png").getScaledCopy(0.4f);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		arrow = new MovableMouseOverArea[2];
		arrow[0] = new MovableMouseOverArea(container, upArrow, 15, 615, listener);
		arrow[0].setNormalColor(new Color(1, 1, 1, 0.5F));
		arrow[0].setMouseOverColor(new Color(1, 1, 1, 1.0F));
		arrow[1] = new MovableMouseOverArea(container, downArrow, 15, 660, listener);
		arrow[1].setNormalColor(new Color(1, 1, 1, 0.5F));
		arrow[1].setMouseOverColor(new Color(1, 1, 1, 1.0F));
		
	}

	@Override
	public void render(GUIContext container, Graphics g) throws SlickException {
		//Display background, display messages i -> i + TEXT_AMOUNT
		bg.draw(0, 0);
		spacer = 0;
		for(int ii = rowNum; ii < rowNum + TEXT_AMOUNT; ii++) {
			customFont.drawString(-Game.camera.getTopLeftX() + 50,
					-Game.camera.getTopLeftY() + 600 + spacer, messages.get(ii), Color.white);
			spacer += 20;
		}
		spacer = 0;
		for(int ii = 0; ii < 2; ii++) {
			//-camera.getTopLeftX() + 80, -camera.getTopLeftY() + space
			arrow[ii].updateLocation(-Game.camera.getTopLeftX() + 15, -Game.camera.getTopLeftY() + 615 + spacer);
			arrow[ii].render(container, g);
			spacer += 45;
		}
		//Testing spawn coordinates
		/*g.drawImage(upArrow, 15, 615);
		g.drawImage(downArrow, 15, 660);*/
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
