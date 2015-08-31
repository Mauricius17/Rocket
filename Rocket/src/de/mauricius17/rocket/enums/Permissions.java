package de.mauricius17.rocket.enums;

public enum Permissions {

	GETROCKETITEM("rocket.item"),
	GETPARACHUTEITEM("parachute.item"),
	USEROCKET("rocket.use"),
	USEPARACHUTE("parachute.use"),
	LISTWARPS("rocket.warp.list"),
	SETWARP("rocket.warp.set"),
	REMOVEWARP("rocket.warp.remove"),
	TELEPORTTOWARP("rocket.warp.teleport");
	
	private String permission;
	
	private Permissions(String permission) {
		this.permission = permission;
	}
	
	public String getPermission() {
		return permission;
	}
}