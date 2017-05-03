package com.alphainc.gameObjects;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Super class for a card that is part of a structure.
 * @author crystalchun
 *
 */
public class StructureCard extends Card
{
	/**The name of the card.*/
	private String name;
	/**The special this card has.*/
	private String special;
	/**The attack power of this card.*/
	private int attackPower;
	/**The amount of transferable power this card has.*/
	private int transferablePower;
	/**The resistance of this card.*/
	private int resistance;
	/**The income of this card.*/
	private int income;
	/**The treasury (bank) of this card.*/
	private int treasury;
	/**The arrows on this card.*/
	private Arrow[] arrows;
	/** Card image */
	private Image cardImage, scaledCardImage;
	/** Card bounds */
	private int cardWidth, cardHeight, scaledCardWidth, scaledCardHeight;
	
	/**
	 * The main constructor for a structure card object.
	 * @param name The name of this card.
	 * @param special The special this card has.
	 * @param attackPower The attack power of this card.
	 * @param resistance The resistance this card has.
	 * @param income The income this card gets. 
	 * @param arrows The arrows on this card.
	 * @param flipped Whether or not this card is flipped over.
	 * @param transferrable The amount of transferable power this card has.
	 */
	public StructureCard(String path, String name, String special, int attackPower, boolean flipped, int resistance, Arrow[] arrows, int income, int transferable)
	{
		//Calls Card constructor
		super(flipped);
		initCardFace(path);
		this.name = name;
		this.special = special;
		this.attackPower = attackPower;
		this.resistance = resistance;
		this.income = income;
		this.treasury = 0;
		this.arrows = arrows;
		this.transferablePower = transferable;
	}
	
	/**
	 * Overloaded constructor for when this card is automatically not flipped over.
	 * @param name The name of the card.
	 * @param special The special this card has.
	 * @param attackPower The attack power this card has.
	 * @param resistance The resistance this card has.
	 * @param income The income this card gets.
	 * @param arrows The arrows on this card.
	 * @param transferrable The amount of transferable power this card has.
	 */
	public StructureCard(String path, String name, String special, int attackPower, int resistance, int income, Arrow[] arrows, int transferable)
	{
		this(path, name, special, attackPower, false, resistance, arrows, income, transferable);
	}
	
	/**
	 * Overloaded constructor for a structure card with no resistance.
	 * @param transferrable The amount of transferable power this card has.
	 * @param name The name of this card.
	 * @param special The special this card has.
	 * @param attackPower The amount of attack power this card has.
	 * @param income The income this card gets.
	 * @param arrows The arrows this card has.
	 * @param flipped Whether or not this card is flipped over.
	 */
	public StructureCard(String path, int transferable, String name, String special, int attackPower, int income, Arrow[] arrows, boolean flipped)
	{
		this(path, name, special, attackPower, false, 0, arrows, income, transferable);
	}
	
	/**
	 * Overloaded constructor for when this card has no transferable power.
	 * @param name The name of the card.
	 * @param special The special this card has.
	 * @param attackPower The attack power this card has.
	 * @param resistance The resistance this card has.
	 * @param income The income this card gets.
	 * @param arrows The arrows on this card.
	 * @param flipped Whether or not this card is flipped over.
	 */
	public StructureCard(String path, String name, String special, int attackPower, int resistance, int income, Arrow[] arrows, boolean flipped)
	{
		this(path, name, special, attackPower, false, resistance, arrows, income, 0);
	}
	
	/**
	 * Adds this card's income to its treasury.
	 */
	public void addIncome()
	{
		treasury += income;
	}
	
	/**
	 * Adds a specified amount of money into this card's treasury.
	 * @param money The amount of money to be added.
	 */
	public void addMoney(int money)
	{
		treasury += money;
	}
	
	/**
	 * Gets the amount of money in this card's bank.
	 * @return The amount of money in this card's bank.
	 */
	public int getTreasury()
	{
		return treasury;
	}
	
	/**
	 * Gets the amount of income this card earns.
	 * @return The amount of income this card earns.
	 */
	public int getIncome()
	{
		return income;
	}
	
	/**
	 * Removes the specified amount of money from this card's treasury.
	 * @param amount The amount to be removed from this card's treasury.
	 */
	public void removeFromBank(int amount)
	{
		//The amount to be removed must be a positive amount.
		if(amount < 0)
		{
			System.out.println("Error, this is not a valid amount to remove from this card's treasury.");
		}
		else
		{
			//The amount to be removed must be within bounds of how much is in the bank.
			if((treasury - amount) < 0)
			{
				System.out.println("Error, you do not have enough money in this treasury to remove this much.");
			}
			else
			{
				//Removes specified amount from bank.
				treasury -= amount;
			}
		}
	}
	
	/**
	 * Gets all the arrows on this card.
	 * @return All of the arrows on this card.
	 */
	public Arrow[] getArrows()
	{
		return arrows;
	}
	
	/**
	 * Gets the first open out arrow.
	 * @return The first open outward-facing arrow this card has or null if there are no open arrows.
	 */
	public Arrow getOpenOutArrow()
	{
		for(Arrow a: arrows)
		{
			if(!a.isConnected() && a.getDirection())
			{
				return a;
			}
		}
		return null;
	}
	
	/**
	 * Gets the first open in arrow.
	 * @return The first open inward-facing arrow this card has or null if there are no open arrows.
	 */
	public Arrow getOpenInArrow()
	{
		for(Arrow a: arrows)
		{
			if(!a.isConnected() && !a.getDirection())
			{
				return a;
			}
		}
		return null;
	}
	
	/**
	 * Gets the arrow on this card at a specific location.
	 * @param location The location of this arrow on this card.
	 * @return The arrow of this card at the specified location.
	 */
	public Arrow getArrow(int location)
	{
		return arrows[location];
	}
	/**
	 * Gets whether or not this card has an open arrow.
	 * @return True if this card has an open arrow, false otherwise.
	 */
	public boolean hasOpenOutArrow()
	{
		for(Arrow a: arrows)
		{
			if(!a.isConnected() && a.getDirection())
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets this card's name.
	 * @return The name of this card.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Gets how much attack power this card has.
	 * @return The attack power of this card.
	 */
	public int getAttack()
	{
		return attackPower;
	}
	
	/**
	 * Connects this card to another card to the next open arrow of both cards.
	 * @param card The card this card will connect to.
	 */
	public void connect(StructureCard card)
	{
		if(hasOpenOutArrow())
		{
			Arrow arrow = getOpenOutArrow();
			connect(arrow, card.getOpenInArrow());
		}
		else
		{
			System.out.println("This card does not have any open arrows to connect to.");
		}
	}
	
	/**
	 * Connects a specified arrow from this card to another card's
	 * first open arrow.
	 * @param connectWith The arrow to connect with.
	 * @param card The card to connect with.
	 */
	public void connect(Arrow connectWith, StructureCard card)
	{
		//If the arrow is already connected, gives an error.
		if(connectWith.isConnected())
		{
			System.out.println("Error, this arrow is not opened to connect with.");
			
		}
		else
		{
			//Connects this arrow with the next open arrow.
			connect(connectWith, card.getOpenInArrow());
		}
	}
	
	/**
	 * Connects this card's arrow to another card's arrow.
	 * @param connectTo The arrow this card's arrow is to be connected to.
	 * @param connectWith The arrow this card has.
	 */
	public void connect(Arrow connectTo, Arrow connectWith)
	{
		//Tests if connectWith is an open out arrow and connectTo is an open in arrow
		if(!connectWith.isConnected() && connectWith.getDirection() && !connectTo.getDirection() && connectTo.isConnected())
		{
			connectWith.setConnection(connectTo);
			connectTo.setConnection(connectWith);
		}
	}
	public int getTransferablePower()
	{
		return transferablePower;
	}
	/**
	 * Gets the resistance power this card has.
	 * @return The resistance power of this card.
	 */
	public int getResistance()
	{
		return resistance;
	}
	
	/**
	 * Gets whether or not this card is flipped;
	 * @return True if this card is flipped, false otherwise.
	 */
	public boolean isFlipped()
	{
		return super.isFlipped();
	}
	
	/**
	 * Flips the card the other way.
	 */
	public void flip()
	{
		super.flip();
	}
	
	/**
	 * Flips the card to the specified way.
	 * @parameter flip The way the card is flipped, true if flipped face-side up, false otherwise.
	 */
	public void flip(boolean flip)
	{
		super.flip(flip);
	}
	
	public String getSpecial()
	{
		return special;
	}
	/**
	 * Gets the card image
	 * 
	 * @param choice front or back
	 * @return the card image
	 */
	public Image getImage(int choice) {
		return cardImage;
	}
	/**
	 * Rotates image and scaled version by 90 degrees. Updates bounds.
	 */
	public void rotate() {
		cardImage.rotate(90);
		scaledCardImage.rotate(90);
	}
	
	private void initCardFace(String p) {
		try {
			cardImage = new Image(p);
			cardWidth = cardImage.getWidth();
			cardHeight = cardImage.getHeight();
			scaledCardImage = new Image(p).getScaledCopy(0.5F);
			scaledCardWidth = scaledCardImage.getWidth();
			scaledCardHeight = scaledCardImage.getHeight();
		} catch (SlickException se) {
			System.err.println("Could not find card image of card " + name);
			se.printStackTrace();
		}
	}
	
	
}