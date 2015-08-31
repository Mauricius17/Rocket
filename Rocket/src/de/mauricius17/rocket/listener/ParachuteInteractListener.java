package de.mauricius17.rocket.listener;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.mauricius17.rocket.enums.CertainWorlds;
import de.mauricius17.rocket.enums.Permissions;
import de.mauricius17.rocket.parachute.Parachute;
import de.mauricius17.rocket.utils.Utils;

public class ParachuteInteractListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getItem() != null && e.getItem().hasItemMeta()) {
				if(e.getItem().getItemMeta().getDisplayName().equals(Utils.getParachuteName()) && e.getItem().getType().equals(Utils.getParachuteItem())) {
					if(p.hasPermission(Permissions.USEPARACHUTE.getPermission())) {						
						if(Utils.getCertainWorlds().equals(CertainWorlds.ON)) {
							String[] worlds = Utils.getWorlds().split(",");
							
							for(String w : worlds) {
								if(w.equals(p.getWorld().getName())) {
									if(!Utils.getRocket().containsKey(p.getUniqueId())) {
										if(!Utils.getParachute().containsKey(p.getUniqueId())) {
											
											Location loc = p.getLocation();
											loc.setY(loc.getY() -1);
											
											if(loc.getBlock().getType().equals(Material.AIR)) {
												Parachute para = new Parachute(p, p.getLocation().getBlock());
												para.open();
												Utils.getParachute().put(p.getUniqueId(), para);
											} else {
												p.sendMessage(Utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("parachute.open_fail")));
											}
										} else {
											Utils.getParachute().get(p.getUniqueId()).close();
											Utils.getParachute().remove(p.getUniqueId());
										}
									} else {
										p.sendMessage(Utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("parachute.open_while_rocket_is_started")));
									}
									return;
								}
							}
						} else {
							if(!Utils.getRocket().containsKey(p.getUniqueId())) {
								if(!Utils.getParachute().containsKey(p.getUniqueId())) {
									
									Location loc = p.getLocation();
									loc.setY(loc.getY() -1);
									
									if(loc.getBlock().getType().equals(Material.AIR)) {
										Parachute para = new Parachute(p, p.getLocation().getBlock());
										para.open();
										Utils.getParachute().put(p.getUniqueId(), para);
									} else {
										p.sendMessage(Utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("parachute.open_fail")));
									}
								} else {
									Utils.getParachute().get(p.getUniqueId()).close();
									Utils.getParachute().remove(p.getUniqueId());
								}
							} else {
								p.sendMessage(Utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("parachute.open_while_rocket_is_started")));
							}
						}
					} else {
						p.sendMessage(Utils.getPrefix() + Utils.getNoPermission());
					}
				}
			}
		}
	}
}