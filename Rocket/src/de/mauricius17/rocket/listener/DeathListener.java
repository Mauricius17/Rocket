package de.mauricius17.rocket.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.mauricius17.rocket.parachute.Parachute;
import de.mauricius17.rocket.utils.Utils;

public class DeathListener implements Listener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = e.getEntity();
			
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
}