package com.jordair.gmail.zombiesurvival.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerRemoveEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private String m;
	private Player p;
	private RemoveReason r;

	public PlayerRemoveEvent(String map, Player removed, RemoveReason reason) {
		m = map;
		p = removed;
		r = reason;
	}

	public String getMapName() {
		return m;
	}

	public Player getWhoRemoved() {
		return p;
	}
	
	public RemoveReason getRemoveReason() {
		return r;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
