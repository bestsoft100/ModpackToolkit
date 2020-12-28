package de.b100.modpacktoolkit.tweaks.easyharvest.module;

import java.util.List;

import blusunrize.immersiveengineering.common.blocks.plant.BlockIECrop;
import blusunrize.immersiveengineering.common.items.ItemIESeed;
import de.b100.modpacktoolkit.Utils;
import de.b100.modpacktoolkit.tweaks.easyharvest.croptype.CropTypeImmersiveEngineering;
import de.b100.modpacktoolkit.tweaks.easyharvest.croptype.ICropType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ImmersiveEngineeringModule extends Module{

	public boolean isSeed(Item item) {
		return item instanceof ItemIESeed;
	}

	public void getTypes(List<ICropType> cropTypes) {
		cropTypes.add(new CropTypeImmersiveEngineering(this));
	}
	
	public void harvest(NonNullList<ItemStack> items, ICropType cropType, World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		
		if(state.getBlock() instanceof BlockIECrop) {
			BlockIECrop<?> crop = (BlockIECrop<?>) state.getBlock();
			
			Object topValue = crop.enumValues[crop.enumValues.length - 1];
			Object bottomValue = crop.enumValues[crop.enumValues.length - 2];
			
			@SuppressWarnings("unchecked")
			Object value = state.getValue(crop.getMetaProperty());
			
			if(value == topValue) {
				harvestPlant(items, cropType, world, pos.down());
			}
			if(value == bottomValue) {
				harvestPlant(items, cropType, world, pos);
			}
		}
	}
	
	public static void harvestPlant(NonNullList<ItemStack> items, ICropType cropType, World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		
		BlockPos posUp = pos.up();
		IBlockState stateUp = world.getBlockState(posUp);
		
		state.getBlock().getDrops(items, world, pos, state, 0);
		
		if(cropType.isAtMaxValue(stateUp)) {
			stateUp.getBlock().getDrops(items, world, posUp, stateUp, 0);
			world.setBlockState(posUp, Blocks.AIR.getDefaultState());
		}
		
		world.setBlockState(pos, Utils.setValue(state, cropType.getProperty(block), cropType.getMinValue(block)));
	}

}
