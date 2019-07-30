package i.gishreloaded.gishcode.managers;

import java.util.ArrayList;
import java.util.List;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;

public class FriendManager {
	
	public static ArrayList<String> friendsList = new ArrayList<String>();
	
	public static void addFriend(String friendname) {
		if(!friendsList.contains(friendname)) {
			friendsList.add(friendname);
			FileManager.saveFriends();
			ChatUtils.message(friendname + " Added to \u00a7bfreinds \u00a77list.");
		}
	}
	
	public static void removeFriend(String friendname) {
		if(friendsList.contains(friendname)) {
			friendsList.remove(friendname);
			FileManager.saveFriends();
			ChatUtils.message(friendname + " Removed from \u00a7bfriends \u00a77list.");
		}
	}
	
	public static void clear() {
		if(!friendsList.isEmpty()) {
			friendsList.clear();
			FileManager.saveFriends();
			ChatUtils.message("\u00a7bFriends \u00a77list clear.");
		}
	}
}
