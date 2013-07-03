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
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.TrapDoor;
import org.bukkit.plugin.Plugin;

import com.jordair.gmail.zombiesurvival.api.DoorOpenEvent;

public class Doors {
	Plugin plugin;
	public List<Door> doors = new ArrayList<Door>();
	private final Byte topDoor = (0x8);
	private final Byte bottomDoor = (0x4);

	public Doors(Plugin instance) {
		this.plugin = instance;
	}

	public List<Door> getAll() {
		return new ArrayList<Door>(doors);
	}

	public void saveDoor() {
		try {
			File file = new File(this.plugin.getDataFolder(), "doors.zss");
			FileOutputStream saveFile = new FileOutputStream(file);
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			save.writeObject(this.doors);
			save.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void loadDoor() {
		try {
			File file = new File(this.plugin.getDataFolder(), "doors.zss");
			FileInputStream saveFile = new FileInputStream(file);
			ObjectInputStream restore = new ObjectInputStream(saveFile);
			this.doors = ((ArrayList<Door>) restore.readObject());
			restore.close();
			for (Door d : this.doors)
				d.init();
		} catch (Exception e) {
		}
	}

	public boolean addDoor(Block b, String m, int w) {
		if (findDoor(b.getX(), b.getY(), b.getZ()) != null) { return false; }
		if (b.getTypeId() == 64 || b.getTypeId() == 71) {
			if (b.getRelative(BlockFace.DOWN).getTypeId() == b.getTypeId()) {
				b = b.getRelative(BlockFace.DOWN);
			}
		}
		Door newdr = new Door(b, m, w);
		this.doors.add(newdr);
		showDoor(newdr);
		saveDoor();
		return true;
	}

	public void removeDoor(Door d) {
		if (d == null || d.getLocation() == null) { return; }
		Block b = d.getLocation().getBlock();

		if (d.getType() == 64 || d.getType() == 71) {
			Block b2 = b.getRelative(BlockFace.UP);
			b.setTypeId(d.getType());
			b.setData(bottomDoor);
			b2.setTypeId(d.getType());
			b2.setData(topDoor);
		} else {
			b.setTypeId(d.getType());
			b.setData(d.getData());
		}
		this.doors.remove(d);
		saveDoor();
	}

	public void resetDoors(String m) {
		for (Door d : this.doors)
			if (d.getMap().matches(m)) {
				Block b = d.getLocation().getBlock();

				if (d.getType() == 64 || d.getType() == 71) {
					Block b2 = b.getRelative(BlockFace.UP);
					if (b2.getTypeId() == 64 || b2.getTypeId() == 71) {
						toggleDoor(b2, false);
					} else {
						b.setTypeId(d.getType());
						b.setData(bottomDoor);
						b2.setTypeId(d.getType());
						b2.setData(topDoor);
					}
				} else {
					b.setTypeId(d.getType());
					b.setData(d.getData());
				}
				d.setOpen(false);
				deActivateSpawns(d);
			}
	}

	public Door findDoor(double x, double y, double z) {
		for (Door d : this.doors) {
			if ((d.getX() == x) && (d.getY() == y) && (d.getZ() == z)) { return d; }
		}
		return null;
	}

	public List<Location> doorLocs(String map) {
		List<Location> locs = new ArrayList<Location>();
		for (Door d : this.doors) {
			if ((d.getMap().matches(map)) && (d.getLocation() != null)) {
				locs.add(d.getLocation());
			}
		}

		return locs;
	}

	private boolean isDoor(Block b) {
		int t = b.getTypeId();
		return (t == 96 || t == 64 || t == 71);
	}

	private boolean isDoorClosed(Block block) {
		if (!isDoor(block)) { return false; }
		if (block.getTypeId() == 96) {
			TrapDoor trapdoor = (TrapDoor) block.getState().getData();
			return !trapdoor.isOpen();
		} else {
			byte data = block.getData();
			if ((data & 0x8) == 0x8) {
				block = block.getRelative(BlockFace.DOWN);
				data = block.getData();
			}
			return ((data & 0x4) == 0);
		}
	}

	private void toggleDoor(Block block, boolean open) {
		if (!isDoor(block)) { return; }
		if (block.getTypeId() == 96) {
			BlockState state = block.getState();
			TrapDoor trapdoor = (TrapDoor) state.getData();
			trapdoor.setOpen(open);
			block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
			state.update();
		} else {
			byte data = block.getData();
			if ((data & 0x8) == 0x8) {
				block = block.getRelative(BlockFace.DOWN);
				data = block.getData();
			}
			if (open) {
				if (isDoorClosed(block)) {
					data = (byte) (data | 0x4);
					block.setData(data, true);
					block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
				}
			} else {
				if (!isDoorClosed(block)) {
					data = (byte) (data & 0xb);
					block.setData(data, true);
					block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
				}
			}
		}
	}

	private void toggleDoor(Block block) {
		if (!isDoor(block)) { return; }
		if (block.getTypeId() == 96) {
			BlockState state = block.getState();
			TrapDoor trapdoor = (TrapDoor) state.getData();
			trapdoor.setOpen(!trapdoor.isOpen());
			block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
			state.update();
		} else {
			byte data = block.getData();
			if ((data & 0x8) == 0x8) {
				block = block.getRelative(BlockFace.DOWN);
				data = block.getData();
			}
			if (isDoorClosed(block)) {
				data = (byte) (data | 0x4);
				block.setData(data, true);
				block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
			} else if (!isDoorClosed(block)) {
				data = (byte) (data & 0xb);
				block.setData(data, true);
				block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
			}
		}
	}

	public List<Door> doorWave(String map, int wave) {
		List<Door> dwave = new ArrayList<Door>();
		for (Door d : this.doors) {
			if ((d.getMap().matches(map)) && (d.getWave() == wave)) {
				dwave.add(d);
			}
		}
		return dwave;
	}

	public void openDoors(String map, int wave) {
		for (Door d : doorWave(map, wave)) {
			openDoor(d);
		}
	}

	public boolean openDoor(Door d) {
		if (d == null || d.isOpen()) { return false; }
		Block b = d.getLocation().getBlock();

		if (d.getType() == 64 || d.getType() == 71) {
			toggleDoor(b.getRelative(BlockFace.UP));
		} else {
			b.setTypeId(0);
		}
		d.setOpen(true);
		activateSpawns(d);
		DoorOpenEvent doe = new DoorOpenEvent(d);
		Bukkit.getServer().getPluginManager().callEvent(doe);
		return true;
	}

	public void showDoors(String map) {
		for (Door d : this.doors) {
			if (d.getMap().matches(map)) {
				showDoor(d);
			}
		}
	}

	public void hideDoors(String map) {
		for (Door d : this.doors) {
			if (d.getMap().matches(map)) {
				hideDoor(d);
			}
		}
	}

	public void showDoor(Door d) {
		Block b = d.getLocation().getBlock();

		if (d.getType() == 64 || d.getType() == 71) {
			Block b2 = b.getRelative(BlockFace.UP);
			b2.setTypeId(35);
			b2.setData(DyeColor.LIME.getWoolData());
		}

		b.setTypeId(35);
		b.setData(DyeColor.LIME.getWoolData());
	}

	public void hideDoor(Door d) {
		Block b = d.getLocation().getBlock();

		if (d.getType() == 64 || d.getType() == 71) {
			Block b2 = b.getRelative(BlockFace.UP);
			b.setTypeId(d.getType());
			b.setData(bottomDoor);
			b2.setTypeId(d.getType());
			b2.setData(topDoor);
		} else {
			b.setTypeId(d.getType());
			b.setData(d.getData());
		}
	}

	public void linkDoorSpawn(Door d, Spawn p) {
		d.getSpawns().add(p);
	}

	public void activateSpawns(Door d) {
		for (Spawn s : d.getSpawns())
			s.activated = true;
	}

	public void deActivateSpawns(Door d) {
		for (Spawn s : d.getSpawns())
			s.activated = false;
	}

	public void Destroy() {
		try {
			finalize();
		} catch (Throwable e) {
			this.plugin.getLogger().warning("Failed to destroy class.");
		}
	}
}