package com.jordair.gmail.zombiesurvival;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Games {
	private static Map<String, Integer> pcount = new HashMap<String, Integer>();
	private static Map<String, Integer> state = new HashMap<String, Integer>();
	private static Map<String, Integer> zcount = new HashMap<String, Integer>();
	private static Map<String, Integer> wave = new HashMap<String, Integer>();
	private static Map<String, Integer> zslayed = new HashMap<String, Integer>();
	private static Map<String, Integer> maxzombies = new HashMap<String, Integer>();
	private static Map<String, Integer> maxwaves = new HashMap<String, Integer>();
	private static Map<String, Integer> maxplayers = new HashMap<String, Integer>();
	private static Map<String, String> playermap = new HashMap<String, String>();

	public static void setPcount(String map, int num) {
		pcount.put(map, num);
	}

	public static void setState(String map, int num) {
		state.put(map, num);
	}

	public static void setZcount(String map, int num) {
		zcount.put(map, num);
	}

	public static void setWave(String map, int num) {
		wave.put(map, num);
	}

	public static void setZslayed(String map, int num) {
		zslayed.put(map, num);
	}

	public static void setMaxZombies(String map, int num) {
		maxzombies.put(map, num);
	}

	public static void setMaxPlayers(String map, int num) {
		maxplayers.put(map, num);
	}

	public static void setMaxWaves(String map, int num) {
		maxwaves.put(map, num);
	}

	public static void setPlayerMap(String name, String map) {
		playermap.put(name, map);
	}

	public static int getPcount(String map) {
		if (pcount.get(map) != null) { return pcount.get(map); }
		return 0;
	}

	public static int getState(String map) {
		if (state.get(map) != null) { return state.get(map); }
		return 0;
	}

	public static int getZcount(String map) {
		if (zcount.get(map) != null) { return zcount.get(map); }
		return 0;
	}

	public static int getWave(String map) {
		if (wave.get(map) != null) { return wave.get(map); }
		return 0;
	}

	public static int getZslayed(String map) {
		if (zslayed.get(map) != null) { return zslayed.get(map); }
		return 0;
	}

	public static int getMaxZombies(String map) {
		if (maxzombies.get(map) != null) { return maxzombies.get(map); }
		return 0;
	}

	public static int getMaxWaves(String map) {
		if (maxwaves.get(map) != null) { return maxwaves.get(map); }
		return 0;
	}

	public static int getMaxPlayers(String map) {
		if (maxplayers.get(map) != null) { return maxplayers.get(map); }
		return 0;
	}

	public static String getPlayerMap(String name) {
		return (String) playermap.get(name);
	}

	public static void removePlayerMap(String name) {
		playermap.remove(name);
	}

	public static boolean PlayerExists(String name) {
		if (playermap.containsKey(name)) { return true; }
		return false;
	}

	public static Set<String> playermapKeySet() {
		return playermap.keySet();
	}

	public static boolean exists(String map) {
		if (state.get(map) != null) { return true; }
		return false;
	}
}