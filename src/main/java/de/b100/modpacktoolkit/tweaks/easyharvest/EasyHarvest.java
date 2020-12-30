package de.b100.modpacktoolkit.tweaks.easyharvest;

import java.util.ArrayList;
import java.util.List;

import de.b100.modpacktoolkit.tweaks.TweaksMod;
import de.b100.modpacktoolkit.tweaks.easyharvest.croptype.AbstractCropType;
import de.b100.modpacktoolkit.tweaks.easyharvest.module.HarvestCraftModule;
import de.b100.modpacktoolkit.tweaks.easyharvest.module.ImmersiveEngineeringModule;
import de.b100.modpacktoolkit.tweaks.easyharvest.module.Module;
import de.b100.modpacktoolkit.tweaks.easyharvest.module.VanillaModule;
import de.b100.modpacktoolkit.tweaks.easyharvest.utils.BlockInfo;
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
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.Loader;

public class EasyHarvest {
	
	public static List<Module> modules;
	public static List<AbstractCropType> cropTypes;
	
	public static int cropAmountMax = 4;
	public static int cropAmountMin = 1;
	
	public static void init() {
		modules = new ArrayList<Module>();
		cropTypes = new ArrayList<AbstractCropType>();
		
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
	
	public static void onBlockBreak(HarvestDropsEvent event) {
		for(AbstractCropType cropType : cropTypes) {
			if(event.getState().getBlock().getClass() == cropType.getCropBlockClass()) {
				NonNullList<ItemStack> newDrops = NonNullList.create();
				
				cropType.getDrops(newDrops, new BlockInfo(event.getState(), event.getPos(), event.getWorld()));
				
				List<ItemStack> drops = event.getDrops();
				drops.clear();
				
				for(ItemStack itemStack : newDrops) {
					drops.add(itemStack);
				}
				
				return;
			}
		}
	}
	
	public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
		if(event.getWorld().isRemote)return;
		
		IBlockState state = event.getWorld().getBlockState(event.getPos());
		
		AbstractCropType cropType = isCropAtMaxAge(state);
		if(cropType != null) {
			EntityPlayer player = event.getEntityPlayer();
			
			ItemStack hoe = getHoe(player);
			
			boolean needHoe = cropType.isHoeNeeded();
			if(needHoe) {
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
							
							AbstractCropType newCropType = isCropAtMaxAge(newState);
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
				if(!needHoe) {
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
	
	public static void harvest(AbstractCropType cropType, World world, BlockPos pos, EntityPlayer player, ItemStack hoe) {
		boolean needHoe = cropType.isHoeNeeded();
		if(needHoe && hoe == null) {
			return;
		}
		
		NonNullList<ItemStack> items = NonNullList.create();
		
		cropType.getModule().harvest(items, cropType, world, pos);
		removeSeed(items);
		
		for(ItemStack itemStack : items) {
			world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemStack));
		}
		
		if(needHoe) {
			hoe.damageItem(1, player);
		}
	}
	
	public static void removeSeed(NonNullList<ItemStack> items) {
		if(items.size() == 0)return;
		
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
	
	public static AbstractCropType isCropAtMaxAge(IBlockState state) {
		Block block = state.getBlock();
		for(AbstractCropType cropType : cropTypes) {
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
