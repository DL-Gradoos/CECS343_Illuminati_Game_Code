package com.alphainc.gameObjects;

/**
 * An arrow on one of the cards in the game Illuminati.
 * @author crystalchun
 *
 */
public class Arrow 
{
	/**The location of the arrow on a card.*/
	private String location;
	/**The card this arrow is on.*/
	private StructureCard card;
	/**Whether or not this arrow is connected to anything.*/
	private boolean connected;
	/**The arrow this arrow is connected to.*/
	private Arrow connectedTo;
	/**The direction the arrow points, true for out, false for in.*/
	private boolean direction;
	
	/**
	 * Constructor for an arrow object.
	 * @param location The location of this arrow on a card.
	 * @param card The card this arrow is located on.
	 * @param connected Whether or not the arrow is connected to something else.
	 * @param connectedTo The arrow it is connecting to.
	 * @param direction The direction the arrow points, true if the arrow points outwards, false if the arrow points inward.
	 */
	public Arrow(String location, StructureCard card, boolean connected, Arrow connectedTo, boolean direction)
	{
		this.location = location;
		this.card = card;
		this.connected = connected;
		this.connectedTo = connectedTo;
		this.direction = direction;
	}
	/**
	 * Overloaded constructor for this arrow. The arrow is unconnected by default.
	 * @param location The location of this arrow on a card.
	 * @param card The card this arrow is located on.
	 * @param direction The direction the arrow the points, true for out, false for in.
	 */
	public Arrow(String location, StructureCard card, boolean direction)
	{
		this(location, card, false, null, direction);
	}
	
	/**
	 * Sets what this arrow is connected to.
	 * @param connection The arrow this arrow will connect to.
	 */
	public void setConnection(Arrow connection)
	{
		connectedTo = connection;
		connected = true;
	}

	
	/**
	 * Removes the connection.
	 */
	public void removeConnection()
	{
		connected = false;
		connectedTo = null;
	}
	
	/**
	 * Gets the location on the card where this arrow is.
	 * @return The location on the card where this arrow is.
	 */
	public String getLocation()
	{
		return location;
	}
	
	/**
	 * Gets the card this arrow is on.
	 * @return The card this arrow is on.
	 */
	public StructureCard getCard()
	{
		return card;
	}
	
	/**
	 * Gets whether or not this arrow is connected.
	 * @return True if the arrow is connected to something, false otherwise.
	 */
	public boolean isConnected()
	{
		return connected;
	}
	
	/**
	 * Gets the arrow this arrow connects to.
	 * @return The arrow this arrow is connected to.
	 */
	public Arrow connectedTo()
	{
		return connectedTo;
	}
	
	/**
	 * Returns the card this arrow is connected to.
	 * @return The card this arrow is connected to.
	 */
	public StructureCard getCardConnectedTo()
	{
		return connectedTo.getCard();
	}
	
	public boolean getDirection()
	{
		return direction;
	}
}
