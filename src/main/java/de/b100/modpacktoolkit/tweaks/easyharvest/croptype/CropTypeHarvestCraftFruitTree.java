package de.b100.modpacktoolkit.tweaks.easyharvest.croptype;

import com.pam.harvestcraft.blocks.growables.BlockPamFruit;

import de.b100.modpacktoolkit.tweaks.easyharvest.module.Module;
import de.b100.modpacktoolkit.tweaks.easyharvest.utils.BlockInfo;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class CropTypeHarvestCraftFruitTree extends AbstractCropType{

	public CropTypeHarvestCraftFruitTree(Module module) {
		super(module);
	}
	
	public Class<?> getCropBlockClass() {
		return BlockPamFruit.class;
	}

	public boolean isAtMaxValue(IBlockState state) {
		return state.getValue(BlockPamFruit.AGE) == 2;
	}

	public IProperty<?> getAgeProperty(Block block) {
		if(block instanceof BlockPamFruit) {
			return BlockPamFruit.AGE;
		}
		throw new RuntimeException();
	}

	public Object getMinValue(Block block) {
		return 0;
	}
	
	public boolean isHoeNeeded() {
		return false;
	}

	public ItemStack getSeedItem(Block block) {
		return getCropItem(block);
	}

	public ItemStack getCropItem(Block block) {
		BlockPamFruit fruit = (BlockPamFruit) block;
		return new ItemStack(fruit.getFruitItem());
	}
	
	public void getDrops(NonNullList<ItemStack> items, BlockInfo info) {
		getDrops(items, info, 0, 0, 2, 3);
	}
	
}