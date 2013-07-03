package com.jordair.gmail.zombiesurvival;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class Donator implements Listener {
	public Map<String, ItemStack[]> dcontents = new HashMap<String, ItemStack[]>();
	public Map<String, ItemStack[]> darmor = new HashMap<String, ItemStack[]>();

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if ((p.hasPermission("zs.donator")) && (PlayerMethods.inGame(p))) {
			this.dcontents.put(p.getName(), p.getInventory().getContents());
			this.darmor.put(p.getName(), p.getInventory().getArmorContents());
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		if (this.dcontents.containsKey(p.getName())) {
			p.getInventory().setContents((ItemStack[]) this.dcontents.get(p.getName()));
			this.dcontents.remove(p.getName());
		}
		if (this.darmor.containsKey(p.getName())) {
			p.getInventory().setArmorContents((ItemStack[]) this.darmor.get(p.getName()));
			this.darmor.remove(p.getName());
		}
	}
}