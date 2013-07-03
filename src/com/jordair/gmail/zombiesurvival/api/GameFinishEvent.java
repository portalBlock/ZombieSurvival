package com.jordair.gmail.zombiesurvival.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameFinishEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private String m;
	private GameFinishReason r;
	private Player w;

	public GameFinishEvent(String map, GameFinishReason reason) {
		this(map, reason, null);
	}

	public GameFinishEvent(String map, GameFinishReason reason, Player winner) {
		m = map;
		r = reason;
		w = winner;
	}

	public GameFinishReason getReason() {
		return r;
	}

	public String getMapName() {
		return m;
	}

	public Player getWinner() {
		return w;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
