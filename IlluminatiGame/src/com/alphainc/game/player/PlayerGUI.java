package com.alphainc.game.player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

import com.alphainc.game.components.CenterCardViewer;
import com.alphainc.game.crystalcards.PowerStructure;
import com.alphainc.game.crystalcards.StructureCard;
import com.alphainc.game.states.Game;
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
	/** Component Listener */
	private ComponentListener listener;
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
	/** The special cards the user owns */
	private CenterCardViewer specialCards;
	/** Container passed */
	private GameContainer container;
	
	
	public PlayerGUI(GameContainer container, String playerName, int playerId, ComponentListener listener) {
		super(container);
		this.container = container;
		addListener(this.listener = listener);
		mPlayerName = playerName;
		mPlayerID = playerId;
		initSideBar();
		initButtons();
		initSpecialCardViewer();
	}
	
	public void render(GameContainer container, Graphics g) throws SlickException {
		if(shouldBeRendered) {
			g.drawImage(sideBar, -Game.camera.getTopLeftX(), -Game.camera.getTopLeftY());
			g.drawImage(playerNum, -Game.camera.getTopLeftX() + 80, -Game.camera.getTopLeftY() + 50);
			powerStructure.render(container, g);
			specialCards.render(container, g);
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
			sideBar = new Image("res/gui/playergui2.png");
			playerNum = new Image("res/playercounter/playercounter" + (mPlayerID + 1) + ".png").getScaledCopy(0.2F);
			
			
		} catch(SlickException se) {
			System.err.println("COULD NOT LOAD SIDE BAR FOR " + mPlayerName);
		}
	}
	
	private void initSpecialCardViewer() {
		specialCards = new CenterCardViewer("Special Cards", 1000, 615, container, listener);
	}
	
	private void initButtons() {
		
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
	
	public void addSpecialCard(StructureCard c) {
		specialCards.add(c);
	}
	
	public StructureCard removeSpecialCard(int index) {
		return specialCards.remove(index);
	}
	
	public StructureCard getSpecialCard(int index) {
		return specialCards.get(index);
	}
	
	public CenterCardViewer getSpecialCardViewer() {
		return specialCards;
	}
	
	public int getPlayerID() {
		return mPlayerID + 1;
	}
}
