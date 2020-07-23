package i.gishreloaded.gishcode.managers;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.skinchanger.CacheRetriever;
import i.gishreloaded.gishcode.skinchanger.TextureReflection;
import i.gishreloaded.gishcode.skinchanger.resources.BetterJsonObject;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;

public class SkinChangerManager {
	
private static final HashMap<String, String> responses = new HashMap<>();
	
    private static final LinkedHashMap<String, String[]> idCaches = new LinkedHashMap<>();
    private static final LinkedHashMap<String, Boolean> slimSkins = new LinkedHashMap<>();
    
    private static final LinkedHashMap<String, BetterJsonObject> idEncryptedTextures = new LinkedHashMap<>();
    private static final LinkedHashMap<String, ResourceLocation> skins = new LinkedHashMap<>();
	
    public static Map<Type, ResourceLocation> playerTextures = Maps.newEnumMap(Type.class);
    
	private static HashMap<AbstractClientPlayer, NetworkPlayerInfo> cachedPlayerInfo = 
			new HashMap<AbstractClientPlayer, NetworkPlayerInfo>();
	
	private static CacheRetriever cacheRetriever = new CacheRetriever();
	
	private static final String[] TRUSTED_DOMAINS = {
            ".minecraft.net",
            ".mojang.com"
    };
	
	public static void addTexture(Type type, String content) {
		boolean loaded = loadTexture(type, content);
		if(loaded) { ChatUtils.message(
				String.format("\u00a77TYPE: \u00a73%s \u00a77CONTENT: \u00a73%s \u00a77- ADDED.",
						type.toString(), content));
			Hack hack = HackManager.getHack("SkinChanger"); 
			if(hack.isToggled()) hack.onEnable(); }
		else
			ChatUtils.error("SkinChanger: Failed load texture!");
	}
	
	private static boolean loadTexture(Type type, String content) {
		String name = type.toString().substring(0, 1);
		for(int i = 0; i < 6; i++) 
			name += Utils.random(0, 9);
		ResourceLocation resource = null;
		// If content is URL
		if(content.startsWith("http://") || content.startsWith("https://")) {
			resource = cacheRetriever.loadIntoGame(name, content, type);
		}
		else
		// If content is directory
		if(content.contains("/") || content.contains("\\")) {
			//...
		}
		//If content is player name
		//...
		else {
			String playerId = getIdFromUsername(content);
            String userName = getRealNameFromName(content);
            
            if (userName == null) userName = content;
            
            String finalUserName = userName;
			ResourceLocation resourceLocation = getSkinFromId(playerId);
            boolean hasSlimSkin = hasSlimSkin(finalUserName);
            
            resource = resourceLocation;
		}
		if(resource == null) return false;
		playerTextures.put(type, resource);
		return true;
	}
	
	public static void removeTexture(Type type) {
		playerTextures.remove(type);
		// TODO add message
		// TODO add save func
	}
	
	public static void clear() {
		playerTextures.clear();
		// TODO add message
		// TODO add save func
	}
	
	public static boolean setTexture(Type type, AbstractClientPlayer player, ResourceLocation location) {
		NetworkPlayerInfo playerInfo = getPlayerInfo(player);
        return TextureReflection.setTextureForPlayer(type, playerInfo, location);
	}
	
	private static NetworkPlayerInfo getPlayerInfo(AbstractClientPlayer player) {
		if (cachedPlayerInfo.containsKey(player))
			return cachedPlayerInfo.get(player);
		NetworkPlayerInfo playerInfo = TextureReflection.getNetworkPlayerInfo(player);
		if(playerInfo != null)
			cachedPlayerInfo.put(player, playerInfo);
		return playerInfo;
	}
	
	public static String getRealNameFromName(String nameIn) {
        if (nameIn == null) {
            return null;
        }
        
        if (idCaches.containsKey(nameIn)) {
            return idCaches.get(nameIn)[1];
        }
        
        getIdFromUsername(nameIn);
        
        if (idCaches.get(nameIn) == null) {
            return null;
        }
        
        return idCaches.get(nameIn)[1];
    }
	
	public static ResourceLocation getSkinFromId(String id) {
        try {
            return getSkinFromIdUnsafe(id);
        } catch (IllegalArgumentException ex) {
            if (ex.getMessage() != null) {
                System.err.println(ex.getMessage());
            }
            
            return DefaultPlayerSkin.getDefaultSkinLegacy();
        }
    }
	
	private static ResourceLocation getSkinFromIdUnsafe(String id) {
        if (id != null && !id.isEmpty()) {
            if (skins.containsKey(id)) {
                ResourceLocation loc = skins.get(id);
                
                // Test if the resource is still loaded
                if (Minecraft.getMinecraft().getTextureManager().getTexture(loc) != null) {
                    return loc;
                } else {
                    skins.remove(id);
                }
            }
            
            JsonObject realTextures = getEncryptedTexturesUnsafe(id).getData();
            
            // Should never happen
            if (!realTextures.has("SKIN")) {
                skins.put(id, DefaultPlayerSkin.getDefaultSkinLegacy());
                
                return DefaultPlayerSkin.getDefaultSkinLegacy();
            }
            
            JsonObject skinData = realTextures.get("SKIN").getAsJsonObject();
            
            if (!skinData.has("url")) {
                skins.put(id, DefaultPlayerSkin.getDefaultSkinLegacy());
                
                return DefaultPlayerSkin.getDefaultSkinLegacy();
            }
            
            String url = skinData.get("url").getAsString();
            
            // Ensures minecraft servers are not hacked :)
            if (!isTrustedDomain(url)) {
                // Spoofed payload? Does not use an official Mojang network...
                throw new IllegalArgumentException("Invalid payload, the domain issued was not trusted.");
            }
            
            ResourceLocation playerSkin = cacheRetriever.loadIntoGame(id, url, Type.SKIN);
            
            skins.put(id, playerSkin);
            
            return playerSkin;
        } else {
            return DefaultPlayerSkin.getDefaultSkinLegacy();
        }
    }
	
	private static boolean isTrustedDomain(String url) {
        if (url == null) {
            return false;
        }
        
        URI uri;
        
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL '" + url + "'");
        }
        
        String host = uri.getHost();
        
        for (String domain : TRUSTED_DOMAINS) {
            if (host.endsWith(domain)) {
                return true;
            }
        }
        return false;
    }
	
	public static boolean hasSlimSkin(String id) {
        try {
            return hasSlimSkinUnsafe(id);
        } catch (NullPointerException | JsonParseException | IllegalStateException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	private static boolean hasSlimSkinUnsafe(String id) throws NullPointerException, IllegalStateException, JsonParseException {
        if (id == null) {
            return false;
        }
        
        if (slimSkins.containsKey(id)) {
            return slimSkins.get(id);
        }
        
        JsonObject realTextures = getEncryptedTexturesUnsafe(id).getData();
        
        // Should never happen
        if (!realTextures.has("SKIN")) {
            slimSkins.put(id, false);
            
            return false;
        }
        
        JsonObject skinData = realTextures.get("SKIN").getAsJsonObject();
        
        if (skinData.has("metadata")) {
            JsonObject metaData = skinData.get("metadata").getAsJsonObject();
            
            slimSkins.put(id, metaData.has("model"));
            
            return metaData.has("model") && metaData.get("model").getAsString().equals("slim");
        }
        
        slimSkins.put(id, false);
        
        return false;
    }
	
	private static BetterJsonObject getEncryptedTexturesUnsafe(String id) throws IllegalStateException, JsonParseException {
        if (id == null) {
            return new BetterJsonObject();
        }
        
        if (idEncryptedTextures.containsKey(id)) {
            return idEncryptedTextures.get(id);
        }
        
        BetterJsonObject texturesIn = getTexturesFromId(id);
        
        if (!texturesIn.has("properties") || !texturesIn.get("properties").isJsonArray()) {
            return new BetterJsonObject();
        }
        
        // Properties is a JsonArray
        JsonArray propertyArray = texturesIn.get("properties").getAsJsonArray();
        
        for (JsonElement propertyElement : propertyArray) {
            // This shouldn't actually happen at the time of making this,
            // This has just been added for if they add anything to the api
            if (!propertyElement.isJsonObject()) {
                continue;
            }
            
            // Grab the JsonObject version of the property
            JsonObject property = propertyElement.getAsJsonObject();
            
            // Found the textures property!
            if (property.has("name") && property.get("name").getAsString().equals("textures") && property.has("value")) {
                // We need to decode the Base64 value property
                byte[] decoded = Base64.getDecoder().decode(property.get("value").getAsString());
                
                JsonObject decodedObj = new JsonParser().parse(new String(decoded, StandardCharsets.UTF_8)).getAsJsonObject();
                
                // We have a match!
                if (decodedObj.has("textures") && decodedObj.has("profileId") && decodedObj.get("profileId").getAsString().equals(texturesIn.get("id").getAsString())) {
                    idEncryptedTextures.put(id, new BetterJsonObject(decodedObj.get("textures").getAsJsonObject()));
                    
                    return idEncryptedTextures.get(id);
                }
            }
        }
        
        idEncryptedTextures.put(id, new BetterJsonObject());
        
        return idEncryptedTextures.get(id);
    }
	
	public static BetterJsonObject getTexturesFromId(String id) {
        if (id == null || id.isEmpty()) {
            return new BetterJsonObject();
        }
        
        id = id.replace("-", ""); // Remove dashes
        
        return new BetterJsonObject(getUrl("https://sessionserver.mojang.com/session/minecraft/profile/" + id));
    }
	
	public static String getIdFromUsername(String nameIn) {
		if (nameIn == null) {
            return null;
        }
        
        if (idCaches.containsKey(nameIn)) {
            return idCaches.get(nameIn)[0];
        }
        
        if (nameIn.isEmpty()) {
            idCaches.put("", new String[] {"", ""});
            
            return "";
        }
        
        BetterJsonObject profile = getProfileFromUsername(nameIn);
        
        if (profile.has("success") && !profile.get("success").getAsBoolean()) {
            idCaches.put(nameIn, new String[] {"", ""});
            
            return "";
        }
        
        if (profile.has("id")) {
            idCaches.put(nameIn, new String[] {profile.get("id").getAsString(), profile.get("name").getAsString()});
            
            return profile.get("id").getAsString();
        }
        
        idCaches.put(nameIn, new String[] {"", ""});
        
        return "";
	}
	
	public static BetterJsonObject getProfileFromUsername(String name) {
        if (name == null || name.isEmpty()) {
            return new BetterJsonObject();
        }
        
        return new BetterJsonObject(getUrl("https://api.mojang.com/users/profiles/minecraft/" + name));
    }
	
	public static final String getUrl(String url) {
//        if (org.lwjgl.input.Keyboard.isKeyDown(org.lwjgl.input.Keyboard.KEY_L)) {
//            responses.clear();
//        }
        
        if (responses.containsKey(url)) {
            return responses.get(url);
        }
        
        // Store the response
        String response;
        
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url.replace(" ", "%20")).openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (compatible; SkinChanger; 3.0.1) Chrome/83.0.4103.116");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);
            
            response = IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            //e.printStackTrace();
            JsonObject object = new JsonObject();
            object.addProperty("success", false);
            object.addProperty("cause", "Exception");
            object.addProperty("message", e.getMessage() != null ? "" : e.getMessage());
            response = object.toString();
        }
        
        // Stores the response so their api has minimum queries in one session
        responses.put(url, response);
        
        return response;
    }
}
