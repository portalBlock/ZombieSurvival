package com.jordair.gmail.zombiesurvival.api;

public enum PerkType {
	GODMODE(266), INSTA_KILL(264), FIRE(51), BONUS_XP(371), IRON_FIST(265);

	private int value;

	private PerkType(int value) {
		this.value = value;
	}

	public int getID() {
		return value;
	}

	public static PerkType forID(int id) {
		switch (id) {
		case 266:
			return GODMODE;
		case 264:
			return INSTA_KILL;
		case 51:
			return FIRE;
		case 371:
			return BONUS_XP;
		case 265:
			return IRON_FIST;
		}
		return null;
	}
}
