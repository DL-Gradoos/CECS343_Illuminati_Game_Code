package com.alphainc.game.gameObjects;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Deck {
	
	private List<StructureCard> illumCard;
	private List<StructureCard> groupCard;
	
	public Deck() {
		illumCard = new ArrayList<StructureCard>();
		groupCard = new ArrayList<StructureCard>();
		read();
	}
	
	private void read() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("res/cards.txt"));
			for(int ii = 0; ii < 8; ii++) {
				String x = br.readLine();
				String[] edit = x.split(",");
				illumCard.add(new IlluminatiCard("res/cards/illum/" + edit[0], edit[1], edit[2],
						Integer.parseInt(edit[3]), Integer.parseInt(edit[4]),
						new Arrow[] {
							new Arrow(false, null, true),
							new Arrow(false, null, true),
							new Arrow(false, null, true),
							new Arrow(false, null, true)
				}));
			}
			/*for(int ii = 0; ii < 80; ii++) {
				String x = br.readLine();
				String[] edit = x.split(",");
				groupCard.add(new GroupCard(edit[0], edit[1], edit[2],
						Integer.parseInt(edit[3]), Integer.parseInt(edit[4]),
						new Arrow[] {
							new Arrow(false, null, true),
							new Arrow(false, null, true),
							new Arrow(false, null, true),
							new Arrow(false, null, true)
				}));
			}*/
			br.close();
		} catch (FileNotFoundException e) {
			System.err.println("UNABLE TO GET CARD INPUT FILE");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("COULD NOT READ LINE IN CARDS.TXT || COULD NOT CLOSE BUFFERED READER");
			e.printStackTrace();
		}
	}
	/**
	 * Gets a new copy of the illuminati cards
	 * @return List of Illuminati cards
	 */
	public List<StructureCard> getIlluminatiDeck() {
		return new ArrayList<StructureCard>(illumCard);
	}

}