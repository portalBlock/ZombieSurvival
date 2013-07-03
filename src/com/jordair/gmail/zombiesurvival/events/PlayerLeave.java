package com.jordair.gmail.zombiesurvival.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.jordair.gmail.zombiesurvival.Games;
import com.jordair.gmail.zombiesurvival.PlayerMethods;
import com.jordair.gmail.zombiesurvival.ZombieSurvivalPlugin;
import com.jordair.gmail.zombiesurvival.api.PlayerRemoveEvent;
import com.jordair.gmail.zombiesurvival.api.RemoveReason;

public class PlayerLeave implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		ZombieSurvivalPlugin.getInstance().setOnlinep(ZombieSurvivalPlugin.getInstance().getOnlinep() - 1);
		final Player player = event.getPlayer();
		final String name = player.getName();
		ZombieSurvivalPlugin.getInstance().getRevive().removeSign(name);
		if (PlayerMethods.inGame(player)) {
			ZombieSurvivalPlugin.getInstance().getTwomintimer().add(name);
			final String map = PlayerMethods.playerGame(player);
			if (PlayerMethods.playersOnline(map) > 1) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(ZombieSurvivalPlugin.getInstance(), new Runnable() {
					public void run() {
						if (!player.isOnline() && Games.getState(map) <= 1) {
							if ((!ZombieSurvivalPlugin.getInstance().isPoints()) && (ZombieSurvivalPlugin.getInstance().isEmptyaccount())) {
								double original = ZombieSurvivalPlugin.getEcon().getBalance(name);
								ZombieSurvivalPlugin.getEcon().withdrawPlayer(name, original);
							} else if ((!ZombieSurvivalPlugin.getInstance().isPoints())
									&& (ZombieSurvivalPlugin.getInstance().getDeathloss() > 0.0D)) {
								double original = ZombieSurvivalPlugin.getEcon().getBalance(name);
								double withdraw = original * ZombieSurvivalPlugin.getInstance().getDeathloss();
								ZombieSurvivalPlugin.getEcon().withdrawPlayer(name, withdraw);
							}
							ZombieSurvivalPlugin.getInstance();
							if (!ZombieSurvivalPlugin.getDead().containsKey(name)) {
								int tempcount = Games.getPcount(map);
								Games.setPcount(map, tempcount - 1);
							}
							Games.removePlayerMap(name);
							com.jordair.gmail.zombiesurvival.Stats.removeSplayerPoints(name);
							ZombieSurvivalPlugin.getInstance().getTwomintimer().remove(name);
							ZombieSurvivalPlugin.getInstance();
							ZombieSurvivalPlugin.getDead().remove(name);
							PlayerRemoveEvent pre = new PlayerRemoveEvent(map, player, RemoveReason.DISCONNECT);
							Bukkit.getServer().getPluginManager().callEvent(pre);
							ZombieSurvivalPlugin.getInstance().GamesOver(map, false, "quit");
						}
					}
				}, 1200L);
			} else {
				if ((!ZombieSurvivalPlugin.getInstance().isPoints()) && (ZombieSurvivalPlugin.getInstance().isEmptyaccount())) {
					double original = ZombieSurvivalPlugin.getEcon().getBalance(name);
					ZombieSurvivalPlugin.getEcon().withdrawPlayer(name, original);
				} else if ((!ZombieSurvivalPlugin.getInstance().isPoints()) && (ZombieSurvivalPlugin.getInstance().getDeathloss() > 0.0D)) {
					double original = ZombieSurvivalPlugin.getEcon().getBalance(name);
					double withdraw = original * ZombieSurvivalPlugin.getInstance().getDeathloss();
					ZombieSurvivalPlugin.getEcon().withdrawPlayer(name, withdraw);
				}
				ZombieSurvivalPlugin.getInstance();
				if (!ZombieSurvivalPlugin.getDead().containsKey(name)) {
					int tempcount = Games.getPcount(map);
					Games.setPcount(map, tempcount - 1);
				}
				Games.removePlayerMap(name);
				com.jordair.gmail.zombiesurvival.Stats.removeSplayerPoints(name);
				ZombieSurvivalPlugin.getInstance().getTwomintimer().remove(name);
				ZombieSurvivalPlugin.getInstance();
				ZombieSurvivalPlugin.getDead().remove(player.getName());
				PlayerRemoveEvent pre = new PlayerRemoveEvent(map, player, RemoveReason.DISCONNECT);
				Bukkit.getServer().getPluginManager().callEvent(pre);
				ZombieSurvivalPlugin.getInstance().GamesOver(map, false, "quit");
			}
		}
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		ZombieSurvivalPlugin.getInstance().setOnlinep(ZombieSurvivalPlugin.getInstance().getOnlinep() - 1);
		final Player player = event.getPlayer();
		final String name = player.getName();
		ZombieSurvivalPlugin.getInstance().getRevive().removeSign(name);
		if (PlayerMethods.inGame(player)) {
			ZombieSurvivalPlugin.getInstance().getTwomintimer().add(name);
			final String map = PlayerMethods.playerGame(player);
			if (PlayerMethods.playersOnline(map) > 1) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(ZombieSurvivalPlugin.getInstance(), new Runnable() {
					public void run() {
						if (!player.isOnline()) {
							if ((!ZombieSurvivalPlugin.getInstance().isPoints()) && (ZombieSurvivalPlugin.getInstance().isEmptyaccount())) {
								double original = ZombieSurvivalPlugin.getEcon().getBalance(name);
								ZombieSurvivalPlugin.getEcon().withdrawPlayer(name, original);
							} else if ((!ZombieSurvivalPlugin.getInstance().isPoints())
									&& (ZombieSurvivalPlugin.getInstance().getDeathloss() > 0.0D)) {
								double original = ZombieSurvivalPlugin.getEcon().getBalance(name);
								double withdraw = original * ZombieSurvivalPlugin.getInstance().getDeathloss();
								ZombieSurvivalPlugin.getEcon().withdrawPlayer(name, withdraw);
							}
							if (!ZombieSurvivalPlugin.getDead().containsKey(name)) {
								int tempcount = Games.getPcount(map);
								Games.setPcount(map, tempcount - 1);
							}
							Games.removePlayerMap(name);
							com.jordair.gmail.zombiesurvival.Stats.removeSplayerPoints(name);
							ZombieSurvivalPlugin.getInstance().getTwomintimer().remove(name);
							ZombieSurvivalPlugin.getInstance();
							ZombieSurvivalPlugin.getDead().remove(name);
							PlayerRemoveEvent pre = new PlayerRemoveEvent(map, player, RemoveReason.DISCONNECT);
							Bukkit.getServer().getPluginManager().callEvent(pre);
							ZombieSurvivalPlugin.getInstance().GamesOver(map, false, "kicked");
						}
					}
				}, 200L);
			} else {
				if ((!ZombieSurvivalPlugin.getInstance().isPoints()) && (ZombieSurvivalPlugin.getInstance().isEmptyaccount())) {
					double original = ZombieSurvivalPlugin.getEcon().getBalance(name);
					ZombieSurvivalPlugin.getEcon().withdrawPlayer(name, original);
				} else if ((!ZombieSurvivalPlugin.getInstance().isPoints()) && (ZombieSurvivalPlugin.getInstance().getDeathloss() > 0.0D)) {
					double original = ZombieSurvivalPlugin.getEcon().getBalance(name);
					double withdraw = original * ZombieSurvivalPlugin.getInstance().getDeathloss();
					ZombieSurvivalPlugin.getEcon().withdrawPlayer(name, withdraw);
				}
				ZombieSurvivalPlugin.getInstance();
				if (!ZombieSurvivalPlugin.getDead().containsKey(name)) {
					int tempcount = Games.getPcount(map);
					Games.setPcount(map, tempcount - 1);
				}
				Games.removePlayerMap(name);
				com.jordair.gmail.zombiesurvival.Stats.removeSplayerPoints(name);
				ZombieSurvivalPlugin.getInstance().getTwomintimer().remove(name);
				ZombieSurvivalPlugin.getInstance();
				ZombieSurvivalPlugin.getDead().remove(player.getName());
				PlayerRemoveEvent pre = new PlayerRemoveEvent(map, player, RemoveReason.DISCONNECT);
				Bukkit.getServer().getPluginManager().callEvent(pre);
				ZombieSurvivalPlugin.getInstance().GamesOver(map, false, "kicked");
			}
		}
	}
}
