package de.mauricius17.rocket.utils;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.mauricius17.rocket.enums.CertainWorlds;
import de.mauricius17.rocket.parachute.Parachute;
import de.mauricius17.rocket.rocket.Rocket;

public class Utils {

	private static File configFile = new File("plugins/Rocket", "config.yml");
	private static FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
	
	private static File messageFile = new File("plugins/Rocket", "messages.yml");
	private static FileConfiguration messages = YamlConfiguration.loadConfiguration(messageFile);
	
	private static String prefix = "", noPermission = "", console = "", rocketName = "", worlds = "", parachuteName = "";
	
	private static double height = 1.5D, multiply = 1.5D, fast = -0.2D, slow = -0.05D, forwardSpeed = 0.2D;
	
	private static int heightOfTrip = 0;
	
	private static HashMap<UUID, Rocket> rocket = new HashMap<>();
	private static HashMap<UUID, Parachute> parachute = new HashMap<>();

	private static Material rocketMaterial1 = Material.IRON_BLOCK;
	private static Material rocketMaterial2 = Material.FENCE;
	
	private static Material rocketItem = Material.LEVER;
	private static Material parachuteMaterial = Material.WEB;
	private static Material parachuteItem = Material.WEB;
	
	private static CertainWorlds certainWorlds = CertainWorlds.OFF;
	
	public static CertainWorlds getCertainWorlds() {
		return certainWorlds;
	}
	
	public static void setCertainWorlds(CertainWorlds certainWorlds) {
		Utils.certainWorlds = certainWorlds;
	}
	
	public static Material getParachuteItem() {
		return parachuteItem;
	}
	
	public static Material getParachuteMaterial() {
		return parachuteMaterial;
	}
	
	public static Material getRocketItem() {
		return rocketItem;
	}
	
	public static Material getRocketMaterial1() {
		return rocketMaterial1;
	}
	
	public static Material getRocketMaterial2() {
		return rocketMaterial2;
	}
	
	public static void setRocketMaterial1(Material rocketMaterial1) {
		Utils.rocketMaterial1 = rocketMaterial1;
	}
	
	public static void setRocketMaterial2(Material rocketMaterial2) {
		Utils.rocketMaterial2 = rocketMaterial2;
	}
	
	public static void setRocketItem(Material rocketItem) {
		Utils.rocketItem = rocketItem;
	}
	
	public static void setParachuteMaterial(Material parachuteMaterial) {
		Utils.parachuteMaterial = parachuteMaterial;
	}
	
	public static void setParachuteItem(Material parachuteItem) {
		Utils.parachuteItem = parachuteItem;
	}
	
	public static String getParachuteName() {
		return parachuteName;
	}
	
	public static void setParachuteName(String parachuteName) {
		Utils.parachuteName = parachuteName;
	}
	
	public static String getWorlds() {
		return worlds;
	}
	
	public static void setWorlds(String worlds) {
		Utils.worlds = worlds;
	}
	
	public static String getRocketName() {
		return rocketName;
	}
	
	public static void setRocketName(String rocketName) {
		Utils.rocketName = rocketName;
	}
	
	public static int getHeightOfTrip() {
		return heightOfTrip;
	}
	
	public static void setHeightOfTrip(int heightOfTrip) {
		Utils.heightOfTrip = heightOfTrip;
	}
	
	public static String getConsole() {
		return console;
	}
	
	public static void setConsole(String console) {
		Utils.console = console;
	}
	
	public static File getMessageFile() {
		return messageFile;
	}
	
	public static FileConfiguration getMessages() {
		return messages;
	}
	
	public static HashMap<UUID, Rocket> getRocket() {
		return rocket;
	}
	
	public static double getFast() {
		return fast;
	}
	
	public static double getForwardSpeed() {
		return forwardSpeed;
	}
	
	public static double getHeight() {
		return height;
	}
	
	public static double getMultiply() {
		return multiply;
	}
	
	public static double getSlow() {
		return slow;
	}
		
	public static HashMap<UUID, Parachute> getParachute() {
		return parachute;
	}
	
	public static FileConfiguration getConfig() {
		return config;
	}
	
	public static File getConfigFile() {
		return configFile;
	}
	
	public static void setNoPermission(String noPermission) {
		Utils.noPermission = noPermission;
	}
	
	public static void setPrefix(String prefix) {
		Utils.prefix = prefix;
	}
	
	public static String getNoPermission() {
		return noPermission;
	}
	
	public static String getPrefix() {
		return prefix;
	}
}