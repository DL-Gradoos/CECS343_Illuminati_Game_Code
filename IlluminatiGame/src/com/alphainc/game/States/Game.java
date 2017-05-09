package com.alphainc.game.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import com.alphainc.game.components.MessageBox;
import com.alphainc.game.components.MovableMouseOverArea;
import com.alphainc.game.Camera;
import com.alphainc.game.gameObjects.Arrow;
import com.alphainc.game.gameObjects.Deck;
import com.alphainc.game.gameObjects.GroupCard;
import com.alphainc.game.gameObjects.IlluminatiCard;
import com.alphainc.game.gameObjects.StructureCard;
import com.alphainc.game.gameObjects.PowerStructure;
import com.alphainc.game.gameObjects.SpecialCard;
import com.alphainc.game.player.PlayerGUI;
//To do: Create buttons for type of attack
//When button is clicked, check for prereqs if any
//Make structure clickable? Then show list of players and make their structure clickab
/**
 * The main game
 * 
 * @author Daniel
 *
 */
public class Game extends BasicGameState implements KeyListener, ComponentListener {
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
	/** Illuminati Cards */
	private StructureCard ilCards[];
	/** Boolean for allowing camera movement 0 = left, 1 = up, 2 = right, 3 = down*/
	private boolean cameraMovement [];
	private Deck deck;
	//Changes card displayed on space press
	private boolean temp;
	private int prevTurn;
	
	private boolean attack;
	
	/** TESTING (DANIEL) */
	private MessageBox msgBox;
	/** Action button images */
	private Image buttonImage[];
	/** Action buttons */
	private MovableMouseOverArea buttons[];
	/** Number of actions left */
	private int actions = 2;

	
	
	public Game(int id) {
		mID = id;
	}
	
private int counter;
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		/** Initialize only when entered into from Menu state */
		System.out.println("game " + Menu.players);


		if(Menu.players > 0) 
		{
			bg = new Image("com/alphainc/res/gui/tabletop.png");
			initMessageBox(container);
			initIllumCards(container);
			initGroupCards(container);
			initPlayers(container);
			determinePlayerOrder();
			assignIllumCard();
			initSideBarButtons(container);
			initCenterCards(container);
			msgBox.addMessage("THIS IS MORE THAN 30 CHARACTERS WHAT WILL THE PROGRAM DO?");
		}
	}



	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
		g.translate(camera.getTopLeftX(), camera.getTopLeftY());
		bg.draw();

		
		
		//Renders uncontrolled group cards
		for(StructureCard c: currentCenterCards)
		{
			c.render(container, g);
		}
		/* Renders the current players gui */
		playerOrder.get(turn).render(container, g);
		
		renderSideBarElements(container, g);
		msgBox.render(container, g);


		//renderCurrentCenterCards(container, g);
//Clean up
/*		if(counter == 90)
		{
			
			
			counter = 0;
		}
		counter ++;*/
		//ilCards[0].render(container, g);
		//ilCards[1].render(container, g);
		//ilCards[2].render(container, g);
		/*if(temp)
		{
			ilCards[0].render(container, g);
		}
		else
		{
			ilCards[1].render(container, g);
		}*/
		
		
		/** TESTING IMAGES, CAN DELETE IF YOU WANT*/
		//card.draw(20, 20);
		//card.drawCentered(100, 100);
		//card2.draw(20,20);
		//g.drawImage(card, 100, 100);
		//g.draw(card2, 100, 100);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		//Move speed of the camera
		float moveSpeed = delta * 0.3f;
		//left
		if(cameraMovement[0]) 
		{
			if(camera.getTopLeftX() > 1000){
				camera.setCurrentTopLeftCoords(1000.999F, camera.getTopLeftY());
			}
			else{
				camera.adjustCoords(moveSpeed, 0);
			}
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

		
	}

	@Override
	public int getID() 
	{
		
		return mID;
	}
	
	@Override
	public void keyPressed(int key, char c) 
	{
		/* Go to next player's turn */
		if(key == Input.KEY_SPACE) 
		{
			playerOrder.get(turn).setShouldBeRendered(false);
			turn = ((turn + 1) > player.length - 1) ? 0 : turn + 1;
			/*if((turn + 1) > (player.length - 1))	
				turn = 0;
			else
				turn++;*/
			playerOrder.get(turn).setShouldBeRendered(true);
			
//Beginning of player's turn stuff
			//Adds income to all player's cards, create alert for this
			playerOrder.get(turn).getPowerStructure().addIncomeAll();
			
			int index = (int)(Math.random()*groupCard.size());
			
			//Draws random card from main deck and displays to user
			StructureCard card = groupCard.remove(index);
			
			//Tests if card is special, if it is allows user to choose to display face up or down
			if(card instanceof SpecialCard)
			{
				
			}
			else
			{
//Clean up
				card.setPosition(430 + (currentCenterCards.size()*140), 600);
				
				//Alert user it is group card, place in uncontrolled area
				currentCenterCards.add(card);
				
//				TESTING CARDS CONNECTING TO ILLUMINATI
/*				int location = -1;
				for(int i = 0; i < card.getArrows().length; i++)
				{
					if(!card.getArrow(i).getDirection() && card.getArrow(i).exists())
					{
						location = i;
						break;
					}
				}
				if(location >= 0)
				{
					System.out.println(location);
					System.out.println(card);
					player[turn].getPowerStructure().get(0).connect(card, location, 0);
					System.out.println(card);
				}
				else
				{
					System.out.println("Failed");
				}*/
				
				
			}
			
		
		}
		else if(key == Input.KEY_F)
		{

		}
		if(key == Input.KEY_LEFT || key == Input.KEY_A)
			cameraMovement[0] = true;
		if(key == Input.KEY_UP || key == Input.KEY_W)
			cameraMovement[1] = true;
		if(key == Input.KEY_RIGHT || key == Input.KEY_D)
			cameraMovement[2] = true;
		if(key == Input.KEY_DOWN || key == Input.KEY_S)
			cameraMovement[3] = true;
		
		if(key == Input.KEY_O)
			msgBox.scrollUp();
		if(key == Input.KEY_L)
			msgBox.scrollDown();
	}
	
	@Override
	public void keyReleased(int key, char c) {
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
	public void mousePressed(int button, int x, int y) 
	{
		if(button == Input.MOUSE_LEFT_BUTTON) 
		{
			for(StructureCard sc : illumCard)
			{
				sc.flip();
			}
		}
	}
	

	/**
	 * Initializes the amount of players from the option chosen in Menu
	 * @param container The game container
	 */
	private void initPlayers(GameContainer container) 
	{
//Clean up
		//Player GUI's init
		player = new PlayerGUI[Menu.players];
		for(int ii = 0; ii < player.length; ii++) 
		{
			player[ii] = new PlayerGUI(container, "Player " + (ii + 1), ii);
		}
		//Init Camera
		camera = new Camera();
		cameraMovement = new boolean[] {false, false, false, false};
	}
	/**
	 * "Rolls 2 die" according to how many players there are and assigns turn order
	 */
	private void determinePlayerOrder() 
	{
		/* Rolls dice */
		Random dice = new Random();
		/* Determines player order */
		playerOrder = new ArrayList<PlayerGUI>(player.length);
		/* Used to keep track of all rolls */
		orderRoll = new int[player.length];
		int currentNum = 0;
		for(int ii = 0; ii < player.length; ii++) 
		{
			currentNum = dice.nextInt(11) + 2;
			/* Ensures no repeats */
			for(int jj = 0; jj < orderRoll.length; jj++) 
			{
				if(currentNum == orderRoll[jj]) 
				{
					currentNum = dice.nextInt(11) + 2;
					jj = -1;
				}
			}
			player[ii].setRoll(currentNum);
			playerOrder.add(player[ii]);
			orderRoll[ii] = currentNum;
		}
		
		
		/* Sorts from highest to lowest */
		Collections.sort(playerOrder, new Comparator<PlayerGUI>() {
			@Override
			public int compare(PlayerGUI arg0, PlayerGUI arg1) {
				if(arg0.getRoll() < arg1.getRoll()){
					return 1;
				}
				else if (arg0.getRoll() > arg1.getRoll()){
					return -1;
				}
				return 0;
			}
			
		});
		playerOrder.get(0).setShouldBeRendered(true);
	/*	msgBox.addMessage("The first player is " + playerOrder.get(0).getPlayerID());
		msgBox.addMessage("The first player is " + playerOrder.get(0).getPlayerID());
		msgBox.addMessage("The first player is " + playerOrder.get(0).getPlayerID());
		msgBox.addMessage("The first player is " + playerOrder.get(0).getPlayerID());
		msgBox.addMessage("The first player is " + playerOrder.get(0).getPlayerID());*/
		//System.out.println("Turn order:");
		//for(PlayerGUI p : playerOrder) System.out.println(p.getPlayerName() + " " + p.getRoll());
	}
	
	
	/**
	 * Initializes Illuminati Cards
	 */
	private void initIllumCards(GameContainer container) 
	{
		deck = new Deck();
		illumCard = deck.getIlluminatiDeck();
		for(StructureCard sc : illumCard) 
		{
			sc.setPosition(container.getScreenWidth()/2, container.getScreenHeight()/2 - 105/*container.getWidth() / 2 + 150, container.getHeight() / 2*/);
			sc.flip();
		}

	}
	public void componentActivated(AbstractComponent source) 
	{
		//Attack
		if(source == buttons[0]) 
		{
			System.out.println("Attack clicked");
		}
		//Transfer Money
		if(source == buttons[1]) 
		{
			
		}
		//Move a Group
		if(source == buttons[2]) 
		{
			
		}
		//Give a Group Away
		if(source == buttons[3]) 
		{
			
		}
		
		//MessageBox Down Arrow
		if(source == msgBox.getDownArrow())
			msgBox.scrollDown();
		//MessageBox Up Arrow
		if(source == msgBox.getUpArrow())
			msgBox.scrollUp();
	}
	/**
	 * Assigns a random illuminati card for each player
	 */
	private void assignIllumCard() 
	{
		Random rand = new Random();
		for(int ii = 0; ii < player.length; ii++) 
		{
			PowerStructure ps = new PowerStructure(ii);
			ps.add(illumCard.remove(rand.nextInt(illumCard.size())));
			//System.out.println(ps.get(0).getName());
			player[ii].addPowerStructure(ps);
		}
	}
	private void initGroupCards(GameContainer container)
	{
		groupCard = deck.getGroupDeck();
	}
	private void initMessageBox(GameContainer container) 
	{
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
		for(int ii = 0; ii < buttons.length; ii++) 
		{
			buttons[ii].updateLocation(-camera.getTopLeftX() + 80, -camera.getTopLeftY() + space);
			buttons[ii].render(container, g);
			space += 50;
		}
	}
	//Uncontrolled groups
	private void initCenterCards(GameContainer container)
	{
		currentCenterCards = new ArrayList<StructureCard>();
		int i = 0;
		while(i < 4)
		{
			int index = (int)(Math.random()*groupCard.size());
			StructureCard temp = groupCard.get(index);
			if(!(temp instanceof SpecialCard))
			{
				temp.setPosition(430 + (i*140), 600);
				temp.flip();
				currentCenterCards.add(temp);
				groupCard.remove(index);
				i++;
			}
		}
	}
	
//TODO: Fill in skeleton
	private void beginAttackToControl()
	{
		//Tests if this user has a group with an open arrow to attack with
		PlayerGUI attacking = player[turn];
		
		if(attacking.getPowerStructure().hasOpenControlArrow())
		{
			//Attacking card
			StructureCard attacker = null;
			//Defending card 
			StructureCard defender = null;
			
			//Gets all viable cards that can be used for the attack
			ArrayList <StructureCard> attackCards = new ArrayList<StructureCard> ();
			for(int i = 0; i < attacking.getPowerStructure().getSize(); i++)
			{
				if(attacking.getPowerStructure().get(i).hasOpenOutArrow())
				{
					attackCards.add(attacking.getPowerStructure().get(i));
				}
			}
			
			//Show only the cards that the attacker can use for the attack
			
			
			//Get the card used here
			attacker = null;
			
			
			ArrayList <PlayerGUI> defenders = new ArrayList <PlayerGUI>();
			//Gets a list of users who can be attacked
			for(PlayerGUI defending : player)
			{
				//Checks to see if player has something to attack
				if(defending.getPowerStructure().getSize() > 1 && defending.getPlayerID() != (turn + 1))
				{
					defenders.add(defending);
				}
			}
			
			//Shows a list of the players who can be attacked as well as an option to attack an uncontrolled group
			//The list of players: defenders
			
			
			//Get the player's power structure or center cards who's getting attacked here if a player was clicked, 
			PowerStructure defend = null; /**/
			//Or use an array list --> ArrayList <StructureCard> defendCards = ;
			
			//Render the list of cards that are able to be attacked here, minus the Illuminati card
			
			
			//Get the defending card
			defender = null;
			
			//Display bonuses
			
			
		}
		else
		{
			//Exit the attack and alert user that they do not have a group with an open control arrow
			
		}
		
	}
	
	private void beginAttackToNeutralize()
	{
		//Attacking card
		StructureCard attacker = null;
		//Defending card
		StructureCard defender = null;
		
		
		//Get the attacker's card via the player's click -- can be Illuminati card
		attacker = null /*Insert here*/;
		
		//Get the attacker's power here
		
		//Display list of players to user minus with prompt of which user they want to attack who already has group cards
		for(PlayerGUI p: player)
		{
			
		}
	}
	
	
	private void beginAttackToDestroy()
	{
//Come back to fix this
		//The card the user uses to attack
		StructureCard attacker = null;
		
		//The card the user wants to destroy
		StructureCard defender = null;
		
		//Test if the attacker has the defending card in their structure
		
		//If they do, the the player may not use that card's transferrable power and may not have an illuminati bonus
		//Prompt user for transferrable power 
		//Calculate illuminati bounses, alignments bonus
		//Reupdate screen with current powers from each
	}
}