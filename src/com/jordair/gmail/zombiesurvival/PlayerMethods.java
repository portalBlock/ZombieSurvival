package com.jordair.gmail.zombiesurvival;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerMethods {
	public static String playerGame(Player p) {
		if (p == null) { return null; }
		if (Games.PlayerExists(p.getName())) {
			String map = Games.getPlayerMap(p.getName());
			return map;
		}
		return null;
	}

	public static boolean inGame(Player p) {
		if (p == null) { return false; }
		if (Games.PlayerExists(p.getName())) { return true; }
		return false;
	}

	public static List<String> playersInMap(String map) {
		if (map == null) { return null; }
		List<String> mapplayers = new ArrayList<String>();
		for (String p : Games.playermapKeySet()) {
			if (Games.getPlayerMap(p).equalsIgnoreCase(map)) {
				mapplayers.add(p);
			}
		}
		return mapplayers;
	}

	public static int playersOnline(String map) {
		int i = 0;
		for (String mp : playersInMap(map)) {
			Player p = Bukkit.getPlayer(mp);
			if ((p != null) && (!ZombieSurvivalPlugin.getDead().containsKey(mp))) {
				i++;
			}
		}
		return i;
	}

	@SuppressWarnings("unused")
	public static int numberInMap(String map) {
		int i = 0;
		for (String mp : playersInMap(map)) {
			i++;
		}
		return i;
	}
}