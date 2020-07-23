package i.gishreloaded.gishcode.skinchanger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import i.gishreloaded.gishcode.utils.system.Mapping;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;

public class TextureReflection {

	public static boolean setTextureForPlayer(Type type, NetworkPlayerInfo playerInfo, ResourceLocation location) {
		if (playerInfo == null) {
			ChatUtils.error("getPlayerInfo is null!");
            return false;
        }
		try {
        	Field playerTextures = NetworkPlayerInfo.class.getDeclaredField(Mapping.playerTextures);
            boolean accessible = playerTextures.isAccessible();

            playerTextures.setAccessible(true);

            ConcurrentHashMap<Object, Object> concHashMap = new ConcurrentHashMap<Object, Object>();
            concHashMap.put(type, location);
            playerTextures.set(playerInfo, concHashMap);

            playerTextures.setAccessible(accessible);
            return true;
        } catch (Exception ex) { ChatUtils.error(ex.getMessage()); ex.printStackTrace(); return false; }
	}
	
	public static NetworkPlayerInfo getNetworkPlayerInfo(AbstractClientPlayer player) {
        try {
        	Method method = AbstractClientPlayer.class.getDeclaredMethod(Mapping.getPlayerInfo);
        	method.setAccessible(true);
            NetworkPlayerInfo playerInfo = (NetworkPlayerInfo) method.invoke(player);
            return playerInfo;
        } catch (Exception ex) { ChatUtils.error(ex.getMessage()); ex.printStackTrace(); return null; }
    }
	
}
