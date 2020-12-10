package de.b100.modpacktoolkit.customworldgen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BiomeGroup {
	
	private String name;
	private List<Biome> biomes;
	
	public BiomeGroup(String name) {
		this.name = name;
		biomes = new ArrayList<Biome>();
	}
	
	public void add(Biome biome) {
		this.biomes.add(biome);
	}
	
	public boolean contains(Biome biome) {
		for(Biome biome2 : biomes) {
			if(biome == biome2) {
				return true;
			}
		}
		return false;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Biome> getBiomeList(){
		return biomes;
	}
	
	public static List<BiomeGroup> loadBiomeGroups(){
		List<BiomeGroup> biomeGroups = new ArrayList<>();
		
		File biomeGroupFolder = new File(CustomWorldGenMod.modConfigFolder, "biomeGroups");
		if(!(biomeGroupFolder.exists() && biomeGroupFolder.isDirectory())) {
			return biomeGroups;
		}
		
		File[] files = biomeGroupFolder.listFiles();
		for(File biomeGroupFile : files) {
			BiomeGroup biomeGroup = new BiomeGroup(Utils.getFilenameWithoutExtension(biomeGroupFile));
			biomeGroups.add(biomeGroup);
			
			if(biomeGroupFile.isFile()) {
				String[] biomeGroupStr = Utils.loadFile(biomeGroupFile).split("\n");
				
				for(String biomeStr : biomeGroupStr) {
					Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(biomeStr));
					if(biome != null) {
						biomeGroup.add(biome);
					}
				}
			}
		}
		
		return biomeGroups;
	}

	public static BiomeGroup get(JsonElement jsonElement) {
		if(jsonElement.isJsonObject()) {
			JsonObject obj = (JsonObject) jsonElement;
			
			if(obj.has("group")) {
				JsonElement groupElement = obj.get("group");
				
				if(groupElement.isJsonPrimitive()) {
					String biomeGroupName = groupElement.getAsString();
					
					for(BiomeGroup biomeGroup : WorldGenerator.biomeGroups) {
						if(biomeGroup.getName().equals(biomeGroupName)) {
							return biomeGroup;
						}
					}
				}
				if(groupElement.isJsonArray()) {
					BiomeGroup newGroup = new BiomeGroup("");
					JsonArray arr = groupElement.getAsJsonArray();
					
					for(int i=0; i < arr.size(); i++) {
						String definedBiomeGroupName = arr.get(i).getAsString();
						
						for(BiomeGroup biomeGroup : WorldGenerator.biomeGroups) {
							if(biomeGroup.getName().equals(definedBiomeGroupName)) {
								newGroup.addAll(biomeGroup);
							}
						}
					}
					
					return newGroup;
				}
			}
		}
		return null;
	}

	public void addAll(BiomeGroup biomeGroup) {
		for(int i=0; i < biomeGroup.getBiomeList().size(); i++) {
			this.biomes.add(biomeGroup.getBiomeList().get(i));
		}
	}
	
}
