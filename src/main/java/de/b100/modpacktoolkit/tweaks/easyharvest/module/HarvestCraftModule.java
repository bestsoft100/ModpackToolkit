package de.b100.modpacktoolkit.tweaks.easyharvest.module;

import java.util.List;

import com.pam.harvestcraft.HarvestCraft;
import com.pam.harvestcraft.item.items.ItemPamItemSeeds;

import de.b100.modpacktoolkit.ModpackToolkit;
import de.b100.modpacktoolkit.tweaks.easyharvest.croptype.AbstractCropType;
import de.b100.modpacktoolkit.tweaks.easyharvest.croptype.CropTypeHarvestCraftFruitTree;
import de.b100.modpacktoolkit.tweaks.easyharvest.croptype.CropTypeHarvestCraftCrop;
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

	public void getTypes(List<AbstractCropType> cropTypes) {
		cropTypes.add(new CropTypeHarvestCraftCrop(this));
		cropTypes.add(new CropTypeHarvestCraftFruitTree(this));
	}
	
	

}
