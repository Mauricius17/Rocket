package de.mauricius17.rocket.parachute;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.mauricius17.rocket.system.Rocket;
import de.mauricius17.rocket.utils.Utils;

public class Parachute implements Runnable{

	private Player player;
	private Block b;
	private Block[] direction = new Block[11];
	private Block[] ndirection = new Block[11];
	private ParachuteConfigurations direction1;
	private boolean stop = false;
	private double slow = -0.05D;
	private double forwardSpeed = 0.2D;
	
	public Parachute(Player player, Block b) {
		this.player = player;
		this.b = b;
		
		direction1 = new ParachuteConfigurations(player);
		
		updateVectors();
	}
	
	@Override
	public void run() {
		if(b.getWorld().getBlockAt(b.getX(), b.getY() -1, b.getZ()).getType() == Material.AIR)  {
			player.setVelocity(getVector());
			player.setFallDistance(0.0F);
			ndirection = direction1.getDirection();
			
			if(!isSameShoot()) {
				remove();
				displayShoot();
			}
			
			b = player.getLocation().getBlock();
			
			if(!stop) {
				Rocket.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Rocket.getInstance(), this, 1L);
			} else {
				remove();
			}
		} else {
			close();
			
			if(Utils.getParachute().containsKey(player.getUniqueId())) {				
				Utils.getParachute().remove(player.getUniqueId());
			}
		}
		
	}
	
	public void updateVectors() {
		slow = Utils.getSlow();
		forwardSpeed = Utils.getForwardSpeed();
	}
	
	public void open() {
		player.setFlying(false);
		player.setAllowFlight(false);
		
		run();
		
		player.sendMessage(Utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("parachute.open")));
	}
	
	public void close() {
		stop = true;
		player.setFlying(false);

		if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
			player.setAllowFlight(false);
		}
		
		remove();
		player.sendMessage(Utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("parachute.close")));
	}
	
	private void remove() {
		try {
			for(int i = 0; i < direction.length; i++) {
				if((direction[i] != null) && (direction[i].getType() == Utils.getParachuteMaterial())) {
					direction[i].setType(Material.AIR);
				}
			}
		} catch(Exception ex) {}
	}	
	
	private boolean isSameShoot() {
		try {
			if(direction[0] != null) {
				if(direction[1] == ndirection[1] && direction[6] == ndirection[6] || direction[1] == ndirection[1] && direction[6] == ndirection[6]) {
					return true;
				}	
				
				return false;
			}
		} catch(Exception ex) {}
		
		return false;
	}
	
	private Vector getVector() {
		double rotation = (player.getLocation().getYaw() - 90.0F) % 360.0F;
		
		if(rotation < 0.0D) {
			rotation += 360.0D;
		}
		
		return new Vector(-forwardSpeed * Math.cos(Math.toRadians(rotation)), slow, -(forwardSpeed * Math.sin(Math.toRadians(rotation))));
	}
	
	private void displayShoot() {
		direction = ndirection;
		
		try {
			for(int i = 0; i < direction.length; i++) {
				if(direction[i].getType() == Material.AIR) {
					direction[i].setType(Utils.getParachuteMaterial());
				}
			}
		} catch (Exception e) {}
	}
}
