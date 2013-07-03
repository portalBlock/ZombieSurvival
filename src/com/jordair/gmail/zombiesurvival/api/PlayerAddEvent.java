package com.jordair.gmail.zombiesurvival.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerAddEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private String m;
	private Player p;

	public PlayerAddEvent(String map, Player added) {
		m = map;
		p = added;
	}

	public String getMapName() {
		return m;
	}

	public Player getWhoAdded() {
		return p;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
