package de.b100.modpacktoolkit.hungertweaks;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
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
		return "reload";
	}

	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("reload")) {
				
				HungerTweaks.loadConfig();
				
				try{
					new FoodValueLoader();
					
					sender.sendMessage(new TextComponentString("Reloaded!"));
				}catch (Exception e) {
					sender.sendMessage(new TextComponentString("Error! See log for more information."));
					e.printStackTrace();
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
				
				Utils.saveFile(new File(HungerTweaks.modConfigFolder, "allFoodItems.txt"), itemStr);
				sender.sendMessage(new TextComponentString("Done!"));
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

}
