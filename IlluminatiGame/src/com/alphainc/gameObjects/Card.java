package com.alphainc.gameObjects;

public class Card 
{
	/**The boolean variable that says whether or not this card is flipped.*/
	private boolean flipped;
	
	/**
	 * Constructs a card that specifies whether or not the card is flipped.
	 * @param flipped A boolean that is true if the card is flipped or false
	 * if the card is not flipped.
	 */
	public Card(boolean flipped)
	{
		this.flipped = flipped;
	}
	
	/**
	 * Constructs a card that is not flipped by default.
	 */
	public Card()
	{
		this(false);
	}
}
