package com.alphainc.gameObjects;

import java.util.ArrayList;

/**
 * A group card in the game of Illuminati.
 * @author crystalchun
 *
 */
public class GroupCard extends StructureCard
{
	/**This group card's alignments.*/
	private ArrayList <String> alignments;
	/**Whether or not this group card is controlled.*/
	private boolean controlled;
//Need to do this
	private GroupCard master;
	private IlluminatiCard masterIlluminati;
	
	/**
	 * Constructs a group card
	 * @param name The name of the group.
	 * @param special The special this card has.
	 * @param attackPower The attack power of this group.
	 * @param flipped Whether or not this card is flipped over.
	 * @param resistance The resistance power of this group.
	 * @param income The income this group makes.
	 * @param arrows The arrows this card has.
	 * @param alignments The alignments this card is a part of.
	 * @param controlled Whether or not this group is controlled.
	 * @param transferable The amount of transferable power this card has.
	 */
	public GroupCard(String path, String name, String special, int attackPower, boolean flipped, int resistance, int income, Arrow[] arrows, ArrayList <String> alignments, boolean controlled, int transferable)
	{
		super(path, name, special, attackPower, flipped, resistance, arrows, income, transferable);
		this.alignments = alignments;
		this.controlled = controlled;
	}
	
	/**
	 * Initializes an uncontrolled group card.
	 * @param name The name of the group.
	 * @param special The special this card has.
	 * @param attackPower The attack power of this group.
	 * @param flipped Whether or not this card is flipped over.
	 * @param resistance The resistance power of this group.
	 * @param income The income this group makes.
	 * @param arrows The arrows this card has.
	 * @param alignments The alignments this card is a part of.
	 * @param transferable The amount of transferable power this card has.
	 */
	public GroupCard(String path, String name, String special, int attackPower, boolean flipped, int resistance, int income, Arrow[] arrows, ArrayList <String> alignments, int transferable)
	{
		this(path, name, special, attackPower, flipped, resistance, income, arrows, alignments, false, transferable);
	}
	
	/**
	 * Initializes an uncontrolled, unflipped group card.
	 * @param name The name of the group.
	 * @param special The special this card has.
	 * @param attackPower The attack power of this group.
	 * @param resistance The resistance power of this group.
	 * @param income The income this group makes.
	 * @param arrows The arrows this card has.
	 * @param alignments The alignments this card is a part of.
	 * @param transferable The amount of transferable power this card has.
	 */
	public GroupCard(String path, String name, String special, int attackPower, int resistance, int income, Arrow[] arrows, ArrayList <String> alignments, int transferable)
	{
		this(path, name, special, attackPower, false, resistance, income, arrows, alignments, false, transferable);
	}
	
	/**
	 * Initializes an uncontrolled, unflipped group card with no transferable power.
	 * @param name The name of the group.
	 * @param special The special this card has.
	 * @param attackPower The attack power of this group.
	 * @param resistance The resistance power of this group.
	 * @param income The income this group makes.
	 * @param arrows The arrows this card has.
	 * @param alignments The alignments this card is a part of.
	 * @param transferable The amount of transferable power this card has.
	 */
	public GroupCard(String path, String name, String special, int attackPower, int resistance, int income, Arrow[] arrows, ArrayList <String> alignments)
	{
		this(path, name, special, attackPower, false, resistance, income, arrows, alignments, false, 0);
	}
	
	public ArrayList <String> getAlignments()
	{
		return alignments;
	}
	public int getNumAlignments()
	{
		return alignments.size();
	}

	public boolean isControlled()
	{
		return controlled;
	}
	
}