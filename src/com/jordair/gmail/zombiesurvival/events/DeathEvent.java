package com.jordair.gmail.zombiesurvival.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.jordair.gmail.zombiesurvival.Games;
import com.jordair.gmail.zombiesurvival.PlayerMethods;
import com.jordair.gmail.zombiesurvival.ZombieSurvivalPlugin;
import com.jordair.gmail.zombiesurvival.api.PlayerRemoveEvent;
import com.jordair.gmail.zombiesurvival.api.RemoveReason;

public class DeathEvent implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onZombieDeath(EntityDeathEvent event) {
		Entity ent = event.getEntity();
		if (ent == null) { return; }
		if ((!ZombieSurvivalPlugin.getInstance().getZombies().isEmpty())
				&& (ZombieSurvivalPlugin.getInstance().getZombies().get(ent.getEntityId()) != null)
				&& (Games.getState((String) ZombieSurvivalPlugin.getInstance().getZombies().get(ent.getEntityId())) > 1)) {
			String map = (String) ZombieSurvivalPlugin.getInstance().getZombies().get(ent.getEntityId());
			ZombieSurvivalPlugin.getInstance().PayPlayers(ent.getEntityId());
			EntityDamageEvent.DamageCause deathreason = null;
			if (ent.getLastDamageCause() != null) {
				deathreason = ent.getLastDamageCause().getCause();
			}
			if (!ZombieSurvivalPlugin.getInstance().getDrops().isEmpty()) {
				event.getDrops().clear();
				for (ItemStack item : ZombieSurvivalPlugin.getInstance().randomItem()) {
					event.getDrops().add(item);
				}
			}
			if (ZombieSurvivalPlugin.getInstance().getPerk().getPerk(map) == 4) {
				event.setDroppedExp(25);
			}
			if (ZombieSurvivalPlugin.getInstance().getFastzombies().containsKey(ent.getEntityId())) {
				ZombieSurvivalPlugin.getInstance().getFastzombies().remove(ent.getEntityId());
			}
			if ((ZombieSurvivalPlugin.getInstance().isRespawn()) && (deathreason != EntityDamageEvent.DamageCause.ENTITY_ATTACK)
					&& (deathreason != EntityDamageEvent.DamageCause.PROJECTILE))
				Games.setZcount(map, Games.getZcount(map) - 1);
			else {
				Games.setZslayed(map, Games.getZslayed(map) + 1);
			}
			ZombieSurvivalPlugin.getInstance().getZombies().remove(ent.getEntityId());
			if (Games.getZslayed(map) >= ((Integer) ZombieSurvivalPlugin.getInstance().getWavemax().get(map)).intValue()) {
				// getLogger().info(
				// map + " slayed: " + Integer.toString(games.getZslayed(map)) +
				// " wavemax: "
				// + Integer.toString(((Integer)
				// wavemax.get(map)).intValue()));
				ZombieSurvivalPlugin.getInstance().NewWave(map);
			}

			if ((ent.getLastDamageCause() instanceof EntityDamageByEntityEvent)) {
				EntityDamageByEntityEvent entitykiller = (EntityDamageByEntityEvent) ent.getLastDamageCause();
				if (((entitykiller.getDamager() instanceof Player)) || ((entitykiller.getDamager() instanceof Arrow))) {
					Player player = null;
					if ((entitykiller.getDamager() instanceof Arrow)) {
						Arrow arrow = (Arrow) entitykiller.getDamager();
						if ((arrow.getShooter() instanceof Player))
							player = (Player) arrow.getShooter();
					} else {
						player = (Player) entitykiller.getDamager();
					}
					try {
						if (PlayerMethods.playerGame(player).equalsIgnoreCase(map)) {
							com.jordair.gmail.zombiesurvival.Stats.addKills(player.getName(), 1.0D);
							ZombieSurvivalPlugin.getInstance().getAwards().addKill(player);
							ZombieSurvivalPlugin.getInstance().getSecondkills()
									.put(map, ((Integer) ZombieSurvivalPlugin.getInstance().getSecondkills().get(map).intValue() + 1));
						}
					} catch (Exception e) {
						if (player != null) {
							player.teleport(player.getWorld().getSpawnLocation());
							Games.removePlayerMap(player.getName());
							com.jordair.gmail.zombiesurvival.Stats.setPoints(player.getName(), 0.0D);
							com.jordair.gmail.zombiesurvival.Stats.removeSplayerKills(player.getName());

							ZombieSurvivalPlugin.getDead().remove(player.getName());
							if ((ZombieSurvivalPlugin.getInstance().isEmptyaccount()) && (!ZombieSurvivalPlugin.getInstance().isPoints())) {

								double removem = ZombieSurvivalPlugin.getEcon().getBalance(player.getName());

								ZombieSurvivalPlugin.getEcon().withdrawPlayer(player.getName(), removem);
							}
							String name = player.getName();
							player.setDisplayName(name);
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player killed = event.getEntity();
		if (PlayerMethods.inGame(killed)) {
			String map = PlayerMethods.playerGame(killed);
			PlayerRemoveEvent pre = new PlayerRemoveEvent(map, killed, RemoveReason.KILLED);
			Bukkit.getServer().getPluginManager().callEvent(pre);
			// getLogger().info(killed.getName() + " has died! Was in game: " +
			// map);
			event.getDrops().clear();
			if (ZombieSurvivalPlugin.getInstance().isResetpointsdeath()) {
				com.jordair.gmail.zombiesurvival.Stats.setPoints(killed.getName(), 0.0D);
			}
			Games.setPcount(map, Games.getPcount(map) - 1);

			ZombieSurvivalPlugin.getDead().put(killed.getName(), map);
			ZombieSurvivalPlugin.getInstance().getRevive().createRevive(killed);
			com.jordair.gmail.zombiesurvival.Stats.addDeaths(killed.getName(), 1.0D);
			if ((!ZombieSurvivalPlugin.getInstance().isPoints()) && (ZombieSurvivalPlugin.getInstance().getDeathloss() > 0.0D)) {
				double original = ZombieSurvivalPlugin.getEcon().getBalance(killed.getName());
				double withdraw = original * ZombieSurvivalPlugin.getInstance().getDeathloss();
				ZombieSurvivalPlugin.getEcon().withdrawPlayer(killed.getName(), withdraw);
			}
			if ((killed.getLastDamageCause() instanceof EntityDamageByEntityEvent)) {
				EntityDamageByEntityEvent killer = (EntityDamageByEntityEvent) killed.getLastDamageCause();
				if ((killer.getDamager() instanceof Zombie)) {
					killed.sendMessage(ChatColor.DARK_PURPLE + "You were eaten alive!");
				}
				if ((killer.getDamager() instanceof Player))
					killed.sendMessage(ChatColor.DARK_PURPLE + "You were murdered!");
			} else {
				killed.sendMessage(ChatColor.DARK_PURPLE + "You died!");
			}
		}
	}
}
