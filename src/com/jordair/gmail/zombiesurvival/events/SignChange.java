package com.jordair.gmail.zombiesurvival.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import com.jordair.gmail.zombiesurvival.ZombieSurvivalPlugin;

public class SignChange implements Listener {

	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		String[] lines = event.getLines();
		ZombieSurvivalPlugin.getInstance();
		ZombieSurvivalPlugin.getInstance();
		if ((lines[0].equalsIgnoreCase("zombie stats"))
				&& ((ZombieSurvivalPlugin.getMaps().containsKey(lines[1])) || (ZombieSurvivalPlugin.getMaps().containsKey(lines[2])))) {
			Player player = event.getPlayer();
			event.setLine(0, "§dzombie stats");
			if (player.hasPermission("zs.edit")) {
				Location sloc = event.getBlock().getLocation();
				ZombieSurvivalPlugin.getInstance().getSigns().add(sloc);
				ZombieSurvivalPlugin.getInstance().saveSigns();
				player.sendMessage(ChatColor.GREEN + "You have created a new ZombieSurvival sign!");
			} else {
				event.setLine(0, "§4MUST BE OP");
			}
		} else if (lines[0].equalsIgnoreCase("zombie stats")) {
			event.setLine(0, "§4BAD SIGN");
		}
		ZombieSurvivalPlugin.getInstance();
		if ((lines[0].equalsIgnoreCase("zombie door")) && (!lines[2].isEmpty()) && (ZombieSurvivalPlugin.getMaps().containsKey(lines[1]))) {
			Player player = event.getPlayer();
			event.setLine(0, "§9zombie door");
			if (player.hasPermission("zs.edit"))
				player.sendMessage(ChatColor.GREEN + "You have created a new ZombieSurvival sign!");
			else
				event.setLine(0, "§4MUST BE OP");
		} else if (lines[0].equalsIgnoreCase("zombie door")) {
			event.setLine(0, "§4BAD SIGN");
		}
	}
}
