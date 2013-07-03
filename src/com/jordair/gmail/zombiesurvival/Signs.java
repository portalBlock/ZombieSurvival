package com.jordair.gmail.zombiesurvival;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Signs implements Listener {
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && (player.hasPermission("zs.signs"))) {
			Block block = e.getClickedBlock();
			if ((block.getState() instanceof Sign)) {
				Sign sign = (Sign) block.getState();
				String[] lines = sign.getLines();
				if ((lines[0].equalsIgnoreCase("§9zombie")) && (lines[1].equalsIgnoreCase("How To Play"))) {
					player.sendMessage(ChatColor.GRAY + "Join a game by either right clicking a stats sign, or using the command /join");
					player.sendMessage(ChatColor.GRAY
							+ "If you notice items in your inventory, equip them. If not look for a zombie and begin punching!");
					player.sendMessage(ChatColor.GRAY + "Play co-operatively and have fun!");
				} else if ((lines[0].equalsIgnoreCase("§9zombie")) && (lines[1].equalsIgnoreCase("What's a class?"))) {
					player.sendMessage(ChatColor.GRAY + "Classes are sets of user abilities. They include tool kits and power-ups.");
				} else if ((lines[0].equalsIgnoreCase("§9zombie")) && (lines[1].equalsIgnoreCase("Top Players"))) {
					player.sendMessage(ChatColor.GRAY + "The Top Scoring Player: " + Stats.topScorePlayer() + " - "
							+ Double.toString(Stats.topScore()));
					player.sendMessage(ChatColor.GRAY + "The Most Savage Killer: " + Stats.topKillsPlayer() + " - "
							+ Double.toString(Stats.topKills()));
					player.sendMessage(ChatColor.GRAY + "The Dead Guy: " + Stats.topDeathsPlayer() + " - "
							+ Double.toString(Stats.topDeaths()));
				} else if ((lines[0].equalsIgnoreCase("§9zombie")) && (lines[1].equalsIgnoreCase("My Top Scores"))) {
					player.sendMessage(ChatColor.GREEN + "Total Points: " + ChatColor.DARK_RED
							+ Double.toString(Stats.getTotalPoints(player.getName())));
					player.sendMessage(ChatColor.GREEN + "Total Kills: " + ChatColor.DARK_RED
							+ Double.toString(Stats.getTotalKills(player.getName())));
					player.sendMessage(ChatColor.GREEN + "Total Deaths: " + ChatColor.DARK_RED
							+ Double.toString(Stats.getTotalDeaths(player.getName())));
				} else if ((lines[0].equalsIgnoreCase("§9zombie class")) && (lines[1].equalsIgnoreCase("Join"))) {
					if (!PlayerMethods.inGame(player))
						Classes.setUserClass(player, lines[2]);
					else
						player.sendMessage(ChatColor.RED + "You cannot change classes while in-game.");
				} else if ((lines[0].equalsIgnoreCase("§9zombie class")) && (lines[1].equalsIgnoreCase("Leave"))) {
					Classes.removeUser(player);
					player.sendMessage(ChatColor.GRAY + "You have left your class.");
				}
			}
		} else if ((e.getAction() == Action.LEFT_CLICK_BLOCK) && (player.hasPermission("zs.signs"))) {
			Block block = e.getClickedBlock();
			if ((block.getState() instanceof Sign)) {
				Sign sign = (Sign) block.getState();
				String[] lines = sign.getLines();
				if (lines[0].equalsIgnoreCase("§dzombie stats")) {
					player.sendMessage(ChatColor.GRAY + "Right click this sign to join a game!");
					if (Games.exists(lines[1])) {
						player.sendMessage(ChatColor.GREEN + "Players: " + ChatColor.DARK_RED
								+ Integer.toString(PlayerMethods.numberInMap(lines[1])) + ChatColor.GRAY + "/" + ChatColor.DARK_GREEN
								+ Integer.toString(Games.getMaxPlayers(lines[1])));
						player.sendMessage(ChatColor.GREEN + "Players Alive: " + ChatColor.DARK_RED
								+ Integer.toString(Games.getPcount(lines[1])) + ChatColor.GRAY + "/" + ChatColor.DARK_GREEN
								+ Integer.toString(PlayerMethods.numberInMap(lines[1])));
						player.sendMessage(ChatColor.GREEN + "Wave: " + ChatColor.DARK_RED + Integer.toString(Games.getWave(lines[1])));
						player.sendMessage(ChatColor.GREEN + "Zombies: " + ChatColor.DARK_RED + Integer.toString(Games.getZcount(lines[1])));
						player.sendMessage(ChatColor.GREEN + "Zombies Remaining: " + ChatColor.DARK_RED
								+ Integer.toString(Games.getZcount(lines[1]) - Games.getZslayed(lines[1])));
						player.sendMessage(ChatColor.GREEN + "Wave Max Zombies: " + ChatColor.DARK_RED
								+ Integer.toString(Games.getMaxZombies(lines[1])));
					}
				}
			}
		}
	}

	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		String[] lines = e.getLines();
		if ((lines[0].equalsIgnoreCase("zombie")) && (!lines[1].isEmpty())) {
			Player player = e.getPlayer();
			e.setLine(0, "§9zombie");
			if (player.hasPermission("zs.edit"))
				player.sendMessage(ChatColor.GREEN + "You have created a new ZombieSurvival sign!");
			else
				e.setLine(0, "§4MUST BE OP");
		} else if (lines[0].equalsIgnoreCase("zombie")) {
			e.setLine(0, "§4BAD SIGN");
		}
		if ((lines[0].equalsIgnoreCase("zombie box")) && (!lines[1].isEmpty())) {
			Player player = e.getPlayer();
			e.setLine(0, "§9zombie box");
			if (player.hasPermission("zs.edit"))
				player.sendMessage(ChatColor.GREEN + "You have created a new ZombieSurvival sign!");
			else
				e.setLine(0, "§4MUST BE OP");
		} else if (lines[0].equalsIgnoreCase("zombie box")) {
			e.setLine(0, "§4BAD SIGN");
		}
		if ((lines[0].equalsIgnoreCase("zombie class")) && (!lines[1].isEmpty())) {
			Player player = e.getPlayer();
			e.setLine(0, "§9zombie class");
			if (player.hasPermission("zs.edit"))
				player.sendMessage(ChatColor.GREEN + "You have created a new ZombieSurvival sign!");
			else
				e.setLine(0, "§4MUST BE OP");
		} else if (lines[0].equalsIgnoreCase("zombie box")) {
			e.setLine(0, "§4BAD SIGN");
		}
	}
}