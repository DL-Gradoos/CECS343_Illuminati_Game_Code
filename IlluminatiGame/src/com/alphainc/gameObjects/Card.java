package com.alphainc.gameObjects;

/**
 * A generic card object in the Illuminati game.
 * @author crystalchun
 *
 */
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
	
	/**
	 * Gets whether or not this card is flipped face-side up.
	 * @return True if this card is flipped face-side up, false otherwise.
	 */
	public boolean isFlipped()
	{
		return flipped;
	}
	
	/**
	 * Flips this card the other way.
	 */
	public void flip()
	{
		flipped = !flipped;
	}
	
	/**
	 * Flips this card to the specified way.
	 * @param flip True if this card is flipped face-side up, false otherwise.
	 */
	public void flip(boolean flip)
	{
		flipped = flip;
	}
}
