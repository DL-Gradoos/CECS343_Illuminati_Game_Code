package com.alphainc.game.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends BasicGameState {
	
	private static int mID;
	private Image card, card2;
	
	public Game(int id) {
		mID = id;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		card = new Image("res/cards/thenetwork.png");
		card2 = new Image("res/cards/thenetwork.png");
		card2.rotate(90);
		System.out.println("CARD 1: " + card.getWidth() + " " + card.getHeight());
		System.out.println("CARD 2: " + card2.getWidth() + " " + card2.getHeight());
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		card.drawCentered(100, 100);
		card2.drawCentered(100, 100);
		//g.drawImage(card, 100, 100);
		//g.draw(card2, 100, 100);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		
	}

	@Override
	public int getID() {
		
		return mID;
	}
}