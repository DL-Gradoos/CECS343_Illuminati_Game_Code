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
	/** Testing stuff */
	private StructureCard test;
	private Image card, card2;
	/** Background */
	private Image bg;
	/** List of illumnati cards */
	private List<StructureCard> illumCard, groupCard, currentCenterCards;
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
			
			
			
			/** TESTING IMAGES, CAN DELETE IF YOU WANT */
			/*card = new Image("res/cards/thesocietyofassassins.png").getScaledCopy(0.5F);
			System.out.println("CARD BEFORE: " + card.getWidth() + " " + card.getHeight());
			card.setRotation(90);
			card.setImageColor(0.5f, 0.5f, 0.5f);
			System.out.println("CARD AFTER: " + card.getWidth() + " " + card.getHeight());
			card2 = new Image("res/cards/thenetwork.png").getScaledCopy(0.5F);*/
			/*card2.rotate(90);
			System.out.println("CARD 1: " + card.getWidth() + " " + card.getHeight());
			System.out.println("CARD 2: " + card2.getWidth() + " " + card2.getHeight());*/
			//test = new StructureCard();
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.translate(camera.getTopLeftX(), camera.getTopLeftY());
		bg.draw();
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
		//Attack
		if(source == buttons[0]) {
			
		}
		//Transfer Money
		if(source == buttons[1]) {
			
		}
		//Move a Group
		if(source == buttons[2]) {
			
		}
		//Give a Group Away
		if(source == buttons[3]) {
			
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
			bg = new Image("res/gui/tabletop.png");
		} catch (SlickException e) {
			System.err.println("COULD NOT LOAD IMAGE");
			e.printStackTrace();
		}
		prevTurn = player.length - 1;
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
		buttonImage = new Image[4];
		buttons = new MovableMouseOverArea[4];
		try {
			int space = 300;
			for(int ii = 0; ii < 4; ii++) {
				buttonImage[ii] = new Image("res/gui/attack.png").getScaledCopy(0.3F);
				buttons[ii] = new MovableMouseOverArea(container, buttonImage[ii], (int) (-camera.getTopLeftX() + 100), (int) (-camera.getTopLeftY() + space), buttonImage[ii].getWidth(),
						buttonImage[ii].getHeight(), this);
				buttons[ii].setNormalColor(new Color(1, 1, 1, 0.5F));
				buttons[ii].setMouseOverColor(new Color(1, 1, 1, 1.0F));
				space += 50;
			}
		} catch (SlickException e) {
			System.err.println("SIDE BAR IMAGES NOT LOADED");
			e.printStackTrace();
		}
	}
	
	private void renderSideBarElements(GameContainer container, Graphics g) {
		int space = 300;
		for(int ii = 0; ii < buttons.length; ii++) {
			buttons[ii].updateLocation(-camera.getTopLeftX() + 80, -camera.getTopLeftY() + space);
			buttons[ii].render(container, g);
			space += 50;
		}
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
