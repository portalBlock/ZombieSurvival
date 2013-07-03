package com.jordair.gmail.zombiesurvival.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.material.Wool;

import com.jordair.gmail.zombiesurvival.Games;
import com.jordair.gmail.zombiesurvival.PlayerMethods;
import com.jordair.gmail.zombiesurvival.Utilities;
import com.jordair.gmail.zombiesurvival.ZombieSurvivalPlugin;

public class PlayerMove implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerMove(PlayerMoveEvent event) {
		List<Location> spblocks = new ArrayList<Location>();
		Player player = event.getPlayer();
		if ((PlayerMethods.inGame(player)) && (Games.getState(PlayerMethods.playerGame(player)) > 1)) {
			Location location = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() - 1.0D, player
					.getLocation().getZ());
			Block block = location.getBlock();
			for (Location str : ZombieSurvivalPlugin.getInstance().getSpecialblocks().keySet()) {
				if (((String) ZombieSurvivalPlugin.getInstance().getSpecialblocks().get(str)).equalsIgnoreCase(PlayerMethods
						.playerGame(player))) {
					spblocks.add(str);
				}
			}
			if ((block.getTypeId() == 35) && (spblocks.contains(block.getLocation()))) {
				Wool wool = new Wool(block.getType(), block.getData());
				if (wool.getColor() == DyeColor.YELLOW) {
					player.setVelocity(player.getLocation().getDirection().multiply(2));
				}
				if ((wool.getColor() == DyeColor.BLACK) && (!player.isDead())) {
					player.setHealth(0);
				}
				if (wool.getColor() == DyeColor.GREEN) {
					player.setVelocity(player.getLocation().getDirection().multiply(0.5D).setY(0));
				}
				if (wool.getColor() == DyeColor.BLUE) {
					player.setVelocity(player.getLocation().getDirection().setY(1));
				}
				if ((wool.getColor() == DyeColor.ORANGE) && (player.getFireTicks() == 0)) {
					player.setFireTicks(200);
				}
				if (wool.getColor() == DyeColor.LIGHT_BLUE) {
					player.setVelocity(player.getLocation().getDirection().setY(2));
				}
				if (wool.getColor() == DyeColor.SILVER) {
					Utilities.hidePlayer(player);
				}
				if (wool.getColor() == DyeColor.GRAY) {
					Utilities.unhidePlayer(player);
				}
			}
		}

		spblocks.clear();
	}
}
