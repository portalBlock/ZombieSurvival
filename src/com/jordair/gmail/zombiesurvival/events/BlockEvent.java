package com.jordair.gmail.zombiesurvival.events;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.jordair.gmail.zombiesurvival.Games;
import com.jordair.gmail.zombiesurvival.PlayerMethods;
import com.jordair.gmail.zombiesurvival.ZombieSurvivalPlugin;

public class BlockEvent implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		Player player = event.getPlayer();
		if (PlayerMethods.inGame(player)) {
			String map = PlayerMethods.playerGame(player);
			if (Games.getState(map) > 1) {
				if ((ZombieSurvivalPlugin.getInstance().getBlockbreak().contains(block.getTypeId()))
						&& (!((Map<Block, BlockState>) ZombieSurvivalPlugin.getInstance().getChangedBlocks().get(map)).containsKey(block))) {
					((Map<Block, BlockState>) ZombieSurvivalPlugin.getInstance().getChangedBlocks().get(map)).put(block, block.getState());
				} else {
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED + "Not allowed!");
				}
			}
			if ((Games.getState(map) == 1) && (ZombieSurvivalPlugin.getInstance().isAntigrief()) && (!player.isOp())) {
				event.setCancelled(true);
			}
		}
		if ((ZombieSurvivalPlugin.getInstance().getSigns().contains(block.getLocation())) && (!PlayerMethods.inGame(player))
				&& (!event.isCancelled()) && (player.hasPermission("zs.edit"))) {
			ZombieSurvivalPlugin.getInstance().getSigns().remove(block.getLocation());
			ZombieSurvivalPlugin.getInstance().saveSigns();
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Block block = event.getBlock();
		Player player = event.getPlayer();
		if (PlayerMethods.inGame(player)) {
			String map = PlayerMethods.playerGame(player);
			if (Games.getState(map) > 1) {
				if (ZombieSurvivalPlugin.getInstance().getBlockplace().contains(block.getTypeId())) {
					((Map<Block, BlockState>) ZombieSurvivalPlugin.getInstance().getPlacedBlocks().get(map)).put(block, block.getState());
				} else {
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED + "Not allowed!");
				}
			}
			if ((Games.getState(map) == 1) && (ZombieSurvivalPlugin.getInstance().isAntigrief()) && (!player.isOp()))
				event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent event) {
		Block block = event.getBlock();
		Player player = event.getPlayer();
		if (PlayerMethods.inGame(player)) {
			String map = PlayerMethods.playerGame(player);
			if (Games.getState(map) > 1) {
				if ((ZombieSurvivalPlugin.getInstance().getBlockbreak().contains(block.getTypeId()))
						&& (!((Map<Block, BlockState>) ZombieSurvivalPlugin.getInstance().getChangedBlocks().get(map)).containsKey(block)))
					((Map<Block, BlockState>) ZombieSurvivalPlugin.getInstance().getChangedBlocks().get(map)).put(block, block.getState());
				else {
					event.setCancelled(true);
				}
			}
			if ((Games.getState(map) == 1) && (ZombieSurvivalPlugin.getInstance().isAntigrief()) && (!player.isOp()))
				event.setCancelled(true);
		}
	}
}
