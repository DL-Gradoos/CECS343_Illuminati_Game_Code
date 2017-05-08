package com.alphainc.game.gameObjects;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Deck 
{
	
	private List<StructureCard> illumCard;
	private List<StructureCard> groupCard;
	private List<StructureCard> specialCard;
	
	public Deck() {
		illumCard = new ArrayList<StructureCard>();
		groupCard = new ArrayList<StructureCard>();
		specialCard = new ArrayList <StructureCard> ();
		read();
	}
	
	private void read() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("res/cards.txt"));
			int temp = Integer.parseInt(br.readLine());
			System.out.println(temp);
			//Reads in illuminati cards
			for(int ii = 0; ii < temp; ii++) 
			{
				String x = br.readLine();
				String[] edit = x.split(",");
				illumCard.add(new IlluminatiCard("res/cards/illum/" + edit[0], edit[1], edit[2],
						Integer.parseInt(edit[3]), Integer.parseInt(edit[4]),
						new Arrow[] 
						{
							new Arrow(true, false, null, true),
							new Arrow(true, false, null, true),
							new Arrow(true, false, null, true),
							new Arrow(true, false, null, true)
						}
				));
			}
			temp = Integer.parseInt(br.readLine());
			//Reads in group card
			for(int ii = 0; ii < temp; ii++)
			{
				String x = br.readLine();
				String[] edit = x.split(",");
				String [] titles = new String[edit.length - 13];
				for(int jj = 13; jj < edit.length; jj++)
				{
					titles[jj - 13] = edit[jj];
				}
				/* Assigns arrows, exists, connected, connectedTo, in(false) out(true)*/
				//6: top, 7: right, 8: bot, 9: left
				Arrow storage[] = new Arrow[4];
				for(int jj = 6; jj < 10; jj++) 
				{
					if(edit[jj].equals("null"))
					{
						storage[jj - 6] = new Arrow(false, false, null, false);
					}
					else if(edit[jj].equals("in"))
					{
						storage[jj - 6] = new Arrow(true, false, null, false);
					}
					else if(edit[jj].equals("out"))
					{
						storage[jj - 6] = new Arrow(true, false, null, true);
					}
					else 
					{
						System.out.println("READ IN FOR ARROWS INCORRECT"); 
						System.exit(1);
					}
				}
				groupCard.add(new GroupCard("res/cards/group/" + edit[0], edit[1], edit[2], Integer.parseInt(edit[3]),
											Integer.parseInt(edit[4]), Integer.parseInt(edit[5]),
											storage, Boolean.parseBoolean(edit[10]),
											Boolean.parseBoolean(edit[11]), Integer.parseInt(edit[12]),
											titles));
			}
			
			temp = Integer.parseInt(br.readLine());
			//Reads in special cards
			for(int i = 0; i < temp; i++)
			{
				String line = br.readLine();
				String [] info = line.split(",");
				SpecialCard special = new SpecialCard("res/cards/special/" + info[0], info[1], info[2], Boolean.getBoolean(info[3]));
				specialCard.add(special);
			}

			br.close();
		} 
		catch (FileNotFoundException e) 
		{
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
	/**
	 * Gets a new copy of the Group cards
	 * @return List of Group cards
	 */
	public List<StructureCard> getGroupDeck() {
		return new ArrayList<StructureCard>(groupCard);
	}

}