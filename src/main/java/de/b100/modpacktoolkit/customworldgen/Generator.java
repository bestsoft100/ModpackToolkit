package de.b100.modpacktoolkit.customworldgen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.b100.modpacktoolkit.ModpackToolkitMod;
import net.minecraft.world.World;

public abstract class Generator {
	
	private String name;
	
	public Generator(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract void generate(Random random, int chunkX, int chunkZ, World world);
	
	public static List<Generator> loadGenerators(){
		System.out.println("Loading Generators...");
		File generatorsFolder = new File(CustomWorldGenMod.modConfigFolder, "generators");
		
		List<Generator> generators = new ArrayList<Generator>();
		
		for(File file : generatorsFolder.listFiles()) {
			JsonObject json = ModpackToolkitMod.gson.fromJson(Utils.loadFile(file), JsonObject.class);
			
			Set<Entry<String, JsonElement>> generatorSet = json.entrySet();
			
			for(Entry<String, JsonElement> entry : generatorSet) {
				JsonObject object = entry.getValue().getAsJsonObject();
				generators.add(getGeneratorByType(object.get("type").getAsString(), entry.getKey(), object));
			}
			
		}
		
		return generators;
	}
	
	public static Generator getGeneratorByType(String type, String name, JsonObject object) {
		if(type.equals("plant")) {
			return new PlantGenerator(name, object);
		}
		throw new RuntimeException("Invalid GeneratorType: "+type);
	}
	
}
