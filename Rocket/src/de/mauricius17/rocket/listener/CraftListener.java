package de.mauricius17.rocket.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import de.mauricius17.rocket.enums.Permissions;
import de.mauricius17.rocket.utils.Utils;

public class CraftListener implements Listener {

	@EventHandler
	public void onCraftItem(CraftItemEvent e) {
		if(e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			
			if(e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()) {
				if(e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.getParachuteName())) {
					if(p.hasPermission(Permissions.GETPARACHUTEITEM.getPermission())) {
						e.setCancelled(false);
					} else {
						e.setCancelled(true);
						p.sendMessage(Utils.getPrefix() + Utils.getNoPermission());
					}
				}
			}	
			
			if(e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()) {
				if(e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.getRocketName())) {
					if(p.hasPermission(Permissions.GETROCKETITEM.getPermission())) {
						e.setCancelled(false);
					} else {
						e.setCancelled(true);
						p.sendMessage(Utils.getPrefix() + Utils.getNoPermission());
					}
				}
			}	
		}
	}
}