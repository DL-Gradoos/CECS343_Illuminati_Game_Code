package com.alphainc.game.components;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;

import com.alphainc.game.crystalcards.StructureCard;
import com.alphainc.game.states.Game;

public class CenterCardViewer extends AbstractComponent {
	
	private List<StructureCard> cards;
	/** Arrows for scrolling the CenterCardViewer left and right */
	private MovableMouseOverArea arrow[];
	/** Images for arrows */
	private Image leftArrow, rightArrow;
	/** Abstract Component Listener */
	private ComponentListener listener;
	private int rowNum = 0;
	private int spacer = 0;
	private static final int MAX_CARDS = 4;
	
	public CenterCardViewer(GameContainer container, ComponentListener listener) {
		super(container);
		addListener(this.listener = listener);
		cards = new ArrayList<StructureCard>();
		initItems();
	}
	
	@Override
	public void render(GUIContext container, Graphics g) throws SlickException {
		spacer = 0;
		for(int ii = 0; ii < MAX_CARDS; ii++) {
			cards.get(ii).setPosition(-Game.camera.getTopLeftX() + 450 + spacer,
					-Game.camera.getTopLeftY() + 615);
			cards.get(ii).render(container, g);
			spacer += 150;
		}
		spacer = 0;
		for(int ii = 0; ii < 2; ii++) {
			//-camera.getTopLeftX() + 80, -camera.getTopLeftY() + space
			arrow[ii].updateLocation(-Game.camera.getTopLeftX() + 330 + spacer,
					-Game.camera.getTopLeftY() + 603);
			arrow[ii].render(container, g);
			spacer += 670;
		}
		/*for(StructureCard sc : cards) {
			sc.render(container, g);
		}*/
	}
	
	public void add(StructureCard c) {
		cards.add(c);
	}
	
	public StructureCard remove(int index) {
		return cards.remove(index);
	}
	
	public MovableMouseOverArea getLeftArrow() {
		return arrow[0];
	}
	
	public MovableMouseOverArea getRightArrow() {
		return arrow[1];
	}
	
	private void initItems() {
		try {
			leftArrow = new Image("res/centerviewer/leftarrow.png").getScaledCopy(0.4f);
			rightArrow = new Image("res/centerviewer/rightarrow.png").getScaledCopy(0.4f);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		arrow = new MovableMouseOverArea[2];
		arrow[0] = new MovableMouseOverArea(container, leftArrow, 330, 603, listener);
		arrow[0].setNormalColor(new Color(1, 1, 1, 0.5F));
		arrow[0].setMouseOverColor(new Color(1, 1, 1, 1.0F));
		arrow[1] = new MovableMouseOverArea(container, rightArrow, 1000, 603, listener);
		arrow[1].setNormalColor(new Color(1, 1, 1, 0.5F));
		arrow[1].setMouseOverColor(new Color(1, 1, 1, 1.0F));
	}
	
	
	
	/** Unused **/
	@Override
	public void setLocation(int x, int y) {}
	@Override
	public int getX() {return 0;}
	@Override
	public int getY() {return 0;}
	@Override
	public int getWidth() {return 0;}
	@Override
	public int getHeight() {return 0;}
}
