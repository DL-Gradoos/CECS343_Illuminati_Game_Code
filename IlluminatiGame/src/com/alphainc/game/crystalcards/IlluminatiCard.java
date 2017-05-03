package com.alphainc.game.crystalcards;

/**
 * An Illuminati card in the game of Illuminati.
 * @author crystalchun
 *
 */
public class IlluminatiCard extends StructureCard
{
	
	/**
	 * Main constructor of the Illuminati card.
	 * @param name The name of this Illuminati.
	 * @param special The special power this Illuminati card has.
	 * @param attackPower The power of this card.
	 * @param income The income of this card.
	 * @param arrows The arrows on this card.
	 */
	public IlluminatiCard(String path, String name, String special, int attackPower, int income, Arrow[] arrows)
	{
		//Calls StructureCard constructor
		super(path, attackPower, name, special, attackPower, income, arrows, false);
	}
	
}
