package com.jordair.gmail.zombiesurvival;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import com.jordair.gmail.zombiesurvival.api.BarricadeBreakEvent;
import com.jordair.gmail.zombiesurvival.api.BarricadeHealEvent;

public class Barricades {
	Plugin plugin;
	public List<Barricade> bar = new ArrayList<Barricade>();

	public List<Barricade> getAll() {
		return new ArrayList<Barricade>(bar);
	}

	public void saveBar() {
		try {
			File file = new File(this.plugin.getDataFolder(), "barricades.zss");
			FileOutputStream saveFile = new FileOutputStream(file);
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			save.writeObject(this.bar);
			save.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void loadBar() {
		try {
			File file = new File(this.plugin.getDataFolder(), "barricades.zss");
			FileInputStream saveFile = new FileInputStream(file);
			ObjectInputStream restore = new ObjectInputStream(saveFile);
			this.bar = ((ArrayList<Barricade>) restore.readObject());
			restore.close();
			for (Barricade d : this.bar)
				d.init();
		} catch (Exception e) {
		}
	}

	public void addBar(Block b, String m) {
		Barricade newdr = new Barricade(b, m);
		this.bar.add(newdr);
		showBar(newdr);
		saveBar();
	}

	public void removeBar(Barricade d) {
		Block b = d.getLocation().getBlock();
		b.setTypeId(d.getType());
		b.setData(d.getData());
		this.bar.remove(d);
		saveBar();
	}

	public void showBarricades(String map) {
		for (Barricade d : this.bar)
			if (d.getMap().matches(map)) {
				Block b = d.getLocation().getBlock();
				b.setTypeId(35);
				b.setData(DyeColor.LIME.getWoolData());
			}
	}

	public void hideBarricades(String map) {
		for (Barricade d : this.bar)
			if (d.getMap().matches(map)) {
				Block b = d.getLocation().getBlock();
				b.setTypeId(d.getType());
				b.setData(d.getData());
			}
	}

	public void showBar(Barricade d) {
		Block b = d.getLocation().getBlock();
		b.setTypeId(35);
		b.setData(DyeColor.LIME.getWoolData());
	}

	public void hideBar(Barricade d) {
		Block b = d.getLocation().getBlock();
		b.setTypeId(d.getType());
		b.setData(d.getData());
	}

	public Barricade findBar(double x, double y, double z) {
		for (Barricade d : this.bar) {
			if ((d.getX() == x) && (d.getY() == y) && (d.getZ() == z)) { return d; }
		}
		return null;
	}

	public void damageBarricade(Barricade d) {
		d.setHealth(d.getHealth() - 1);
		if (d.getHealth() <= 0) {
			d.setHealth(0);
			Block b = d.getLocation().getBlock();
			b.setTypeId(0);
		}
		BarricadeBreakEvent bbe = new BarricadeBreakEvent(d);
		Bukkit.getServer().getPluginManager().callEvent(bbe);
	}

	public boolean healBarricade(Barricade d) {
		if (d.getHealth() >= 50) { return false; }
		d.setHealth(d.getHealth() + 10);
		if (d.getHealth() >= 50) {
			d.setHealth(50);
			Block b = d.getLocation().getBlock();
			b.setTypeId(d.getType());
			b.setData(d.getData());
		}
		BarricadeHealEvent bhe = new BarricadeHealEvent(d);
		Bukkit.getServer().getPluginManager().callEvent(bhe);
		return true;
	}

	public List<Barricade> checkForBarricades(Location middle, String map) {
		List<Barricade> barshere = new ArrayList<Barricade>();
		for (int x = middle.getBlockX() - 3; x <= middle.getBlockX() + 3; x++) {
			for (int y = middle.getBlockY() - 3; y <= middle.getBlockY() + 3; y++) {
				for (int z = middle.getBlockZ() - 3; z <= middle.getBlockZ() + 3; z++) {
					Barricade d = findBar(x, y, z);
					if ((d != null) && (d.getMap().matches(map))) {
						barshere.add(d);
					}
				}
			}
		}
		return barshere;
	}

	public boolean healBars(Location middle, String map) {
		boolean pay = false;
		for (Barricade d : checkForBarricades(middle, map)) {
			if ((healBarricade(d)) && (!pay)) {
				pay = true;
			}
		}
		return pay;
	}

	public void damageBars(Location middle, String map) {
		for (Barricade d : checkForBarricades(middle, map))
			damageBarricade(d);
	}

	public void resetBars(String map) {
		for (Barricade d : this.bar)
			if (d.getMap().matches(map)) {
				Block b = d.getLocation().getBlock();
				b.setTypeId(d.getType());
				b.setData(d.getData());
				d.setHealth(50);
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