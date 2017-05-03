package com.alphainc.game;

public class PlayerCountSingleton {
	
	private static PlayerCountSingleton singleton;
	private static int count;
	
	private PlayerCountSingleton() {}
	
	public static PlayerCountSingleton getInstance() {
		if(singleton == null)
			singleton = new PlayerCountSingleton();
		return singleton;
	}
	
	public void setPlayerCount(int count) {
		PlayerCountSingleton.count = count;
	}
	
	public int getPlayerCount() {
		return count;
	}
}
