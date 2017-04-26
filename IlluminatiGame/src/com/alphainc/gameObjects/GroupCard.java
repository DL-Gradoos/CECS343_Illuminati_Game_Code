package com.alphainc.gameObjects;

public class GroupCard 
{
	private Arrow[] arrows;
	public Arrow getArrow(String location)
	{
		for(Arrow a: arrows)
		{
			if(a.getLocation().equalsIgnoreCase(location))
			{
				return a;
			}
		}
		return null;
	}
}
