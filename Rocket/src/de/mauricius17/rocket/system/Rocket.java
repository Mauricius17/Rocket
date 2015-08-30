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
import de.mauricius17.rocket.enums.CertainWorlds;
import de.mauricius17.rocket.utils.Items;
import de.mauricius17.rocket.utils.Recipes;
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
		
		Recipes.ParachuteRecipes.A.setMaterial(Items.getMaterial(Utils.getConfig().getString("parachute.recipe.A")));
		Recipes.ParachuteRecipes.B.setMaterial(Items.getMaterial(Utils.getConfig().getString("parachute.recipe.B")));
		Recipes.ParachuteRecipes.C.setMaterial(Items.getMaterial(Utils.getConfig().getString("parachute.recipe.C")));
		
		Recipes.ParachuteRecipes.D.setMaterial(Items.getMaterial(Utils.getConfig().getString("parachute.recipe.D")));
		Recipes.ParachuteRecipes.E.setMaterial(Items.getMaterial(Utils.getConfig().getString("parachute.recipe.E")));
		Recipes.ParachuteRecipes.F.setMaterial(Items.getMaterial(Utils.getConfig().getString("parachute.recipe.F")));
		
		Recipes.ParachuteRecipes.G.setMaterial(Items.getMaterial(Utils.getConfig().getString("parachute.recipe.G")));
		Recipes.ParachuteRecipes.H.setMaterial(Items.getMaterial(Utils.getConfig().getString("parachute.recipe.H")));
		Recipes.ParachuteRecipes.I.setMaterial(Items.getMaterial(Utils.getConfig().getString("parachute.recipe.I")));
		
		Recipes.RocketRecipes.A.setMaterial(Items.getMaterial(Utils.getConfig().getString("rocket.recipe.A")));
		Recipes.RocketRecipes.B.setMaterial(Items.getMaterial(Utils.getConfig().getString("rocket.recipe.B")));
		Recipes.RocketRecipes.C.setMaterial(Items.getMaterial(Utils.getConfig().getString("rocket.recipe.C")));
		
		Recipes.RocketRecipes.D.setMaterial(Items.getMaterial(Utils.getConfig().getString("rocket.recipe.D")));
		Recipes.RocketRecipes.E.setMaterial(Items.getMaterial(Utils.getConfig().getString("rocket.recipe.E")));
		Recipes.RocketRecipes.F.setMaterial(Items.getMaterial(Utils.getConfig().getString("rocket.recipe.F")));
		
		Recipes.RocketRecipes.G.setMaterial(Items.getMaterial(Utils.getConfig().getString("rocket.recipe.G")));
		Recipes.RocketRecipes.H.setMaterial(Items.getMaterial(Utils.getConfig().getString("rocket.recipe.H")));
		Recipes.RocketRecipes.I.setMaterial(Items.getMaterial(Utils.getConfig().getString("rocket.recipe.I")));
		
		if(Utils.getConfig().getBoolean("recipes.enable")) {
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
		Utils.getConfig().addDefault("certain_worlds", false);		
		Utils.getConfig().addDefault("worlds", "world,world_nether");
		Utils.getConfig().addDefault("recipes.enable", true);
		
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
