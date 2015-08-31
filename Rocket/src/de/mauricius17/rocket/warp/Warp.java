package de.mauricius17.rocket.warp;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.mauricius17.rocket.enums.CertainWorlds;
import de.mauricius17.rocket.rocket.Rocket;
import de.mauricius17.rocket.rocket.Rocket.RocketInterface;
import de.mauricius17.rocket.utils.Utils;

public class Warp {
	Location location;
	String name;
	
	public Warp(String name) {
		this.location = null;
		this.name = name;
	}
	
	public Warp(String name, Location location) {
		this.location = location;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public Location getLocationByConfig() {
		String world = Utils.getWarpsConfiguration().getString("warp." + name + ".world");
		
		if(world == null) {
			return null;
		}
		
		double x = Utils.getWarpsConfiguration().getDouble("warp." + name + ".x");
		double y = Utils.getWarpsConfiguration().getDouble("warp." + name + ".y");
		double z = Utils.getWarpsConfiguration().getDouble("warp." + name + ".z");
		double yaw = Utils.getWarpsConfiguration().getDouble("warp." + name + ".yaw");
		double pitch = Utils.getWarpsConfiguration().getDouble("warp." + name + ".pitch");
		
		Bukkit.createWorld(new WorldCreator(world));			
		
		Location loc = new Location(Bukkit.getWorld(world), x, y, z);
		loc.setYaw((float) yaw);
		loc.setPitch((float) pitch);
		
		return loc;	
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public void teleport(Player p) {
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
												p.teleport(location);
												p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 1, false));
												p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1F);
												p.sendMessage(ChatColor.translateAlternateColorCodes('&', Utils.getPrefix() + Utils.getMessages().getString("command.warp.teleport.successful")).replace("[WARP]", name));
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
										p.teleport(location);
										p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 1, false));
										p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1F);
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', Utils.getPrefix() + Utils.getMessages().getString("command.warp.teleport.successful")).replace("[WARP]", name));
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
	}
	
	public boolean remove() {
		String[] warps = Utils.getWarpsConfiguration().getString("warps").split(";");
		String newWarps = "";
		
		for(String w : warps) {
			if(w.equalsIgnoreCase(name)) continue;
			newWarps = newWarps + w + ";";
		}
		
		if(newWarps.equals(";")) newWarps = "";
		
		Utils.getWarpsConfiguration().set("warps", newWarps);
		
		Utils.getWarpsConfiguration().set("warp." + name + ".world", null);
		Utils.getWarpsConfiguration().set("warp." + name + ".x", null);
		Utils.getWarpsConfiguration().set("warp." + name + ".y", null);
		Utils.getWarpsConfiguration().set("warp." + name + ".z", null);
		Utils.getWarpsConfiguration().set("warp." + name + ".yaw", null);
		Utils.getWarpsConfiguration().set("warp." + name + ".pitch", null);
		
		try {
			Utils.getWarpsConfiguration().save(Utils.getWarpFile());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean set() {
		String world = location.getWorld().getName();
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		double yaw = location.getYaw();
		double pitch = location.getPitch();
		
		String[] warps = Utils.getWarpsConfiguration().getString("warps").split(";");
		String newWarps = "";
		
		for(String w : warps) {
			if(w.equals(name)) {
				return false;
			}
		}
		
		for(String w : warps) {
			newWarps = newWarps + w + ";";
		}
		
		newWarps = newWarps + name + ";";
		
		Utils.getWarpsConfiguration().set("warps", newWarps);
		
		Utils.getWarpsConfiguration().set("warp." + name + ".world", world);
		Utils.getWarpsConfiguration().set("warp." + name + ".x", x);
		Utils.getWarpsConfiguration().set("warp." + name + ".y", y);
		Utils.getWarpsConfiguration().set("warp." + name + ".z", z);
		Utils.getWarpsConfiguration().set("warp." + name + ".yaw", yaw);
		Utils.getWarpsConfiguration().set("warp." + name + ".pitch", pitch);
		
		try {
			Utils.getWarpsConfiguration().save(Utils.getWarpFile());	
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}