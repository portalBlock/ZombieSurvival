package com.jordair.gmail.zombiesurvival.api;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.jordair.gmail.zombiesurvival.Barricade;

public class BarricadeBreakEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private Barricade b;

	public BarricadeBreakEvent(Barricade barricade) {
		b = barricade;
	}

	public Barricade getBarricade() {
		return b;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
