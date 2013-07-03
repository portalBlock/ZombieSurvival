package com.jordair.gmail.zombiesurvival;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;

public class Spectate implements Listener {
	private static Map<String, String> spectators = new HashMap<String, String>();

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if ((spectators.containsKey(p.getName())) && (Games.getState((String) spectators.get(p.getName())) > 1))
			e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if ((spectators.containsKey(p.getName())) && (Games.getState((String) spectators.get(p.getName())) > 1))
			e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerClickEntity(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if ((spectators.containsKey(p.getName())) && (Games.getState((String) spectators.get(p.getName())) > 1))
			e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerEggThrow(PlayerEggThrowEvent e) {
		Player p = e.getPlayer();
		if ((spectators.containsKey(p.getName())) && (Games.getState((String) spectators.get(p.getName())) > 1))
			e.setHatching(false);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerPickup(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		if ((spectators.containsKey(p.getName())) && (Games.getState((String) spectators.get(p.getName())) > 1))
			e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerPortal(PlayerPortalEvent e) {
		Player p = e.getPlayer();
		if ((spectators.containsKey(p.getName())) && (Games.getState((String) spectators.get(p.getName())) > 1))
			e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerInventory(InventoryOpenEvent e) {
		HumanEntity he = e.getPlayer();
		Player p = null;
		if ((he instanceof Player)) {
			p = (Player) he;
		}
		if ((spectators.containsKey(p.getName())) && (Games.getState((String) spectators.get(p.getName())) > 1))
			e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerInventory(InventoryClickEvent e) {
		HumanEntity he = e.getWhoClicked();
		Player p = null;
		if ((he instanceof Player)) {
			p = (Player) he;
		}
		if ((spectators.containsKey(p.getName())) && (Games.getState((String) spectators.get(p.getName())) > 1))
			e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerExp(PlayerExpChangeEvent e) {
		Player p = e.getPlayer();
		if ((spectators.containsKey(p.getName())) && (Games.getState((String) spectators.get(p.getName())) > 1))
			e.setAmount(0);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerBreakBlock(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if ((spectators.containsKey(p.getName())) && (Games.getState((String) spectators.get(p.getName())) > 1))
			e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerPlaceBlock(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if ((spectators.containsKey(p.getName())) && (Games.getState((String) spectators.get(p.getName())) > 1))
			e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerAttack(EntityDamageByEntityEvent e) {
		Entity damager = e.getDamager();
		if ((damager instanceof Player)) {
			Player p = (Player) damager;
			if ((spectators.containsKey(p.getName())) && (Games.getState((String) spectators.get(p.getName())) > 1))
				e.setCancelled(true);
		}
	}

	public static Map<String, String> getSpectators() {
		return spectators;
	}

	public static void setSpectators(Map<String, String> spectators) {
		Spectate.spectators = spectators;
	}
}