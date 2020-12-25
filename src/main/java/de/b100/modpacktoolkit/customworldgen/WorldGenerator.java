package de.b100.modpacktoolkit.customworldgen;

import java.util.List;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenerator implements IWorldGenerator{
	
	public static WorldGenerator instance;
	
	public static List<Generator> generators;
	public static List<BiomeGroup> biomeGroups;
	
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		
		if(generators == null) {
			return;
		}
		
		for(Generator generator : generators) {
			generator.generate(random, chunkX, chunkZ, world);
		}
	}

}
