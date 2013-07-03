package com.jordair.gmail.zombiesurvival;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.plugin.Plugin;

public class Spawns {
	Plugin plugin;
	public List<Spawn> spawns = new ArrayList<Spawn>();
	public Map<String, Location> lobbies = new HashMap<String, Location>();
	public Map<String, Location> spectate = new HashMap<String, Location>();
	public Random rand = new Random();

	public Spawns(Plugin instance) {
		this.plugin = instance;
	}

	public void saveSpawn() {
		try {
			File file = new File(this.plugin.getDataFolder(), "spawns.zss");
			FileOutputStream saveFile = new FileOutputStream(file);
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			save.writeObject(this.spawns);
			save.close();
		} catch (Exception e) {
		}
	}

	@SuppressWarnings("unchecked")
	public void loadSpawn() {
		try {
			File file = new File(this.plugin.getDataFolder(), "spawns.zss");
			FileInputStream saveFile = new FileInputStream(file);
			ObjectInputStream restore = new ObjectInputStream(saveFile);
			this.spawns = ((ArrayList<Spawn>) restore.readObject());
			restore.close();
			for (Spawn sp : this.spawns)
				sp.init();
		} catch (Exception e) {
		}
	}

	public void addSpawn(Block b, String m, int w) {
		Spawn newsp = new Spawn(b, m, w);
		this.spawns.add(newsp);
		showSpawn(newsp);
		saveSpawn();
	}

	public void removeSpawn(Spawn d) {
		Block b = d.location.getBlock();
		b.setTypeId(d.type);
		b.setData(d.data);
		this.spawns.remove(d);
		saveSpawn();
	}

	public void resetSpawn(String m) {
		for (Spawn sp : this.spawns)
			if (sp.map.matches(m))
				sp.activated = false;
	}

	public Spawn findSpawn(double x, double y, double z) {
		for (Spawn sp : this.spawns) {
			if ((sp.x == x) && (sp.y == y) && (sp.z == z)) { return sp; }
		}
		return null;
	}

	public List<Location> spawnLocs(String map, int wave) {
		List<Location> locs = new ArrayList<Location>();
		for (Spawn sp : this.spawns) {
			if ((sp.map.matches(map)) && ((sp.wave <= wave) || (sp.activated == true)) && (sp.location != null)) {
				locs.add(sp.location);
			}
		}

		return locs;
	}

	public Location spawn(String map, int wave) {
		Random random = new Random();
		Location loc = (Location) this.lobbies.get(map);
		try {
			loc = (Location) spawnLocs(map, wave).get(random.nextInt(spawnLocs(map, wave).size()));
		} catch (Exception e) {
			System.err.println("[ZombieSurvival] No zombie spawns for: " + map + " using lobby spawnpoint instead!");
		}
		return loc;
	}

	public void showSpawns(String map) {
		for (Spawn d : this.spawns)
			if (d.map.matches(map))
				showSpawn(d);
	}

	public void hideSpawns(String map) {
		for (Spawn d : this.spawns)
			if (d.map.matches(map))
				hideSpawn(d);
	}

	public void showSpawn(Spawn s) {
		Block b = s.location.getBlock();
		b.setTypeId(35);
		b.setData(DyeColor.LIME.getWoolData());
	}

	public void hideSpawn(Spawn s) {
		Block b = s.location.getBlock();
		b.setTypeId(s.type);
		b.setData(s.data);
	}

	public void EquipMob(LivingEntity ent) {
		if ((ent instanceof Zombie))
			;
	}

	public void Destroy() {
		try {
			finalize();
		} catch (Throwable e) {
			this.plugin.getLogger().warning("Failed to destroy class.");
		}
	}
}