package com.jordair.gmail.zombiesurvival.api;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.jordair.gmail.zombiesurvival.Door;

public class DoorOpenEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private Door d;

	public DoorOpenEvent(Door door) {
		d = door;
	}

	public Door getDoorOpened() {
		return d;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
