package com.alphainc.game.components;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import com.alphainc.game.states.Menu;
/**
 * Created to show system generated messages to all players
 * @author Daniel
 *
 */
public class MessageBox {
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
	
	/**
	 * Constructor
	 */
	public MessageBox() {
		messages = new ArrayList<String>();
		initInitialText();
		initMessageBoxBackground();
		initFont();
	}
	/**
	 * Renders the MessageBox
	 * @param container The game container
	 * @param g The graphics content
	 */
	public void render(GameContainer container, Graphics g) {
		//Display background, display messages i -> i + 10, depends on how far the user scrolls
		bg.draw(0, 0);
		spacer = 0;
		for(int ii = rowNum; ii < rowNum + TEXT_AMOUNT; ii++) {
			customFont.drawString(300, 300 + spacer, messages.get(ii), Color.white);
			spacer += 20;
		}
	}
	/**
	 * Adds a message to the list
	 * @param m The message to add
	 */
	public void addMessage(String m) {
		messages.add(messages.size() - 1, m);
		scrollDown();
	}
	
	public void scrollUp() {
		if(rowNum - 1 < 0)
			rowNum = 0;
		else
			rowNum--;
	}
	
	public void scrollDown() {
		//System.out.println(rowNum + " " + messages.get(rowNum + 4).equals(""));
		if(!messages.get(rowNum + TEXT_AMOUNT).equals(""))
			rowNum++;
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
	
	private void initInitialText() {
		messages.add("Welcome to Illuminati, the Card Game!");
		messages.add("If you haven't already, please check out the User Manual.");
		messages.add("Here is the amount of chosen players: " + Menu.playerCountData.getPlayerCount());
		messages.add("Players will now roll the dice.");
		messages.add("Rolling...");
		messages.add("");
	}
}
