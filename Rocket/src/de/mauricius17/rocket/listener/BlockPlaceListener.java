package de.mauricius17.rocket.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import de.mauricius17.rocket.utils.Permissions;
import de.mauricius17.rocket.utils.Utils;

public class BlockPlaceListener implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		
		if(e.getItemInHand() != null && e.getItemInHand().hasItemMeta()) {
			if(e.getItemInHand().getType().equals(Material.LEVER)) {
				if(e.getItemInHand().getItemMeta().getDisplayName().equals(Utils.getRocketName())) {
					if(p.hasPermission(Permissions.PERMISSIONUSEROCKET.getPermission())) {
						if(Utils.getCertainWorlds()) {
							String[] worlds = Utils.getWorlds().split(",");
							
							for(String w : worlds) {
								if(w.equals(p.getWorld().getName())) {
									e.setCancelled(true);									
								}
							}
						} else {
							e.setCancelled(true);
						}
					}
				}	
			}
			
			if(e.getItemInHand().getType().equals(Material.WEB)) {
				if(p.getItemInHand().getItemMeta().getDisplayName().equals(Utils.getParachuteName())) {
					if(p.hasPermission(Permissions.PERMISSIONUSEROCKET.getPermission())) {
						if(Utils.getCertainWorlds()) {
							String[] worlds = Utils.getWorlds().split(",");
							
							for(String w : worlds) {
								if(w.equals(p.getWorld().getName())) {
									e.setCancelled(true);									
								}
							}
						} else {
							e.setCancelled(true);
						}
					}	
				}
			}
		}
	}
}