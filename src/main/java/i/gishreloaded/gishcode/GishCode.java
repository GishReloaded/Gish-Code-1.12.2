package i.gishreloaded.gishcode;

import java.io.IOException;

import i.gishreloaded.gishcode.gui.click.ClickGuiScreen;
import i.gishreloaded.gishcode.gui.click.theme.dark.DarkTheme;
import i.gishreloaded.gishcode.managers.FileManager;
import i.gishreloaded.gishcode.managers.GuiManager;
import i.gishreloaded.gishcode.managers.HackManager;
import i.gishreloaded.gishcode.utils.LoginUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = GishCode.MODID, name = GishCode.NAME, version = GishCode.VERSION)
public class GishCode {
	
	public static final String MODID = "gishcode";
	public static final String NAME = "GishCode";
	public static final String VERSION = "0.2.9";
	public static HackManager hackManager;
	public static FileManager fileManager;
	public static Events events;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent E) {
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent E) {
		hackManager = new HackManager();
		fileManager = new FileManager();
		events = new Events();
        FMLCommonHandler.instance().bus().register(events);
        MinecraftForge.EVENT_BUS.register(events);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent E) {
	}
}
