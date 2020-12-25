package de.b100.modpacktoolkit.startinv;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class EventHandler {
	
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {
		System.out.println("Login");
		NBTTagCompound playerData = event.player.getEntityData();
		if(!playerData.hasKey(StarterInventoryMod.itemsReceivedTag)) {
			System.out.println("No Tag");
			StarterInventoryMod.giveStarterGear(event.player);
			playerData.setBoolean(StarterInventoryMod.itemsReceivedTag, true);
		}else {
			if(!playerData.getBoolean(StarterInventoryMod.itemsReceivedTag)) {
				System.out.println("Not Received");
				StarterInventoryMod.giveStarterGear(event.player);
				playerData.setBoolean(StarterInventoryMod.itemsReceivedTag, true);
			}else {
				System.out.println("Received");
			}
		}
	}
	
}
