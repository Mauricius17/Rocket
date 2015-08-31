package de.mauricius17.rocket.system;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.reflect.ClassPath;

import de.mauricius17.rocket.commands.ParachuteCommand;
import de.mauricius17.rocket.commands.RocketCommand;
import de.mauricius17.rocket.enums.CertainWorlds;
import de.mauricius17.rocket.utils.Items;
import de.mauricius17.rocket.utils.Recipes;
import de.mauricius17.rocket.utils.Recipes.ParachuteRecipes;
import de.mauricius17.rocket.utils.Recipes.RocketRecipes;
import de.mauricius17.rocket.utils.Utils;
import de.mauricius17.rocket.warp.Warp;

public class Rocket extends JavaPlugin {

	private static Rocket instance;
	
	@Override
	public void onEnable() {
		instance = this;
		
		loadConfig();
		loadMessages();
		loadWarpsConfig();
		loadWarps();
		
		registerEvents();
		registerCommands();
		
		Utils.setPrefix(ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("prefix")));		
		Utils.setNoPermission(ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("noPermission")));
		Utils.setConsole(ChatColor.translateAlternateColorCodes('&', Utils.getMessages().getString("console")));
		
		Utils.setHeightOfTrip(Utils.getConfig().getInt("heigh_of_rockettrip"));
		
		if(Utils.getConfig().getBoolean("certain_worlds")) {
			Utils.setCertainWorlds(CertainWorlds.ON);
		} else {
			Utils.setCertainWorlds(CertainWorlds.ON);			
		}
		
		Utils.setWorlds(Utils.getConfig().getString("worlds"));
		
		Utils.setRocketMaterial1(Items.getMaterial(Utils.getConfig().getString("rocket.block1.type")));
		Utils.setRocketMaterial2(Items.getMaterial(Utils.getConfig().getString("rocket.block2.type")));
		Utils.setRocketItem(Items.getMaterial(Utils.getConfig().getString("rocket.item.type")));
		Utils.setRocketName(ChatColor.translateAlternateColorCodes('&', Utils.getConfig().getString("rocket.item.name")));

		Utils.setParachuteItem(Items.getMaterial(Utils.getConfig().getString("parachute.item.type")));
		Utils.setParachuteMaterial(Items.getMaterial(Utils.getConfig().getString("parachute.block.type")));
		Utils.setParachuteName(ChatColor.translateAlternateColorCodes('&', Utils.getConfig().getString("parachute.item.name")));
		
		for(ParachuteRecipes recipes : Recipes.ParachuteRecipes.values()) {
			recipes.setMaterial(Items.getMaterial(Utils.getConfig().getString("parachute.recipe." + recipes.getChar().toUpperCase())));
		}
		
		for(RocketRecipes recipes : Recipes.RocketRecipes.values()) {
			recipes.setMaterial(Items.getMaterial(Utils.getConfig().getString("rocket.recipe." + recipes.getChar().toUpperCase())));
		}
		
		if(Utils.getConfig().getBoolean("recipes")) {
			Recipes.createParachuteRecipe();
			Recipes.createRocketRecipe();
		}
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
			Bukkit.getConsoleSender().sendMessage("§cThe Listeners could not be registered sucessfuly!");
			e.printStackTrace();
		}
	}
	
	private void registerCommands() {
		getCommand("rocket").setExecutor(new RocketCommand());
		getCommand("parachute").setExecutor(new ParachuteCommand());
	}
	
	private void loadWarpsConfig() {
		Utils.getWarpsConfiguration().options().header("In this file you can save warps!");
		
		Utils.getWarpsConfiguration().addDefault("warps", "");
		
		Utils.getWarpsConfiguration().options().copyDefaults(true);
		
		try {
			Utils.getWarpsConfiguration().save(Utils.getWarpFile());
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage("§cThe warps.yml could not be saved! Restart your Server!");
			e.printStackTrace();
		}
	}
	
	private void loadWarps() {
		String[] warps = Utils.getWarpsConfiguration().getString("warps").split(";");
		
		for(String w : warps) {
			Warp warp = new Warp(w);
			
			Location location = warp.getLocationByConfig();
						
			if(location != null) {
				warp.setLocation(warp.getLocationByConfig());
				Utils.getWarps().add(warp);				
			}
		}
	}
	
	private void loadConfig() {
		Utils.getConfig().options().header("In this file you can edit some settings!");
		
		Utils.getConfig().addDefault("heigh_of_rockettrip", 60);
		Utils.getConfig().addDefault("certain_worlds", false);		
		Utils.getConfig().addDefault("worlds", "world,world_nether");
		Utils.getConfig().addDefault("recipes", true);
		
		Utils.getConfig().addDefault("rocket.item.name", "&cRocket");
		Utils.getConfig().addDefault("rocket.item.type", "LEVER");
		Utils.getConfig().addDefault("rocket.block1.type", "IRON_BLOCK");
		Utils.getConfig().addDefault("rocket.block2.type", "FENCE");
		
		Utils.getConfig().addDefault("rocket.recipe.A", "AIR");
		Utils.getConfig().addDefault("rocket.recipe.B", "IRON_BLOCK");
		Utils.getConfig().addDefault("rocket.recipe.C", "AIR");
		
		Utils.getConfig().addDefault("rocket.recipe.D", "FENCE");
		Utils.getConfig().addDefault("rocket.recipe.E", "IRON_BLOCK");
		Utils.getConfig().addDefault("rocket.recipe.F", "FENCE");
		
		Utils.getConfig().addDefault("rocket.recipe.G", "FENCE");
		Utils.getConfig().addDefault("rocket.recipe.H", "FIREWORK");
		Utils.getConfig().addDefault("rocket.recipe.I", "FENCE");
		
		Utils.getConfig().addDefault("parachute.item.name", "&9Parachute");
		Utils.getConfig().addDefault("parachute.item.type", "WEB");
		Utils.getConfig().addDefault("parachute.block.type", "WEB");
		
		Utils.getConfig().addDefault("parachute.recipe.A", "AIR");
		Utils.getConfig().addDefault("parachute.recipe.B", "WEB");
		Utils.getConfig().addDefault("parachute.recipe.C", "AIR");
		
		Utils.getConfig().addDefault("parachute.recipe.D", "WEB");
		Utils.getConfig().addDefault("parachute.recipe.E", "STRING");
		Utils.getConfig().addDefault("parachute.recipe.F", "WEB");
		
		Utils.getConfig().addDefault("parachute.recipe.G", "STRING");
		Utils.getConfig().addDefault("parachute.recipe.H", "AIR");
		Utils.getConfig().addDefault("parachute.recipe.I", "STRING");
		
		Utils.getConfig().options().copyDefaults(true);
		
		try {
			Utils.getConfig().save(Utils.getConfigFile());
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage("§cThe config.yml could not be saved! Restart your Server!");
			e.printStackTrace();
		}
	}
	
	private void loadMessages() {
		Utils.getMessages().options().header("In this file you can edit some messages!");
		
		Utils.getMessages().addDefault("prefix", "&8[&5Rocket&8] ");
		Utils.getMessages().addDefault("noPermission", "&cYou can not do this!");
		Utils.getMessages().addDefault("console", "&cOnly a player can use that command!");
		
		Utils.getMessages().addDefault("command.help.header", "&8==========&5[Rocket&8]==========");
		Utils.getMessages().addDefault("command.help.line01", "&7/rocket item - &5Get the rocket item");
		Utils.getMessages().addDefault("command.help.line02", "&7/rocket list- &5List all warps");
		Utils.getMessages().addDefault("command.help.line03", "&7/rocket <warp> - &5Warp to warp");
		Utils.getMessages().addDefault("command.help.line04", "&7/rocket set <warp> - &5Set a warp");
		Utils.getMessages().addDefault("command.help.line05", "&7/rocket remove <warp> - &5Remove a warp");
		Utils.getMessages().addDefault("command.help.footer", "&8============================");
		
		Utils.getMessages().addDefault("command.warp.list.header", "&aThere are the following warps:");
		Utils.getMessages().addDefault("command.warp.list.line01", "&a> [WARPS]");

		Utils.getMessages().addDefault("command.warp.set.successful", "&aYou set the warp &e[WARP]&a!");
		Utils.getMessages().addDefault("command.warp.set.failed.save", "&cThe warp &e[WARP] &ccould not be set!");

		Utils.getMessages().addDefault("command.warp.remove.successful", "&aYou removed the warp &e[WARP]&a!");
		Utils.getMessages().addDefault("command.warp.remove.failed.save", "&cThe warp &e[WARP] &ccould not be removed!");
		Utils.getMessages().addDefault("command.warp.remove.failed.notexists", "&cThe warp &e[WARP] &cdoes not exists!");
		
		Utils.getMessages().addDefault("command.warp.teleport.successful", "&aYou are now at warp &e[WARP]&a!");
		Utils.getMessages().addDefault("command.warp.teleport.failed", "&cThe warp &e[WARP] &cdoes not exists!");
		Utils.getMessages().addDefault("command.warp.teleport.failed_certain_worlds", "&cIn your world are rockets forbidden!");
		
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
			Bukkit.getConsoleSender().sendMessage("§cThe messages.yml could not be saved! Restart your Server!");
			e.printStackTrace();
		}
	}
}
