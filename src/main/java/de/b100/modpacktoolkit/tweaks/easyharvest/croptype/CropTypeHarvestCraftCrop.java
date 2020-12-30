package de.b100.modpacktoolkit.tweaks.easyharvest.croptype;

import com.pam.harvestcraft.blocks.CropRegistry;
import com.pam.harvestcraft.blocks.growables.BlockPamCrop;

import de.b100.modpacktoolkit.tweaks.easyharvest.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class CropTypeHarvestCraftCrop extends AbstractCropType{

	public CropTypeHarvestCraftCrop(Module module) {
		super(module);
	}

	public Class<?> getCropBlockClass() {
		return BlockPamCrop.class;
	}

	public boolean isAtMaxValue(IBlockState state) {
		return state.getValue(BlockPamCrop.CROPS_AGE) == 3;
	}

	public IProperty<?> getAgeProperty(Block block) {
		return BlockPamCrop.CROPS_AGE;
	}

	public Object getMinValue(Block block) {
		return 0;
	}

	public ItemStack getSeedItem(Block block) {
		BlockPamCrop crop = (BlockPamCrop) block;
		return new ItemStack(CropRegistry.getSeed(crop.name));
	}

	public ItemStack getCropItem(Block block) {
		BlockPamCrop crop = (BlockPamCrop) block;
		return new ItemStack(CropRegistry.getFood(crop.name));
	}

}
