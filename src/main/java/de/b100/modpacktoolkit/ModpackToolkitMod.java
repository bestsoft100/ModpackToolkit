package de.b100.modpacktoolkit;

import java.io.File;

import com.google.gson.Gson;

import de.b100.modpacktoolkit.customworldgen.CustomWorldGenMod;
import de.b100.modpacktoolkit.hungertweaks.HungerTweaks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = ModpackToolkitMod.MODID, name = ModpackToolkitMod.NAME, version = ModpackToolkitMod.VERSION)
public class ModpackToolkitMod {
	
	public static final String MODID = "modpacktoolkit";
	public static final String NAME = "Modpack Toolkit";
	public static final String VERSION = "1.0";
	
	public static Gson gson = new Gson();

	public static File configFolder;
	public static File modConfigFolder;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		configFolder = event.getModConfigurationDirectory();
		modConfigFolder = new File(configFolder, "ModpackToolkit");
		
		CustomWorldGenMod.preInit();
		HungerTweaks.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		CustomWorldGenMod.init();
		HungerTweaks.init();
	}
	
	@EventHandler
	public void onServerStarting(FMLServerStartingEvent event) {
		HungerTweaks.serverStart(event);
	}
}
