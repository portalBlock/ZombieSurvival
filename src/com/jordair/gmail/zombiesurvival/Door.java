package com.jordair.gmail.zombiesurvival;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

@SuppressWarnings("serial")
public class Door implements Serializable {

	private double x;
	private double y;
	private double z;
	private byte data;
	private int type;
	private int wave;
	private String world;
	private String map;
	private List<Spawn> spawns = new ArrayList<Spawn>();
	private boolean open;
	private transient Location location;

	public Door(Block b, String m, int w) {
		this.setX(b.getX());
		this.setY(b.getY());
		this.setZ(b.getZ());
		this.setData(b.getData());
		this.setType(b.getTypeId());
		this.setOpen(false);
		this.setLocation(b.getLocation());
		this.setWave(w);
		this.world = b.getWorld().getName();
		this.map = m;
	}

	public int getWave() {
		return wave;
	}

	public void setWave(int wave) {
		this.wave = wave;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public byte getData() {
		return data;
	}

	public void setData(byte data) {
		this.data = data;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<Spawn> getSpawns() {
		return new ArrayList<Spawn>(spawns);
	}
	
	public void addSpawn(Spawn s) {
		spawns.add(s);
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public String getMap() {
		return map;
	}
	
	public void init() {
		World w = Bukkit.getWorld(this.world);
		this.setLocation(new Location(w, this.getX(), this.getY(), this.getZ()));
		this.setOpen(false);
		Block b = this.getLocation().getBlock();
		b.setData(this.getData());
		b.setTypeId(this.getType());
	}
}