package de.b100.modpacktoolkit.tweaks.easyharvest.module;

import java.util.List;

import de.b100.modpacktoolkit.tweaks.easyharvest.croptype.AbstractCropType;
import de.b100.modpacktoolkit.tweaks.easyharvest.croptype.CropType;
import de.b100.modpacktoolkit.tweaks.easyharvest.croptype.CropTypeVanillaBeetroot;
import de.b100.modpacktoolkit.tweaks.easyharvest.croptype.CropTypeVanillaWheat;
import net.minecraft.block.BlockCarrot;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockPotato;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class VanillaModule extends Module{

	public boolean isSeed(Item item) {
		return item == Items.WHEAT_SEEDS || item == Items.PUMPKIN_SEEDS || item == Items.MELON_SEEDS || item == Items.BEETROOT_SEEDS;
	}

	public void getTypes(List<AbstractCropType> cropTypes) {
		cropTypes.add(new CropTypeVanillaWheat(this));
		cropTypes.add(new CropType(this, BlockNetherWart.class, BlockNetherWart.AGE, 0, 3, Items.NETHER_WART));
		cropTypes.add(new CropType(this, BlockCarrot.class, BlockCarrot.AGE, 0, 7, Items.CARROT));
		cropTypes.add(new CropType(this, BlockPotato.class, BlockPotato.AGE, 0, 7, Items.POTATO));
		cropTypes.add(new CropTypeVanillaBeetroot(this));
	}

}
