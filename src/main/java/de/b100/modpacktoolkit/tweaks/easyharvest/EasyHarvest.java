package de.b100.modpacktoolkit.tweaks.easyharvest;

import java.util.ArrayList;
import java.util.List;

import de.b100.modpacktoolkit.tweaks.TweaksMod;
import de.b100.modpacktoolkit.tweaks.easyharvest.croptype.ICropType;
import de.b100.modpacktoolkit.tweaks.easyharvest.module.HarvestCraftModule;
import de.b100.modpacktoolkit.tweaks.easyharvest.module.ImmersiveEngineeringModule;
import de.b100.modpacktoolkit.tweaks.easyharvest.module.Module;
import de.b100.modpacktoolkit.tweaks.easyharvest.module.VanillaModule;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Loader;

public class EasyHarvest {
	
	public static List<Module> modules;
	public static List<ICropType> cropTypes;
	
	public static void init() {
		modules = new ArrayList<Module>();
		cropTypes = new ArrayList<ICropType>();
		
		modules.add(new VanillaModule());
		if(Loader.isModLoaded("harvestcraft")) {
			modules.add(new HarvestCraftModule());
		}
		if(Loader.isModLoaded("immersiveengineering")) {
			modules.add(new ImmersiveEngineeringModule());
		}
		
		for(Module module : modules) {
			module.getTypes(cropTypes);
		}
	}
	
	public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
		if(event.getWorld().isRemote)return;
		
		IBlockState state = event.getWorld().getBlockState(event.getPos());
		
		ICropType cropType = isCropAtMaxAge(state);
		if(cropType != null) {
			EntityPlayer player = event.getEntityPlayer();
			
			ItemStack hoe = getHoe(player);
			
			if(TweaksMod.easyHarvestNeedHoe) {
				if(hoe == null){
					return;
				}
			}
			
			if(hoe != null) {
				if(TweaksMod.easyHarvestHoeRange > 0) {
					int r = TweaksMod.easyHarvestHoeRange;
					
					for(int i = -r; i <= r; i++) {
						for(int j = -r; j <= r; j++) {
							BlockPos newPos = event.getPos().add(i, 0, j);
							IBlockState newState = event.getWorld().getBlockState(newPos);
							
							ICropType newCropType = isCropAtMaxAge(newState);
							if(newCropType != null) {
								harvest(cropType, event.getWorld(), newPos, player, hoe);
							}
							
							if(!isHoe(hoe.getItem()))break;
						}
						if(!isHoe(hoe.getItem()))break;
					}
					
				}else {
					harvest(cropType, event.getWorld(), event.getPos(), player, hoe);
				}
				
			}else {
				if(!TweaksMod.easyHarvestNeedHoe) {
					harvest(cropType, event.getWorld(), event.getPos(), player, hoe);
				}
			}
		}
	}
	
	public static ItemStack getHoe(EntityPlayer player) {
		if(isHoe(player.getHeldItemMainhand().getItem())) {
			return player.getHeldItemMainhand();
		}
		if(isHoe(player.getHeldItemOffhand().getItem())) {
			return player.getHeldItemOffhand();
		}
		return null;
	}
	
	public static void harvest(ICropType cropType, World world, BlockPos pos, EntityPlayer player, ItemStack hoe) {
		NonNullList<ItemStack> items = NonNullList.create();
		
		cropType.getModule().harvest(items, cropType, world, pos);
		removeSeed(items);
		
		for(ItemStack itemStack : items) {
			world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemStack));
		}
		
		hoe.damageItem(1, player);
	}
	
	public static void removeSeed(NonNullList<ItemStack> items) {
		for(ItemStack itemStack : items) {
			Item item = itemStack.getItem();
			if(isSeed(item)) {
				itemStack.setCount(itemStack.getCount() - 1);
				return;
			}
		}
		
		ItemStack itemStack = items.get(0);
		itemStack.setCount(itemStack.getCount() - 1);
	}
	
	public static boolean isSeed(Item item) {
		for(Module module : modules) {
			if(module.isSeed(item))return true;
		}
		return item.getUnlocalizedName().contains("seed");
	}
	
	public static ICropType isCropAtMaxAge(IBlockState state) {
		Block block = state.getBlock();
		for(ICropType cropType : cropTypes) {
			if(cropType.getCropBlockClass() == block.getClass()) {
				if(cropType.isAtMaxValue(state)) {
					return cropType;
				}
			}else {
			}
		}
		return null;
	}
	
	public static boolean isHoe(Item item) {
		return item instanceof ItemHoe;
	}
	
	public static void easyHarvest() {
		
	}
	
}
