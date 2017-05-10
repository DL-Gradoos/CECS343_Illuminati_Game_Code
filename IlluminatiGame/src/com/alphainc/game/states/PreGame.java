package com.alphainc.game.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import com.alphainc.game.Main;

public class PreGame extends BasicGameState {
	
	private static int mID;
	/** Current time passed */
	private long timePassed;
	/** Splash Screen Image */
	private Image logo;
	
	public PreGame(int id) {
		mID = id;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		logo = new Image("res/logo/alphainclogo.png").getScaledCopy(1.0f);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.fill(new Rectangle(0, 0, container.getWidth(), container.getHeight()));
		g.drawImage(logo, container.getWidth() / 2.0f - logo.getWidth() / 2.0f,
				container.getHeight() / 2.0f - logo.getHeight() / 2.0f);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		timePassed += delta;
		if(timePassed > 3500)
			game.enterState(Main.MENU, new FadeOutTransition(), new FadeInTransition());
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return mID;
	}
}
