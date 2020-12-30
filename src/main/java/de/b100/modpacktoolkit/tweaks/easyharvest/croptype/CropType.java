package de.b100.modpacktoolkit.tweaks.easyharvest.croptype;

import de.b100.modpacktoolkit.tweaks.easyharvest.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CropType extends AbstractCropType{
	
	private Class<?> cropBlockClass;
	private IProperty<?> property;
	private Object minValue;
	private Object maxValue;
	private Item seedItem;
	private Item cropItem;
	
	public CropType(Module module, Class<?> cropBlockClass, IProperty<?> ageProperty, Object minValue, Object maxValue, Item seedItem, Item cropItem) {
		super(module);
		this.cropBlockClass = cropBlockClass;
		this.property = ageProperty;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.seedItem = seedItem;
		this.cropItem = cropItem;
	}
	
	public CropType(Module module, Class<?> cropBlockClass, IProperty<?> ageProperty, Object minValue, Object maxValue, Item cropItem) {
		this(module, cropBlockClass, ageProperty, minValue, maxValue, cropItem, cropItem);
	}
	
	public IProperty<?> getAgeProperty(Block block) {
		return property;
	}
	
	public Object getMinValue(Block block) {
		return minValue;
	}
	
	public boolean isAtMaxValue(IBlockState state) {
		return state.getValue(property) == maxValue;
	}

	public Class<?> getCropBlockClass() {
		return cropBlockClass;
	}

	public ItemStack getSeedItem(Block block) {
		return new ItemStack(seedItem);
	}

	public ItemStack getCropItem(Block block) {
		return new ItemStack(cropItem);
	}
	
}
