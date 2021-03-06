package de.mauricius17.rocket.listener;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.mauricius17.rocket.enums.CertainWorlds;
import de.mauricius17.rocket.enums.Permissions;
import de.mauricius17.rocket.parachute.Parachute;
import de.mauricius17.rocket.rocket.Rocket;
import de.mauricius17.rocket.rocket.Rocket.RocketInterface;
import de.mauricius17.rocket.utils.Utils;

public class RocketInteractListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getItem() != null && e.getItem().hasItemMeta()) {
				if(e.getItem().getItemMeta().getDisplayName().equals(Utils.getRocketName()) && e.getItem().getType().equals(Utils.getRocketItem())) {
					if(p.hasPermission(Permissions.USEROCKET.getPermission())) {						
						if(Utils.getCertainWorlds().equals(CertainWorlds.ON)) {
							String[] worlds = Utils.getWorlds().split(",");
							
							for(String w : worlds) {
								if(w.equals(p.getWorld().getName())) {
									if(!Utils.getRocket().containsKey(p.getUniqueId())) {
										if(!Utils.getParachute().containsKey(p.getUniqueId())) {
											Rocket rocket = new Rocket(de.mauricius17.rocket.system.Rocket.getInstance(), p);
											rocket.build(p.getLocation());
											rocket.addPlayer();
																													
											rocket.start(Utils.getHeightOfTrip(), new RocketInterface() {
												
												@SuppressWarnings("deprecation")
												@Override
												public void onRocketDestroy() {
													Block b = p.getLocation().getBlock();
							
													for(int i = 0; i < 3; i++) {
														if(b.getRelative(BlockFace.DOWN).getTypeId() == 0) {
															b = b.getRelative(BlockFace.DOWN);
															
															if(i == 2) {
																Parachute para = new Parachute(p, p.getLocation().getBlock());
																para.open();
																Utils.getParachute().put(p.getUniqueId(), para);
															}
														} else {
															i = 3;
														}
													}
												}
											});
											
											Utils.getRocket().put(p.getUniqueId(), rocket);
											
											p.sendMessage(Utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("rocket.started")));
										} else {
											p.sendMessage(Utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("rocket.start_while_parachute_is_open")));
										}
									} else {
										p.sendMessage(Utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("rocket.already_started")));
									}
									
									return;
								}
							}
						} else {	
							if(!Utils.getRocket().containsKey(p.getUniqueId())) {
								if(!Utils.getParachute().containsKey(p.getUniqueId())) {
									Rocket rocket = new Rocket(de.mauricius17.rocket.system.Rocket.getInstance(), p);
									rocket.build(p.getLocation());
									rocket.addPlayer();
																											
									rocket.start(Utils.getHeightOfTrip(), new RocketInterface() {
										
										@SuppressWarnings("deprecation")
										@Override
										public void onRocketDestroy() {
											Block b = p.getLocation().getBlock();
					
											for(int i = 0; i < 3; i++) {
												if(b.getRelative(BlockFace.DOWN).getTypeId() == 0) {
													b = b.getRelative(BlockFace.DOWN);
													
													if(i == 2) {
														Parachute para = new Parachute(p, p.getLocation().getBlock());
														para.open();
														Utils.getParachute().put(p.getUniqueId(), para);
													}
												} else {
													i = 3;
												}
											}
										}
									});
									
									Utils.getRocket().put(p.getUniqueId(), rocket);
									
									p.sendMessage(Utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("rocket.started")));
								} else {
									p.sendMessage(Utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("rocket.start_while_parachute_is_open")));
								}
							} else {
								p.sendMessage(Utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("rocket.already_started")));
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