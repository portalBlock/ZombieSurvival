package com.jordair.gmail.zombiesurvival.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.jordair.gmail.zombiesurvival.Games;
import com.jordair.gmail.zombiesurvival.PlayerMethods;
import com.jordair.gmail.zombiesurvival.Utilities;
import com.jordair.gmail.zombiesurvival.ZombieSurvivalPlugin;

public class PlayerJoin implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		ZombieSurvivalPlugin.getInstance().setOnlinep(ZombieSurvivalPlugin.getInstance().getOnlinep() + 1);
		Player player = event.getPlayer();
		String name = player.getName();
		if (!PlayerMethods.inGame(player)) {
			if ((ZombieSurvivalPlugin.getInstance().isInvsave()) && (ZombieSurvivalPlugin.getInstance().getInv().containsKey(name))) {
				player.getInventory().setContents((ItemStack[]) ZombieSurvivalPlugin.getInstance().getInv().get(name));
				ZombieSurvivalPlugin.getInstance().getInv().remove(name);
			}
			if ((ZombieSurvivalPlugin.getInstance().isInvsave()) && (ZombieSurvivalPlugin.getInstance().getInvarmor().containsKey(name))) {
				player.getInventory().setArmorContents((ItemStack[]) ZombieSurvivalPlugin.getInstance().getInvarmor().get(name));
				ZombieSurvivalPlugin.getInstance().getInvarmor().remove(name);
			}
			com.jordair.gmail.zombiesurvival.Stats.setPoints(name, 0.0D);
			player.setHealth(20);
			player.setFoodLevel(20);
			Utilities.unhidePlayer(player);
			player.setDisplayName(name);
			if (ZombieSurvivalPlugin.getInstance().isForcespawn()) {
				player.teleport(player.getWorld().getSpawnLocation());
			} else if (ZombieSurvivalPlugin.getInstance().isForcewarp()
					&& ZombieSurvivalPlugin.getInstance().getForcewarplocation() != null) {
				player.teleport(ZombieSurvivalPlugin.getInstance().getForcewarplocation());
			}
		}
		if ((ZombieSurvivalPlugin.getInstance().isForceclear()) && (!ZombieSurvivalPlugin.getInstance().getTwomintimer().contains(name))) {
			player.getInventory().clear();
			player.getInventory().setArmorContents(null);
		}
		if (PlayerMethods.inGame(player)) {
			String map = PlayerMethods.playerGame(player);
			if (Games.getState(map) < 2) {
				player.getInventory().clear();
				player.getInventory().setArmorContents(null);
				player.teleport(player.getWorld().getSpawnLocation());
				Games.removePlayerMap(name);
				com.jordair.gmail.zombiesurvival.Stats.setPoints(name, 0.0D);
				com.jordair.gmail.zombiesurvival.Stats.removeSplayerKills(name);
				ZombieSurvivalPlugin.getInstance();
				ZombieSurvivalPlugin.getDead().remove(name);
				if ((ZombieSurvivalPlugin.getInstance().isEmptyaccount()) && (!ZombieSurvivalPlugin.getInstance().isPoints())) {
					ZombieSurvivalPlugin.getInstance();
					double removem = ZombieSurvivalPlugin.getEcon().getBalance(name);
					ZombieSurvivalPlugin.getInstance();
					ZombieSurvivalPlugin.getEcon().withdrawPlayer(name, removem);
				}
				player.setDisplayName(name);
			}
		}
		if (ZombieSurvivalPlugin.getInstance().isAntigrief())
			player.sendMessage(ChatColor.GREEN + "Right click a sign or type /join to join a ZombieSurvival game!");
	}
}
