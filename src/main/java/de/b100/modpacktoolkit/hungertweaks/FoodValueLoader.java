package de.b100.modpacktoolkit.hungertweaks;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.b100.modpacktoolkit.ModpackToolkit;
import de.b100.modpacktoolkit.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class FoodValueLoader {
	
	private File foodValuesFile;
	
	private List<FoodValue> loadedFoodValues;
	
	public FoodValueLoader() {
		foodValuesFile = new File(ModpackToolkit.hungerTweaksMod.getModConfigFolder(), "foodValues.txt");
		
		if(!foodValuesFile.exists()) {
			Utils.createFile(foodValuesFile);
			return;
		}
		
		loadFoodValues();
		
		Collection<Item> allItems = ForgeRegistries.ITEMS.getValuesCollection();
		
		for(Item item : allItems) {
			if(item instanceof ItemFood) {
				ItemFood itemFood = (ItemFood) item;
				
				FoodValue foodValue = getFoodValueForItem(loadedFoodValues, itemFood);
				
				
				
				if(foodValue == null) {
					if(!HungerTweaksMod.developerMode) {
						foodValue = new FoodValue(itemFood, (int)(itemFood.getHealAmount(new ItemStack(itemFood)) * HungerTweaksMod.defaultHungerModifier),
								itemFood.getSaturationModifier(new ItemStack(itemFood)) * HungerTweaksMod.defaultSaturationModifier);
					}else {
						FoodValue defaultFoodValue = getFoodValueForItem(HungerTweaksMod.defaultFoodValues, itemFood);
						
						foodValue = new FoodValue(itemFood, (int)(defaultFoodValue.getFood() * HungerTweaksMod.defaultHungerModifier),
								defaultFoodValue.getSaturation() * HungerTweaksMod.defaultSaturationModifier);
					}
				}
				
				foodValue.set();
			}
		}
	}
	
	private void loadFoodValues() {
		loadedFoodValues = new ArrayList<FoodValue>();
		
		String[] lines = Utils.loadFile(foodValuesFile).split("\n");
		
		for(String line : lines) {
			if(line.length() > 0 && line.charAt(0) == '#')continue;
			
			String[] args = line.split(",");
			
			String id = args[0];
			ItemFood item = getFoodItem(id);
			
			if(item == null) {
				continue;
			}
			
			int heal = Integer.parseInt(args[1]);
			float saturation = Float.parseFloat(args[2]);
			
			loadedFoodValues.add(new FoodValue(item, heal, saturation));
		}
	}
	
	public static ItemFood getFoodItem(String id) {
		Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
		if(item != null && item instanceof ItemFood) {
			return (ItemFood) item;
		}
		return null;
	}
	
	public static FoodValue getFoodValueForItem(List<FoodValue> foodValues, ItemFood item) {
		for(FoodValue foodValue : foodValues) {
			if(foodValue.getItem() == item) {
				return foodValue;
			}
		}
		return null;
	}
	
}
