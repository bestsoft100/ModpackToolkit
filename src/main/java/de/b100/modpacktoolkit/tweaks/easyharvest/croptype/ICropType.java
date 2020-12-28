package de.b100.modpacktoolkit.tweaks.easyharvest.croptype;

import de.b100.modpacktoolkit.tweaks.easyharvest.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

public interface ICropType {
	
	public Module getModule();
	
	public Class<?> getCropBlockClass();
	
	public abstract boolean isAtMaxValue(IBlockState state);
	
	public abstract IProperty<?> getProperty(Block block);
	
	public abstract Object getMinValue(Block block);
	
}
