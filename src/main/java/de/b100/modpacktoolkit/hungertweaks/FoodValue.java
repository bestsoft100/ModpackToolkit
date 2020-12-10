package de.b100.modpacktoolkit.hungertweaks;

import java.lang.reflect.Field;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

public class FoodValue {
	
	private ItemFood item;
	private int food;
	private float saturation;
	
	public FoodValue(ItemFood item, int food, float saturation) {
		this.item = item;
		this.food = food;
		this.saturation = saturation;
	}
	
	public Item getItem() {
		return item;
	}
	
	public int getFood() {
		return food;
	}
	
	public float getSaturation() {
		return saturation;
	}

	public void set() {
		setFoodValues(item, food, saturation);
	}
    
    public static void setFoodValues(ItemFood item, int food, float saturation) {
    	try {
			Field healField = getField(ItemFood.class, "healAmount", "field_77853_b");
			Field saturationField = getField(ItemFood.class, "saturationModifier", "field_77854_c");

			healField.setAccessible(true);
			saturationField.setAccessible(true);
			
			healField.set(item, food);
			saturationField.set(item, saturation);
    	}catch (Exception e) {
    		throw new RuntimeException(e);
		}
    }
    
    public static Field getField(Class<?> clazz, String... strings) {
    	for(String string : strings) {
    		try {
				return clazz.getDeclaredField(string);
			} catch (Exception e) {}
    	}
    	return null;
    }
	
}
