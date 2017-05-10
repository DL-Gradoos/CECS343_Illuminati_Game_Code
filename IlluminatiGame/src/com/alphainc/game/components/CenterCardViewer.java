package com.alphainc.game.components;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;

import com.alphainc.game.crystalcards.StructureCard;
import com.alphainc.game.states.Game;

public class CenterCardViewer extends AbstractComponent {
	
	private List<StructureCard> cards;
	private String name;
	/** Arrows for scrolling the CenterCardViewer left and right */
	private MovableMouseOverArea arrow[];
	/** Images for arrows */
	private Image leftArrow, rightArrow;
	/** Abstract Component Listener */
	private ComponentListener listener;
	private int rowNum = 0;
	private int spacer = 0;
	/** Font */
	private Font segoeUIFont;
	/** Slick drawable font */
	private TrueTypeFont customFont;
	private int xCoords, yCoords;
	private MovableMouseOverArea cardClick[];
	private Image clickableCard;
	private static final int MAX_CARDS = 2;
	
	public CenterCardViewer(String name, int x, int y, GameContainer container, ComponentListener listener) {
		super(container);
		addListener(this.listener = listener);
		cards = new ArrayList<StructureCard>();
		this.name = name;
		xCoords = x;
		yCoords = y;
		initItems();
		initFont();
	}
	
	@Override
	public void render(GUIContext container, Graphics g) throws SlickException {
		spacer = 0;
		if(cards.size() >= 2) {
			for(int ii = rowNum; ii < rowNum + MAX_CARDS; ii++) {
				cards.get(ii).setPosition(-Game.camera.getTopLeftX() + xCoords + spacer,
						-Game.camera.getTopLeftY() + yCoords);
				cards.get(ii).render(container, g);
				spacer += 150;
			}
			cardClick[0].render(container, g);
			cardClick[1].render(container, g);
		} else if(cards.size() == 1) {
			cards.get(0).setPosition(-Game.camera.getTopLeftX() + xCoords,
					-Game.camera.getTopLeftY() + yCoords);
			cards.get(0).render(container, g);
			cardClick[0].render(container, g);
		}
		spacer = 0;
		for(int ii = 0; ii < 2; ii++) {
			//-camera.getTopLeftX() + 80, -camera.getTopLeftY() + space
			arrow[ii].updateLocation(-Game.camera.getTopLeftX() + (xCoords - 120) + spacer,
					-Game.camera.getTopLeftY() + (yCoords - 12));
			arrow[ii].render(container, g);
			spacer += 360;
		}
		customFont.drawString(-Game.camera.getTopLeftX() + 390,
					-Game.camera.getTopLeftY() + 530, name, Color.white);
		/*for(StructureCard sc : cards) {
			sc.render(container, g);
		}*/
	}
	
	public void add(StructureCard c) {
		cards.add(0, c);
	}
	
	public StructureCard remove(int index) {
		return cards.remove(index);
	}
	
	public StructureCard get(int index) {
		return cards.get(index);
	}
	
	public MovableMouseOverArea getLeftArrow() {
		return arrow[0];
	}
	
	public MovableMouseOverArea getRightArrow() {
		return arrow[1];
	}
	
	public MovableMouseOverArea getLeftClickableArea() {
		return cardClick[0];
	}
	
	public MovableMouseOverArea getRightClickableArea() {
		return cardClick[1];
	}
	
	public int getLeftCardIndex() {
		return rowNum;
	}
	
	public int getRightCardIndex() {
		return rowNum + 1;
	}
	
	/**
	 * Scrolls view left
	 */
	public void scrollLeft() {
		if(rowNum - 1 < 0)
			rowNum = 0;
		else
			rowNum--;
		System.out.println(rowNum);
	}
	
	/**
	 * Scrolls view right
	 */
	public void scrollRight() {
		//System.out.println(rowNum + " " + messages.get(rowNum + 4).equals(""));
		//if(!cards.get(rowNum + MAX_CARDS).equals(null))
		//System.out.println("IS ROW NUM + 1 > 4???" + (rowNum + 1 >= cards.size() - 1));
		if(!(rowNum + 1 >= cards.size() - 1))
			rowNum++;
		
	}
	
	public void print() {
		for(StructureCard sc : cards) {
			if(sc == null)
				System.out.println("ENDING");
			else
				System.out.println(sc.getName());
		}
	}
	
	private void initItems() {
		try {
			leftArrow = new Image("res/centerviewer/leftarrow.png").getScaledCopy(0.4f);
			rightArrow = new Image("res/centerviewer/rightarrow.png").getScaledCopy(0.4f);
			clickableCard = new Image("res/centerviewer/cardclick.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		arrow = new MovableMouseOverArea[2];
		arrow[0] = new MovableMouseOverArea(container, leftArrow, (xCoords - 120), (yCoords - 12), listener);
		arrow[0].setNormalColor(new Color(1, 1, 1, 0.5F));
		arrow[0].setMouseOverColor(new Color(1, 1, 1, 1.0F));
		//690 603
		arrow[1] = new MovableMouseOverArea(container, rightArrow, (xCoords + 240), (yCoords - 12), listener);
		arrow[1].setNormalColor(new Color(1, 1, 1, 0.5F));
		arrow[1].setMouseOverColor(new Color(1, 1, 1, 1.0F));
		
		cardClick = new MovableMouseOverArea[2];
		//w: 130, h: 87
		cardClick[0] = new MovableMouseOverArea(container, clickableCard, xCoords - (130 / 2), yCoords - (87 / 2), listener);
		cardClick[0].setNormalColor(new Color(1, 1, 1, 0.5F));
		cardClick[0].setMouseOverColor(new Color(1, 1, 1, 1.0F));
		cardClick[1] = new MovableMouseOverArea(container, clickableCard, xCoords - (130 / 2) + 150, yCoords - (87 / 2), listener);
		cardClick[1].setNormalColor(new Color(1, 1, 1, 0.5F));
		cardClick[1].setMouseOverColor(new Color(1, 1, 1, 1.0F));
	}
	
	/**
	 * Initalizes the font to be used
	 */
	private void initFont() {
		segoeUIFont = new Font("Segoe UI Light", Font.BOLD, 28);
		customFont = new TrueTypeFont(segoeUIFont, true);
	}
	
	/** Unused **/
	@Override
	public void setLocation(int x, int y) {
		xCoords = x;
		yCoords = y;
	}
	@Override
	public int getX() {return 0;}
	@Override
	public int getY() {return 0;}
	@Override
	public int getWidth() {return 0;}
	@Override
	public int getHeight() {return 0;}
}
