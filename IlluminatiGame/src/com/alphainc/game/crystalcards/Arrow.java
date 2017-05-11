package com.alphainc.game.crystalcards;

/**
 * An arrow on one of the cards in the game Illuminati.
 * @author crystalchun
 *
 */
public class Arrow 
{
	/**Whether or not this arrow is connected to anything.*/
	private boolean connected;
	/**The arrow this arrow is connected to.*/
	private Arrow connectedTo;
	/**The direction the arrow points, true for out, false for in.*/
	private boolean direction;
	/**Determines if this arrow exists or not*/
	private boolean exists;
	
	/**
	 * Constructor for an arrow object.
	 * @param connected Whether or not the arrow is connected to something else.
	 * @param connectedTo The arrow it is connecting to.
	 * @param direction The direction the arrow points, true if the arrow points outwards, false if the arrow points inward.
	 */
	public Arrow(boolean exists, boolean connected, Arrow connectedTo, boolean direction)
	{
		this.exists = exists;
		this.connected = connected;
		this.connectedTo = connectedTo;
		this.direction = direction;
	}
	/**
	 * Overloaded constructor for this arrow. The arrow is unconnected by default.
	 * @param direction The direction the arrow the points, true for out, false for in.
	 */
	public Arrow(boolean direction)
	{
		this(true, false, null, direction);
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
	 * Gets which way this arrow is facing, true for out, false for in.
	 * @return True if this arrow faces out, false if this arrow is facing inward.
	 */
	public boolean getDirection()
	{
		return direction;
	}
	
	public boolean exists()
	{
		return exists;
	}
}