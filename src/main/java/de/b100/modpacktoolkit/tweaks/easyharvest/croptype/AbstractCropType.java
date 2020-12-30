package de.b100.modpacktoolkit.tweaks.easyharvest.croptype;

import java.util.Random;

import de.b100.modpacktoolkit.tweaks.TweaksMod;
import de.b100.modpacktoolkit.tweaks.easyharvest.EasyHarvest;
import de.b100.modpacktoolkit.tweaks.easyharvest.module.Module;
import de.b100.modpacktoolkit.tweaks.easyharvest.utils.BlockInfo;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public abstract class AbstractCropType {
	
	private Module module;
	
	public AbstractCropType(Module module) {
		this.module = module;
	}
	
	public final Module getModule() {
		return module;
	}
	
	public abstract Class<?> getCropBlockClass();
	
	public abstract boolean isAtMaxValue(IBlockState state);
	
	public abstract IProperty<?> getAgeProperty(Block block);
	
	public abstract Object getMinValue(Block block);
	
	public boolean isHoeNeeded() {
		return TweaksMod.easyHarvestNeedHoe;
	}
	
	public void getDrops(NonNullList<ItemStack> items, BlockInfo info) {
		getDrops(items, info, 0, 0, EasyHarvest.cropAmountMin, EasyHarvest.cropAmountMax);
	}
	
	protected final void getDrops(NonNullList<ItemStack> items, BlockInfo info, int seedMin, int seedMax, int cropMin, int cropMax) {
		IBlockState state = info.getState();
		World world = info.getWorld();
		
		ItemStack crop = getCropItem(state.getBlock());
		ItemStack seed = getSeedItem(state.getBlock());
		
		if(isAtMaxValue(state)) {
			Random random = world.rand;
			
			int cropAmount = getRandomAmount(random, cropMin, cropMax);
			int seedAmount = getRandomAmount(random, seedMin, seedMax);
			
			if(cropAmount > 0)
				items.add(new ItemStack(crop.getItem(), cropAmount, crop.getMetadata()));
			if(seedAmount > 0)
				items.add(new ItemStack(seed.getItem(), seedAmount, seed.getMetadata()));
		}
		else
		{
			items.add(new ItemStack(seed.getItem(), 1, seed.getMetadata()));
		}
	}
	
	public abstract ItemStack getSeedItem(Block block);
	
	public abstract ItemStack getCropItem(Block block);
	
	public static int getRandomAmount(Random random, int min, int max) {
		if(min >= max)return max;
		return random.nextInt((max - min)+1) + min;
	}
	
}
