package com.jordair.gmail.zombiesurvival.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerUsePerkEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private String m;
	private Player p;
	private PerkType t;

	public PlayerUsePerkEvent(String map, Player player, PerkType perk) {
		m = map;
		p = player;
		t = perk;
	}

	public String getMapName() {
		return m;
	}

	public Player getPlayer() {
		return p;
	}
	
	public PerkType getPerkType() {
		return t;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
