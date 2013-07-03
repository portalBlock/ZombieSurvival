package com.jordair.gmail.zombiesurvival;

public class SmartGames {

	public int smartWaveCount(String map) {
		int mz = Games.getMaxZombies(map);
		int w = Games.getWave(map);
		int pre = 1;
		int fin = 1;
		if (mz < 10) {
			pre = (int) (mz * w * 0.5D);
		}
		if ((mz >= 10) && (mz <= 50)) {
			pre = (int) (mz * w * 0.1D);
		}
		if ((mz >= 51) && (mz <= 100)) {
			pre = (int) (mz * w * 0.08D);
		}
		if ((mz >= 101) && (mz <= 200)) {
			pre = (int) (mz * w * 0.05D);
		}
		if (mz >= 201) {
			pre = (int) (mz * w * 0.04D);
		}
		if (pre < 1) {
			pre = 1;
		}
		fin = pre * (PlayerMethods.numberInMap(map) / Games.getMaxPlayers(map));
		if (fin < pre * 0.6D) {
			fin = (int) (pre * 0.6D);
		}
		if (fin < 1) {
			fin = 1;
		}
		return fin;
	}
}