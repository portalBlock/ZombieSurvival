package com.jordair.gmail.zombiesurvival.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.jordair.gmail.zombiesurvival.ZombieSurvivalPlugin;

public class PlayerPickup implements Listener {

	@EventHandler
	public void onPlayerPickup(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		ZombieSurvivalPlugin.getInstance();
		if (ZombieSurvivalPlugin.getDead().containsKey(player.getName()))
			event.setCancelled(true);
	}
}
