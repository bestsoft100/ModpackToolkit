package de.b100.modpacktoolkit.tweaks;

import java.lang.reflect.Field;

import de.b100.modpacktoolkit.Utils;
import de.b100.modpacktoolkit.tweaks.easyharvest.EasyHarvest;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TweaksEvents {
	
	private static Field targetClassField = Utils.findField(EntityAINearestAttackableTarget.class, "targetClass", "field_75307_b");
	
	@SubscribeEvent
	public void entitySpawned(EntityJoinWorldEvent event) {
		if(TweaksMod.friendlyEndermite){
			//Code by CreativeMD
			
			if(event.getEntity() instanceof EntityEnderman) {
				EntityEnderman man = (EntityEnderman) event.getEntity();
				
				try {
					for(EntityAITasks.EntityAITaskEntry task : man.tasks.taskEntries) {
						if(task.action instanceof EntityAINearestAttackableTarget && targetClassField.get(task.action) == EntityEndermite.class) {
							man.tasks.removeTask(task.action);
						}
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onRightClick(PlayerInteractEvent.RightClickBlock event) {
		if(TweaksMod.easyHarvest) {
			EasyHarvest.onRightClick(event);
		}
	}
	
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.HarvestDropsEvent event) {
		if(TweaksMod.easyHarvest) {
			EasyHarvest.onBlockBreak(event);
		}
	}
	
}
