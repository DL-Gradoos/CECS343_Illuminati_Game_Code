package com.alphainc.game.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.alphainc.game.Camera;
import com.alphainc.game.gameObjects.Arrow;
import com.alphainc.game.gameObjects.Deck;
import com.alphainc.game.gameObjects.IlluminatiCard;
import com.alphainc.game.gameObjects.StructureCard;
import com.alphainc.game.gameObjects.PowerStructure;
import com.alphainc.game.player.PlayerGUI;
/**
 * The main game
 * 
 * @author Daniel
 *
 */
public class Game extends BasicGameState implements KeyListener {
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
	private List<StructureCard> illumCard;
	/** When shifting the camera, keeps gui locked to side of screen */
	private Camera camera;
	/** The players */
	private PlayerGUI player[];
	/** The dice rolls */
	private int orderRoll[];
	/** The order in which players will go */
	private List<PlayerGUI> playerOrder;
	/** Whose turn it is */
	private int turn = 0;
	/** Iluminati Cards */
	private StructureCard ilCards[];
	//Changes card displayed on space press
	private boolean temp;
	public Game(int id) {
		mID = id;
	}
	
private int counter;
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		/** Initialize only when entered into from Menu state */
		System.out.println("game " + Menu.players);
		
//Clean up
		counter = 0;
		if(Menu.players > 0) 
		{
			bg = new Image("com/alphainc/res/gui/tabletop.png");

			initIllumCards(container);
			initPlayers(container);
			determinePlayerOrder();
			assignIllumCard();
			//initCards();
			
			
			ilCards = new IlluminatiCard[8];
			//Testing illuminati card 1 initialize
			ilCards[0] = new IlluminatiCard("res/cards/illum/thenetwork.png", "The Network", "special", 10, 9,
						 new Arrow[] {
								 new Arrow(false, null, true),
								 new Arrow(false, null, true),
								 new Arrow(false, null, true),
								 new Arrow(false, null, true)
						 });
			ilCards[0].setPosition(container.getScreenWidth()/2, container.getScreenHeight()/2 - 105);
			//ilCards[0].rotate(90);
			//Illuminati card 2 initialize
			ilCards[1] = new IlluminatiCard("src/com/alphainc/res/cards/thesocietyofassassins.png", "The Society of Assassins", "special", 10, 9,
					 new Arrow[] {
							 new Arrow(false, null, true),
							 new Arrow(false, null, true),
							 new Arrow(false, null, true),
							 new Arrow(false, null, true)
					 });
			ilCards[2] = new IlluminatiCard("src/com/alphainc/res/cards/thesocietyofassassins.png", "The Society of Assassins", "special", 10, 9,
					 new Arrow[] {
							 new Arrow(false, null, true),
							 new Arrow(false, null, true),
							 new Arrow(false, null, true),
							 new Arrow(false, null, true)
					 });
			
			ilCards[1].setPosition(container.getScreenWidth()/2, container.getScreenHeight()/2 - 105);
			
			//ilCards[1].rotate(90);
			//ilCards[1].rotateToThisAmount(0);
			//System.out.println("current rotation" + ilCards[1].getRotate());
			/*ilCards[0].rotate(90);
			ilCards[0].connect(ilCards[1], 2, 0); //Illum card 0 connects with top arrow to Illum card 1's top arrow
			ilCards[1].connect(ilCards[2], 1, 0);*/
			
			/** TESTING IMAGES, CAN DELETE IF YOU WANT */
			card = new Image("src/com/alphainc/res/cards/theufos.png").getScaledCopy(0.5F);
			System.out.println("CARD BEFORE: " + card.getWidth() + " " + card.getHeight());
			card.setRotation(90);
			//card.setImageColor(0.5f, 0.5f, 0.5f);
			System.out.println("CARD AFTER: " + card.getWidth() + " " + card.getHeight());
			card2 = new Image("res/cards/illum/thenetwork.png").getScaledCopy(0.5F);
			/*card2.rotate(90);
			System.out.println("CARD 1: " + card.getWidth() + " " + card.getHeight());
			System.out.println("CARD 2: " + card2.getWidth() + " " + card2.getHeight());*/
			//test = new StructureCard();
		}
	}



	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		bg.draw();
		/* Renders the current players gui */
		for(int ii = 0; ii < player.length; ii++) {
			playerOrder.get(ii).render(container, g);
		}
//Clean up
/*		if(counter == 90)
		{
			
			
			counter = 0;
		}
		counter ++;*/
		ilCards[0].render(container, g);
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
		card.draw(20, 20);
		//card.drawCentered(100, 100);
		card2.draw(20,20);
		//g.drawImage(card, 100, 100);
		//g.draw(card2, 100, 100);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		
	}

	@Override
	public int getID() {
		
		return mID;
	}
	
	@Override
	public void keyPressed(int key, char c) {
		/* Go to next player's turn */
		if(key == Input.KEY_SPACE) {
			playerOrder.get(turn).setShouldBeRendered(false);
			turn = ((turn + 1) > player.length - 1) ? 0 : turn + 1;
			/*if((turn + 1) > (player.length - 1))	
				turn = 0;
			else
				turn++;*/
			playerOrder.get(turn).setShouldBeRendered(true);
//Clean up
			//System.out.println("We're connecting 1's top with 2's bottom");
			/*System.out.println("1. " +ilCards[0].getName() + " Rotation: " + ilCards[0].getRotate());
			System.out.println("2. " + ilCards[1].getName() + " Rotation: " + ilCards[1].getRotate());*/
			//temp = true;
			if(temp)
			{
				//ilCards[0].rotate(90);
				temp = false;
			}
			else
			{
				temp = true;
			}
			
		}
		else if(key == Input.KEY_F)
		{
			//System.out.println("current rotation" + ilCards[1].getRotate());
		/*	
			System.out.println("Incorrect connection.");
			System.out.println("1. " +ilCards[0].getName() + " Arrow:" 
			+ ilCards[0].getConnectedArrow() + " " + " Rotation: " + ilCards[0].getRotate());
			System.out.println("2. " +ilCards[1].getName() + " Arrow:"  
			+ ilCards[1].getConnectedArrow() + " " + " Rotation: " + ilCards[1].getRotate());*/
			
			/*ilCards[0].rotate(90);
			ilCards[1].setRotation(0);
			ilCards[2].setRotation(0);
			ilCards[0].connect(ilCards[1], 1, 0);
			ilCards[1].connect(ilCards[2], 0, 2);*/
		}
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		if(button == Input.MOUSE_LEFT_BUTTON) {
			for(StructureCard sc : illumCard)
				sc.flip();
			//ilCards[0].flip();
		}
	}
	

	/**
	 * Initializes the amount of players from the option chosen in Menu
	 * @param container The game container
	 */
	private void initPlayers(GameContainer container) {
//Clean up
		//Player GUI's init
		player = new PlayerGUI[Menu.players];
		for(int ii = 0; ii < player.length; ii++) {
			player[ii] = new PlayerGUI(container, "Player " + (ii + 1), ii);
		}
		//Init Camera
		camera = new Camera();
		//Init background image
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
		//System.out.println("Turn order:");
		//for(PlayerGUI p : playerOrder) System.out.println(p.getPlayerName() + " " + p.getRoll());
	}
	
	
	/**
	 * Initializes Illuminati Cards
	 */
	private void initIllumCards(GameContainer container) {
		Deck deck = new Deck();
		illumCard = deck.getIlluminatiDeck();
		for(StructureCard sc : illumCard) {
			sc.setPosition(container.getWidth() / 2 + 150, container.getHeight() / 2);
			sc.flip();
		}
		/*illumCard[0] = new IlluminatiCard("res/illumcards/thebavarianilluminati.png", 
				"The Bavarian Illuminati", "May make one privileged attack each turn at a cost of 5MB.",
				10, 9, );*/
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
}