/**
* This is an arrow class for the Illuminati card game.
**/
public class Arrow
{
	private String location;
	private Card card;
	private boolean connected;
	private Card connectedTo;
	public Arrow(String location, Card card, boolean connected, Card connectedTo)
	{
		this.location = location;
		this.card = card;
		this.connected = connected;
		this.connectedTo = connectedTo;
	}
	public Arrow(String location, Card card)
	{
		Arrow(location, card, false, null);
	}
	public String getLocation()
	{
		return location;
	}
	public Card getCard()
	{
		return card;
	}
	public boolean isConnected()
	{
		return connected;
	}
	public Card connectedTo()
	{
		return connectedTo;
	}
}
