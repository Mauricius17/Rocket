package de.mauricius17.rocket.rocket;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import de.mauricius17.rocket.utils.Utils;

public class Rocket {

	private Plugin instance;
	private Player p;
	private FallingBlock seat, fb;
	private FallingBlock[] blocks;
	private Thread thread;
	
	private int taskID = -1;
	
	public Rocket(Plugin instance, Player p) {
		this.instance = instance;
		this.p = p;
		this.blocks = new FallingBlock[6];
	}
	
	@SuppressWarnings("deprecation")
	public void build(Location loc) {
		Location temp = loc.clone();
		
		// Rocket
		blocks[0] = loc.getWorld().spawnFallingBlock(temp.add(1.0D, 0.0D, 0.0D), Utils.getRocketMaterial2(), (byte) 1); temp = loc.clone();
		blocks[1] = loc.getWorld().spawnFallingBlock(temp.add(-1.0D, 0.0D, 0.0D), Utils.getRocketMaterial2(), (byte) 1); temp = loc.clone();
		blocks[2] = loc.getWorld().spawnFallingBlock(temp.add(0.0D, 0.0D, -1.0D), Utils.getRocketMaterial2(), (byte) 1); temp = loc.clone();
		blocks[3] = loc.getWorld().spawnFallingBlock(temp.add(0.0D, 0.0D, 1.0D), Utils.getRocketMaterial2(), (byte) 1); temp = loc.clone();
		blocks[4] = loc.getWorld().spawnFallingBlock(temp, Utils.getRocketMaterial1(), (byte) 1); temp = loc.clone();
		blocks[5] = loc.getWorld().spawnFallingBlock(temp.add(0.0D, 1.0D, 0.0D), Utils.getRocketMaterial1(), (byte) 1); temp = loc.clone();
		
		this.fb = blocks[4];
		this.seat = blocks[5];
		
		for(FallingBlock rocket : blocks) {
			rocket.setDropItem(false);
		}
	}
	
	public void addPlayer() {
		this.fb.setPassenger(this.seat);
		this.seat.setPassenger(p);
	}
	
	public void start(int blockHigh, RocketInterface onRocketDestroy) {		
		final int y = blockHigh + blocks[0].getLocation().getBlockY();
		
		this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {
			
			@Override
			public void run() {
				for(FallingBlock rocket : blocks) {
					rocket.setVelocity(new Vector(0, 1, 0));
				}
				
				p.getWorld().playEffect(p.getLocation().add(0D, -0.25D, 0D), Effect.MOBSPAWNER_FLAMES, 10);
				p.playSound(p.getLocation(), Sound.FIREWORK_LARGE_BLAST, 1F, 1F);
			}
		}, 0L, 1);
		
		this.thread = new Thread(new Runnable() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				while(true) {
					
					Location loc = p.getLocation();
					loc.setY(loc.getY() + 2);
					
					for(FallingBlock rocket : blocks) {
						Location b = rocket.getLocation();
						b.setY(b.getY() + 1);
						
						if(b.getBlock().getType() != Material.AIR) {
							Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable() {
								
								@Override
								public void run() {
									if(onRocketDestroy != null) {
										onRocketDestroy.onRocketDestroy();
									}
								}
							});

							despawn();
							thread.stop();
							return;
						}
					}
				
					if(p.getLocation().getY() >= 250) {
						Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable() {
							
							@Override
							public void run() {
								if(onRocketDestroy != null) {
									onRocketDestroy.onRocketDestroy();
								}
							}
						});

						despawn();
						thread.stop();
						return;
					}
					
					if(blocks[0].getLocation().getY() > y) {						
						Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable() {
							
							@Override
							public void run() {
								if(onRocketDestroy != null) {
									onRocketDestroy.onRocketDestroy();
								}
							}
						});

						despawn();
						thread.stop();
						return;
					}
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		thread.start();
	}
	
	@SuppressWarnings("deprecation")
	public void despawn() {
		for(FallingBlock rocket : blocks) {
			rocket.remove();
		}
		
		if(Utils.getRocket().containsKey(p.getUniqueId())) {
			Utils.getRocket().remove(p.getUniqueId());
		}
		
		if(taskID != -1) {
			Bukkit.getScheduler().cancelTask(taskID);
		}
		
		if(thread != null && thread.isAlive()) {
			thread.stop();
		}
	}
	
	public interface RocketInterface {
		public void onRocketDestroy();
	}
}