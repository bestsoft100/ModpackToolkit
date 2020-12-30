package de.b100.modpacktoolkit.tweaks.easyharvest.croptype;

import de.b100.modpacktoolkit.tweaks.easyharvest.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBeetroot;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CropTypeVanillaBeetroot extends CropTypeVanillaWheat{

	public CropTypeVanillaBeetroot(Module module) {
		super(module);
	}
	
	public Class<?> getCropBlockClass() {
		return BlockBeetroot.class;
	}
	
	public boolean isAtMaxValue(IBlockState state) {
		return state.getValue(BlockBeetroot.BEETROOT_AGE) == 3;
	}
	
	public IProperty<?> getAgeProperty(Block block) {
		return BlockBeetroot.BEETROOT_AGE;
	}

	public ItemStack getSeedItem(Block block) {
		return new ItemStack(Items.BEETROOT_SEEDS, 1, 0);
	}

	public ItemStack getCropItem(Block block) {
		return new ItemStack(Items.BEETROOT, 1, 0);
	}

}
