package i.gishreloaded.gishcode.managers;

import java.util.ArrayList;
import java.util.List;

import i.gishreloaded.gishcode.utils.visual.ChatUtils;

public class EnemyManager {
	
	public static ArrayList<String> enemysList = new ArrayList<String>();
	public static ArrayList<String> murders = new ArrayList<String>();
	public static ArrayList<String> detects = new ArrayList<String>();
	
	public static void addEnemy(String enemyname) {
		if(!enemysList.contains(enemyname)) {
			enemysList.add(enemyname);
			FileManager.saveEnemys();
			ChatUtils.message(enemyname + " Added to \u00a7cenemys \u00a77list.");
		}
	}
	
	public static void removeEnemy(String enemyname) {
		if(enemysList.contains(enemyname)) {
			enemysList.remove(enemyname);
			FileManager.saveEnemys();
			ChatUtils.message(enemyname + " Removed from \u00a7cenemys \u00a77list.");
		}
	}
	
	public static void clear() {
		if(!enemysList.isEmpty()) {
			enemysList.clear();
			FileManager.saveEnemys();
			ChatUtils.message("\u00a7cEnemys \u00a77list clear.");
		}
	}
}
