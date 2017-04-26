package com.alphainc.gameObjects;
import java.util.ArrayList;

/**
 * A group card in the game of Illuminati.
 * @author crystalchun
 *
 */
public class GroupCard extends StructureCard
{
	private ArrayList <String> alignments;
	private boolean controlled;
	
	/**
	 * Constructs a group card
	 * @param name The name of the group.
	 * @param attackPower The attack power of this group.
	 * @param flipped Whether or not this card is flipped over.
	 * @param resistance The resistance power of this group.
	 * @param income The income this group makes.
	 * @param arrows The arrows this card has.
	 * @param alignments The alignments this card is a part of.
	 * @param controlled Whether or not this group is controlled.
	 */
	public GroupCard(String name, int attackPower, boolean flipped, int resistance, int income, Arrow[] arrows, ArrayList <String> alignments, boolean controlled)
	{
		super(name, attackPower, flipped, resistance, arrows, income);
		this.alignments = alignments;
		this.controlled = controlled;
	}
	
	/**
	 * Initializes an uncontrolled group card.
	 * @param name The name of the group.
	 * @param attackPower The attack power of this group.
	 * @param flipped Whether or not this card is flipped over.
	 * @param resistance The resistance power of this group.
	 * @param income The income this group makes.
	 * @param arrows The arrows this card has.
	 * @param alignments The alignments this card is a part of.
	 */
	public GroupCard(String name, int attackPower, boolean flipped, int resistance, int income, Arrow[] arrows, ArrayList <String> alignments)
	{
		this(name, attackPower, flipped, resistance, income, arrows, alignments, false);
	}
	
	/**
	 * Initializes an uncontrolled, unflipped group card.
	 * @param name The name of the group.
	 * @param attackPower The attack power of this group.
	 * @param resistance The resistance power of this group.
	 * @param income The income this group makes.
	 * @param arrows The arrows this card has.
	 * @param alignments The alignments this card is a part of.
	 */
	public GroupCard(String name, int attackPower, int resistance, int income, Arrow[] arrows, ArrayList <String> alignments)
	{
		this(name, attackPower, false, resistance, income, arrows, alignments, false);
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
