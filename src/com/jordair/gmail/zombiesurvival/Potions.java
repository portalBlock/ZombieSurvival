package com.jordair.gmail.zombiesurvival;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Potions {
	Plugin plugin;
	Random random = new Random();
	Language lang = new Language(this.plugin);
	public int effectchance = 20;
	public int bitelength = 10;
	public boolean pot1 = true;
	public boolean pot2 = true;
	public boolean pot3 = true;
	public boolean pot4 = true;
	public boolean pot5 = true;
	public boolean pot6 = true;
	public boolean pot7 = true;

	public Potions(Plugin instance) {
		this.plugin = instance;
	}

	public void checkForBite(Player p) {
		if (this.effectchance != 0) {
			int doeffect = this.random.nextInt(this.effectchance) + 1;
			if (doeffect == 1) {
				PotionEffect potion1 = new PotionEffect(PotionEffectType.BLINDNESS, this.bitelength, 1);
				PotionEffect potion2 = new PotionEffect(PotionEffectType.CONFUSION, this.bitelength, 1);
				PotionEffect potion3 = new PotionEffect(PotionEffectType.SLOW, this.bitelength, 1);
				PotionEffect potion4 = new PotionEffect(PotionEffectType.WEAKNESS, this.bitelength, 1);
				PotionEffect potion5 = new PotionEffect(PotionEffectType.POISON, this.bitelength / 3, 1);
				PotionEffect potion6 = new PotionEffect(PotionEffectType.HUNGER, this.bitelength, 1);
				PotionEffect potion7 = new PotionEffect(PotionEffectType.JUMP, this.bitelength, 1);
				List<PotionEffect> potions = new ArrayList<PotionEffect>();
				if (this.pot1) {
					potions.add(potion1);
				}
				if (this.pot2) {
					potions.add(potion2);
				}
				if (this.pot3) {
					potions.add(potion3);
				}
				if (this.pot4) {
					potions.add(potion4);
				}
				if (this.pot5) {
					potions.add(potion5);
				}
				if (this.pot6) {
					potions.add(potion6);
				}
				if (this.pot7) {
					potions.add(potion7);
				}
				int potionget = this.random.nextInt(potions.size());
				p.addPotionEffect((PotionEffect) potions.get(potionget));
				p.sendMessage((String) this.lang.getStrings().get(69));
			}
		}
	}

	public void loadPotions() {
		this.pot1 = this.plugin.getConfig().getBoolean("Effects.blindness");
		this.pot2 = this.plugin.getConfig().getBoolean("Effects.confusion");
		this.pot3 = this.plugin.getConfig().getBoolean("Effects.slowness");
		this.pot4 = this.plugin.getConfig().getBoolean("Effects.weakness");
		this.pot5 = this.plugin.getConfig().getBoolean("Effects.poison");
		this.pot6 = this.plugin.getConfig().getBoolean("Effects.hungerpoison");
		this.pot7 = this.plugin.getConfig().getBoolean("Effects.jump");
		this.lang.plugin = this.plugin;
		this.lang.LoadLanguage();
	}

	public void Destroy() {
		try {
			finalize();
		} catch (Throwable e) {
			this.plugin.getLogger().warning("Failed to destroy class.");
		}
	}
}