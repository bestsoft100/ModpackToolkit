package de.b100.modpacktoolkit;

import java.io.File;
import java.util.List;

import com.google.gson.Gson;

import de.b100.modpacktoolkit.customworldgen.CustomWorldGenMod;
import de.b100.modpacktoolkit.hungertweaks.HungerTweaksMod;
import de.b100.modpacktoolkit.startinv.StarterInventoryMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = ModpackToolkit.MODID, name = ModpackToolkit.NAME, version = ModpackToolkit.VERSION)
public class ModpackToolkit {
	
	public static final String MODID = "modpacktoolkit";
	public static final String NAME = "Modpack Toolkit";
	public static final String VERSION = "1.0";
	
	public static Gson gson = new Gson();

	public static File configFolder;
	public static File modConfigFolder;
	
	public static ModpackToolkitMod starterInventoryMod;
	public static ModpackToolkitMod hungerTweaksMod;
	public static ModpackToolkitMod customWorldGenMod;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		configFolder = event.getModConfigurationDirectory();
		modConfigFolder = new File(configFolder, "ModpackToolkit");
		
		starterInventoryMod = new StarterInventoryMod();
		hungerTweaksMod = new HungerTweaksMod();
		customWorldGenMod = new CustomWorldGenMod();
		
		getMods().forEach(mod -> mod.preInit());
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		getMods().forEach(mod -> mod.init());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		getMods().forEach(mod -> mod.postInit());
	}
	
	@EventHandler
	public void onServerStarting(FMLServerStartingEvent event) {
		getMods().forEach(mod -> mod.onServerStarting(event));
	}
	
	@SuppressWarnings("unchecked")
	public static List<ModpackToolkitMod> getMods(){
		return Utils.getAllObjects(ModpackToolkit.class, ModpackToolkitMod.class, null, false);
	}
}
