package de.b100.modpacktoolkit.customworldgen;

import de.b100.modpacktoolkit.ModpackToolkitMod;

public class CustomWorldGenMod extends ModpackToolkitMod{
	
	public void init() {
		WorldGenerator.biomeGroups = BiomeGroup.loadBiomeGroups(getModConfigFolder());
		WorldGenerator.generators = Generator.loadGenerators(getModConfigFolder());
		
	}
	
	public String getName() {
		return "CustomWorldGen";
	}

}
