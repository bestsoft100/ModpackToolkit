package de.b100.modpacktoolkit.customworldgen;

import java.io.File;

import de.b100.modpacktoolkit.ModpackToolkitMod;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class CustomWorldGenMod {
	
	public static File modConfigFolder;
	
	public static void preInit() {
		modConfigFolder = new File(ModpackToolkitMod.configFolder, "CustomWorldGen");
		
		WorldGenerator.instance = new WorldGenerator();
		GameRegistry.registerWorldGenerator(WorldGenerator.instance, 1);
	}
	
	public static void init() {
		WorldGenerator.biomeGroups = BiomeGroup.loadBiomeGroups();
		WorldGenerator.generators = Generator.loadGenerators();
	}

}
