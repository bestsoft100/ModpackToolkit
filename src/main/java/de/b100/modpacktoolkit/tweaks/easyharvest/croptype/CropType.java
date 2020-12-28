package de.b100.modpacktoolkit.tweaks.easyharvest.croptype;

import de.b100.modpacktoolkit.tweaks.easyharvest.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

public class CropType implements ICropType{
	
	private Module module;
	private Class<?> cropBlockClass;
	private IProperty<?> property;
	private Object minValue;
	private Object maxValue;
	
	public CropType(Module module, Class<?> cropBlockClass, IProperty<?> ageProperty, Object minValue, Object maxValue) {
		this.module = module;
		this.cropBlockClass = cropBlockClass;
		this.property = ageProperty;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
	
	public IProperty<?> getProperty(Block block) {
		return property;
	}
	
	public Object getMinValue(Block block) {
		return minValue;
	}
	
	public boolean isAtMaxValue(IBlockState state) {
		return state.getValue(property) == maxValue;
	}

	public Module getModule() {
		return module;
	}

	public Class<?> getCropBlockClass() {
		return cropBlockClass;
	}
	
}
