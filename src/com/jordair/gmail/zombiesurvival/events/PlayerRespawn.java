package com.jordair.gmail.zombiesurvival.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.jordair.gmail.zombiesurvival.Games;
import com.jordair.gmail.zombiesurvival.PlayerMethods;
import com.jordair.gmail.zombiesurvival.Spectate;
import com.jordair.gmail.zombiesurvival.Utilities;
import com.jordair.gmail.zombiesurvival.ZombieSurvivalPlugin;

public class PlayerRespawn implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		final Player player = event.getPlayer();
		final String name = player.getName();
		Bukkit.getScheduler().scheduleSyncDelayedTask(ZombieSurvivalPlugin.getInstance(), new Runnable() {
			public void run() {
				if (PlayerMethods.inGame(player)) {
					String map = PlayerMethods.playerGame(player);
					ZombieSurvivalPlugin.getInstance().GamesOver(map, false);
					if ((Games.getState(map) > 1) && (ZombieSurvivalPlugin.getDead().containsKey(name)))
						if ((ZombieSurvivalPlugin.getInstance().getSpawn().spectate.get(map) != null)
								&& (ZombieSurvivalPlugin.getInstance().isSpectateallow())) {
							player.teleport((Location) ZombieSurvivalPlugin.getInstance().getSpawn().spectate.get(map));
							player.setAllowFlight(true);
							player.setFlying(true);
							player.setGameMode(GameMode.CREATIVE);
							Spectate.getSpectators().put(name, map);
							Utilities.hidePlayer(player);
							player.getInventory().clear();
							player.getInventory().setArmorContents(null);
						} else if ((ZombieSurvivalPlugin.getInstance().getDeathPoints().get(map) != null)
								&& (!ZombieSurvivalPlugin.getInstance().isSpectateallow())) {
							player.teleport((Location) ZombieSurvivalPlugin.getInstance().getDeathPoints().get(map));
						} else {
							player.teleport(player.getWorld().getSpawnLocation());
							player.sendMessage("No waiting lobby defined and spectating is disabled.");
						}
				}
			}
		});
	}
}
