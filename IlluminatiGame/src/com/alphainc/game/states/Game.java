package com.alphainc.game.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.alphainc.game.Camera;
import com.alphainc.game.Main;
import com.alphainc.game.components.CenterCardViewer;
import com.alphainc.game.components.MessageBox;
import com.alphainc.game.components.MovableMouseOverArea;
import com.alphainc.game.crystalcards.Arrow;
import com.alphainc.game.crystalcards.Deck;
import com.alphainc.game.crystalcards.GroupCard;
import com.alphainc.game.crystalcards.IlluminatiCard;
import com.alphainc.game.crystalcards.IlluminatiCardWrapper;
import com.alphainc.game.crystalcards.PowerStructure;
import com.alphainc.game.crystalcards.StructureCard;
import com.alphainc.game.player.PlayerGUI;
/**
 * The main game
 * 
 * @author Daniel
 *
 */
public class Game extends BasicGameState implements ComponentListener, KeyListener {
	/** State id */
	private static int mID;
	/** not used yet */
	private boolean playerOrderRunning = true;
	/** Background */
	private Image bg[];
	/** List of illumnati cards */
	private List<StructureCard> illumCard, groupCard, specialCard, currentCenterCards;
	/** When shifting the camera, keeps gui locked to side of screen */
	public static Camera camera;
	/** The players */
	private PlayerGUI player[];
	/** The dice rolls */
	private int orderRoll[];
	/** The order in which players will go */
	private List<PlayerGUI> playerOrder;
	/** Whose turn it is */
	private int turn = 0;
	/** The previous turn */
	private int prevTurn;
	
	
	/** Boolean for allowing camera movement 0 = left, 1 = up, 2 = right, 3 = down*/
	private boolean cameraMovement[];
	/** Deck of cards */
	private Deck deck;
	/** TESTING (DANIEL) */
	private MessageBox msgBox;
	/** Action button images */
	private Image buttonImage[];
	/** Action buttons */
	private MovableMouseOverArea buttons[];
	/** Number of actions left */
	private int actions = 2;
	/** The uncontrolled Groups in play */
	private CenterCardViewer uncontrolledCards;
	
	
	public Game(int id) {
		mID = id;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		/** Initialize only when entered into from Menu state */
		if(Menu.playerCountData.getPlayerCount() > 0) {
			initMessageBox(container);
			initIllumCards(container);
			initGroupCards(container);
			initPlayers(container);
			determinePlayerOrder();
			assignIllumCard();
			initSideBarButtons(container);
			initCurrentCenterCards(container);
			msgBox.addMessage("THIS IS MORE THAN 30 CHARACTERS WHAT WILL THE PROGRAM DO?");
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.translate(camera.getTopLeftX(), camera.getTopLeftY());
		renderBackground();
		/* Renders the current players gui */
		for(int ii = 0; ii < player.length; ii++) {
			playerOrder.get(ii).render(container, g);
		}
		renderSideBarElements(container, g);
		msgBox.render(container, g);
		//renderCurrentCenterCards(container, g);
		uncontrolledCards.render(container, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		//Move speed of the camera
		float moveSpeed = delta * 0.3f;
		//left
		if(cameraMovement[0]) {
			if(camera.getTopLeftX() > 1000)
				camera.setCurrentTopLeftCoords(1000.999F, camera.getTopLeftY());
			else
				camera.adjustCoords(moveSpeed, 0);
		}
		//up
		if(cameraMovement[1]) {
			if(camera.getTopLeftY() > 600)
				camera.setCurrentTopLeftCoords(camera.getTopLeftX(), 600.999F);
			else
				camera.adjustCoords(0, moveSpeed);
		}
		//right
		if(cameraMovement[2]) {
			if(camera.getTopLeftX() < -1000)
				camera.setCurrentTopLeftCoords(-1000.999F, camera.getTopLeftY());
			else
				camera.adjustCoords(-moveSpeed, 0);
		}
		//down
		if(cameraMovement[3]) {
			if(camera.getTopLeftY() < -600)
				camera.setCurrentTopLeftCoords(camera.getTopLeftX(), -600.999F);
			else
				camera.adjustCoords(0, -moveSpeed);
		}
		
		/* Sequence of play */
		if(actions != 0) {
			
		} else if (actions == 0) {
			/* If there are no more actions, change the player turn */
			//prevTurn = turn;
			playerOrder.get(turn).setShouldBeRendered(false);
			turn = ((turn + 1) > player.length - 1) ? 0 : turn + 1;
			/* Reset the number of actions available */
			actions = 2;
		}
		/*if(prevTurn != turn) {
			prevTurn = turn;
		}*/
	}

	@Override
	public int getID() {
		return mID;
	}
	
	@Override
	public void keyPressed(int key, char c) {
		/* Go to next player's turn */
		if(key == Input.KEY_SPACE) {
			prevTurn = turn;
			playerOrder.get(turn).setShouldBeRendered(false);
			turn = ((turn + 1) > player.length - 1) ? 0 : turn + 1;
			/*if((turn + 1) > (player.length - 1))	
				turn = 0;
			else
				turn++;*/
			playerOrder.get(turn).setShouldBeRendered(true);
		}
		if(key == Input.KEY_LEFT || key == Input.KEY_A)
			cameraMovement[0] = true;
		if(key == Input.KEY_UP || key == Input.KEY_W)
			cameraMovement[1] = true;
		if(key == Input.KEY_RIGHT || key == Input.KEY_D)
			cameraMovement[2] = true;
		if(key == Input.KEY_DOWN || key == Input.KEY_S)
			cameraMovement[3] = true;
		
		if(key == Input.KEY_P)
			playerOrder.get(turn).addSpecialCard(groupCard.remove(0));
		
		if(key == Input.KEY_O)
			msgBox.scrollUp();
		if(key == Input.KEY_L)
			msgBox.scrollDown();
		if(key == Input.KEY_C)
		{
			int connect = (int)(Math.random()*4);
			int r = (int)(Math.random()*groupCard.size());
			int r2 = (int)(Math.random() * playerOrder.get(turn).getPowerStructure().size());
			StructureCard card = groupCard.remove(r);
			card.removeConnections();
			playerOrder.get(turn).getPowerStructure().removeExIl();
			playerOrder.get(turn).getPowerStructure().get(0).removeConnections();
			playerOrder.get(turn).getPowerStructure().get(0).connect(card, card.getOpenInArrow(), connect);
			playerOrder.get(turn).getPowerStructure().add(card);
			
		}
	}
	
	@Override
	public void keyReleased(int key, char c) {
		if(key == Input.KEY_U)
			uncontrolledCards.add(groupCard.remove(0));
		
		if(key == Input.KEY_LEFT || key == Input.KEY_A)
			cameraMovement[0] = false;
		if(key == Input.KEY_UP || key == Input.KEY_W)
			cameraMovement[1] = false;
		if(key == Input.KEY_RIGHT || key == Input.KEY_D)
			cameraMovement[2] = false;
		if(key == Input.KEY_DOWN || key == Input.KEY_S)
			cameraMovement[3] = false;
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		if(button == Input.MOUSE_LEFT_BUTTON) {
			for(StructureCard sc : illumCard)
				sc.flip();
			//ilCards[0].flip();
			
		}
	}
	
	@Override
	public void componentActivated(AbstractComponent source) {
		//Attack to Control
		if(source == buttons[0]) {
			
		}
		//Attack to Destroy
		if(source == buttons[1]) {
			
		}
		//Attack to Neutralize
		if(source == buttons[2]) {
			
		}
		//Transfer Money
		if(source == buttons[3]) {
			
		}
		//Move Group
		if(source == buttons[4]) {
			
		}
		//Give a Group Away
		if(source == buttons[5]) {
			
		}
		
		//MessageBox Down Arrow
		if(source == msgBox.getDownArrow())
			msgBox.scrollDown();
		//MessageBox Up Arrow
		if(source == msgBox.getUpArrow())
			msgBox.scrollUp();
		
		//CenterCardViewer Left Arrow
		if(source == uncontrolledCards.getLeftArrow())
			uncontrolledCards.scrollLeft();
		//CenterCardViewer Right Arrow
		if(source == uncontrolledCards.getRightArrow())
			uncontrolledCards.scrollRight();
		
		if(source == uncontrolledCards.getLeftClickableArea())
			System.out.println("LEFT CARD INDEX: " + uncontrolledCards.getLeftCardIndex());
		if(source == uncontrolledCards.getRightClickableArea())
			System.out.println("RIGHT CARD INDEX: " + uncontrolledCards.getRightCardIndex());
		
		for(int ii = 0; ii < playerOrder.size(); ii++) {
			//CenterCardViewer Left Arrow
			if(source == playerOrder.get(ii).getSpecialCardViewer().getLeftArrow())
				playerOrder.get(ii).getSpecialCardViewer().scrollLeft();
			//CenterCardViewer Right Arrow
			if(source == playerOrder.get(ii).getSpecialCardViewer().getRightArrow())
				playerOrder.get(ii).getSpecialCardViewer().scrollRight();
			if(source == playerOrder.get(ii).getSpecialCardViewer().getLeftClickableArea())
				System.out.println("LEFT CARD INDEX: " + playerOrder.get(ii).getSpecialCardViewer().getLeftCardIndex());
			if(source == playerOrder.get(ii).getSpecialCardViewer().getRightClickableArea())
				System.out.println("RIGHT CARD INDEX: " + playerOrder.get(ii).getSpecialCardViewer().getRightCardIndex());
		}
	}
	
	/**
	 * Initializes the amount of players from the option chosen in Menu
	 * @param container The game container
	 */
	private void initPlayers(GameContainer container) {
		//Player GUI's init
		player = new PlayerGUI[Menu.playerCountData.getPlayerCount()];
		for(int ii = 0; ii < player.length; ii++) {
			player[ii] = new PlayerGUI(container, "Player " + (ii + 1), ii, this);
		}
		//Init Camera
		camera = new Camera();
		cameraMovement = new boolean[] {false, false, false, false};
		//Init background image
		try {
			bg = new Image[9];
			for(int ii = 0; ii < 9; ii++) {
				bg[ii] = new Image("res/gui/tabletop.png");
			}
		} catch (SlickException e) {
			System.err.println("COULD NOT LOAD IMAGE");
			e.printStackTrace();
		}
		prevTurn = player.length - 1;
	}
	
	private void renderBackground() {
		bg[0].draw(-1280, -720);
		bg[1].draw(0, -720);
		bg[2].draw(1280, -720);
		bg[3].draw(-1280, 0);
		bg[4].draw(0, 0);
		bg[5].draw(1280, 0);
		bg[6].draw(-1280, 720);
		bg[7].draw(0, 720);
		bg[8].draw(1280, 720);
	}
	
	/**
	 * "Rolls 2 die" according to how many players there are and assigns turn order
	 */
	private void determinePlayerOrder() {
		/* Rolls dice */
		Random dice = new Random();
		/* Determines player order */
		playerOrder = new ArrayList<PlayerGUI>(player.length);
		/* Used to keep track of all rolls */
		orderRoll = new int[player.length];
		int currentNum = 0;
		for(int ii = 0; ii < player.length; ii++) {
			currentNum = dice.nextInt(11) + 2;
			/* Ensures no repeats */
			for(int jj = 0; jj < orderRoll.length; jj++) {
				if(currentNum == orderRoll[jj]) {
					currentNum = dice.nextInt(11) + 2;
					jj = -1;
				}
			}
			player[ii].setRoll(currentNum);
			playerOrder.add(player[ii]);
			orderRoll[ii] = currentNum;
			msgBox.addMessage("Player " + (ii + 1) + " has rolled: " + currentNum);
		}
		/* Sorts from highest to lowest */
		Collections.sort(playerOrder, new Comparator<PlayerGUI>() {
			@Override
			public int compare(PlayerGUI arg0, PlayerGUI arg1) {
				if(arg0.getRoll() < arg1.getRoll()) return 1;
				else if (arg0.getRoll() > arg1.getRoll()) return -1;
				return 0;
			}
			
		});
		playerOrder.get(0).setShouldBeRendered(true);
		/* Messages for player order */
		msgBox.addMessage("\n");
		for(int ii = 0; ii < playerOrder.size(); ii++) {
			if(ii == 0)
				msgBox.addMessage("Player " + playerOrder.get(ii).getPlayerID() + " will go 1st!");
			else if(ii == 1)
				msgBox.addMessage("Player " + playerOrder.get(ii).getPlayerID() + " will go 2nd!");
			else if(ii == 2)
				msgBox.addMessage("Player " + playerOrder.get(ii).getPlayerID() + " will go 3rd!");
			else
				msgBox.addMessage("Player " + playerOrder.get(ii).getPlayerID() + " will go " + (ii + 1) + "th!");
				
		}
		//System.out.println("Turn order:");
		//for(PlayerGUI p : playerOrder) System.out.println(p.getPlayerName() + " " + p.getRoll());
	}
	
	
	/**
	 * Initializes Illuminati Cards
	 */
	private void initIllumCards(GameContainer container) {
		deck = new Deck();
		illumCard = deck.getIlluminatiDeck();
		for(StructureCard sc : illumCard) {
			sc.setPosition(container.getWidth() / 2 + 150, container.getHeight() / 2);
			sc.flip();
		}
	}
	
	private void initGroupCards(GameContainer container) {
		groupCard = deck.getGroupDeck();
		Collections.shuffle(groupCard);
	}
	
	/**
	 * Assigns a random illuminati card for each player
	 */
	private void assignIllumCard() {
		Random rand = new Random();
		for(int ii = 0; ii < player.length; ii++) {
			PowerStructure ps = new PowerStructure(ii);
			ps.add(illumCard.remove(rand.nextInt(illumCard.size())));
			System.out.println(ps.get(0).getName());
			player[ii].addPowerStructure(ps);
		}
	}
	
	private void initMessageBox(GameContainer container) {
		msgBox = new MessageBox(container, this);
	}
	
	private void initSideBarButtons(GameContainer container) {
		buttonImage = new Image[6];
		buttons = new MovableMouseOverArea[6];
		try {
			buttonImage[0] = new Image("res/gui/attacktocontrol.png").getScaledCopy(0.3F);
			buttonImage[1] = new Image("res/gui/attacktodestroy.png").getScaledCopy(0.3F);
			buttonImage[2] = new Image("res/gui/attacktoneutralize.png").getScaledCopy(0.3F);
			buttonImage[3] = new Image("res/gui/transfermoney.png").getScaledCopy(0.3F);
			buttonImage[4] = new Image("res/gui/moveagroup.png").getScaledCopy(0.3F);
			buttonImage[5] = new Image("res/gui/givegroupaway.png").getScaledCopy(0.3F);
			int space = 300;
			buttons[0] = new MovableMouseOverArea(container, buttonImage[0], (int) (-camera.getTopLeftX() + 60),
					(int) (-camera.getTopLeftY() + 150), buttonImage[0].getWidth(),
					buttonImage[0].getHeight(), this);
			buttons[0].setNormalColor(new Color(1, 1, 1, 0.5F));
			buttons[0].setMouseOverColor(new Color(1, 1, 1, 1.0F));
			
			buttons[1] = new MovableMouseOverArea(container, buttonImage[1], (int) (-camera.getTopLeftX() + 60),
					(int) (-camera.getTopLeftY() + 225), buttonImage[1].getWidth(),
					buttonImage[1].getHeight(), this);
			buttons[1].setNormalColor(new Color(1, 1, 1, 0.5F));
			buttons[1].setMouseOverColor(new Color(1, 1, 1, 1.0F));
			
			buttons[2] = new MovableMouseOverArea(container, buttonImage[2], (int) (-camera.getTopLeftX() + 50),
					(int) (-camera.getTopLeftY() + 300), buttonImage[2].getWidth(),
					buttonImage[2].getHeight(), this);
			buttons[2].setNormalColor(new Color(1, 1, 1, 0.5F));
			buttons[2].setMouseOverColor(new Color(1, 1, 1, 1.0F));
			
			buttons[3] = new MovableMouseOverArea(container, buttonImage[3], (int) (-camera.getTopLeftX() + 60),
					(int) (-camera.getTopLeftY() + 375), buttonImage[3].getWidth(),
					buttonImage[3].getHeight(), this);
			buttons[3].setNormalColor(new Color(1, 1, 1, 0.5F));
			buttons[3].setMouseOverColor(new Color(1, 1, 1, 1.0F));
			
			buttons[4] = new MovableMouseOverArea(container, buttonImage[4], (int) (-camera.getTopLeftX() + 70),
					(int) (-camera.getTopLeftY() + 450), buttonImage[4].getWidth(),
					buttonImage[4].getHeight(), this);
			buttons[4].setNormalColor(new Color(1, 1, 1, 0.5F));
			buttons[4].setMouseOverColor(new Color(1, 1, 1, 1.0F));
			
			buttons[5] = new MovableMouseOverArea(container, buttonImage[5], (int) (-camera.getTopLeftX() + 40),
					(int) (-camera.getTopLeftY() + 525), buttonImage[5].getWidth(),
					buttonImage[5].getHeight(), this);
			buttons[5].setNormalColor(new Color(1, 1, 1, 0.5F));
			buttons[5].setMouseOverColor(new Color(1, 1, 1, 1.0F));
		} catch (SlickException e) {
			System.err.println("SIDE BAR IMAGES NOT LOADED");
			e.printStackTrace();
		}
	}
	/**
	 * Renders Side bar elements
	 * 
	 * @param container Game Container
	 * @param g Graphics object
	 */
	private void renderSideBarElements(GameContainer container, Graphics g) {
		buttons[0].updateLocation(-camera.getTopLeftX() + 60, -camera.getTopLeftY() + 150);
		buttons[0].render(container, g);
		buttons[1].updateLocation(-camera.getTopLeftX() + 60, -camera.getTopLeftY() + 225);
		buttons[1].render(container, g);
		buttons[2].updateLocation(-camera.getTopLeftX() + 50, -camera.getTopLeftY() + 300);
		buttons[2].render(container, g);
		buttons[3].updateLocation(-camera.getTopLeftX() + 60, -camera.getTopLeftY() + 375);
		buttons[3].render(container, g);
		buttons[4].updateLocation(-camera.getTopLeftX() + 70, -camera.getTopLeftY() + 450);
		buttons[4].render(container, g);
		buttons[5].updateLocation(-camera.getTopLeftX() + 40, -camera.getTopLeftY() + 525);
		buttons[5].render(container, g);
		/*int space = 300;
		for(int ii = 0; ii < buttons.length; ii++) {
			buttons[ii].updateLocation(-camera.getTopLeftX() + 80, -camera.getTopLeftY() + space);
			buttons[ii].render(container, g);
			space += 50;
		}*/
	}
	
	private void initCurrentCenterCards(GameContainer container) {
		uncontrolledCards = new CenterCardViewer("Uncontrolled Groups", 450, 615, container, this);
		for(int ii = 0; ii < 4; ii++)
			uncontrolledCards.add(groupCard.remove(0));
		//uncontrolledCards.setLocation(450, 615);
	}
	
	private void renderCurrentCenterCards(GameContainer container, Graphics g) {
		int spacing = 0;
		for(int ii = 0; ii < currentCenterCards.size(); ii++) {
			g.drawImage(currentCenterCards.get(ii).getImage(0), 500 + spacing, 100);
			spacing += 150;
		}
	}
}
