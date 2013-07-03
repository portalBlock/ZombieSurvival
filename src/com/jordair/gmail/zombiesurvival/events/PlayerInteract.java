package com.jordair.gmail.zombiesurvival.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.jordair.gmail.zombiesurvival.Door;
import com.jordair.gmail.zombiesurvival.Games;
import com.jordair.gmail.zombiesurvival.PlayerMethods;
import com.jordair.gmail.zombiesurvival.Spawn;
import com.jordair.gmail.zombiesurvival.ZombieSurvivalPlugin;

public class PlayerInteract implements Listener {

	@EventHandler
	public void onOpAction(PlayerInteractEvent event) {
		int OPadd = -1;
		int OPremove = -1;
		Block block = event.getClickedBlock();
		Player player = event.getPlayer();
		if ((player.isOp()) || (player.hasPermission("zs.edit"))) {
			if (ZombieSurvivalPlugin.getInstance().getAdd().containsKey(player.getName())) {
				OPadd = ((Integer) ZombieSurvivalPlugin.getInstance().getAdd().get(player.getName())).intValue();
			}
			if (ZombieSurvivalPlugin.getInstance().getRemove().containsKey(player.getName())) {
				OPremove = ((Integer) ZombieSurvivalPlugin.getInstance().getRemove().get(player.getName())).intValue();
			}
			switch (OPadd) {
			case 0:
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					ZombieSurvivalPlugin.getInstance().addLobby(block.getLocation(),
							(String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(25));
				} else if ((ZombieSurvivalPlugin.getInstance().getEasycreate().get(player.getName()) != null)
						&& (((Integer) ZombieSurvivalPlugin.getInstance().getEasycreate().get(player.getName())).intValue() == 4)) {
					ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(43));
					player.performCommand("zsa-spawn " + ZombieSurvivalPlugin.getInstance().getCommandMap() + " 1");
				} else {
					ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(41));
				}
				event.setCancelled(true);
				break;
			case 1:
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					ZombieSurvivalPlugin.getInstance().addSpec(block.getLocation(),
							(String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(26));
				} else {
					ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(41));
				}
				event.setCancelled(true);
				break;
			case 2:
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					ZombieSurvivalPlugin.getInstance().addLight(block.getLocation(),
							(String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(27));
				} else {
					ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(41));
				}
				event.setCancelled(true);
				break;
			case 3:
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					ZombieSurvivalPlugin.getInstance().addSpawn(block,
							((Integer) ZombieSurvivalPlugin.getInstance().getCommandwave().get(player.getName())).intValue(),
							(String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(28));
				} else if ((ZombieSurvivalPlugin.getInstance().getEasycreate().get(player.getName()) != null)
						&& (((Integer) ZombieSurvivalPlugin.getInstance().getEasycreate().get(player.getName())).intValue() == 4)) {
					ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(44));
					ZombieSurvivalPlugin.getInstance().getEasycreate().remove(player.getName());
				} else {
					ZombieSurvivalPlugin.getInstance().getSpawn()
							.hideSpawns((String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(41));
				}
				event.setCancelled(true);
				break;
			case 4:
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					ZombieSurvivalPlugin.getInstance().addFire(block.getLocation(),
							(String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(29));
				} else {
					ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(41));
				}
				event.setCancelled(true);
				break;
			case 5:
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					if (ZombieSurvivalPlugin.getInstance().addDoor(block,
							((Integer) ZombieSurvivalPlugin.getInstance().getCommandwave().get(player.getName())).intValue(),
							(String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()))) {
						player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(30));
					}
					// if (block.getTypeId() == 64 || block.getTypeId() == 71) {
					// smartDoors(block, (String)
					// ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()),
					// ((Integer)
					// commandwave.get(player.getName())).intValue());
					// }
				} else {
					ZombieSurvivalPlugin.getInstance().getDoor()
							.hideDoors((String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(41));
				}
				event.setCancelled(true);
				break;
			case 6:
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					if (block.getTypeId() == 35) {
						ZombieSurvivalPlugin.getInstance().addSpecial(block.getLocation(),
								(String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
						player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(31));
					} else {
						player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(42));
					}
				} else {
					ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(41));
				}
				event.setCancelled(true);
				break;
			case 7:
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					ZombieSurvivalPlugin.getInstance().addDeath(block.getLocation(),
							(String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(32));
				} else {
					ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(41));
				}
				event.setCancelled(true);
				break;
			case 8:
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					ZombieSurvivalPlugin.getInstance().addBar(block,
							(String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(45));
				} else {
					ZombieSurvivalPlugin.getInstance().getBar()
							.hideBarricades((String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(41));
				}
				event.setCancelled(true);
				break;
			case 9:
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					ZombieSurvivalPlugin.getInstance().addfPerk(block.getLocation(),
							(String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(46));
				} else {
					ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(41));
				}
				event.setCancelled(true);
				break;
			case 10:
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					if (ZombieSurvivalPlugin.getInstance().getPlayerdoorlink().get(player.getName()) == null) {
						Door d = ZombieSurvivalPlugin.getInstance().getDoor().findDoor(block.getX(), block.getY(), block.getZ());
						if ((d != null)
								&& (d.getMap().matches((String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName())))) {
							ZombieSurvivalPlugin.getInstance().getPlayerdoorlink().put(player.getName(), d);
							ZombieSurvivalPlugin.getInstance().getDoor()
									.hideDoors((String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
							ZombieSurvivalPlugin.getInstance().getSpawn()
									.showSpawns((String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
						} else {
							player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(71));
							break;
						}
					} else {
						Spawn s = ZombieSurvivalPlugin.getInstance().getSpawn()
								.findSpawn(block.getX() + 0.5D, block.getY(), block.getZ() + 0.5D);
						if ((s != null)
								&& (s.map.matches((String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName())))) {
							Door d = (Door) ZombieSurvivalPlugin.getInstance().getPlayerdoorlink().get(player.getName());
							d.addSpawn(s);
							ZombieSurvivalPlugin.getInstance().getSpawn().hideSpawn(s);
							player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(72));
						} else {
							player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(73));
							break;
						}
					}
				} else {
					ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
					ZombieSurvivalPlugin.getInstance().getPlayerdoorlink().remove(player.getName());
					ZombieSurvivalPlugin.getInstance().getDoor()
							.hideDoors((String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					ZombieSurvivalPlugin.getInstance().getSpawn()
							.hideSpawns((String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(41));
				}
				event.setCancelled(true);
				break;
			}
			switch (OPremove) {
			case 0:
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					ZombieSurvivalPlugin.getInstance().removeLobby(block.getLocation(),
							(String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(33));
				} else {
					ZombieSurvivalPlugin.getInstance().getRemove().remove(player.getName());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(41));
				}
				event.setCancelled(true);
				break;
			case 1:
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					ZombieSurvivalPlugin.getInstance().removeSpec(block.getLocation(),
							(String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(34));
				} else {
					ZombieSurvivalPlugin.getInstance().getRemove().remove(player.getName());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(41));
				}
				event.setCancelled(true);
				break;
			case 2:
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					ZombieSurvivalPlugin.getInstance().removeLight(block.getLocation(),
							(String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(35));
				} else {
					ZombieSurvivalPlugin.getInstance().getRemove().remove(player.getName());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(41));
				}
				event.setCancelled(true);
				break;
			case 3:
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					ZombieSurvivalPlugin.getInstance().removeSpawn(block,
							(String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(36));
				} else {
					ZombieSurvivalPlugin.getInstance().getSpawn()
							.hideSpawns((String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					ZombieSurvivalPlugin.getInstance().getRemove().remove(player.getName());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(41));
				}
				event.setCancelled(true);
				break;
			case 4:
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					ZombieSurvivalPlugin.getInstance().removeFire(block.getLocation(),
							(String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(37));
				} else {
					ZombieSurvivalPlugin.getInstance().getRemove().remove(player.getName());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(41));
				}
				event.setCancelled(true);
				break;
			case 5:
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					ZombieSurvivalPlugin.getInstance().removeDoor(block.getLocation());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(38));
				} else {
					ZombieSurvivalPlugin.getInstance().getDoor()
							.hideDoors((String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					ZombieSurvivalPlugin.getInstance().getRemove().remove(player.getName());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(41));
				}
				event.setCancelled(true);
				break;
			case 6:
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					if (block.getTypeId() == 35) {
						ZombieSurvivalPlugin.getInstance().removeSpecial(block.getLocation(),
								(String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
						player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(39));
					} else {
						player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(42));
					}
				} else {
					ZombieSurvivalPlugin.getInstance().getRemove().remove(player.getName());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(41));
				}
				event.setCancelled(true);
				break;
			case 7:
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					ZombieSurvivalPlugin.getInstance().removeDeath(block.getLocation(),
							(String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(40));
				} else {
					ZombieSurvivalPlugin.getInstance().getAdd().remove(player.getName());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(41));
				}
				event.setCancelled(true);
				break;
			case 8:
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					ZombieSurvivalPlugin.getInstance().removeBar(block,
							(String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(48));
				} else {
					ZombieSurvivalPlugin.getInstance().getBar()
							.hideBarricades((String) ZombieSurvivalPlugin.getInstance().getCommandMap().get(player.getName()));
					ZombieSurvivalPlugin.getInstance().getRemove().remove(player.getName());
					player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(41));
				}
				event.setCancelled(true);
				break;
			case 9:
			case 10:
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) && (player.hasPermission("zs.signs"))) {
			Block block = event.getClickedBlock();
			if ((block.getState() instanceof Sign)) {
				Sign sign = (Sign) block.getState();
				String[] lines = sign.getLines();
				if ((PlayerMethods.inGame(player)) && (Games.getState(PlayerMethods.playerGame(player)) > 1)) {
					if (lines.length < 1) { return; }
					if ((lines[0].equalsIgnoreCase("§9zombie")) && (!lines[1].contains("heal")) && (!lines[1].contains("leave"))
							&& (!lines[1].contains("Repair"))) {
						String[] temp = lines[3].split(":");
						String[] temp2 = lines[2].split("-");
						int cost = 0;
						int item = 0;
						int amount = 1;
						short damage = 0;
						try {
							amount = Integer.parseInt(temp2[0]);
							cost = Integer.parseInt(temp2[1]);
							damage = Short.parseShort(temp[1]);
							item = Integer.parseInt(temp[0]);
						} catch (Exception e) {
							player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(48));
						}
						
						if ((!ZombieSurvivalPlugin.getInstance().isPoints())
								&& (ZombieSurvivalPlugin.getEcon().getBalance(player.getName()) >= cost)) {
							player.getInventory().addItem(new ItemStack[] { new ItemStack(item, amount, damage) });
							player.updateInventory();
							
							ZombieSurvivalPlugin.getEcon().withdrawPlayer(player.getName(), cost);
							player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(52)
									+ Integer.toString(cost) + ChatColor.DARK_RED + " dollars!");
						} else if (com.jordair.gmail.zombiesurvival.Stats.getSesPoints(player.getName()) >= cost) {
							player.getInventory().addItem(new ItemStack[] { new ItemStack(item, amount, damage) });
							player.updateInventory();
							com.jordair.gmail.zombiesurvival.Stats.addPoints(player.getName(), -cost);
							String name = player.getName();
							player.setDisplayName("[" + Double.toString(com.jordair.gmail.zombiesurvival.Stats.getSesPoints(player.getName()))
									+ "]" + name);
							player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(52)
									+ Integer.toString(cost) + ChatColor.DARK_RED + " points!");
						} else {
							player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(49));
						}
					}
					if ((lines[0].equalsIgnoreCase("§9zombie")) && (lines[1].contains("heal"))) {
						int cost2heal = 50;
						try {
							cost2heal = Integer.parseInt(lines[2]);
						} catch (Exception e) {
							player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(48));
						}
						if (player.getHealth() < 20 || player.getFoodLevel() < 20) {
							
							if ((!ZombieSurvivalPlugin.getInstance().isPoints())
									&& (ZombieSurvivalPlugin.getEcon().getBalance(player.getName()) >= cost2heal)) {
								
								ZombieSurvivalPlugin.getEcon().withdrawPlayer(player.getName(), cost2heal);
								player.setHealth(20);
								player.setFoodLevel(20);
								player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(53)
										+ Integer.toString(cost2heal) + ChatColor.DARK_RED + " dollars");
							} else if (com.jordair.gmail.zombiesurvival.Stats.getSesPoints(player.getName()) >= cost2heal) {
								player.setHealth(20);
								player.setFoodLevel(20);
								com.jordair.gmail.zombiesurvival.Stats.addPoints(player.getName(), -cost2heal);
								String name = player.getName();
								player.setDisplayName("["
										+ Double.toString(com.jordair.gmail.zombiesurvival.Stats.getSesPoints(player.getName())) + "]" + name);
								player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(53)
										+ Integer.toString(cost2heal) + ChatColor.DARK_RED + " points");
							} else {
								player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(49));
							}
						}
					}
					if ((lines[0].equalsIgnoreCase("§9zombie")) && (lines[1].contains("Repair"))) {
						if (ZombieSurvivalPlugin.getInstance().getBar().healBars(sign.getLocation(), PlayerMethods.playerGame(player))) {
							if (!ZombieSurvivalPlugin.getInstance().isPoints()) {
								
								ZombieSurvivalPlugin.getEcon().depositPlayer(player.getName(), 1.0D);
								player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(75));
							} else {
								com.jordair.gmail.zombiesurvival.Stats.addPoints(player.getName(), 1.0D);
								player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(50));
							}
						} else
							player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(74));
					}

					if ((lines[0].equalsIgnoreCase("§9zombie")) && (lines[1].contains("leave"))) {
						player.performCommand("bsapl");
					}
					if (lines[0].equalsIgnoreCase("§9zombie door")) {
						int cost2open = 100;
						try {
							cost2open = Integer.parseInt(lines[2]);
						} catch (Exception e) {
							player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(48));
						}
						
						if ((!ZombieSurvivalPlugin.getInstance().isPoints())
								&& (ZombieSurvivalPlugin.getEcon().getBalance(player.getName()) >= cost2open))
							try {
								if (ZombieSurvivalPlugin.getInstance().openDoors(sign.getLocation(), lines[1])) {
									
									ZombieSurvivalPlugin.getEcon().withdrawPlayer(player.getName(), cost2open);
									player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(51)
											+ Integer.toString(cost2open) + ChatColor.DARK_RED + " dollars!");
								} else {
									player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(77));
								}
							} catch (Exception e) {
								e.printStackTrace();
								player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(48));
							}
						else if (com.jordair.gmail.zombiesurvival.Stats.getSesPoints(player.getName()) >= cost2open)
							try {
								if (ZombieSurvivalPlugin.getInstance().openDoors(sign.getLocation(), lines[1])) {
									com.jordair.gmail.zombiesurvival.Stats.addPoints(player.getName(), -cost2open);
									String name = player.getName();
									player.setDisplayName("["
											+ Double.toString(com.jordair.gmail.zombiesurvival.Stats.getSesPoints(player.getName())) + "]" + name);

									player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(51)
											+ Integer.toString(cost2open) + ChatColor.DARK_RED + " points!");
								} else {
									player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(77));
								}
							} catch (Exception e) {
								e.printStackTrace();
								player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(48));
							}
						else {
							player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(49));
						}
					}
				}

				if ((lines[0].equalsIgnoreCase("§dzombie stats")) && (!lines[1].contains("zombies"))) {
					if (ZombieSurvivalPlugin.getInstance().getJustleftgame().containsKey(player.getName())) {
						player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(62)
								+ Integer.toString(ZombieSurvivalPlugin.getInstance().getLeavetimer()
										- ((Integer) ZombieSurvivalPlugin.getInstance().getJustleftgame().get(player.getName())).intValue())
								+ (String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(63));
						return;
					}
					try {
						String joinmap = lines[1];
						
						if ((PlayerMethods.numberInMap(joinmap) < Games.getMaxPlayers(joinmap)) && (!PlayerMethods.inGame(player))
								&& (!ZombieSurvivalPlugin.getDead().containsKey(player.getName()))) {
							if (ZombieSurvivalPlugin.getInstance().isJm()) {
								Bukkit.broadcastMessage(ChatColor.GOLD + "[ZombieSurvival] " + ChatColor.GREEN + player.getName()
										+ " just joined " + joinmap + ".");
							}
							Games.setPlayerMap(player.getName(), joinmap);
							Games.setPcount(joinmap, Games.getPcount(joinmap) + 1);
							com.jordair.gmail.zombiesurvival.Stats.setPoints(player.getName(), 0.0D);
							com.jordair.gmail.zombiesurvival.Stats.setKills(player.getName(), 0.0D);
							ZombieSurvivalPlugin.getInstance().getPlayerloconjoin().put(player.getName(), player.getLocation());
							if (ZombieSurvivalPlugin.getInstance().isInvsave()) {
								ZombieSurvivalPlugin.getInstance().getInv().put(player.getName(), player.getInventory().getContents());
								ZombieSurvivalPlugin.getInstance().getInvarmor()
										.put(player.getName(), player.getInventory().getArmorContents());
							}
							player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(54));
							if (Games.getState(joinmap) < 2)
								ZombieSurvivalPlugin.getInstance().StartGames(joinmap, false);
							else if (Games.getState(joinmap) > 1)
								ZombieSurvivalPlugin.getInstance().placeInGame(player, joinmap, false);
						}
					} catch (Exception e) {
						player.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(48));
					}
				}
			}
			if ((block.getState() instanceof Chest)) {
				Chest chest = (Chest) block.getState();
				if ((PlayerMethods.inGame(player)) && (!ZombieSurvivalPlugin.getInstance().mysteryBox(block.getLocation(), chest, player)))
					event.setCancelled(true);
			}
		}
	}
}
