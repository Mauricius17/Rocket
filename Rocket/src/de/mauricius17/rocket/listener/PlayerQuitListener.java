package de.mauricius17.rocket.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.mauricius17.rocket.parachute.Parachute;
import de.mauricius17.rocket.utils.Utils;

public class PlayerQuitListener implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		if(Utils.getRocket().containsKey(p.getUniqueId())) {
			Utils.getRocket().get(p.getUniqueId()).despawn();
			Utils.getRocket().remove(p.getUniqueId());
		}
		
		if(Utils.getParachute().containsKey(p.getUniqueId())) {
			Parachute para  = Utils.getParachute().get(p.getUniqueId());
			para.close();
			Utils.getParachute().remove(p.getUniqueId());
		}
	}
}