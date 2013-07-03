package com.jordair.gmail.zombiesurvival;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_6_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utilities {
	public static boolean isArmor(ItemStack i) {
		int id = i.getTypeId();
		if ((id >= 298) && (id <= 317)) { return true; }
		return false;
	}

	public static boolean isArmor(int id) {
		if ((id >= 298) && (id <= 317)) { return true; }
		return false;
	}

	public static boolean isHelm(ItemStack i) {
		int id = i.getTypeId();
		if ((id == 298) || (id == 302) || (id == 306) || (id == 310)) { return true; }
		return false;
	}

	public static boolean isChestplate(ItemStack i) {
		int id = i.getTypeId();
		if ((id == 299) || (id == 303) || (id == 307) || (id == 311)) { return true; }
		return false;
	}

	public static boolean isLeggings(ItemStack i) {
		int id = i.getTypeId();
		if ((id == 300) || (id == 304) || (id == 308) || (id == 312)) { return true; }
		return false;
	}

	public static boolean isBoots(ItemStack i) {
		int id = i.getTypeId();
		if ((id == 301) || (id == 305) || (id == 309) || (id == 313)) { return true; }
		return false;
	}

	public static void equipPlayer(Player p, ItemStack i) {
		if (isArmor(i)) {
			if (isHelm(i))
				p.getInventory().setHelmet(i);
			else if (isChestplate(i))
				p.getInventory().setChestplate(i);
			else if (isLeggings(i))
				p.getInventory().setLeggings(i);
			else if (isBoots(i))
				p.getInventory().setBoots(i);
		} else
			p.getInventory().addItem(new ItemStack[] { i });
	}

	public static String processForColors(String str) {
		String parsed;

		if ((str != null) && (str.contains("&"))) {
			parsed = str.replaceAll("&", "§");
		} else {
			if (str != null)
				parsed = str;
			else
				parsed = "";
		}
		return parsed;
	}

	public static String annouceMax(String map) {
		int max = 0;
		int mz = Games.getMaxZombies(map);
		int w = Games.getWave(map);
		if (mz < 10) {
			max = (int) (mz * w * 0.5D);
		}
		if ((mz >= 10) && (mz <= 50)) {
			max = (int) (mz * w * 0.1D);
		}
		if ((mz >= 51) && (mz <= 100)) {
			max = (int) (mz * w * 0.08D);
		}
		if ((mz >= 101) && (mz <= 200)) {
			max = (int) (mz * w * 0.05D);
		}
		if (mz >= 201) {
			max = (int) (mz * w * 0.04D);
		}
		String string = Integer.toString(max);
		return string;
	}

	public static void clearDrops(String map) {
		World world = Bukkit.getWorld((String) ZombieSurvivalPlugin.getMaps().get(map));
		for (Entity e : ZombieSurvivalPlugin.getEnts(world))
			if (((e instanceof Item)) && (e.isValid()))
				e.remove();
	}

	public static void hidePlayer(Player player) {
		if ((player != null) && (player.isValid())) {
			for (Player p : Bukkit.getOnlinePlayers())
				if ((p != null) && (p.isValid())) {
					p.hidePlayer(player);
					if (!PlayerMethods.inGame(player))
						player.hidePlayer(p);
				}
		}
	}

	public static void unhidePlayer(Player player) {
		if ((player != null) && (player.isValid())) {
			for (Player p : Bukkit.getOnlinePlayers())
				if ((p != null) && (p.isValid()))
					p.showPlayer(player);
		}
	}

	public static void livingEntityMoveTo(LivingEntity livingEntity, double x, double y, double z, float speed) {
		((CraftLivingEntity) livingEntity).getHandle().move(x, y, z);
	}

	public static Location getSegment(LivingEntity mob, Location tar) {
		if ((Math.abs(tar.getX() - mob.getLocation().getX()) < 10.0D) && (Math.abs(tar.getZ() - mob.getLocation().getZ()) < 10.0D)) { return tar; }
		return trigdis(mob.getLocation(), tar);
	}

	private static Location trigdis(Location o, Location t) {
		double xdis = 9.0D;
		double zdis = 9.0D;
		if (t.getX() < o.getX()) {
			xdis = -9.0D;
		}
		if (Math.abs(o.getX() - t.getX()) < 9.0D) {
			xdis = -(o.getX() - t.getX());
		}
		if (t.getZ() < o.getZ()) {
			zdis = -9.0D;
		}
		if (Math.abs(o.getZ() - t.getZ()) < 9.0D) {
			zdis = -(o.getZ() - t.getZ());
		}
		return new Location(o.getWorld(), o.getX() + xdis, o.getY(), o.getZ() + zdis);
	}

	public static double calcMaxZ(int pmax) {
		if (pmax <= 5)
			return pmax * 0.12D;
		if ((pmax > 5) && (pmax <= 10))
			return pmax * 0.1D;
		if ((pmax > 10) && (pmax <= 15)) { return pmax * 0.09D; }
		return pmax * 0.1D;
	}
}