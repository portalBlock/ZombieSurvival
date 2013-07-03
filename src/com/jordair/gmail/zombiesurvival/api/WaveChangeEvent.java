package com.jordair.gmail.zombiesurvival.api;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WaveChangeEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private String m;
	private int f, t;

	public WaveChangeEvent(String map, int from) {
		m = map;
		f = from;
		t = f + 1;
	}

	public String getMapName() {
		return m;
	}

	public int getWaveFrom() {
		return f;
	}

	public int getWaveTo() {
		return t;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
