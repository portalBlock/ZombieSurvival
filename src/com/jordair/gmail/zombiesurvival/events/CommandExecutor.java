package com.jordair.gmail.zombiesurvival.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UnknownFormatConversionException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.jordair.gmail.zombiesurvival.Games;
import com.jordair.gmail.zombiesurvival.PlayerMethods;
import com.jordair.gmail.zombiesurvival.Spectate;
import com.jordair.gmail.zombiesurvival.Utilities;
import com.jordair.gmail.zombiesurvival.ZombieSurvivalPlugin;
import com.jordair.gmail.zombiesurvival.api.GameFinishEvent;
import com.jordair.gmail.zombiesurvival.api.GameFinishReason;
import com.jordair.gmail.zombiesurvival.api.PlayerAddEvent;
import com.jordair.gmail.zombiesurvival.api.PlayerRemoveEvent;
import com.jordair.gmail.zombiesurvival.api.RemoveReason;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("zsdebug")) {
			if ((sender instanceof Player)) {
				Player player = (Player) sender;
				ZombieSurvivalPlugin.getInstance().zsDebug(1, player);
				return true;
			}
			ZombieSurvivalPlugin.getInstance().zsDebug(0, null);
			return true;
		}

		if ((sender instanceof Player)) {
			Player player = (Player) sender;

			if (cmd.getName().equalsIgnoreCase("zsa-spawn")) {
				if (args.length != 2) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				ZombieSurvivalPlugin.getInstance().getAdd().put(player.getName(), 3);
				ZombieSurvivalPlugin.getInstance().getCommandMap().put(player.getName(), args[0]);
				ZombieSurvivalPlugin.getInstance().getCommandwave().put(player.getName(), Integer.parseInt(args[1]));
				ZombieSurvivalPlugin.getInstance().getRemove().remove(player.getName());
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(10));
				ZombieSurvivalPlugin.getInstance().getSpawn().showSpawns(args[0]);
			} else if (cmd.getName().equalsIgnoreCase("zsr-spawn")) {
				if (args.length != 1) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				ZombieSurvivalPlugin.getInstance().getCommandMap().put(player.getName(), args[0]);
				ZombieSurvivalPlugin.getInstance().getRemove().put(player.getName(), 3);
				ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(10));
				ZombieSurvivalPlugin.getInstance().getSpawn().showSpawns(args[0]);
			} else if (cmd.getName().equalsIgnoreCase("zs-start")) {
				if (args.length != 1) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				Bukkit.broadcastMessage(ChatColor.AQUA + "An admin has started the game!");
				ZombieSurvivalPlugin.getInstance().StartGames(args[0], true);
			} else if (cmd.getName().equalsIgnoreCase("zs-end")) {
				if (args.length != 1) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				ZombieSurvivalPlugin.getInstance().GamesOver(args[0], true);
				GameFinishEvent gfe = new GameFinishEvent(args[0], GameFinishReason.FORCE_END, null);
				Bukkit.getServer().getPluginManager().callEvent(gfe);
				player.sendMessage(ChatColor.GOLD + "You have ended the games early!");
			} else if (cmd.getName().equalsIgnoreCase("zs-create")) {
				if (args.length != 4)
					return false;
				try {
					ZombieSurvivalPlugin.getInstance().getCommandMap().put(player.getName(), args[0]);
					ZombieSurvivalPlugin.getInstance().getAdd().put(player.getName(), 0);
					ZombieSurvivalPlugin.getInstance().getRemove().remove(player.getName());
					ZombieSurvivalPlugin.getInstance().getCustomConfig()
							.set(player.getWorld().getName() + "." + args[0] + ".maxzombies", (int) (Double.parseDouble(args[1]) * 100.0D));
					ZombieSurvivalPlugin.getInstance().getCustomConfig()
							.set(player.getWorld().getName() + "." + args[0] + ".maxplayers", Integer.parseInt(args[2]));
					ZombieSurvivalPlugin.getInstance().getCustomConfig()
							.set(player.getWorld().getName() + "." + args[0] + ".maxwaves", Integer.parseInt(args[3]));
					Games.setMaxZombies(args[0], (int) (Double.parseDouble(args[1]) * 100.0D));
					Games.setMaxPlayers(args[0], Integer.parseInt(args[2]));
					Games.setMaxWaves(args[0], Integer.parseInt(args[3]));
					Games.setPcount(args[0], 0);
					Games.setState(args[0], 1);
					ZombieSurvivalPlugin.getMaps().put(args[0], player.getWorld().getName());
					ZombieSurvivalPlugin.getInstance().getPerk().setPerk(args[0], 0);
					ZombieSurvivalPlugin.getInstance().getPerkcount().put(args[0], 0);
					ZombieSurvivalPlugin.getInstance().getPerkend().put(args[0], false);
					Games.setWave(args[0], 0);
					ZombieSurvivalPlugin.getInstance().getChangedBlocks().put(args[0], new HashMap<Block, BlockState>());
					ZombieSurvivalPlugin.getInstance().getPlacedBlocks().put(args[0], new HashMap<Block, BlockState>());
					List<String> gamemaps = new ArrayList<String>();
					for (String s : ZombieSurvivalPlugin.getMaps().keySet()) {
						if (((String) ZombieSurvivalPlugin.getMaps().get(s)).equalsIgnoreCase(player.getWorld().getName())) {
							gamemaps.add(s);
						}
					}
					ZombieSurvivalPlugin.getInstance().getCustomConfig()
							.set((String) ZombieSurvivalPlugin.getMaps().get(args[0]) + ".maps", gamemaps);
					ZombieSurvivalPlugin.getInstance().saveCustomConfig();
				} catch (UnknownFormatConversionException ex) {
					player.sendMessage(ChatColor.RED + "Try again, illegal arguments!");
					return false;
				}
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(12));
			} else if (cmd.getName().equalsIgnoreCase("zs-remove")) {
				if (args.length != 1) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				ZombieSurvivalPlugin.getInstance().getCustomConfig()
						.set((String) ZombieSurvivalPlugin.getMaps().get(args[0]) + ".maps", ZombieSurvivalPlugin.getMaps());
				ZombieSurvivalPlugin.getInstance().getCustomConfig()
						.set((String) ZombieSurvivalPlugin.getMaps().get(args[0]) + "." + args[0], null);
				ZombieSurvivalPlugin.getInstance().removeBars(ZombieSurvivalPlugin.getInstance().getBar().getAll(), args[0]);
				ZombieSurvivalPlugin.getInstance().removeDoors(ZombieSurvivalPlugin.getInstance().getDoor().getAll());
				ZombieSurvivalPlugin.getMaps().remove(args[0]);
				ZombieSurvivalPlugin.getInstance().saveCustomConfig();
				ZombieSurvivalPlugin.getInstance().reload();
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(59) + args[0]);
			} else if (cmd.getName().equalsIgnoreCase("zsa-fire")) {
				if (args.length != 1) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				ZombieSurvivalPlugin.getInstance().getCommandMap().put(player.getName(), args[0]);
				ZombieSurvivalPlugin.getInstance().getAdd().put(player.getName(), 4);
				ZombieSurvivalPlugin.getInstance().getRemove().remove(player.getName());
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(13));
			} else if (cmd.getName().equalsIgnoreCase("zsr-fire")) {
				if (args.length != 1) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				ZombieSurvivalPlugin.getInstance().getCommandMap().put(player.getName(), args[0]);
				ZombieSurvivalPlugin.getInstance().getRemove().put(player.getName(), 4);
				ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(14));
			} else if (cmd.getName().equalsIgnoreCase("zsa-spectate")) {
				if (args.length != 1) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				ZombieSurvivalPlugin.getInstance().getCommandMap().put(player.getName(), args[0]);
				ZombieSurvivalPlugin.getInstance().getAdd().put(player.getName(), 1);
				ZombieSurvivalPlugin.getInstance().getRemove().remove(player.getName());
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(15));
			} else if (cmd.getName().equalsIgnoreCase("zsr-spectate")) {
				if (args.length != 1) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				ZombieSurvivalPlugin.getInstance().getCommandMap().put(player.getName(), args[0]);
				ZombieSurvivalPlugin.getInstance().getRemove().put(player.getName(), 1);
				ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(16));
			} else if (cmd.getName().equalsIgnoreCase("zsa-waiting")) {
				if (args.length != 1) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				ZombieSurvivalPlugin.getInstance().getCommandMap().put(player.getName(), args[0]);
				ZombieSurvivalPlugin.getInstance().getAdd().put(player.getName(), 7);
				ZombieSurvivalPlugin.getInstance().getRemove().remove(player.getName());
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(17));
			} else if (cmd.getName().equalsIgnoreCase("zsr-waiting")) {
				if (args.length != 1) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				ZombieSurvivalPlugin.getInstance().getCommandMap().put(player.getName(), args[0]);
				ZombieSurvivalPlugin.getInstance().getRemove().put(player.getName(), 7);
				ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(18));
			} else if (cmd.getName().equalsIgnoreCase("zsa-lightning")) {
				if (args.length != 1) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				ZombieSurvivalPlugin.getInstance().getCommandMap().put(player.getName(), args[0]);
				ZombieSurvivalPlugin.getInstance().getAdd().put(player.getName(), 2);
				ZombieSurvivalPlugin.getInstance().getRemove().remove(player.getName());
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(19));
			} else if (cmd.getName().equalsIgnoreCase("zsr-lightning")) {
				if (args.length != 1) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				ZombieSurvivalPlugin.getInstance().getCommandMap().put(player.getName(), args[0]);
				ZombieSurvivalPlugin.getInstance().getRemove().put(player.getName(), 2);
				ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(20));
			} else if (cmd.getName().equalsIgnoreCase("zsa-door")) {
				if (args.length != 2) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				ZombieSurvivalPlugin.getInstance().getCommandMap().put(player.getName(), args[0]);
				ZombieSurvivalPlugin.getInstance().getAdd().put(player.getName(), 5);
				ZombieSurvivalPlugin.getInstance().getCommandwave().put(player.getName(), Integer.parseInt(args[1]));
				ZombieSurvivalPlugin.getInstance().getRemove().remove(player.getName());
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(21));
				ZombieSurvivalPlugin.getInstance().getDoor().showDoors(args[0]);
			} else if (cmd.getName().equalsIgnoreCase("zsr-door")) {
				if (args.length != 1) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				ZombieSurvivalPlugin.getInstance().getCommandMap().put(player.getName(), args[0]);
				ZombieSurvivalPlugin.getInstance().getRemove().put(player.getName(), 5);
				ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(22));
				ZombieSurvivalPlugin.getInstance().getDoor().showDoors(args[0]);
			} else if (cmd.getName().equalsIgnoreCase("zs-reload")) {
				ZombieSurvivalPlugin.getInstance().reload();
				player.sendMessage("ZombieSurvival was reloaded.");
			} else if (cmd.getName().equalsIgnoreCase("zsa-special")) {
				if (args.length != 1) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				ZombieSurvivalPlugin.getInstance().getCommandMap().put(player.getName(), args[0]);
				ZombieSurvivalPlugin.getInstance().getAdd().put(player.getName(), 6);
				ZombieSurvivalPlugin.getInstance().getRemove().remove(player.getName());
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(23));
			} else if (cmd.getName().equalsIgnoreCase("zsr-special")) {
				if (args.length != 1) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				ZombieSurvivalPlugin.getInstance().getCommandMap().put(player.getName(), args[0]);
				ZombieSurvivalPlugin.getInstance().getRemove().put(player.getName(), 6);
				ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(24));
			} else if (cmd.getName().equalsIgnoreCase("zsa-bar")) {
				if (args.length != 1) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				ZombieSurvivalPlugin.getInstance().getCommandMap().put(player.getName(), args[0]);
				ZombieSurvivalPlugin.getInstance().getAdd().put(player.getName(), 8);
				ZombieSurvivalPlugin.getInstance().getRemove().remove(player.getName());
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(55));
				ZombieSurvivalPlugin.getInstance().getBar().showBarricades(args[0]);
			} else if (cmd.getName().equalsIgnoreCase("zsr-bar")) {
				if (args.length != 1) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				ZombieSurvivalPlugin.getInstance().getCommandMap().put(player.getName(), args[0]);
				ZombieSurvivalPlugin.getInstance().getRemove().put(player.getName(), 8);
				ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(56));
				ZombieSurvivalPlugin.getInstance().getBar().showBarricades(args[0]);
			} else if (cmd.getName().equalsIgnoreCase("zsa-perk")) {
				if (args.length != 1) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				ZombieSurvivalPlugin.getInstance().getCommandMap().put(player.getName(), args[0]);
				ZombieSurvivalPlugin.getInstance().getAdd().put(player.getName(), 9);
				ZombieSurvivalPlugin.getInstance().getRemove().remove(player.getName());
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(57));
			} else if (cmd.getName().equalsIgnoreCase("zsr-perk")) {
				if (args.length != 1) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				ZombieSurvivalPlugin.getInstance().getCommandMap().put(player.getName(), args[0]);
				ZombieSurvivalPlugin.getInstance().removefPerk(args[0]);
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(58));
			} else if (cmd.getName().equalsIgnoreCase("zstp")) {
				if (args.length != 1) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				player.teleport((Location) ZombieSurvivalPlugin.getInstance().getSpawn().lobbies.get(args[0]));
			} else if (cmd.getName().equalsIgnoreCase("zs-c")) {
				ZombieSurvivalPlugin.getInstance().getEasycreate().put(player.getName(), 0);
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(60));
			} else if (cmd.getName().equalsIgnoreCase("zs-doorlink")) {
				if (args.length != 1) { return false; }
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				ZombieSurvivalPlugin.getInstance().getAdd().put(player.getName(), 10);
				ZombieSurvivalPlugin.getInstance().getCommandMap().put(player.getName(), args[0]);
				ZombieSurvivalPlugin.getInstance().getRemove().remove(player.getName());
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(70));
				ZombieSurvivalPlugin.getInstance().getDoor().showDoors(args[0]);
			}
			if (cmd.getName().equalsIgnoreCase("stats")) {
				if ((args.length < 1) && (PlayerMethods.inGame(player))) {
					ZombieSurvivalPlugin.getInstance().info(player, player);
				}

				if (args.length > 0) {
					Player p = Bukkit.getPlayer(args[0]);
					if ((p != null) && (PlayerMethods.inGame(p)))
						ZombieSurvivalPlugin.getInstance().info(p, player);
					else {
						player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(61));
					}
				}
				player.sendMessage("Number of players online: " + Integer.toString(ZombieSurvivalPlugin.getInstance().getOnlinep()));
			} else if (cmd.getName().equalsIgnoreCase("whisper")) {
				Player other = Bukkit.getServer().getPlayer(args[0]);
				if (other == null) { return false; }
				String send = " ";
				for (int i = 1; i < args.length; i++) {
					send = send + " " + args[i];
				}
				other.sendMessage("From: " + player.getName() + send);
				// player.sendMessage("Sent To: " + other.getName() +
				// " Message:" + send);
			} else if (cmd.getName().equalsIgnoreCase("zshelp")) {
				player.sendMessage("Commands: stats, whisper, join, leave, zshelp");
				String arg = "";
				if (args.length >= 1) {
					arg = " " + args[0];
				}
				if (player.isOp())
					player.performCommand("help ZombieSurvival" + arg);
			} else if (cmd.getName().equalsIgnoreCase("bsapj")) {
				if (args.length < 1) {
					String message = (String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(64)
							+ ZombieSurvivalPlugin.getMaps().keySet().toString();
					player.sendMessage(ChatColor.BLUE + message);
					return false;
				}
				if (!ZombieSurvivalPlugin.getMaps().containsKey(args[0])) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(8));
					return false;
				}
				if (ZombieSurvivalPlugin.getInstance().getJustleftgame().containsKey(player.getName())) {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(62)
							+ Integer.toString(ZombieSurvivalPlugin.getInstance().getLeavetimer()
									- ((Integer) ZombieSurvivalPlugin.getInstance().getJustleftgame().get(player.getName())).intValue())
							+ (String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(63));
					return true;
				}
				if ((PlayerMethods.numberInMap(args[0]) < Games.getMaxPlayers(args[0])) && (!PlayerMethods.inGame(player))
						&& (!ZombieSurvivalPlugin.getDead().containsKey(player.getName()))
						&& (com.jordair.gmail.zombiesurvival.Stats.getSesPoints(player.getName()) >= 0.0D)) {
					PlayerAddEvent pae = new PlayerAddEvent(args[0], player);
					Bukkit.getServer().getPluginManager().callEvent(pae);
					if (ZombieSurvivalPlugin.getInstance().isJm()) {
						Bukkit.broadcastMessage(ChatColor.GOLD + "[ZombieSurvival] " + ChatColor.GREEN + player.getName() + " just joined "
								+ args[0] + ".");
					}
					Games.setPlayerMap(player.getName(), args[0]);
					com.jordair.gmail.zombiesurvival.Stats.setPoints(player.getName(), 0.0D);
					com.jordair.gmail.zombiesurvival.Stats.setKills(player.getName(), 0.0D);
					Games.setPcount(args[0], Games.getPcount(args[0]) + 1);
					ZombieSurvivalPlugin.getInstance().getPlayerloconjoin().put(player.getName(), player.getLocation());
					if (ZombieSurvivalPlugin.getInstance().isInvsave()) {
						ZombieSurvivalPlugin.getInstance().getInv().put(player.getName(), player.getInventory().getContents());
						ZombieSurvivalPlugin.getInstance().getInvarmor().put(player.getName(), player.getInventory().getArmorContents());
					}
					if (Games.getState(args[0]) < 2)
						ZombieSurvivalPlugin.getInstance().StartGames(args[0], false);
					else if (Games.getState(args[0]) > 1) {
						ZombieSurvivalPlugin.getInstance().placeInGame(player, args[0], false);
					}
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(54));
				} else {
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(65));
				}
			} else if ((cmd.getName().equalsIgnoreCase("bsapl")) && (PlayerMethods.inGame(player))) {
				String map = PlayerMethods.playerGame(player);
				PlayerRemoveEvent pre = new PlayerRemoveEvent(map, player, RemoveReason.COMMAND);
				Bukkit.getServer().getPluginManager().callEvent(pre);
				ZombieSurvivalPlugin.getInstance().getJustleftgame().put(player.getName(), 0);
				if (!ZombieSurvivalPlugin.getDead().containsKey(player.getName())) {
					int tempcount = Games.getPcount(map);
					Games.setPcount(map, tempcount - 1);
				}
				if (ZombieSurvivalPlugin.getInstance().isInvsave()) {
					player.getInventory().clear();
					player.getInventory().setArmorContents(null);
				}
				player.teleport((Location) ZombieSurvivalPlugin.getInstance().getPlayerloconjoin().get(player.getName()));
				player.setGameMode(GameMode.SURVIVAL);
				Spectate.getSpectators().remove(player.getName());
				ZombieSurvivalPlugin.getInstance().getPlayerloconjoin().remove(player.getName());
				player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(66));
				Games.removePlayerMap(player.getName());
				com.jordair.gmail.zombiesurvival.Stats.setPoints(player.getName(), 0.0D);
				com.jordair.gmail.zombiesurvival.Stats.removeSplayerKills(player.getName());
				ZombieSurvivalPlugin.getDead().remove(player.getName());
				player.setAllowFlight(false);
				player.setFlying(false);
				Utilities.unhidePlayer(player);
				if ((ZombieSurvivalPlugin.getInstance().isEmptyaccount()) && (!ZombieSurvivalPlugin.getInstance().isPoints())) {
					double removem = ZombieSurvivalPlugin.getEcon().getBalance(player.getName());
					ZombieSurvivalPlugin.getEcon().withdrawPlayer(player.getName(), removem);
				}
				if ((ZombieSurvivalPlugin.getInstance().isInvsave())
						&& (ZombieSurvivalPlugin.getInstance().getInv().containsKey(player.getName()))) {
					player.getInventory().setContents((ItemStack[]) ZombieSurvivalPlugin.getInstance().getInv().get(player.getName()));
					ZombieSurvivalPlugin.getInstance().getInv().remove(player.getName());
				}
				if ((ZombieSurvivalPlugin.getInstance().isInvsave())
						&& (ZombieSurvivalPlugin.getInstance().getInvarmor().containsKey(player.getName()))) {
					player.getInventory().setArmorContents(
							(ItemStack[]) ZombieSurvivalPlugin.getInstance().getInvarmor().get(player.getName()));
					ZombieSurvivalPlugin.getInstance().getInvarmor().remove(player.getName());
				}
				ZombieSurvivalPlugin.getInstance().GamesOver(map, false, "left");
				String name = player.getName();
				player.setDisplayName(name);
			}
		} else {
			sender.sendMessage("Must be a player!");
			return false;
		}

		return true;
	}
}
