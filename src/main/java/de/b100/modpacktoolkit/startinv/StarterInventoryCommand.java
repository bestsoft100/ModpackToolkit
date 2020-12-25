package de.b100.modpacktoolkit.startinv;

import de.b100.modpacktoolkit.ModpackToolkit;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class StarterInventoryCommand extends CommandBase{

	public String getName() {
		return "starterInventory";
	}

	public String getUsage(ICommandSender sender) {
		return "reload give";
	}

	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length > 0) {
			StarterInventoryMod mod = (StarterInventoryMod) ModpackToolkit.starterInventoryMod;
			
			if(args[0].equalsIgnoreCase("reload")) {
				try{
					mod.loadConfig();
					sender.sendMessage(new TextComponentString("Reloaded!"));
				}catch (Exception e) {
					e.printStackTrace();
					sender.sendMessage(new TextComponentString("Error!"));
				}
			}
			if(args[0].equalsIgnoreCase("give")) {
				try{
					StarterInventoryMod.giveStarterGear((EntityPlayer) sender);
				}catch (Exception e) {
					e.printStackTrace();
					sender.sendMessage(new TextComponentString("Error!"));
				}
			}
		}
	}

}
