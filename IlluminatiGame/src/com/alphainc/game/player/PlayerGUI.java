package com.alphainc.game.player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

import com.alphainc.game.crystalcards.PowerStructure;
/**
 * This class provides options for the player to take, also renders
 * their power structure.
 * 
 * @author Daniel
 *
 */
public class PlayerGUI extends AbstractComponent {
	
	/** The player this GUI is assigned to */
	private String mPlayerName;
	/** Player id */
	private int mPlayerID;
	/** GUI Sidebar background */
	private Image sideBar;
	/** The player num */
	private Image playerNum;
	/** First roll */
	private int firstDiceRoll;
	/** Should be rendered or not (play order) */
	private boolean shouldBeRendered = false;
	/** The power structure tied to the player */
	private PowerStructure powerStructure;
	
	public PlayerGUI(GameContainer container, String playerName, int playerId) {
		super(container);
		mPlayerName = playerName;
		mPlayerID = playerId;
		initSideBar();
	}
	public void render(GameContainer container, Graphics g) throws SlickException {
		if(shouldBeRendered) {
			g.drawImage(sideBar, 0, 0);
			g.drawImage(playerNum, 100, 100);
			powerStructure.render(container, g);
		}
	}
	
	@Override
	public void render(GUIContext container, Graphics g) throws SlickException {
		/*if(shouldBeRendered) {
			g.drawImage(sideBar, 0, 0);
			g.drawImage(playerNum, 100, 100);
			powerStructure.render(container, g);
		}*/
	}

	@Override
	public void setLocation(int x, int y) {
		// TODO Auto-generated method stub
		
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
	
	private void initSideBar() {
		try {
			sideBar = new Image("res/gui/playergui3.png");
			playerNum = new Image("res/playercounter/playercounter" + (mPlayerID + 1) + ".png").getScaledCopy(0.4F);
		} catch(SlickException se) {
			System.err.println("COULD NOT LOAD SIDE BAR FOR " + mPlayerName);
		}
	}
	
	public String getPlayerName() {
		return mPlayerName;
	}
	
	public void setRoll(int roll) {
		firstDiceRoll = roll;
	}
	
	public int getRoll() {
		return firstDiceRoll;
	}
	
	public void setShouldBeRendered(boolean s) {
		shouldBeRendered = s;
	}
	
	public boolean isRendered() {
		return shouldBeRendered;
	}
	
	public void addPowerStructure(PowerStructure ps) {
		powerStructure = ps;
	}
}
