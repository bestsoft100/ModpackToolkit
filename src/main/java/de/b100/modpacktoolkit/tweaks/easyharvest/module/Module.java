package de.b100.modpacktoolkit.tweaks.easyharvest.module;

import java.util.List;

import de.b100.modpacktoolkit.Utils;
import de.b100.modpacktoolkit.tweaks.easyharvest.croptype.ICropType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class Module {

	public abstract boolean isSeed(Item item);
	
	public abstract void getTypes(List<ICropType> cropTypes);

	public void harvest(NonNullList<ItemStack> items, ICropType cropType, World world, BlockPos pos) {
		Block block = world.getBlockState(pos).getBlock();
		block.getDrops(items, world, pos, world.getBlockState(pos), 0);
		world.setBlockState(pos, Utils.setValue(world.getBlockState(pos), cropType.getProperty(block), cropType.getMinValue(block)));
	}
	
}
