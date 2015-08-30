package de.mauricius17.rocket.parachute;

import org.bukkit.entity.Player;

public class PlayerUtility {
	
	Player player;
	
	public PlayerUtility(Player player) {
		this.player = player;
	}
	
	public String getDirection() {
		double rot = player.getLocation().getYaw() - 90.0F % 360.0F;
		
		if(rot < 0.0D) {
			rot += 360.0D;
		}
		
		return getDirection(rot);
	}
	
	private String getDirection(double rot) {
	
		 if(0.0D <= rot && rot < 22.5D) {
			 return "Norden";
		 }
		 
		 if(22.5D <= rot && rot < 67.5D) {
			 return "Nordosten";
		 }
		 
		 if(67.5D <= rot && rot < 112.5D) {
			 return "Osten";
		 }
		 
		 if(112.5D <= rot && rot < 157.5D) {
			 return "Suedosten";
		 }
		 
		 if(157.5D <= rot && rot < 202.5D) {
			 return "Sueden";
		 }
		 
		 if(202.5D <= rot && rot < 247.5D) {
			 return "Suedwesten";
		 }
		 
		 if(247.5D <= rot && rot < 292.5D) {
			 return "Westen";
		 }
		 
		 if(292.5D <= rot && rot < 337.5D) {
			 return "Nordwesten";
		 }
		 
		 if(337.5D <= rot && rot < 360.5D) {
			 return "Norden";
		 }
		 
		 return null;
	}
}
