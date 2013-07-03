package com.jordair.gmail.zombiesurvival;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Classes {
	public Plugin plugin;
	private static Map<String, String> userclass = new HashMap<String, String>();
	private static Map<String, List<ItemStack>> classitems = new HashMap<String, List<ItemStack>>();
	private static Map<String, String> classes = new HashMap<String, String>();
	private static FileConfiguration cConfig = null;
	private static File cConfigFile = null;

	public static String getUserClass(Player p) {
		return (String) userclass.get(p.getName());
	}

	public static void setUserClass(Player p, String cl) {
		if (classes.containsKey(cl)) {
			if ((userclass.get(p.getName()) != null) && (((String) userclass.get(p.getName())).equalsIgnoreCase(cl))) {
				p.sendMessage(ChatColor.GRAY + "You're already in that class!");
				return;
			}
			userclass.put(p.getName(), cl);
			p.sendMessage(ChatColor.DARK_RED + "You joined: " + ChatColor.GRAY + (String) classes.get(cl));
		} else {
			p.sendMessage(ChatColor.RED + "Invalid class.");
		}
	}

	public static Set<String> getAllUsers() {
		return userclass.keySet();
	}

	public static Set<String> getAllClasses() {
		return classes.keySet();
	}

	public static void removeUser(Player p) {
		userclass.remove(p.getName());
	}

	public static boolean isClassed(Player p) {
		if (userclass.containsKey(p.getName())) { return true; }
		return false;
	}

	public static void setupPlayer(Player p) {
		String name = p.getName();
		if ((userclass.get(name) != null) && (isClassed(p))) {
			for (ItemStack i : (List<ItemStack>) classitems.get(userclass.get(name)))
				Utilities.equipPlayer(p, i);
		}
	}

	public void savecConfig() {
		if ((cConfig == null) || (cConfigFile == null))
			return;
		try {
			getcConfig().save(cConfigFile);
		} catch (IOException ex) {
			this.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + cConfigFile, ex);
		}
	}

	public FileConfiguration getcConfig() {
		if (cConfig == null) {
			reloadcConfig();
		}
		return cConfig;
	}

	public void reloadcConfig() {
		if (cConfigFile == null) {
			cConfigFile = new File(this.plugin.getDataFolder(), "classes.yml");
		}
		cConfig = YamlConfiguration.loadConfiguration(cConfigFile);
		InputStream defConfigStream = this.plugin.getResource("classes.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			cConfig.setDefaults(defConfig);
		}
	}

	public void LoadClasses() {
		for (Iterator<String> i$ = getcConfig().getStringList("Classes").iterator(); i$.hasNext();) {
			String cls = (String) i$.next();
			classes.put(cls, getcConfig().getString(cls + ".description"));
			for (String it : getcConfig().getStringList(cls + ".items"))
				try {
					String[] line = it.split(":");
					int itemid = Integer.parseInt(line[0]);
					short itemdamage = 0;
					if (line.length > 1) {
						itemdamage = Short.parseShort(line[1]);
					}
					ItemStack item = new ItemStack(itemid, 1, itemdamage);
					if (classitems.get(cls) != null) {
						((List<ItemStack>) classitems.get(cls)).add(item);
					} else {
						classitems.put(cls, new ArrayList<ItemStack>());
						((List<ItemStack>) classitems.get(cls)).add(item);
					}
				} catch (Exception e) {
					this.plugin.getLogger().warning("Could not load class: " + cls);
				}
		}
	}
}