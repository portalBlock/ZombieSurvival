package com.jordair.gmail.zombiesurvival;

import java.io.Serializable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

@SuppressWarnings("serial")
public class Barricade implements Serializable {

	private double x;
	private double y;
	private double z;
	private byte data;
	private int type;
	private String world;
	private String map;
	private int health;
	private transient Location location;

	public Barricade(Block b, String m) {
		this.setX(b.getX());
		this.setY(b.getY());
		this.setZ(b.getZ());
		this.map = m;
		this.world = b.getWorld().getName();
		this.setLocation(b.getLocation());
		this.setData(b.getData());
		this.setType(b.getTypeId());
		this.health = 50;
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

	public byte getData() {
		return data;
	}

	public void setData(byte data) {
		this.data = data;
	}

	public String getMap() {
		return map;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void init() {
		World w = Bukkit.getWorld(this.world);
		this.setLocation(new Location(w, this.getX(), this.getY(), this.getZ()));
		Block b = this.getLocation().getBlock();
		b.setData(this.getData());
		b.setTypeId(this.getType());
	}
}