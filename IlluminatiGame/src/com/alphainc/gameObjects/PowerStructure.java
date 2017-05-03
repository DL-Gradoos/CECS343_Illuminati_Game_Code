package com.alphainc.gameObjects;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
/**
 * Currently debating whether to use this or not. Power structure is just an
 * array that contains all cards a player has.
 * 
 * @author Daniel
 *
 */
public class PowerStructure {
	/** The player that this power structure belongs to */
	private int mPowerStructureID;
	/** An array list of renderable Structure Cards */
	private List<StructureCard> cards;
	
	public PowerStructure(int id) {
		mPowerStructureID = id;
		cards = new ArrayList<StructureCard>();
	}
	
	public void render(GameContainer container, Graphics g) {
		for(StructureCard c : cards) {
			//g.drawImage(c, );
		}
	}
}