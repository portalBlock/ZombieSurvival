package com.jordair.gmail.zombiesurvival.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerRevivedEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private String m;
	private Player p, w;

	public PlayerRevivedEvent(String map, Player revived, Player byWho) {
		m = map;
		p = revived;
		w = byWho;
	}

	public String getMapName() {
		return m;
	}

	public Player getWhoWasRevived() {
		return p;
	}

	public Player getReviver() {
		return w;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
