package de.b100.modpacktoolkit.tweaks.easyharvest.croptype;

import de.b100.modpacktoolkit.tweaks.easyharvest.module.Module;
import de.b100.modpacktoolkit.tweaks.easyharvest.utils.BlockInfo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class CropTypeVanillaWheat extends AbstractCropType{

	public CropTypeVanillaWheat(Module module) {
		super(module);
	}

	public Class<?> getCropBlockClass() {
		return BlockCrops.class;
	}

	public boolean isAtMaxValue(IBlockState state) {
		return state.getValue(BlockCrops.AGE) == 7;
	}

	public IProperty<?> getAgeProperty(Block block) {
		return BlockCrops.AGE;
	}

	public Object getMinValue(Block block) {
		return 0;
	}

	public ItemStack getSeedItem(Block block) {
		return new ItemStack(Items.WHEAT_SEEDS, 1, 0);
	}

	public ItemStack getCropItem(Block block) {
		return new ItemStack(Items.WHEAT, 1, 0);
	}
	
	public void getDrops(NonNullList<ItemStack> items, BlockInfo info) {
		super.getDrops(items, info, 1, 2, 1, 2);
	}

}
