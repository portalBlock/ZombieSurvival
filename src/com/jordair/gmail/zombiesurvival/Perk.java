package com.jordair.gmail.zombiesurvival;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import com.jordair.gmail.zombiesurvival.api.PerkType;
import com.jordair.gmail.zombiesurvival.api.PlayerUsePerkEvent;

public class Perk implements Listener {
	Random random = new Random();
	private Map<String, Location> dLoc = new HashMap<String, Location>();
	private Map<String, Integer> perkID = new HashMap<String, Integer>();
	private Map<String, Integer> gameperks = new HashMap<String, Integer>();
	public boolean isDropped = false;

	public void addLoc(String map, Location l) {
		this.dLoc.put(map, l);
	}

	public void removeLoc(String map) {
		this.dLoc.remove(map);
	}

	public void setPerk(String map, int i) {
		this.gameperks.put(map, Integer.valueOf(i));
	}

	public int getPerk(String map) {
		return ((Integer) this.gameperks.get(map)).intValue();
	}

	public Set<String> setPerk() {
		return this.gameperks.keySet();
	}

	public void callPerk(String map) {
		if ((this.dLoc.get(map) != null) && (!this.isDropped)) {
			ItemStack item = new ItemStack(perkItem(), 1);
			World world = Bukkit.getWorld((String) ZombieSurvivalPlugin.getMaps().get(map));
			Item i = world.dropItem((Location) this.dLoc.get(map), item);
			i.teleport((Location) this.dLoc.get(map));
			this.perkID.put(map, Integer.valueOf(i.getEntityId()));
			this.isDropped = true;
			for (String pl : PlayerMethods.playersInMap(map)) {
				Player p = Bukkit.getPlayer(pl);
				if (p != null)
					p.sendMessage(ChatColor.DARK_PURPLE + "Perk ready!");
			}
		} else {
			NewPerk(map);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPickUp(PlayerPickupItemEvent e) {
		Player player = e.getPlayer();
		Item im = e.getItem();
		if (PlayerMethods.inGame(player)) {
			String map = PlayerMethods.playerGame(player);
			if ((this.perkID.get(map) != null) && (im.getEntityId() == ((Integer) this.perkID.get(map)).intValue())) {
				e.setCancelled(true);
				int randomperk = im.getItemStack().getTypeId();
				boolean d = false;
				switch (randomperk) {
				case 266:
					this.gameperks.put(map, Integer.valueOf(1));
					for (String pl : PlayerMethods.playersInMap(map)) {
						Player p = Bukkit.getPlayer(pl);
						if (p != null) {
							p.sendMessage(ChatColor.DARK_PURPLE + "GODMODE PERK ENABLED");
							d = true;
						}
					}
					break;
				case 264:
					this.gameperks.put(map, Integer.valueOf(2));
					for (String pl : PlayerMethods.playersInMap(map)) {
						Player p = Bukkit.getPlayer(pl);
						if (p != null) {
							p.sendMessage(ChatColor.DARK_PURPLE + "INSTA-KILL PERK ENABLED");
							d = true;
						}
					}
					break;
				case 51:
					this.gameperks.put(map, Integer.valueOf(3));
					for (String pl : PlayerMethods.playersInMap(map)) {
						Player p = Bukkit.getPlayer(pl);
						if (p != null) {
							p.sendMessage(ChatColor.DARK_PURPLE + "FIRE PERK ENABLED");
							d = true;
						}
					}
					break;
				case 371:
					this.gameperks.put(map, Integer.valueOf(4));
					for (String pl : PlayerMethods.playersInMap(map)) {
						Player p = Bukkit.getPlayer(pl);
						if (p != null) {
							p.sendMessage(ChatColor.DARK_PURPLE + "BONUS XP PERK ENABLED");
							d = true;
						}
					}
					break;
				case 265:
					this.gameperks.put(map, Integer.valueOf(6));
					for (String pl : PlayerMethods.playersInMap(map)) {
						Player p = Bukkit.getPlayer(pl);
						if (p != null) {
							p.sendMessage(ChatColor.DARK_PURPLE + "IRON FIST PERK ENABLED");
							d = true;
						}
					}
				}
				if(d) {
					PlayerUsePerkEvent pue = new PlayerUsePerkEvent(map, player, PerkType.forID(randomperk));
					Bukkit.getServer().getPluginManager().callEvent(pue);
				}

				im.remove();
				this.isDropped = false;
			}
		}
	}

	@EventHandler
	public void onDespawn(ItemDespawnEvent e) {
		int id = e.getEntity().getEntityId();
		for (Iterator<String> it = this.perkID.keySet().iterator(); it.hasNext();) {
			String m = (String) it.next();
			if (((Integer) this.perkID.get(m)).intValue() == id) {
				it.remove();
				this.isDropped = false;
			}
		}
	}

	public void NewPerk(String map) {
		int randomperk = this.random.nextInt(5) + 1;
		switch (randomperk) {
		case 1:
			this.gameperks.put(map, Integer.valueOf(1));
			for (String pl : PlayerMethods.playersInMap(map)) {
				Player p = Bukkit.getPlayer(pl);
				if (p != null) {
					p.sendMessage(ChatColor.DARK_PURPLE + "GOD MODE.");
				}
			}
			break;
		case 2:
			this.gameperks.put(map, Integer.valueOf(2));
			for (String pl : PlayerMethods.playersInMap(map)) {
				Player p = Bukkit.getPlayer(pl);
				if (p != null) {
					p.sendMessage(ChatColor.DARK_PURPLE + "INSTANT-KILL.");
				}
			}
			break;
		case 3:
			this.gameperks.put(map, Integer.valueOf(3));
			for (String pl : PlayerMethods.playersInMap(map)) {
				Player p = Bukkit.getPlayer(pl);
				if (p != null) {
					p.sendMessage(ChatColor.DARK_PURPLE + "FIRE PERK.");
				}
			}
			break;
		case 4:
			this.gameperks.put(map, Integer.valueOf(4));
			for (String pl : PlayerMethods.playersInMap(map)) {
				Player p = Bukkit.getPlayer(pl);
				if (p != null) {
					p.sendMessage(ChatColor.DARK_PURPLE + "BONUS XP.");
				}
			}
			break;
		case 5:
			this.gameperks.put(map, Integer.valueOf(6));
			for (String pl : PlayerMethods.playersInMap(map)) {
				Player p = Bukkit.getPlayer(pl);
				if (p != null)
					p.sendMessage(ChatColor.DARK_PURPLE + "IRON FIST.");
			}
		}
		PlayerUsePerkEvent pue = new PlayerUsePerkEvent(map, null, PerkType.forID(perkItem(randomperk)));
		Bukkit.getServer().getPluginManager().callEvent(pue);
	}

	public int perkItem(int randomperk) {
		switch (randomperk) {
		case 1:
			return 266;
		case 2:
			return 264;
		case 3:
			return 51;
		case 4:
			return 371;
		case 5:
			return 265;
		}
		return 0;
	}
	
	public int perkItem() {
		return perkItem(this.random.nextInt(5) + 1);
	}
}