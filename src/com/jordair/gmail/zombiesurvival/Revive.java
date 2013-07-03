package com.jordair.gmail.zombiesurvival;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

import com.jordair.gmail.zombiesurvival.api.PlayerRevivedEvent;

public class Revive implements Listener {
	Plugin plugin;
	public Map<Sign, String> timedSigns = new ConcurrentHashMap<Sign, String>();

	public void counterTask() {
		this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
			public void run() {
				for (Iterator<Sign> it = Revive.this.timedSigns.keySet().iterator(); it.hasNext();) {
					Sign s = (Sign) it.next();
					String line4 = s.getLine(3);
					String chars = line4.substring(2);
					int time = Integer.parseInt(chars);
					time--;
					if (time > 0) {
						if (time > 15) {
							s.setLine(3, "§a" + Integer.toString(time));
							s.update();
						} else if (time > 5) {
							s.setLine(3, "§e" + Integer.toString(time));
							s.update();
						} else {
							s.setLine(3, "§c" + Integer.toString(time));
							s.update();
						}
					} else {
						Block b = s.getBlock();
						b.setTypeId(0);
						it.remove();
					}
				}
			}
		}, 20L, 20L);
	}

	public void removeSign(String name) {
		for (Iterator<Sign> it = this.timedSigns.keySet().iterator(); it.hasNext();) {
			Sign s = (Sign) it.next();
			if (((String) this.timedSigns.get(s)).matches(name)) {
				Block b = s.getBlock();
				b.setTypeId(0);
				it.remove();
			}
		}
	}

	public void createRevive(Player p) {
		Block b = p.getLocation().getBlock();
		Location middle = p.getLocation();
		if (b.getTypeId() != 0) {
			for (int y = middle.getBlockY() - 2; y <= middle.getBlockY() + 2; y++) {
				for (int x = middle.getBlockX() - 6; x <= middle.getBlockX() + 6; x++) {
					for (int z = middle.getBlockZ() - 6; z <= middle.getBlockZ() + 6; z++) {
						Location temp = new Location(middle.getWorld(), x, y, z);
						if ((temp.getBlock().getTypeId() == 0) && (temp.getBlock().getRelative(BlockFace.DOWN).getTypeId() != 0)) {
							b = temp.getBlock();
							break;
						}
					}
				}
			}
		}
		if (b.getTypeId() == 0) {
			b.getWorld().strikeLightningEffect(b.getLocation());
			b.setType(Material.SIGN_POST);
			Sign s = (Sign) b.getState();
			s.setLine(0, "BREAK TO REVIVE");
			s.setLine(1, "§1" + p.getName());
			s.setLine(2, "§4TIME LEFT");
			s.setLine(3, "§a30");
			s.update();
			this.timedSigns.put(s, p.getName());
		} else {
			this.plugin.getLogger().warning("Could not create revive sign for: " + p.getName());
		}
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onSignBreak(BlockBreakEvent e) {
		Block b = e.getBlock();
		Player player = e.getPlayer();
		if ((b.getState() instanceof Sign)) {
			Sign s = (Sign) b.getState();
			if (this.timedSigns.containsKey(s)) {
				Player p = Bukkit.getPlayer((String) this.timedSigns.get(s));
				if ((p != null) && (PlayerMethods.inGame(p)) && (!p.isDead())) {
					b.setTypeId(0);
					Games.setPcount(PlayerMethods.playerGame(p), Games.getPcount(PlayerMethods.playerGame(p)) + 1);
					ZombieSurvivalPlugin.getDead().remove(p.getName());
					Spectate.getSpectators().remove(p.getName());
					p.teleport(s.getLocation());
					p.setAllowFlight(false);
					p.setFlying(false);
					p.setGameMode(GameMode.SURVIVAL);
					p.setHealth(20);
					p.setFoodLevel(20);
					Utilities.unhidePlayer(p);
					this.timedSigns.remove(s);
					PlayerRevivedEvent pre = new PlayerRevivedEvent(PlayerMethods.playerGame(p), p, player);
					Bukkit.getServer().getPluginManager().callEvent(pre);
					player.sendMessage(ChatColor.GREEN + "You revived " + ChatColor.GRAY + p.getName());
					p.sendMessage(ChatColor.GREEN + "You were revived by " + ChatColor.GRAY + player.getName());
					e.setCancelled(true);
				} else if ((p != null) || (!PlayerMethods.inGame(p))) {
					b.setTypeId(0);
					this.timedSigns.remove(s);
					e.setCancelled(true);
				}
			}
		}
	}

	public void Destroy() {
		try {
			finalize();
		} catch (Throwable e) {
			this.plugin.getLogger().warning("Failed to destroy class.");
		}
	}
}