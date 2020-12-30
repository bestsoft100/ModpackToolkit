package de.b100.modpacktoolkit.tweaks.easyharvest.croptype;

import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.blocks.plant.BlockIECrop;
import de.b100.modpacktoolkit.tweaks.easyharvest.module.Module;
import de.b100.modpacktoolkit.tweaks.easyharvest.utils.BlockInfo;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class CropTypeImmersiveEngineering extends AbstractCropType{

	public CropTypeImmersiveEngineering(Module module) {
		super(module);
	}
	
	public IProperty<?> getAgeProperty(Block block) {
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
			Object value = state.getValue(getAgeProperty(crop));
			
			if(value == crop.enumValues[crop.enumValues.length - 1] || value == crop.enumValues[crop.enumValues.length - 2]) {
				return true;
			}else {
				return false;
			}
		}else {
			throw new RuntimeException("Invalid Block: "+state.getBlock().getClass().getName());
		}
	}

	public Class<?> getCropBlockClass() {
		return BlockIECrop.class;
	}

	public ItemStack getSeedItem(Block block) {
		return new ItemStack(IEContent.itemSeeds);
	}

	public ItemStack getCropItem(Block block) {
		return new ItemStack(IEContent.itemMaterial, 1, 4);
	}
	
	public void getDrops(NonNullList<ItemStack> items, BlockInfo info) {
		getDrops(items, info, 0, 1, 1, 2);
	}

}
