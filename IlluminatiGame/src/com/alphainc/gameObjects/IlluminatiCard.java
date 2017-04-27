package com.alphainc.gameObjects;

/**
 * An Illuminati card in the game of Illuminati.
 * @author crystalchun
 *
 */
public class IlluminatiCard extends StructureCard
{
	/**This Illuminati's special power.*/
	private String specialPower;
	
	/**
	 * Main constructor of the Illuminati card.
	 * @param name The name of this Illuminati.
	 * @param special The special power this Illuminati card has.
	 * @param attackPower The power of this card.
	 * @param income The income of this card.
	 * @param arrows The arrows on this card.
	 */
	public IlluminatiCard(String name, String special, int attackPower, int income, Arrow[] arrows)
	{
		//Calls StructureCard constructor
		super(name, attackPower, income, arrows, false);
		specialPower = special;
	}

	/**
	 * Overloaded constructor for when Illuminati card does not have a special power.
	 * @param name The name of the Illuminati card.
	 * @param attackPower The power of this card.
	 * @param income The income of this card.
	 * @param arrows The arrows on this card.
	 */
	public IlluminatiCard(String name, int attackPower, int income, Arrow [] arrows)
	{
		this(name, "", attackPower, income, arrows);
	}
	
	/**
	 * Gets this Illuminati's special power.
	 * @return This Illuminati's special power.
	 */
	public String getSpecial()
	{
		return specialPower;
	}
	
	public String getName()
	{
		return super.getName();
	}
	
	public int getAttackPower()
	{
		return super.getAttack();
	}
	
	public int getIncome()
	{
		return super.getIncome();
	}
	
	public int getTreasury()
	{
		return super.getTreasury();
	}
	
	public void addMoney(int money)
	{
		super.addMoney(money);
	}
	
	public void addIncome()
	{
		super.addIncome();
	}
	
	public void removeMoney(int amount)
	{
		removeFromBank(amount);
	}
	
	public Arrow[] getArrows()
	{
		return super.getArrows();
	}
	
	public boolean hasOpenArrow()
	{
		return super.hasOpenArrow();
	}
	
	public Arrow getOpenArrow()
	{
		return super.getOpenArrow();
	}
	
	public Arrow getArrow(String location)
	{
		return super.getArrow(location);
	}
	
	public void connect(StructureCard card)
	{
		super.connect(card);
	}
	
	public void connect(Arrow connectWith, StructureCard card)
	{
		super.connect(connectWith, card);
	}
	
	public void connect(Arrow connectTo, Arrow connectWith)
	{
		super.connect(connectTo, connectWith);
	}
	
}
