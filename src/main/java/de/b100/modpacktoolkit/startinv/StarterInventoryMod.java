package de.b100.modpacktoolkit.startinv;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.b100.modpacktoolkit.ModpackToolkitMod;
import de.b100.modpacktoolkit.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class StarterInventoryMod extends ModpackToolkitMod{

	public static String itemsReceivedTag = "gotStarterItems";
	
	public static List<ItemStack> starterItems;
	
	private File starterItemsFile;
	
	public void preInit() {
		starterItemsFile = new File(getModConfigFolder(), "starter_items.txt");
		
		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}

	public void init() {
		loadConfig();
	}

	public void loadConfig() {
		starterItems = new ArrayList<ItemStack>();
		
		if(!starterItemsFile.exists()) {
			Utils.createFile(starterItemsFile);
			
			Utils.saveFile(starterItemsFile, "#ID, Amount, Meta\nminecraft:stone_pickaxe,1,0");
		}
		
		String[] lines = Utils.loadFile(starterItemsFile).split("\n");
		
		for(String line : lines) {
			if(line.startsWith("#"))continue;
			String[] args = line.split(",");
			
			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(args[0]));
			int amount = 1;
			int meta = 0;
			if(args.length > 1) {
				amount = Integer.parseInt(Utils.clipWhitespace(args[1]));
			}
			if(args.length > 2) {
				meta = Integer.parseInt(Utils.clipWhitespace(args[2]));
			}
			
			starterItems.add(new ItemStack(item, amount, meta));
		}
	}
	
	public void onServerStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new StarterInventoryCommand());
	}
	
	public static void giveStarterGear(EntityPlayer player) {
		if(starterItems == null)return;
		
		for(ItemStack itemStack : starterItems) {
			player.inventory.addItemStackToInventory(new ItemStack(itemStack.getItem(), itemStack.getCount(), itemStack.getMetadata()));
		}
	}

	public String getName() {
		return "StarterInventory";
	}
	
	
	
}
