package i.gishreloaded.gishcode.utils.system;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;

public class Mapping {
	
	public static String session = isMCP() ? "session" : "field_71449_j";
	public static String yaw = isMCP() ? "yaw" : "field_149476_e";
	public static String pitch = isMCP() ? "pitch" : "field_149473_f";
	public static String currentGameType = isMCP() ? "currentGameType" : "field_78779_k";
	public static String connection = isMCP() ? "connection" : "field_78774_b";

    public static boolean isMCP() {
        try {
        	Field instance = Minecraft.class.getDeclaredField("instance");
        	if(instance == null) {
        		return false;
        	}
        	else
        	{
        		return true;
        	}
        } catch (Exception ex) {
            return false;
        }
    }
}
