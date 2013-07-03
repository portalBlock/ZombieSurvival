package com.jordair.gmail.zombiesurvival.api;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStartEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private String m;
	private List<Player> p;

	public GameStartEvent(String map, List<Player> players) {
		m = map;
		p = players;
	}

	public String getMapName() {
		return m;
	}

	public List<Player> getPlayers() {
		return p;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
