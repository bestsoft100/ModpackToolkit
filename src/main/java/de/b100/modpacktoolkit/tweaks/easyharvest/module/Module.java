package de.b100.modpacktoolkit.tweaks.easyharvest.module;

import java.util.List;

import de.b100.modpacktoolkit.Utils;
import de.b100.modpacktoolkit.tweaks.easyharvest.croptype.AbstractCropType;
import de.b100.modpacktoolkit.tweaks.easyharvest.utils.BlockInfo;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class Module {

	public abstract boolean isSeed(Item item);
	
	public abstract void getTypes(List<AbstractCropType> cropTypes);

	public void harvest(NonNullList<ItemStack> items, AbstractCropType cropType, World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		cropType.getDrops(items, new BlockInfo(state, pos, world));
		world.setBlockState(pos, Utils.setValue(world.getBlockState(pos), cropType.getAgeProperty(block), cropType.getMinValue(block)));
	}
	
}
