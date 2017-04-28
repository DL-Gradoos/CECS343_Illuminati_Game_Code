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
	 * @param transferable The amount of transferable power this card has.
	 */
	public GroupCard(String name, int attackPower, boolean flipped, int resistance, int income, Arrow[] arrows, ArrayList <String> alignments, boolean controlled, int transferable)
	{
		super(name, attackPower, flipped, resistance, arrows, income, transferable);
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
	 * @param transferable The amount of transferable power this card has.
	 */
	public GroupCard(String name, int attackPower, boolean flipped, int resistance, int income, Arrow[] arrows, ArrayList <String> alignments, int transferable)
	{
		this(name, attackPower, flipped, resistance, income, arrows, alignments, false, transferable);
	}
	
	/**
	 * Initializes an uncontrolled, unflipped group card.
	 * @param name The name of the group.
	 * @param attackPower The attack power of this group.
	 * @param resistance The resistance power of this group.
	 * @param income The income this group makes.
	 * @param arrows The arrows this card has.
	 * @param alignments The alignments this card is a part of.
	 * @param transferable The amount of transferable power this card has.
	 */
	public GroupCard(String name, int attackPower, int resistance, int income, Arrow[] arrows, ArrayList <String> alignments, int transferable)
	{
		this(name, attackPower, false, resistance, income, arrows, alignments, false, transferable);
	}
	
	/**
	 * Initializes an uncontrolled, unflipped group card with no transferable power.
	 * @param name The name of the group.
	 * @param attackPower The attack power of this group.
	 * @param resistance The resistance power of this group.
	 * @param income The income this group makes.
	 * @param arrows The arrows this card has.
	 * @param alignments The alignments this card is a part of.
	 * @param transferable The amount of transferable power this card has.
	 */
	public GroupCard(String name, int attackPower, int resistance, int income, Arrow[] arrows, ArrayList <String> alignments)
	{
		this(name, attackPower, false, resistance, income, arrows, alignments, false, 0);
	}
	
	public boolean isControlled()
	{
		return controlled;
	}
	public ArrayList <String> getAlignments()
	{
		return alignments;
	}
	public int getNumAlignments()
	{
		return alignments.size();
	}
	
	public int getTransferablePower()
	{
		return super.getTransferablePower();
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
	
	public boolean hasOpenOutArrow()
	{
		return super.hasOpenOutArrow();
	}
	
	public Arrow getOpenOutArrow()
	{
		return super.getOpenOutArrow();
	}
	
	public Arrow getArrow(int location)
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
