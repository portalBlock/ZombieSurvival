package com.jordair.gmail.zombiesurvival;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Awards {
	Plugin plugin;
	private Map<String, Integer> indKills = new HashMap<String, Integer>();
	private Map<Integer, ItemStack> rewardItems = new HashMap<Integer, ItemStack>();
	private Map<Integer, String> rewardMessage = new HashMap<Integer, String>();
	private FileConfiguration rConfig = null;
	private File rConfigFile = null;

	public Awards(ZombieSurvivalPlugin instance) {
		this.plugin = instance;
	}

	public void LoadAwards() {
		for (Iterator<?> it = getRConfig().getIntegerList("award-at-kill").iterator(); it.hasNext();) {
			int i = ((Integer) it.next()).intValue();
			try {
				String[] line = getRConfig().getString(Integer.toString(i) + ".item").split(":");
				int itemid = Integer.parseInt(line[0]);
				short itemdamage = 0;
				if (line.length > 1) {
					itemdamage = Short.parseShort(line[1]);
				}
				ItemStack item = new ItemStack(itemid, 1, itemdamage);
				this.rewardItems.put(Integer.valueOf(i), item);
				this.rewardMessage.put(Integer.valueOf(i), getRConfig().getString(Integer.toString(i) + ".message"));
				resetKills();
			} catch (Exception e) {
				this.plugin.getLogger().warning("Could not load award for kill: " + Integer.toString(i));
			}
		}
	}

	public void addKill(Player p) {
		String name = p.getName();
		if (this.indKills.containsKey(name))
			this.indKills.put(name, Integer.valueOf(((Integer) this.indKills.get(name)).intValue() + 1));
		else
			this.indKills.put(name, Integer.valueOf(1));
	}

	public void callAward(String name) {
		Player p = Bukkit.getPlayer(name);
		int ir = 0;
		for (Iterator<Integer> it = this.rewardItems.keySet().iterator(); it.hasNext();) {
			int i = ((Integer) it.next()).intValue();
			if ((((Integer) this.indKills.get(name)).intValue() >= i) && (i > ir)) {
				ir = i;
			}
		}

		if (this.rewardItems.containsKey(Integer.valueOf(ir))) {
			p.getInventory().addItem(new ItemStack[] { (ItemStack) this.rewardItems.get(Integer.valueOf(ir)) });
		}
		if (this.rewardMessage.containsKey(Integer.valueOf(ir)))
			p.sendMessage(ChatColor.DARK_PURPLE + (String) this.rewardMessage.get(Integer.valueOf(ir)));
	}

	public void resetKills() {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
			public void run() {
				for (String p : Awards.this.indKills.keySet()) {
					Awards.this.callAward(p);
				}
				Awards.this.indKills.clear();
			}
		}, 40L, 40L);
	}

	public void saveRConfig() {
		if ((this.rConfig == null) || (this.rConfigFile == null))
			return;
		try {
			getRConfig().save(this.rConfigFile);
		} catch (IOException ex) {
			this.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.rConfigFile, ex);
		}
	}

	public FileConfiguration getRConfig() {
		if (this.rConfig == null) {
			reloadRConfig();
		}
		return this.rConfig;
	}

	public void reloadRConfig() {
		if (this.rConfigFile == null) {
			this.rConfigFile = new File(this.plugin.getDataFolder(), "awards.yml");
		}
		this.rConfig = YamlConfiguration.loadConfiguration(this.rConfigFile);
		InputStream defConfigStream = this.plugin.getResource("awards.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			this.rConfig.setDefaults(defConfig);
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