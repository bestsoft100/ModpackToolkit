package de.b100.modpacktoolkit.tweaks.easyharvest.module;

import java.util.List;

import com.pam.harvestcraft.HarvestCraft;
import com.pam.harvestcraft.blocks.growables.BlockPamCrop;
import com.pam.harvestcraft.blocks.growables.BlockPamFruit;
import com.pam.harvestcraft.item.items.ItemPamItemSeeds;

import de.b100.modpacktoolkit.ModpackToolkit;
import de.b100.modpacktoolkit.tweaks.easyharvest.croptype.ICropType;
import de.b100.modpacktoolkit.tweaks.easyharvest.croptype.CropType;
import net.minecraft.item.Item;

public class HarvestCraftModule extends Module{
	
	public HarvestCraftModule() {
		if(HarvestCraft.config.enableEasyHarvest) {
			ModpackToolkit.logger.error("HarvestCraft Easy Harvest is enabled! Turn in off so ModpackToolkit EasyHarvest works properly!");
		}
	}

	public boolean isSeed(Item item) {
		return item instanceof ItemPamItemSeeds;
	}

	public void getTypes(List<ICropType> cropTypes) {
		cropTypes.add(new CropType(this, BlockPamCrop.class, BlockPamCrop.CROPS_AGE, 0, 3));
		cropTypes.add(new CropType(this, BlockPamFruit.class, BlockPamFruit.AGE, 0, 3));
	}

}
