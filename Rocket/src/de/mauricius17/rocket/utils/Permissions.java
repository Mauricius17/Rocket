package de.mauricius17.rocket.utils;

public enum Permissions {

	PERMISSIONUSECOMMAND("rocket.command"),
	PERMISSIONUSEROCKET("rocket.use");
	
	private String permission;
	
	private Permissions(String permission) {
		this.permission = permission;
	}
	
	public String getPermission() {
		return permission;
	}
}