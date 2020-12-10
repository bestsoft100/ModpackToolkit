package de.b100.modpacktoolkit.customworldgen;

import java.util.List;
import java.util.Random;

import com.google.gson.JsonObject;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class PlantGenerator extends Generator{

	public List<IBlockState> plants;
	public IBlockState ground;
	public IBlockState soil;
	
	public int rarity = 1, min = 1, max = 0, radius = 3;
	
	public BiomeGroup biomes;
	
	public PlantGenerator(String name, JsonObject object) {
		super(name);
		
		ground = JsonUtils.getBlockState(object.get("ground"));
		soil = JsonUtils.getBlockState(object.get("soil"));
		plants = JsonUtils.getBlockStates(object.get("plants"));
		rarity = object.get("rarity").getAsInt();
		min = object.get("min").getAsInt();
		max = object.get("max").getAsInt();
		radius = object.get("radius").getAsInt();
		
		if(object.has("biome")) {
			biomes = BiomeGroup.get(object.get("biome"));
		}
		
		
		if(ground == null) {
			throw new RuntimeException();
		}
		if(soil == null) {
			throw new RuntimeException();
		}
		if(plants.size() < 1) {
			throw new RuntimeException();
		}
		if(rarity < 1) {
			throw new RuntimeException();
		}
		if(max > 0 && !(max > min)) {
			throw new RuntimeException();
		}
		if(radius < 0 || radius > 7) {
			throw new RuntimeException();
		}
	}
	
	public void generate(Random random, int chunkX, int chunkZ, World world) {
		if(random.nextInt(rarity) == 0) {
			int centerX = random.nextInt(16 - 2*radius) + radius;
			int centerZ = random.nextInt(16 - 2*radius) + radius;
			
			if(biomes != null) {
				Biome biome = world.getBiome(new BlockPos(chunkX * 16 + centerX, 0, chunkZ * 16 + centerZ));
				if(!biomes.contains(biome)) {
					return;
				}
			}
			
			IBlockState plant = plants.get(random.nextInt(plants.size()));
			for(int i=10; i < 245; i++) {
				BlockPos pos = new BlockPos(chunkX * 16 + centerX, i, chunkZ * 16 + centerZ);
				
				if(world.getBlockState(pos).equals(ground)) {
					generate2(random, pos, world, plant);
				}
			}
		}
	}
	
	public void generate2(Random random, BlockPos center, World world, IBlockState plant) {
		int plantCount = this.min;
		if(max > 0) {
			plantCount += random.nextInt(max - min);
		}
		
		for(int i=0; i < plantCount; i++) {
			int plantX = random.nextInt(radius * 2 - 1) - radius;
			int plantZ = random.nextInt(radius * 2 - 1) - radius;
			
			for(int j=-radius; j < radius; j++) {
				BlockPos groundPos = new BlockPos(center.getX() + plantX, center.getY() + j, center.getZ() + plantZ);
				
				if(world.getBlockState(groundPos).equals(ground)) {
					world.setBlockState(groundPos, soil);
					world.setBlockState(groundPos.up(), plant);
					break;
				}
			}
		}
	}
	/*
	public void generate(Random random, int chunkX, int chunkZ, World world) {
		if(random.nextInt(rarity) == 0) {
			int x = random.nextInt(6) + 5;
			int z = random.nextInt(6) + 5;
			
			if(biomes != null) {
				Biome biome = world.getBiome(new BlockPos(chunkX * 16 + x, 0, chunkZ * 16 + z));
				
				if(!biomes.contains(biome)) {
					return;
				}
			}
			
			IBlockState plant = plants.get(random.nextInt(plants.size()));
			for(int i=0; i < 250; i++) {
				BlockPos center = new BlockPos(chunkX * 16 + x, i, chunkZ * 16 + z);
				if(world.getBlockState(center) == ground) {
					int count = this.min;
					if(max > 0) {
						count += random.nextInt(max - min);
					}
					
					for(int j=0; j < count; j++) {
						int dx = random.nextInt(radius * 2 - 1) - this.radius;
						int dz = random.nextInt(radius * 2 - 1) - this.radius;
						
						for(int k = -this.radius; k < this.radius; k++) {
							BlockPos pos = new BlockPos(chunkX * 16 + x + dx, i + k, chunkZ * 16 + z + dz);
							if(world.getBlockState(pos) == ground) {
								world.setBlockState(pos, soil);
								world.setBlockState(pos.up(), plant);
								break;
							}
						}
					}
				}
			}
		}
	}
	*/

	
}
