package com.jordair.gmail.zombiesurvival.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.jordair.gmail.zombiesurvival.Utilities;
import com.jordair.gmail.zombiesurvival.ZombieSurvivalPlugin;

public class AsyncChat implements Listener {

	@EventHandler
	public void CommandListener(PlayerCommandPreprocessEvent e) {
		String message = e.getMessage().substring(1);
		String[] command = message.split(" ");
		if (ZombieSurvivalPlugin.getInstance().getJoincommand().contains(command[0])) {
			if (command.length > 1)
				e.getPlayer().performCommand("bsapj " + command[1]);
			else {
				e.getPlayer().performCommand("bsapj ");
			}
			e.setCancelled(true);
		} else if (ZombieSurvivalPlugin.getInstance().getLeavecommand().contains(command[0])) {
			e.getPlayer().performCommand("bsapl");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		String p = e.getPlayer().getName();
		String m = e.getMessage();
		Player pp = e.getPlayer();
		if (ZombieSurvivalPlugin.getInstance().getEasycreate().containsKey(p)) {
			int step = ((Integer) ZombieSurvivalPlugin.getInstance().getEasycreate().get(p)).intValue();
			switch (step) {
			case 0:
				ZombieSurvivalPlugin.getInstance().getEcname().put(p, m);
				pp.sendMessage(ChatColor.GREEN + "Please type the max players for "
						+ (String) ZombieSurvivalPlugin.getInstance().getEcname().get(p));
				ZombieSurvivalPlugin.getInstance().getEasycreate().put(p, 1);
				e.setCancelled(true);
				break;
			case 1:
				try {
					ZombieSurvivalPlugin.getInstance().getEcpcount().put(p, Integer.parseInt(m));
					ZombieSurvivalPlugin.getInstance().getEczcount().put(p, Double.valueOf(Utilities.calcMaxZ(Integer.parseInt(m))));
					pp.sendMessage(ChatColor.GREEN + "Please type the max waves for "
							+ (String) ZombieSurvivalPlugin.getInstance().getEcname().get(p));
					ZombieSurvivalPlugin.getInstance().getEasycreate().put(p, 2);
				} catch (Exception ec) {
					pp.sendMessage(ChatColor.RED + "Please enter a valid number.");
				}
				e.setCancelled(true);
				break;
			case 2:
				try {
					ZombieSurvivalPlugin.getInstance().getEcwcount().put(p, Integer.parseInt(m));
					ZombieSurvivalPlugin.getInstance().getEasycreate().put(p, 3);
					pp.sendMessage(ChatColor.GOLD + "Creating game for " + (String) ZombieSurvivalPlugin.getInstance().getEcname().get(p)
							+ "!" + ChatColor.DARK_RED + " Is this correct?" + ChatColor.GOLD + "\nPlayers: "
							+ Integer.toString(((Integer) ZombieSurvivalPlugin.getInstance().getEcpcount().get(p)).intValue())
							+ "\nWaves: "
							+ Integer.toString(((Integer) ZombieSurvivalPlugin.getInstance().getEcwcount().get(p)).intValue()));
					pp.sendMessage(ChatColor.GREEN + "Please enter y (yes) or n (no)");
				} catch (Exception ex) {
					pp.sendMessage(ChatColor.RED + "Please enter a valid number.");
				}
				e.setCancelled(true);
				break;
			case 3:
				if (m.contains("y")) {
					pp.performCommand("zs-create " + (String) ZombieSurvivalPlugin.getInstance().getEcname().get(p) + " "
							+ Double.toString(((Double) ZombieSurvivalPlugin.getInstance().getEczcount().get(p)).doubleValue()) + " "
							+ Integer.toString(((Integer) ZombieSurvivalPlugin.getInstance().getEcpcount().get(p)).intValue()) + " "
							+ Integer.toString(((Integer) ZombieSurvivalPlugin.getInstance().getEcwcount().get(p)).intValue()));
					ZombieSurvivalPlugin.getInstance().getEasycreate().put(p, 4);
				} else {
					pp.sendMessage(ChatColor.GREEN + "Aborting.");
					ZombieSurvivalPlugin.getInstance().getEasycreate().remove(p);
					ZombieSurvivalPlugin.getInstance().getEcpcount().remove(p);
					ZombieSurvivalPlugin.getInstance().getEczcount().remove(p);
					ZombieSurvivalPlugin.getInstance().getEcwcount().remove(p);
					ZombieSurvivalPlugin.getInstance().getEcname().remove(p);
				}
				e.setCancelled(true);
			}
		}
	}
}
