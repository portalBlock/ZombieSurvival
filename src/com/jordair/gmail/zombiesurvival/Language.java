package com.jordair.gmail.zombiesurvival;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Language {
	Plugin plugin;
	private FileConfiguration lConfig = null;
	private File lConfigFile = null;
	private List<String> strings = new ArrayList<String>(100);

	public Language(Plugin instance) {
		this.plugin = instance;
	}

	public void LoadLanguage() {
		for (int i = 1; i < 80; i++)
			this.strings.add(Utilities.processForColors(getlConfig().getString(Integer.toString(i))));
	}

	public void savelConfig() {
		if ((this.lConfig == null) || (this.lConfigFile == null))
			return;
		try {
			getlConfig().save(this.lConfigFile);
		} catch (IOException ex) {
			this.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.lConfigFile, ex);
		}
	}

	public FileConfiguration getlConfig() {
		if (this.lConfig == null) {
			reloadlConfig();
		}
		return this.lConfig;
	}

	public void reloadlConfig() {
		if (this.lConfigFile == null) {
			this.lConfigFile = new File(this.plugin.getDataFolder(), "language.yml");
		}
		this.lConfig = YamlConfiguration.loadConfiguration(this.lConfigFile);
		InputStream defConfigStream = this.plugin.getResource("language.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			this.lConfig.setDefaults(defConfig);
		}
	}

	public Plugin getPlugin() {
		return plugin;
	}

	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}

	public File getlConfigFile() {
		return lConfigFile;
	}

	public void setlConfigFile(File lConfigFile) {
		this.lConfigFile = lConfigFile;
	}

	public List<String> getStrings() {
		return strings;
	}

	public void setStrings(List<String> strings) {
		this.strings = strings;
	}

	public void setlConfig(FileConfiguration lConfig) {
		this.lConfig = lConfig;
	}
}