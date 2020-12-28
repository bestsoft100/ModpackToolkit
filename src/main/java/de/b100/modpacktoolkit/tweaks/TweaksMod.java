package de.b100.modpacktoolkit.tweaks;

import java.io.File;

import de.b100.modpacktoolkit.ModpackToolkitMod;
import de.b100.modpacktoolkit.tweaks.easyharvest.EasyHarvest;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

public class TweaksMod extends ModpackToolkitMod{
	
	public static Configuration config;
	
	public static boolean friendlyEndermite;
	public static boolean easyHarvest;
	public static boolean easyHarvestNeedHoe;
	public static int easyHarvestHoeRange;
	
	public void preInit() {
		config = new Configuration(new File(getModConfigFolder(), "config.cfg"));
		
		friendlyEndermite = config.getBoolean("friendlyEndermite", "misc", true, "Endermen will no longer attack endermites. No easy XP farm");
		easyHarvest = config.getBoolean("easyHarvest", "misc", true, "Enabled harvesting crops by right-clicking");
		easyHarvestNeedHoe = config.getBoolean("easyHarvestNeedHoe", "misc", true, "Easy harvesting only works while holding a hoe. This will also damage the hoe.");
		easyHarvestHoeRange = config.getInt("easyHarvestHoeRange", "misc", 1, 0, 5, "Increases the radius for easy-harvesting with a hoe. This number is added to the radius. Range = 1 means 3x3, Range = 2 means 5x5...");
		
		config.save();
		
		MinecraftForge.EVENT_BUS.register(new TweaksEvents());
	}
	
	public void init() {
		if(easyHarvest) {
			EasyHarvest.init();
		}
	}
	
	public String getName() {
		return "Tweaks";
	}

}
