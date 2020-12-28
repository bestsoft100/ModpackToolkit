package de.b100.modpacktoolkit.tweaks.easyharvest.croptype;

import blusunrize.immersiveengineering.common.blocks.plant.BlockIECrop;
import de.b100.modpacktoolkit.tweaks.easyharvest.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

public class CropTypeImmersiveEngineering implements ICropType{

	private Module module;
	
	public CropTypeImmersiveEngineering(Module module) {
		this.module = module;
	}
	
	public IProperty<?> getProperty(Block block) {
		if(block instanceof BlockIECrop) {
			BlockIECrop<?> crop = (BlockIECrop<?>) block;
			
			return crop.getMetaProperty();
		}
		throw new RuntimeException("Invalid Block: "+block.getClass().getName());
	}
	
	public Object getMinValue(Block block) {
		if(block instanceof BlockIECrop) {
			BlockIECrop<?> crop = (BlockIECrop<?>) block;
			
			return crop.enumValues[0];
		}
		throw new RuntimeException("Invalid Block: "+block.getClass().getName());
	}
	
	public Object getMaxValue(Block block) {
		if(block instanceof BlockIECrop) {
			BlockIECrop<?> crop = (BlockIECrop<?>) block;
			
			return crop.enumValues[crop.enumValues.length - 1];
		}
		throw new RuntimeException("Invalid Block: "+block.getClass().getName());
	}
	
	public boolean isAtMaxValue(IBlockState state) {
		if(state.getBlock() instanceof BlockIECrop) {
			BlockIECrop<?> crop = (BlockIECrop<?>) state.getBlock();
			Object value = state.getValue(getProperty(crop));
			
			if(value == crop.enumValues[crop.enumValues.length - 1] || value == crop.enumValues[crop.enumValues.length - 2]) {
				return true;
			}else {
				return false;
			}
		}else {
			throw new RuntimeException("Invalid Block: "+state.getBlock().getClass().getName());
		}
	}

	public Module getModule() {
		return module;
	}

	public Class<?> getCropBlockClass() {
		return BlockIECrop.class;
	}

}
