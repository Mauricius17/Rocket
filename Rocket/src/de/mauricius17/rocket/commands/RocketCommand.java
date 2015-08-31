package de.mauricius17.rocket.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.mauricius17.rocket.enums.Permissions;
import de.mauricius17.rocket.utils.Items;
import de.mauricius17.rocket.utils.Utils;
import de.mauricius17.rocket.warp.Warp;

public class RocketCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(Utils.getConsole());
			return true;
		}
		
		Player p = (Player) sender;
		
		if(args.length != 1 && args.length != 2) {
			sendHelp(p);
			return true;
		}
		
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("item")) {
				if(p.hasPermission(Permissions.GETROCKETITEM.getPermission())) {
					ItemStack item = Items.getItemStack(Utils.getRocketItem(), Utils.getRocketName(), 1, (byte) 0);
					p.getInventory().addItem(item);
					p.sendMessage(Utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("rocket.item")));
				} else {
					p.sendMessage(Utils.getPrefix() + Utils.getNoPermission());
				}	
				
				return true;
			}
			
			if(args[0].equalsIgnoreCase("list")) {
				if(p.hasPermission(Permissions.LISTWARPS.getPermission())) {
					StringBuilder sb = new StringBuilder();
					
					int i = 0;
					for(Warp w : Utils.getWarps()) {
						if(i == 1) {
							sb.append(", ");
						} else {
							i = 1;
						}
						
						sb.append(w.getName());
					}
					
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("command.warp.list.header")));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("command.warp.list.line01")).replace("[WARPS]", sb.toString()));	
				} else {
					p.sendMessage(Utils.getPrefix() + Utils.getNoPermission());
				}
				
				return true;
			}
			
			if(p.hasPermission(Permissions.TELEPORTTOWARP.getPermission())) {
				String warp = args[0];
				
				for(Warp w : Utils.getWarps()) {
					if(w.getName().equalsIgnoreCase(warp)) {
						w.teleport(p);
						return true;
					}
				}
				
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Utils.getPrefix() + Utils.getMessages().getString("command.warp.teleport.failed")).replace("[WARP]", warp));
			} else {
				p.sendMessage(Utils.getPrefix() + Utils.getNoPermission());
			}
		}

		if(args.length == 2) {
			if(args[0].equalsIgnoreCase("set")) {
				if(p.hasPermission(Permissions.SETWARP.getPermission())) {
					Warp warp = new Warp(args[1].toLowerCase(), p.getLocation());
					
					if(warp.set()) {
						Utils.getWarps().add(warp);
						p.sendMessage(Utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("command.warp.set.successful").replace("[WARP]", warp.getName())));
					} else {
						p.sendMessage(Utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("command.warp.set.failed.save").replace("[WARP]", warp.getName())));
					}
				} else {
					p.sendMessage(Utils.getPrefix() + Utils.getNoPermission());
				}
				return true;
			}
			
			if(args[0].equalsIgnoreCase("remove")) {
				if(p.hasPermission(Permissions.REMOVEWARP.getPermission())) {
					
					String warp = args[1].toLowerCase();
					
					for(Warp w : Utils.getWarps()) {
						if(w.getName().equalsIgnoreCase(warp)) {
							if(w.remove()) {
								Utils.getWarps().remove(w);
								p.sendMessage(Utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("command.warp.remove.successful").replace("[WARP]", w.getName())));									
							} else {
								p.sendMessage(Utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("command.warp.remove.failed.save").replace("[WARP]", w.getName())));
							}
							return true;
						}
					}
					
					p.sendMessage(Utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("command.warp.remove.failed.notexists").replace("[WARP]", warp)));
				} else {
					p.sendMessage(Utils.getPrefix() + Utils.getNoPermission());
				}
				return true;
			}	
			
			sendHelp(p);
		}
		
		return true;
	}
	
	private void sendHelp(Player p) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("command.help.header")));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("command.help.line01")));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("command.help.line02")));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("command.help.line03")));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("command.help.line04")));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("command.help.line05")));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("command.help.footer")));
	}
}
