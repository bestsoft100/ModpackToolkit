package de.b100.modpacktoolkit.customworldgen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.b100.modpacktoolkit.ModpackToolkit;
import de.b100.modpacktoolkit.Utils;
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
	
	public static List<Generator> loadGenerators(File worldGenConfigFolder){
		System.out.println("Loading Generators...");
		File generatorsFolder = new File(worldGenConfigFolder, "generators");
		if(!generatorsFolder.exists()) {
			generatorsFolder.mkdirs();
			return null;
		}
		
		List<Generator> generators = new ArrayList<Generator>();
		File[] files = generatorsFolder.listFiles();
		if(files == null || files.length == 0) {
			return null;
		}
		
		for(File file : files) {
			JsonObject json = ModpackToolkit.gson.fromJson(Utils.loadFile(file), JsonObject.class);
			
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
