package com.alphainc.game;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The game screen for the Illuminati game.
 * @author crystalchun
 *
 */
public class Game extends BasicGameState
{
	private Image background;
	private static int mID;
	public Game(int id)
	{
		mID = id;
	}
	/**
	 * Initializes all resources
	 */
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		// TODO Auto-generated method stub
		background = new Image("res/background.png");
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.drawImage(background, 0, 0);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() 
	{
		// TODO Auto-generated method stub
		return mID;
	}

}
