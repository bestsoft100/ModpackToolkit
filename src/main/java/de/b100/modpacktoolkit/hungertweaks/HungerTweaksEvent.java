package de.b100.modpacktoolkit.hungertweaks;

import de.b100.modpacktoolkit.Utils;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HungerTweaksEvent {
	
	@SubscribeEvent
	public void onEat(LivingEntityUseItemEvent.Start event) {
		if(HungerTweaksMod.modifyEatingspeed) {
			if(event.getItem().getItem() instanceof ItemFood) {
				ItemStack itemStack = event.getItem();
				ItemFood itemFood = (ItemFood) itemStack.getItem();
				
				int hungerRestore = itemFood.getHealAmount(itemStack);
				
				double hf = hungerRestore / 20.0;
				double speed = Utils.mix(HungerTweaksMod.eatingSpeedSmallItem, HungerTweaksMod.eatingSpeedBigItem, hf);
				
				double newDuration = event.getDuration() * (1.0 / speed);
				
				event.setDuration((int) newDuration);
			}
		}
	}
	
}
