package de.b100.modpacktoolkit.customworldgen;

import de.b100.modpacktoolkit.ModpackToolkitMod;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CustomWorldGenMod extends ModpackToolkitMod{
	
	public void init() {
		WorldGenerator.instance = new WorldGenerator();
		GameRegistry.registerWorldGenerator(WorldGenerator.instance, 1);
		
		WorldGenerator.biomeGroups = BiomeGroup.loadBiomeGroups(getModConfigFolder());
		WorldGenerator.generators = Generator.loadGenerators(getModConfigFolder());
	}
	
	public String getName() {
		return "CustomWorldGen";
	}

}
