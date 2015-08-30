package de.mauricius17.rocket.system;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.reflect.ClassPath;

import de.mauricius17.rocket.commands.ParachuteCommand;
import de.mauricius17.rocket.commands.RocketCommand;
import de.mauricius17.rocket.utils.Items;
import de.mauricius17.rocket.utils.Utils;

public class Rocket extends JavaPlugin {

	private static Rocket instance;
	
	@Override
	public void onEnable() {
		instance = this;
		
		loadConfig();
		loadMessages();
		registerEvents();
		registerCommands();
		
		Utils.setPrefix(ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("prefix")));		
		Utils.setNoPermission(ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("noPermission")));
		Utils.setConsole(ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("console")));
		
		Utils.setHeightOfTrip(Utils.getConfig().getInt("heigh_of_rockettrip"));
		Utils.setCertainWorlds(Utils.getConfig().getBoolean("certain_worlds"));
		Utils.setWorlds(Utils.getConfig().getString("worlds"));
		
		Utils.setRocketMaterial1(Items.getMaterial(Utils.getConfig().getString("rocket.block1.type")));
		Utils.setRocketMaterial2(Items.getMaterial(Utils.getConfig().getString("rocket.block2.type")));
		Utils.setRocketItem(Items.getMaterial(Utils.getConfig().getString("rocket.item.type")));
		Utils.setRocketName(ChatColor.translateAlternateColorCodes('&', Utils.getConfig().getString("rocket.item.name")));

		Utils.setParachuteItem(Items.getMaterial(Utils.getConfig().getString("parachute.item.type")));
		Utils.setParachuteMaterial(Items.getMaterial(Utils.getConfig().getString("parachute.block.type")));
		Utils.setParachuteName(ChatColor.translateAlternateColorCodes('&', Utils.getConfig().getString("parachute.item.name")));
	}
	
	@Override
	public void onDisable() {
		instance = null;
	}
	
	public static Rocket getInstance() {
		return instance;
	}
	
	private void registerEvents() {
		PluginManager pluginManager = Bukkit.getPluginManager();
		
		try {
			for(ClassPath.ClassInfo classInfo : ClassPath.from(getClassLoader()).getTopLevelClasses("de.mauricius17.rocket.listener")) {
				Class<?> clazz = Class.forName(classInfo.getName());
				
				if(Listener.class.isAssignableFrom(clazz)) {
					pluginManager.registerEvents((Listener) clazz.newInstance(), this);
				}
			}
		} catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			Bukkit.getConsoleSender().sendMessage("�cThe Listeners could not be registered sucessfuly!");
			e.printStackTrace();
		}
	}
	
	private void registerCommands() {
		getCommand("rocket").setExecutor(new RocketCommand());
		getCommand("parachute").setExecutor(new ParachuteCommand());
	}
	
	private void loadConfig() {
		Utils.getConfig().options().header("In this file you can edit some settings!");
		
		Utils.getConfig().addDefault("heigh_of_rockettrip", 60);
		
		Utils.getConfig().addDefault("rocket.item.name", "&cRocket");
		Utils.getConfig().addDefault("rocket.item.type", "LEVER");
		Utils.getConfig().addDefault("rocket.block1.type", "IRON_BLOCK");
		Utils.getConfig().addDefault("rocket.block2.type", "FENCE");
		
		Utils.getConfig().addDefault("parachute.item.name", "&9Parachute");
		Utils.getConfig().addDefault("parachute.item.type", "WEB");
		Utils.getConfig().addDefault("parachute.block.type", "WEB");
		
		Utils.getConfig().addDefault("certain_worlds", false);		
		Utils.getConfig().addDefault("worlds", "world,world_nether");
		
		Utils.getConfig().options().copyDefaults(true);
		
		try {
			Utils.getConfig().save(Utils.getConfigFile());
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage("�cThe config.yml could not be saved! Restart your Server!");
			e.printStackTrace();
		}
	}
	
	private void loadMessages() {
		Utils.getMessages().options().header("In this file you can edit some messages!");
		
		Utils.getMessages().addDefault("prefix", "&8[&5Rocket&8] ");
		Utils.getMessages().addDefault("noPermission", "&cYou can not do this!");
		Utils.getMessages().addDefault("console", "&cOnly a player can use that command!");
		
		Utils.getMessages().addDefault("rocket.started", "&aRocket started!");
		Utils.getMessages().addDefault("rocket.already_started", "&cThe rocket has been started! You can not activate them again!");
		Utils.getMessages().addDefault("rocket.start_while_parachute_is_open", "&cThe parachute is open! You can not start the rocket!");
		Utils.getMessages().addDefault("rocket.item", "&aYou got the rocket item!");
		
		Utils.getMessages().addDefault("parachute.open", "&aThe parachute is now open!");
		Utils.getMessages().addDefault("parachute.open_fail", "&cYou can open the parachute only while falling!");
		Utils.getMessages().addDefault("parachute.open_while_rocket_is_started", "&cYou are inside of the rocket! You can not open the parachute!");
		Utils.getMessages().addDefault("parachute.close", "&cThe parachute is now closed!");
		Utils.getMessages().addDefault("parachute.item", "&aYou got the parachute item!");

		
		Utils.getMessages().options().copyDefaults(true);
		
		try {
			Utils.getMessages().save(Utils.getMessageFile());
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage("�cThe messages.yml could not be saved! Restart your Server!");
			e.printStackTrace();
		}
	}
}