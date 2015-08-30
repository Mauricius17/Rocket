package de.mauricius17.rocket.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import de.mauricius17.rocket.utils.Utils;

public class ToggleSneakListener implements Listener {

	@EventHandler
	public void onToggleSneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		
		if(Utils.getRocket().containsKey(p.getUniqueId())) {
			Utils.getRocket().get(p.getUniqueId()).despawn();
			Utils.getRocket().remove(p.getUniqueId());
		}
	}
	
}
