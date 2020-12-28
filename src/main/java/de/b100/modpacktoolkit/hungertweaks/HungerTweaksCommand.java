package de.b100.modpacktoolkit.hungertweaks;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.b100.modpacktoolkit.ModpackToolkit;
import de.b100.modpacktoolkit.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class HungerTweaksCommand extends CommandBase{

	public String getName() {
		return "hungertweaks";
	}

	public String getUsage(ICommandSender sender) {
		return "reload printfood sethunger";
	}

	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("reload")) {
				if(HungerTweaksMod.developerMode) {
					HungerTweaksMod hungerTweaks = (HungerTweaksMod) ModpackToolkit.hungerTweaksMod;
					hungerTweaks.loadConfig();
					
					try{
						new FoodValueLoader();
						
						sender.sendMessage(new TextComponentString("Reloaded!"));
					}catch (Exception e) {
						sender.sendMessage(new TextComponentString("Error! See log for more information."));
						e.printStackTrace();
					}
				}else {
					sendMessage(sender, "To use this command enable developer mode and restart the game");
				}
			}
			if(args[0].equalsIgnoreCase("printfood")) {
				String itemStr = "";
				Collection<Item> items = ForgeRegistries.ITEMS.getValuesCollection();
				List<String> mods = new ArrayList<>();
				List<Item> itemSort = new ArrayList<>();
				
				for(Item item : items) {
					String mod = item.getRegistryName().getResourceDomain();
					
					boolean a = false;
					for(String string : mods) {
						if(mod.equals(string)) {
							a = true;
							break;
						}
					}
					if(!a)mods.add(mod);
				}
				
				for(String mod : mods) {
					for(Item item : items) {
						if(item instanceof ItemFood) {
							String itemMod = item.getRegistryName().getResourceDomain();
							
							if(mod.equals(itemMod)) {
								itemSort.add(item);
							}
						}
					}
				}
				
				boolean first = true;
				for(Item item : itemSort) {
					if(!first) {
						itemStr += "\n";
					}
					
					itemStr += item.getRegistryName();
					
					first = false;
				}
				
				Utils.saveFile(new File(ModpackToolkit.hungerTweaksMod.getModConfigFolder(), "allFoodItems.txt"), itemStr);
				sender.sendMessage(new TextComponentString("Done!"));
			}
			if(args[0].equalsIgnoreCase("sethunger")) {
				if(args.length == 1) {
					sendMessage(sender, "sethunger [hunger] <saturation>");
				}
				if(sender instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) sender;
					try {
						int hunger = Integer.parseInt(args[1]);
						player.getFoodStats().setFoodLevel(hunger);
						if(args.length > 2) {
							float saturation = Float.parseFloat(args[2]);
							player.getFoodStats().setFoodSaturationLevel(saturation);
						}
					}catch (Exception e) {
						sendMessage(sender, "Invalid value!");
					}
				}else {
					sendMessage(sender, "Must be used by a player");
				}
			}
		}
	}
	
	public int getRequiredPermissionLevel() {
		return 4;
	}
	
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		if(args.length == 0) {
			return stringList("reload", "printFoods");
		}
		return null;
	}
	
	public static List<String> stringList(String...strings){
		List<String> list = new ArrayList<String>();
		
		for(String string : strings) {
			list.add(string);
		}
		
		return list;
	}
	
	public void sendMessage(ICommandSender sender, String msg) {
		sender.sendMessage(new TextComponentString(msg));
	}

}
