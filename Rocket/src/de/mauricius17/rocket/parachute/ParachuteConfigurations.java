package de.mauricius17.rocket.parachute;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class ParachuteConfigurations {

	private PlayerUtility util;
	private Player player;
	
	public ParachuteConfigurations(Player player) {
		this.player = player;
		util = new PlayerUtility(player);
	}
	
	public Block[] getDirection() {
		String dir = util.getDirection();
		
		if((dir == "Norden") || (dir == "Sueden")) {
			return osten();
		}
		
		if((dir == "Osten") || (dir == "Westen")) {
			return norden();
		}
		
		if((dir == "Nordosten") || (dir == "Suedwesten")) {
			return nordwesten();
		}
		
		if((dir == "Nordwesten") || (dir == "Suedosten")) {
			return nordosten();
		}
		
		return null;
	}
	
	public Block[] norden() {
		Block[] richtung = new Block[11];
	    richtung[0] = player.getLocation().getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.UP).getRelative(BlockFace.UP).getRelative(BlockFace.UP).getRelative(BlockFace.UP);
	    richtung[1] = richtung[0].getRelative(BlockFace.EAST);
	    richtung[2] = richtung[1].getRelative(BlockFace.EAST);
	    richtung[3] = richtung[2].getRelative(BlockFace.DOWN);
	    richtung[4] = richtung[3].getRelative(BlockFace.EAST);
	    richtung[5] = richtung[4].getRelative(BlockFace.DOWN);
	    richtung[6] = richtung[0].getRelative(BlockFace.WEST);
	    richtung[7] = richtung[6].getRelative(BlockFace.WEST);
	    richtung[8] = richtung[7].getRelative(BlockFace.DOWN);
	    richtung[9] = richtung[8].getRelative(BlockFace.WEST);
	    richtung[10] = richtung[9].getRelative(BlockFace.DOWN);
	    return richtung;
	}
	
	public Block[] osten() {
		Block[] richtung = new Block[11];
	    richtung[0] = player.getLocation().getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.UP).getRelative(BlockFace.UP).getRelative(BlockFace.UP).getRelative(BlockFace.UP);
	    richtung[1] = richtung[0].getRelative(BlockFace.NORTH);
	    richtung[2] = richtung[1].getRelative(BlockFace.NORTH);
	    richtung[3] = richtung[2].getRelative(BlockFace.DOWN);
	    richtung[4] = richtung[3].getRelative(BlockFace.NORTH);
	    richtung[5] = richtung[4].getRelative(BlockFace.DOWN);
	    richtung[6] = richtung[0].getRelative(BlockFace.SOUTH);
	    richtung[7] = richtung[6].getRelative(BlockFace.SOUTH);
	    richtung[8] = richtung[7].getRelative(BlockFace.DOWN);
	    richtung[9] = richtung[8].getRelative(BlockFace.SOUTH);
	    richtung[10] = richtung[9].getRelative(BlockFace.DOWN);
	    return richtung;
	}
	
	public Block[] nordosten() {
		Block[] richtung = new Block[11];
	    richtung[0] = player.getLocation().getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.UP).getRelative(BlockFace.UP).getRelative(BlockFace.UP).getRelative(BlockFace.UP);
	    richtung[1] = richtung[0].getRelative(BlockFace.EAST).getRelative(BlockFace.SOUTH);
	    richtung[2] = richtung[1].getRelative(BlockFace.EAST).getRelative(BlockFace.SOUTH);
	    richtung[3] = richtung[2].getRelative(BlockFace.DOWN);
	    richtung[4] = richtung[3].getRelative(BlockFace.EAST).getRelative(BlockFace.SOUTH);
	    richtung[5] = richtung[4].getRelative(BlockFace.DOWN);
	    richtung[6] = richtung[0].getRelative(BlockFace.WEST).getRelative(BlockFace.NORTH);
	    richtung[7] = richtung[6].getRelative(BlockFace.WEST).getRelative(BlockFace.NORTH);
	    richtung[8] = richtung[7].getRelative(BlockFace.DOWN);
	    richtung[9] = richtung[8].getRelative(BlockFace.WEST).getRelative(BlockFace.NORTH);
	    richtung[10] = richtung[9].getRelative(BlockFace.DOWN);
	    return richtung;
	}
	
	public Block[] nordwesten() {
		Block[] richtung = new Block[11];
	    richtung[0] = player.getLocation().getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.UP).getRelative(BlockFace.UP).getRelative(BlockFace.UP).getRelative(BlockFace.UP);
	    richtung[1] = richtung[0].getRelative(BlockFace.EAST).getRelative(BlockFace.NORTH);
	    richtung[2] = richtung[1].getRelative(BlockFace.EAST).getRelative(BlockFace.NORTH);
	    richtung[3] = richtung[2].getRelative(BlockFace.DOWN);
	    richtung[4] = richtung[3].getRelative(BlockFace.EAST).getRelative(BlockFace.NORTH);
	    richtung[5] = richtung[4].getRelative(BlockFace.DOWN);
	    richtung[6] = richtung[0].getRelative(BlockFace.WEST).getRelative(BlockFace.SOUTH);
	    richtung[7] = richtung[6].getRelative(BlockFace.WEST).getRelative(BlockFace.SOUTH);
	    richtung[8] = richtung[7].getRelative(BlockFace.DOWN);
	    richtung[9] = richtung[8].getRelative(BlockFace.WEST).getRelative(BlockFace.SOUTH);
	    richtung[10] = richtung[9].getRelative(BlockFace.DOWN);
	    return richtung;
	}
}
