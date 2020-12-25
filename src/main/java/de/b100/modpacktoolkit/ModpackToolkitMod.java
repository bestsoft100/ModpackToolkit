package de.b100.modpacktoolkit;

import java.io.File;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public abstract class ModpackToolkitMod {
	
	private File modConfigFolder;
	
	public ModpackToolkitMod() {
		modConfigFolder = new File(ModpackToolkit.modConfigFolder, getName());
	}
	
	public void preInit() {}
	
	public void init() {}
	
	public void postInit() {}
	
	public void onServerStarting(FMLServerStartingEvent event) {}
	
	public File getModConfigFolder() {
		return modConfigFolder;
	}
	
	public abstract String getName();
	
}
