/*
 *     Copyright (C) 2020 boomboompower
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package i.gishreloaded.gishcode.skinchanger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import i.gishreloaded.gishcode.managers.FileManager;
import i.gishreloaded.gishcode.skinchanger.resources.CapeBuffer;
import i.gishreloaded.gishcode.skinchanger.resources.LocalFileData;
import i.gishreloaded.gishcode.skinchanger.resources.SkinBuffer;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;

/**
 * A class which handles saving and loading files to the SkinChanger cache to save
 * bandwidth for users and make the mod work offline as well.
 * <p>
 * Cached files generally expire after about a day
 *
 * @authors boomboompower, Gish_Reloaded
 * @since 3.0.0
 */
public class CacheRetriever {
    
    private static final ResourceLocation undefinedTexture = 
    		new ResourceLocation("skinchanger", "light.png");
    
    private static final boolean FORCE_HTTPS = true;
    
    private final HashMap<String, String> cachedValues = new HashMap<>();
    
    private final File cacheDirectory;
    
    /**
     * Simple constructor for the mod
     *
     * @param mod the SkinChanger mod instance
     */
    public CacheRetriever() {
        this.cacheDirectory = FileManager.SKINCHANGER; genCacheDirectory();
    }
    
    /**
     * Loads a file/url into the game.
     *
     * @param name the name for the file
     * @param url  the url of the file
     *
     * @return a resource location with the loaded url.
     */
    public ResourceLocation loadIntoGame(String name, String url) {
        return loadIntoGame(name, url, null);
    }
    
    /**
     * Loads a url into the game and caches it into the game.
     *
     * @param name      the name for the file (to be cached)
     * @param url       the url for the file (to be retrieved)
     * @param cacheType the cache type, influences if
     *
     * @return the resource which has been loaded into the game
     */
    // String.format("https://minotar.net/skin/%s", name)
    public ResourceLocation loadIntoGame(String name, String url, Type type) {
    	CacheType cacheType = null;
    	
    	if(type == null) 
    		cacheType = CacheType.OTHER;
    	 else 
    		cacheType = CacheType.valueOf(type.toString());
    	
    	if(cacheType == null)
    		return null;

        File cacheDirectory = getCacheDirForName(name);
        
        File cachedFile = cacheType != CacheType.OTHER ? getCacheFileIfIExists(name, ".png") : null;
        
        ResourceLocation location = new ResourceLocation("skins/" + getCacheName(name));
        
        final IImageBuffer buffer = cacheType == CacheType.CAPE ? 
        		new CapeBuffer() : cacheType == CacheType.SKIN ? new SkinBuffer() : null;
        
        if (cachedFile != null) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                Minecraft.getMinecraft().renderEngine.loadTexture(location, 
                		new LocalFileData(DefaultPlayerSkin.getDefaultSkinLegacy(), cachedFile, buffer));
            });
            return location;
        }
        
        File dataFile = new File(cacheDirectory, cacheDirectory.getName() + ".png");
        
        if (FORCE_HTTPS && (url.startsWith("http://") && !url.contains("optifine"))) {
            url = "https://" + url.substring("http://".length());
        }
        
        ThreadDownloadImageData imageData = 
        		new ThreadDownloadImageData(dataFile, url, cacheType == CacheType.CAPE ? 
        				new ResourceLocation("skinchanger", "light.png") :
        					DefaultPlayerSkin.getDefaultSkinLegacy(), buffer);
        
        Minecraft.getMinecraft().addScheduledTask(() -> {
            Minecraft.getMinecraft().renderEngine.loadTexture(location, imageData);
        });
        
        if(generateCacheFiles(name))
        	return location;
        return null;
    }
    
    /**
     * Creates a cache directory and data file for the cache
     *
     * @param name the name of the file
     */
    public boolean generateCacheFiles(String name) {
        File cacheDirectory = getCacheDirForName(name);
        
        if (isCacheExpired(name)) {
            //noinspection ResultOfMethodCallIgnored
            cacheDirectory.delete();
        }
        
        if (!cacheDirectory.exists()) {
            if (!cacheDirectory.mkdir()) {
                ChatUtils.error("Failed to create a cache directory.");
                return false;
            }
        }
        
        File dataFile = new File(cacheDirectory, cacheDirectory.getName() + ".png");
        File cacheFile = new File(cacheDirectory, cacheDirectory.getName() + ".lock");
        
        try {
            if (!dataFile.exists()) {
                if (!dataFile.createNewFile()) {
                	ChatUtils.error("SkinChanger: Failed to create a data file.");
                    return false;
                }
            }
            
            if (!cacheFile.exists()) {
                if (!cacheFile.createNewFile()) {
                    ChatUtils.error("Failed to create a cache file.");
                    return false;
                } else {
                    // Write the cache information
                    FileWriter writer = new FileWriter(cacheFile);
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);
                    
                    long expirationTime = System.currentTimeMillis();
                    
                    // Current time + 1 day
                    expirationTime += 24 * 60 * 60 * 1000;
                    
                    // Write the one line.
                    bufferedWriter.write(expirationTime + System.lineSeparator());
                    bufferedWriter.close();
                }
            }
        } catch (Exception ex) {
        	ChatUtils.error(ex.getMessage());
            return false;
        }
        return true;
    }
    
    /**
     * Gets the cached data file from a name
     *
     * @param name      the name of the file (will be converted to a cache name)
     * @param extension the extension of the file (must start with a .)
     *
     * @return the file for the cache or null if it does not exist.
     */
    public File getCacheFileIfIExists(String name, String extension) {
        if (!extension.startsWith(".")) {
            return null;
        }
        
        if (!doesCacheExist(name) || isCacheExpired(name)) {
            return null;
        }
        
        String cached_name = getCacheName(name);
        File dir = new File(this.cacheDirectory, cached_name);
        File data_file = new File(dir, cached_name + extension);
        
        if (!data_file.exists()) {
            return null;
        }
        
        if (data_file.isDirectory()) {
            if (data_file.delete()) {
                return null;
            }
            
            return null;
        }
        
        return data_file;
    }
    
    /**
     * Returns true if the value inside the cache file is either missing
     *
     * @param name the name of the file to check the cache from.
     *
     * @return true if the cached file should have expired.
     */
    public boolean isCacheExpired(String name) {
        if (name == null) {
            return true;
        }
        
        File fileCache = getCacheDirForName(name);
        
        if (!fileCache.exists()) {
            return true;
        }
        
        File cacheLock = new File(fileCache, fileCache.getName() + ".lock");
        
        if (!cacheLock.exists()) {
            return true;
        }
        
        try {
            FileReader fileReader = new FileReader(cacheLock);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            // First line contains the expiration time.
            String line = bufferedReader.readLine();
            
            bufferedReader.close();
            
            long time = Long.parseLong(line);
            
            return System.currentTimeMillis() > time;
        } catch (IOException ex) {
        	ChatUtils.error("SkinChanger: Unable to read cache file for " + name);
            return true;
        } catch (NumberFormatException ex) {
            ChatUtils.error("SkinChanger: Cache file had an invalid number.");
            return true;
        }
    }
    
    /**
     * Returns true if the cache file for a name exists.
     *
     * @param name the name to check for a cache file.
     *
     * @return true if the cache file exists
     */
    public boolean doesCacheExist(String name) {
        if (!genCacheDirectory()) {
            return false;
        }
        
        File cacheDirectory = getCacheDirForName(name);
        
        if (!cacheDirectory.exists()) {
            return false;
        }
        
        File dataFile = new File(cacheDirectory, cacheDirectory.getName() + ".png");
        File cacheFile = new File(cacheDirectory, cacheDirectory.getName() + ".lock");
        
        return dataFile.exists() && dataFile.length() > 2 && cacheFile.exists();
    }
    
    /**
     * Generates a unique ID from a string which can be used as a cache name.
     *
     * @param name the name to retrieve a cache name from
     *
     * @return a unique ID for the entered name which corresponds to the name's cache directory.
     */
    private String getCacheName(String name) {
        name = name.toLowerCase();
        
        // If the cache file exists in memory, just return the name of it.
        if (this.cachedValues.containsKey(name)) {
            return this.cachedValues.get(name);
        }
        
        UUID id = UUID.nameUUIDFromBytes(name.getBytes());
        
        // Take up to the first 5 characters of the name
        String subStrName = name.substring(0, Math.min(7, name.length()));
        
        // Split the UUID into its four segments 0-1-2-3
        String[] uuidSplit = id.toString().split("-");
        
        // Take the first and second component of the UUID.
        String idFirstComponent = uuidSplit[0] + uuidSplit[1];
        
        // Creates the final name of the cache file.
        String finalCacheName = subStrName + "_" + idFirstComponent;
        
        // Cache the name so this code doesn't run on it again.
        this.cachedValues.put(name, finalCacheName);
        
        return finalCacheName;
    }
    
    /**
     * Retrieves the cache directory from a name/input.
     *
     * @param nameOfFile the input which will be cached
     *
     * @return a directory which corresponds to a names cache file.
     */
    private File getCacheDirForName(String nameOfFile) {
        String cacheName = getCacheName(nameOfFile);
        
        return new File(this.cacheDirectory, cacheName);
    }
    
    /**
     * Attempts to generate a cache directory.
     *
     * @return true if the cache directory already existed.
     */
    private boolean genCacheDirectory() {
        boolean existed = true;
        
        if (!this.cacheDirectory.getParentFile().exists()) {
            existed = false;
            
            if (this.cacheDirectory.getParentFile().mkdirs()) {
                //System.out.println("Suggested mod directory created.");
            } else {
            	ChatUtils.error("SkinChanger: Unable to create the mod directory.");
                
                return false;
            }
        }
        
        if (!this.cacheDirectory.exists()) {
            existed = false;
            
            if (this.cacheDirectory.mkdir()) {
                //System.out.println("Cache directory created.");
            } else {
                ChatUtils.error("SkinChanger: Unable to create cache directory.");
                return false;
            }
        }
        
        return existed;
    }
    
    public File getCacheDirectory() {
        return this.cacheDirectory;
    }
    
    /**
     * Tells the code how the cached file should be parsed by the mod
     * <p>
     * SKIN will be parsed through {@link SkinBuffer}
     * CAPE will be parsed through {@link CapeBuffer}
     * OTHER will not be parsed through anything.
     */
    public enum CacheType {
        SKIN,
        CAPE,
        OTHER
    }
}
