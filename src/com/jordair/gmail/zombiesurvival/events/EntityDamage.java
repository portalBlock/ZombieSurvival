package com.jordair.gmail.zombiesurvival.events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.jordair.gmail.zombiesurvival.Games;
import com.jordair.gmail.zombiesurvival.PlayerMethods;
import com.jordair.gmail.zombiesurvival.ZombieSurvivalPlugin;

public class EntityDamage implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamagePvP(EntityDamageByEntityEvent event) {
		Entity ent = event.getEntity();
		Entity dEnt = event.getDamager();
		int entid = ent.getEntityId();
		int dEntid = dEnt.getEntityId();
		Player player = null;

		if (((ent instanceof Player)) && ((dEnt instanceof Player))) {
			player = (Player) ent;
			Player damager = (Player) dEnt;
			String map = PlayerMethods.playerGame(player);
			if (PlayerMethods.inGame(player)) {
				if ((Games.getState(map) > 1) && (PlayerMethods.playerGame(player).equalsIgnoreCase(PlayerMethods.playerGame(damager)))) {
					ZombieSurvivalPlugin.getInstance();
					if (ZombieSurvivalPlugin.getDead().containsKey(damager.getName())) {
						event.setCancelled(true);
					}
					if (ZombieSurvivalPlugin.getInstance().isPoints()) {
						double dscore = com.jordair.gmail.zombiesurvival.Stats.getSesPoints(damager.getName());
						double dnewscore = dscore - event.getDamage();
						com.jordair.gmail.zombiesurvival.Stats.addPoints(damager.getName(), -event.getDamage());
						damager.sendMessage((String) ZombieSurvivalPlugin.getInstance().getLang().getStrings().get(68));
						String dname = damager.getName();
						damager.setDisplayName("[" + dnewscore + "]" + dname);
					} else {
						ZombieSurvivalPlugin.getInstance();
						ZombieSurvivalPlugin.getEcon().withdrawPlayer(damager.getName(), event.getDamage());
						damager.sendMessage(ChatColor.RED + "You have lost " + (int) event.getDamage()
								+ " dollars for attacking another survivor!");
					}
				}

				if ((Games.getState(map) == 1) && (ZombieSurvivalPlugin.getInstance().isAntigrief())) {
					event.setCancelled(true);
				}
			}
		}
		if ((ZombieSurvivalPlugin.getInstance().getZombies().containsKey(entid)) && (!(dEnt instanceof Player))
				&& (!(dEnt instanceof Projectile))) {
			event.setCancelled(true);
		}
		if ((ZombieSurvivalPlugin.getInstance().getZombies().containsKey(entid))
				&& (((dEnt instanceof Player)) || ((dEnt instanceof Arrow)))) {
			if ((dEnt instanceof Arrow)) {
				Arrow arrow = (Arrow) dEnt;
				if ((arrow.getShooter() instanceof Player))
					player = (Player) arrow.getShooter();
			} else {
				player = (Player) dEnt;
			}
			if ((PlayerMethods.inGame(player)) && (ZombieSurvivalPlugin.getInstance().getZombies().containsKey(entid))) {
				String map = PlayerMethods.playerGame(player);
				ZombieSurvivalPlugin.getInstance();
				if ((((String) ZombieSurvivalPlugin.getInstance().getZombies().get(entid)).equalsIgnoreCase(map))
						&& (!ZombieSurvivalPlugin.getDead().containsKey(player.getName()))) {
					if (ZombieSurvivalPlugin.getInstance().getZombiescore().get(entid) == null) {
						Map<String, Integer> temp = new HashMap<String, Integer>();
						temp.put(player.getName(), (int) event.getDamage());
						ZombieSurvivalPlugin.getInstance().getZombiescore().put(entid, temp);
					} else if (((Map<String, Integer>) ZombieSurvivalPlugin.getInstance().getZombiescore().get(entid))
							.get(player.getName()) == null) {
						((Map<String, Integer>) ZombieSurvivalPlugin.getInstance().getZombiescore().get(entid)).put(player.getName(),
								(int) event.getDamage());
					} else {
						int olddamg = ((Integer) ((Map<String, Integer>) ZombieSurvivalPlugin.getInstance().getZombiescore().get(entid))
								.get(player.getName())).intValue();
						int damg = olddamg + (int) event.getDamage();
						((Map<String, Integer>) ZombieSurvivalPlugin.getInstance().getZombiescore().get(entid)).put(player.getName(), damg);
					}
					if ((((Map<String, Integer>) ZombieSurvivalPlugin.getInstance().getZombiescore().get(entid)).get(player.getName()) != null)
							&& (((Integer) ((Map<String, Integer>) ZombieSurvivalPlugin.getInstance().getZombiescore().get(entid))
									.get(player.getName())).intValue() > ZombieSurvivalPlugin.getInstance().getMaxpoints())) {
						((Map<String, Integer>) ZombieSurvivalPlugin.getInstance().getZombiescore().get(entid)).put(player.getName(),
								ZombieSurvivalPlugin.getInstance().getMaxpoints());
					}
				}
				switch (ZombieSurvivalPlugin.getInstance().getPerk().getPerk(map)) {
				case 2:
					LivingEntity lent = (LivingEntity) ent;
					lent.setHealth(0);
					break;
				case 3:
					event.getEntity().setFireTicks(60);
					break;
				case 5:
					ent.setVelocity(player.getLocation().getDirection().multiply(3));
				case 4:
				}
			}
		}
		if (((ent instanceof Player)) && (ZombieSurvivalPlugin.getInstance().getZombies().containsKey(dEntid))) {
			player = (Player) ent;
			if (PlayerMethods.inGame(player)) {
				String map = PlayerMethods.playerGame(player);
				if (ZombieSurvivalPlugin.getInstance().getPerk().getPerk(map) == 1) {
					event.setCancelled(true);
				}
				if (ZombieSurvivalPlugin.getInstance().getDamagemulti() != 0.0D) {
					event.setDamage(2 + (int) (Games.getWave(map) * ZombieSurvivalPlugin.getInstance().getDamagemulti()));
				}
				ZombieSurvivalPlugin.getInstance().getPot().checkForBite(player);
			}
		}
	}

	@EventHandler
	public void onZombieDamage(EntityDamageEvent event) {
		Entity ent = event.getEntity();
		if ((!ZombieSurvivalPlugin.getInstance().getZombies().isEmpty())
				&& (ZombieSurvivalPlugin.getInstance().getZombies().get(ent.getEntityId()) != null)
				&& (ZombieSurvivalPlugin.getInstance().getZombies().containsKey(ent.getEntityId()))
				&& (Games.getState((String) ZombieSurvivalPlugin.getInstance().getZombies().get(ent.getEntityId())) > 1)
				&& (!(event instanceof EntityDamageByEntityEvent)) && (!ZombieSurvivalPlugin.getInstance().isAllhurt()))
			event.setCancelled(true);
	}
}
