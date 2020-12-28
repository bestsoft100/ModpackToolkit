package de.b100.modpacktoolkit.tweaks.easyharvest.module;

import java.util.List;

import de.b100.modpacktoolkit.tweaks.easyharvest.croptype.ICropType;
import de.b100.modpacktoolkit.tweaks.easyharvest.croptype.CropType;
import net.minecraft.block.BlockBeetroot;
import net.minecraft.block.BlockCarrot;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockPotato;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class VanillaModule extends Module{

	public boolean isSeed(Item item) {
		return item == Items.WHEAT_SEEDS || item == Items.PUMPKIN_SEEDS || item == Items.MELON_SEEDS || item == Items.BEETROOT_SEEDS;
	}

	public void getTypes(List<ICropType> cropTypes) {
		cropTypes.add(new CropType(this, BlockCrops.class, BlockCrops.AGE, 0, 7));
		cropTypes.add(new CropType(this, BlockNetherWart.class, BlockNetherWart.AGE, 0, 3));
		cropTypes.add(new CropType(this, BlockCarrot.class, BlockCarrot.AGE, 0, 7));
		cropTypes.add(new CropType(this, BlockPotato.class, BlockPotato.AGE, 0, 7));
		cropTypes.add(new CropType(this, BlockBeetroot.class, BlockBeetroot.BEETROOT_AGE, 0, 3));
	}

}
