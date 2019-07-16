package i.gishreloaded.gishcode.managers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import i.gishreloaded.gishcode.Wrapper;
import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.utils.XRayData;
import i.gishreloaded.gishcode.value.BooleanValue;
import i.gishreloaded.gishcode.value.Mode;
import i.gishreloaded.gishcode.value.ModeValue;
import i.gishreloaded.gishcode.value.NumberValue;
import i.gishreloaded.gishcode.value.Value;

public class FileManager {

    private static Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();

    private static JsonParser jsonParser = new JsonParser();

    public static final File GISHCODE_DIR = new File(String.format("%s%sGishCode-1.12.2%s", Wrapper.INSTANCE.mc().mcDataDir, File.separator, File.separator));

    private static final File HACKS = new File(GISHCODE_DIR, "hacks.json");
    private static final File XRAYDATA = new File(GISHCODE_DIR, "xraydata.json");
    private static final File PFILTER = new File(GISHCODE_DIR, "pickupfilter.json");
    private static final File FRIENDS = new File(GISHCODE_DIR, "friends.json");
    private static final File ENEMYS = new File(GISHCODE_DIR, "enemys.json");
    
    public FileManager() {
        if (!GISHCODE_DIR.exists()) {
            GISHCODE_DIR.mkdir();
        }
        if (!HACKS.exists()) {
        	saveHacks();
        }
        else
        {
        	loadHacks();
        }
        if (!XRAYDATA.exists()) {
        	saveXRayData();
        }
        else
        {
        	loadXRayData();
        }
        if (!FRIENDS.exists()) {
        	saveFriends();
        }
        else
        {
        	loadFriends();
        }
//        if (!PFILTER.exists()) {
//        	savePFilter();
//        }
//        else
//        {
//        	loadPFilter();
//        }
        if (!ENEMYS.exists()) {
        	saveEnemys();
        }
        else
        {
        	loadEnemys();
        }
	}


    public static void loadHacks() {
        try {
            BufferedReader loadJson = new BufferedReader(new FileReader(HACKS));
            JsonObject moduleJason = (JsonObject) jsonParser.parse(loadJson);
            loadJson.close();

            for (Map.Entry<String, JsonElement> entry : moduleJason.entrySet()) {
                Hack mods = HackManager.getHack(entry.getKey());

                if (mods != null) {
                    JsonObject jsonMod = (JsonObject) entry.getValue();
                    boolean enabled = jsonMod.get("toggled").getAsBoolean();

                    if (enabled) {
                        mods.setToggled(true);
                    }

                    if (!mods.getValues().isEmpty()) {
                        for (Value value : mods.getValues()) {
                            if (value instanceof BooleanValue) {
                                boolean bvalue = jsonMod.get(value.getName()).getAsBoolean();
                                value.setValue(bvalue);
                            }
                            if (value instanceof NumberValue) {
                                double dvalue = jsonMod.get(value.getName()).getAsDouble();
                                value.setValue(dvalue);
                            }
                            if (value instanceof ModeValue) {
                            	ModeValue modeValue = (ModeValue) value;
                            	for(Mode mode : modeValue.getModes()) {
                                	boolean mvalue = jsonMod.get(mode.getName()).getAsBoolean();
                            		mode.setToggled(mvalue);
                            	}
                            }
                        }
                    }
                    mods.setKey(jsonMod.get("key").getAsInt());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void loadFriends() {
    	final List<String> friends = read(FRIENDS);
    	for(String name : friends) {
    		FriendManager.addFriend(name);
    	}
    }
    
    public static void loadEnemys() {
    	final List<String> enemys = read(ENEMYS);
    	for(String name : enemys) {
    		EnemyManager.addEnemy(name);
    	}
    }
    
    public static void loadPFilter() {
    	final List<String> items = read(PFILTER);
    	for(String id : items) {
    		PickupFilterManager.add(id);
    	}
    }
    
    public static void loadXRayData() {
        try {
        	BufferedReader loadJson = new BufferedReader(new FileReader(XRAYDATA));
            JsonObject json = (JsonObject) jsonParser.parse(loadJson);
            loadJson.close();
            
            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            	JsonObject jsonData = (JsonObject) entry.getValue();
            	
            	int id = Integer.parseInt(entry.getKey());
            	
            	int red = jsonData.get("red").getAsInt();
            	int green = jsonData.get("green").getAsInt();
            	int blue = jsonData.get("blue").getAsInt();
            	
            	XRayManager.addData(new XRayData(id, red, green, blue));
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void saveXRayData() {
        try {
            JsonObject json = new JsonObject();
            
            for(XRayData data : XRayManager.xrayList) {
            	JsonObject jsonData = new JsonObject();
            	
            	jsonData.addProperty("red", data.getRed());
            	jsonData.addProperty("green", data.getGreen());
            	jsonData.addProperty("blue", data.getBlue());
            	
            	json.add("" + data.getId(), jsonData);
            }
            
            PrintWriter saveJson = new PrintWriter(new FileWriter(XRAYDATA));
            saveJson.println(gsonPretty.toJson(json));
            saveJson.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void saveFriends() {
       write(FRIENDS, FriendManager.friendsList, true, true);
    }
    
    public static void saveEnemys() {
    	write(ENEMYS, EnemyManager.enemysList, true, true);
    }
    
    public static void savePFilter() {
        write(PFILTER, PickupFilterManager.itemList, true, true);
     }

    public static void saveHacks() {
        try {
            JsonObject json = new JsonObject();
            
            for (Hack hack : HackManager.getHacks()) {
                JsonObject jsonHack = new JsonObject();
                jsonHack.addProperty("toggled", hack.isToggled());
                jsonHack.addProperty("key", hack.getKey());

                if (!hack.getValues().isEmpty()) {
                    for (Value value : hack.getValues()) {
                        if (value instanceof BooleanValue) {
                        	jsonHack.addProperty(value.getName(), (Boolean) value.getValue());
                        }
                        if (value instanceof NumberValue) {
                        	jsonHack.addProperty(value.getName(), (Number) value.getValue());
                        }
                        if (value instanceof ModeValue) {
                        	ModeValue modeValue = (ModeValue) value;
                        	for(Mode mode : modeValue.getModes()) {
                        		jsonHack.addProperty(mode.getName(), mode.isToggled());
                        	}
                        }
                    }
                }
                json.add(hack.getName(), jsonHack);
            }

            PrintWriter saveJson = new PrintWriter(new FileWriter(HACKS));
            saveJson.println(gsonPretty.toJson(json));
            saveJson.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void write(File outputFile, List<String> writeContent, boolean newline, boolean overrideContent) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputFile, !overrideContent));
            for (final String outputLine : writeContent) {
                writer.write(outputLine);
                writer.flush();
                if(newline) {
                	writer.newLine();
                }
            }
        }
        catch (Exception ex) {
            try {
                if (writer != null) {
                    writer.close();
                }
            }
            catch (Exception ex2) {}
        }
    }
	
    public static List<String> read(File inputFile) {
        ArrayList<String> readContent = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            String line;
            while ((line = reader.readLine()) != null) {
                readContent.add(line);
            }
        }
        catch (Exception ex) {
        	try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception ex2) {}
        }
        return readContent;
    }
}
