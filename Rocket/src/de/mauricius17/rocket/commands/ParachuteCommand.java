package de.mauricius17.rocket.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.mauricius17.rocket.utils.Items;
import de.mauricius17.rocket.utils.Permissions;
import de.mauricius17.rocket.utils.Utils;

public class ParachuteCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(Utils.getConsole());
			return true;
		}
		
		Player p = (Player) sender;
		
		if(p.hasPermission(Permissions.PERMISSIONUSECOMMAND.getPermission())) {
			ItemStack item = Items.getItemStack(Utils.getParachuteItem(), Utils.getParachuteName(), 1, (byte) 0);
			p.getInventory().addItem(item);
			p.sendMessage(Utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("parachute.item")));
		} else {
			p.sendMessage(Utils.getPrefix() + Utils.getNoPermission());
		}
		
		return true;
	}
}