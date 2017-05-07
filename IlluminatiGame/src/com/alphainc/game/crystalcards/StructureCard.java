package com.alphainc.game.crystalcards;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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
	private int rotated;
	/** Coords */
	private int xCoords, yCoords;
	
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
		this.rotated = 0;
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
	 * Gets whether or not this card has an open arrow.
	 * @return True if this card has an open arrow, false otherwise.
	 */
	public boolean hasOpenInArrow()
	{
		for(Arrow a: arrows)
		{
			if(!a.isConnected() && !a.getDirection())
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

	public int getWidth()
	{
		return scaledCardImage.getWidth();
	}
	public int getHeight()
	{
		return scaledCardImage.getHeight();
	}
/*//Clean up
	public int getConnectedArrow()
	{
		for(int i = 0; i < arrows.length; i++)
		{
			if(arrows[i].isConnected())
			{
				return i;
			}
			
		}
		return -1;
	}
	
	public void removeConnections()
	{
		for(Arrow a: arrows)
		{
			a.removeConnection();
		}
	}*/
	
	/**
	 * Connects this card's arrow to another card's arrow.
	 * @param card The card you're connecting to.
	 * @param connectTo The arrow this card's arrow is to be connected to.
	 * @param connectWith The arrow this card has.
	 */
	public void connect(StructureCard card, int connectTo, int connectWith)
	{
		
		//Tests if connectWith is an open out arrow and connectTo is an open in arrow
		if(true/*!arrows[connectWith].isConnected() 
				&& arrows[connectWith].getDirection() 
				&& !card.getArrow(connectTo).getDirection()
				&& !card.getArrow(connectTo).isConnected()*/)
		{

			//Connects
			arrows[connectWith].setConnection(card.getArrow(connectTo));
			card.getArrow(connectTo).setConnection(arrows[connectWith]);

			if(rotated != 0)
			{
				if(rotated % 180 != 0)
				{
					//This card is rotated either 90 degrees or 270 degrees
					
					if(connectWith % 2 == 0) //This card's arrow is "technically" L/R
					{
						int xAdjust = 0;
						//Rotate card connecting
						if((connectWith == 0 && rotated == 90) 
								|| (connectWith == 2 && rotated == 270)) //this = top or bottom, but really right 
						{
							if(connectTo == 0) //card = top
							{
								card.rotate(270);
								xAdjust = card.getHeight()/2;
							}
							else if(connectTo == 2) //card = bottom
							{
								card.rotate(90);
								xAdjust = card.getHeight()/2;
							}
							else if(connectTo == 1) //card = right
							{
								card.rotate(180);
								xAdjust = card.getWidth()/2;
							}
							else
							{
								xAdjust = card.getWidth()/2;
							}
							
							//Set card's new position
							card.setPosition(xCoords + this.getHeight()/2 + xAdjust, yCoords);
						}
						else if((connectWith == 0 && rotated == 270) 
								|| (connectWith == 2 && rotated == 90)) //this = top or bottom, but really left
						{
							if(connectTo == 0) //card = top
							{
								card.rotate(90);
								xAdjust = card.getHeight()/2;
							}
							else if(connectTo == 2) //card = bottom
							{
								card.rotate(270);
								xAdjust = card.getHeight()/2;
							}
							else if(connectTo == 3) //card = left
							{
								card.rotate(180);
								xAdjust = card.getWidth()/2;
							}
							else
							{
								xAdjust = card.getWidth()/2;
							}
							
							//Set card's new position
							card.setPosition(xCoords - this.getHeight()/2 - xAdjust, yCoords);
						}
					}
					else if (connectWith % 2 == 1) //Really T/B
					{
						int yAdjust = 0;
						if((connectWith == 1 && rotated == 90) || (connectWith == 3 && rotated == 270))
						{
							//this = right/left, but really bottom
							if(connectTo == 1) //card = right
							{
								card.rotate(270);
								yAdjust = card.getWidth()/2;
							}
							else if(connectTo == 3) //card = left
							{
								card.rotate(90);
								yAdjust = card.getWidth()/2;
							}
							else if(connectTo == 2) //card = bottom
							{
								card.rotate(180);
								yAdjust = card.getHeight()/2;
							}
							else //card = top
							{
								yAdjust = card.getHeight()/2;
							}
							
							//Set position
							card.setPosition(xCoords, yCoords + yAdjust + getWidth()/2);
						}
						else if((connectWith == 1 && rotated == 270) || (connectWith == 3 && rotated == 90))
						{
							//this = right/left, but really top
							if(connectTo == 1) //card = right
							{
								card.rotate(90);
								yAdjust = card.getWidth()/2;
							}
							else if(connectTo == 3) //card = left
							{
								card.rotate(270);
								yAdjust = card.getWidth()/2;
							}
							else if(connectTo == 0) //card = top
							{
								card.rotate(180);
								yAdjust = card.getHeight()/2;
							}
							else //card = bottom
							{
								yAdjust = card.getHeight()/2;
							}
							
							//Set position
							card.setPosition(xCoords, yCoords - yAdjust - getWidth()/2);
						}
					}
				}
				else
				{
					//This card is flipped 180 degrees
					if(connectWith == 0) //this = top, but really bottom
					{
						int yAdjust = 0;
						if(connectTo == 1) //connecting to = right
						{
							card.rotate(270);
							yAdjust = card.getWidth()/2;
						}
						else if(connectTo == 3) //connecting to = left
						{
							card.rotate(90);
							yAdjust = card.getHeight()/2;
						}
						else if(connectTo == 2) //connecting to = bottom
						{
							card.rotate(180);
							yAdjust = card.getHeight()/2;
						}
						else
						{
							yAdjust = card.getHeight()/2;
						}
						card.setPosition(xCoords, yCoords + getHeight()/2 + yAdjust);
					}
					else if(connectWith == 2) //This = bottom, but really top
					{
						int yAdjust = 0;
						if(connectTo == 1) //connecting to = right
						{
							yAdjust = card.getWidth()/2;
							card.rotate(90);
						}
						else if(connectTo == 3) //connecting to = left
						{
							yAdjust = card.getWidth()/2;
							card.rotate(270);
						}
						else if(connectTo == 0) //Connecting to = top
						{
							card.rotate(180);
							yAdjust = card.getHeight()/2;
						}
						else
						{
							yAdjust = card.getHeight()/2;
						}
						card.setPosition(xCoords, yCoords - getHeight()/2 - yAdjust);
					}
					else if(connectWith == 1) //This = right, but really left
					{
						int xAdjust = 0;
						if(connectTo == 0) //connect to = top
						{
							card.rotate(90);
							xAdjust = card.getHeight()/2;
						}
						else if(connectTo == 2) //Connect to = bottom
						{
							card.rotate(270);
							xAdjust = card.getHeight()/2;
						}
						else if(connectTo == 3) //connect to = left
						{
							card.rotate(180);
							xAdjust = card.getWidth()/2;
						}
						else
						{
							xAdjust = card.getWidth()/2;
						}
						card.setPosition(xCoords - xAdjust - getWidth()/2, yCoords);
					}
					else //This = left, but really right
					{
						int xAdjust = 0;
						if(connectTo == 0) //connect to = top
						{
							card.rotate(270);
							xAdjust = card.getHeight()/2;
						}
						else if(connectTo == 2) //Connect to = bottom
						{
							card.rotate(90);
							xAdjust = card.getHeight()/2;
						}
						else if(connectTo == 1) //connect to = right
						{
							card.rotate(180);
							xAdjust = card.getWidth()/2;
						}
						else
						{
							xAdjust = card.getWidth()/2;
						}
						card.setPosition(xCoords + xAdjust + getWidth()/2, yCoords);
					}
				}
				
				
			}
			else
			{
				//Checks to see if have to rotate card and check if this card is rotated
				if(connectTo % 2 == 1 && connectWith % 2 == 0)
				{
					//Need to add/subtract half this height and add half width to get y coordinate
					if(connectWith == 0 ) //this = top
					{
						if(connectTo == 1) //connecting to = right
						{
							card.rotate(90);
						}
						else //connecting to = left
						{
							card.rotate(270);
						}
						card.setPosition(xCoords, (yCoords - (scaledCardImage.getHeight()/2)) - (card.getWidth()/2));
					}
					else //This = bottom
					{
						if(connectTo == 1) //connecting to = right
						{
							card.rotate(270);
						}
						else //connecting to = left
						{
							card.rotate(90);
						}
						card.setPosition(xCoords, (yCoords + (scaledCardImage.getHeight()/2)) + (card.getWidth()/2));
					}
				}
				else if (connectTo % 2 == 0 && connectWith % 2 == 1)
				{
					if(connectWith == 1) //This = right
					{
						if(connectTo == 0) //connect to = top
						{
							card.rotate(270);
						}
						else //Connect to = bottom
						{
							card.rotate(90);
						}
						card.setPosition(xCoords + (scaledCardImage.getWidth()/2) + (card.getWidth()/3), yCoords);
					}
					else //This = left
					{
						if(connectTo == 0) //connect to = top
						{
							card.rotate(90);
							
						}
						else //Connect to = bottom
						{
							card.rotate(270);
						}
						card.setPosition(xCoords - (scaledCardImage.getWidth()/2) - (card.getWidth()/3), yCoords);
					}
				}
				else
				{
					//The cards connecting are on the same "plane"
					if(connectTo == connectWith)
					{
						//Rotate the card connecting to this card 180
						card.rotate(180);
					}
					
					//Sets new position of card
					if(connectWith % 2 == 0)
					{
						int y = 0;
						//The card is connecting at the top 
						if(connectWith == 0)
						{
							y = yCoords - getHeight()/2 - card.getHeight()/2;
						}
						else //The card is connecting at the bottom
						{
							y = yCoords + getHeight()/2 + card.getHeight()/2;
						}
						card.setPosition(xCoords, y);
					}
					else
					{
						int x = 0;
						if(connectWith == 1) //The card is connecting on the right
						{
							x = xCoords + getWidth()/2 + card.getWidth() / 2;
						}
						else //The card is connecting on the left
						{
							x = xCoords - getWidth()/2 - card.getWidth() / 2;
						}
						card.setPosition(x, yCoords);
					}

				}
			}

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
	
	public void setPosition(int x, int y) 
	{
		xCoords = x;
		yCoords = y;
	}
	public void render(GameContainer container, Graphics g) 
	{
			scaledCardImage.drawCentered(xCoords, yCoords);

	}
	/**
	 * Rotates image by specified amount
	 */
	public void rotate(int amount) 
	{
		if(rotated+amount >= 360)
		{
			rotated = rotated + amount;
			rotated = rotated % 360;
			cardImage.rotate(amount);
			scaledCardImage.rotate(amount);
		}
		else
		{
			rotated = rotated + amount;
			cardImage.rotate(amount);
			scaledCardImage.rotate(amount);
		}
		
	}
	public void setRotation(int amount)
	{
		int diff = 360 - rotated;
		if(amount >= 360)
		{
			rotated = amount % 360;
		}
		rotated = amount;
		//Resets rotation to 0
		cardImage.rotate(diff);
		scaledCardImage.rotate(diff);
		
		//Rotates to this angle
		cardImage.rotate(rotated);
		scaledCardImage.rotate(rotated);
		
	}
	public int getRotate()
	{
		return rotated;
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