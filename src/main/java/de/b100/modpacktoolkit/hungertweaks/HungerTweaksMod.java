package de.b100.modpacktoolkit.hungertweaks;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.b100.modpacktoolkit.ModpackToolkitMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class HungerTweaksMod extends ModpackToolkitMod{
	
	public File configFile;

	private Configuration config;

	public static boolean developerMode;
	public static float defaultHungerModifier;
	public static float defaultSaturationModifier;

	public static List<FoodValue> defaultFoodValues;

	public void preInit() {
		configFile = new File(getModConfigFolder(), "hungertweaks.cfg");

		loadConfig();
	}

	public void loadConfig() {
		config = new Configuration(configFile);

		if (!developerMode) {
			developerMode = config.getBoolean("developerMode", "misc", false,
					"Allows reloading food values in-game with /hungertweaks reload, but needs more ram");
		}
		defaultHungerModifier = config.getFloat("defaultHungerModifier", "misc", 1.0f, 0.0f, Float.MAX_VALUE,
				"Food values not manually set get multiplied by this");
		defaultSaturationModifier = config.getFloat("defaultSaturationModifier", "misc", 1.0f, 0.0f, Float.MAX_VALUE,
				"Saturation values not manually set get multiplied by this");

		config.save();

	}

	public void init() {
		if (developerMode) {
			defaultFoodValues = new ArrayList<>();

			Collection<Item> items = ForgeRegistries.ITEMS.getValuesCollection();

			for (Item item : items) {
				if (item instanceof ItemFood) {
					ItemFood food = (ItemFood) item;

					defaultFoodValues.add(new FoodValue(food, food.getHealAmount(new ItemStack(food)),
							food.getSaturationModifier(new ItemStack(food))));
				}
			}
		}

		new FoodValueLoader();
	}

	public void serverStart(FMLServerStartingEvent e) {
		if (developerMode) {
			e.registerServerCommand(new HungerTweaksCommand());
		}
	}

	public static void createFile(File file) {
		if (file.exists())
			return;

		File folder = file.getAbsoluteFile().getParentFile();
		if (!folder.exists()) {
			folder.mkdirs();
		}

		try {
			file.createNewFile();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String testMethod() {
		return "defaultText";
	}

	public String getName() {
		return "HungerTweaks";
	}
}
