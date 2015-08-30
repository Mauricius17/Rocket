package de.mauricius17.rocket.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import de.mauricius17.rocket.system.Rocket;

public class Recipes {

	public static void createRocketRecipe() {
		ItemStack item = Items.getItemStack(Utils.getRocketItem(), Utils.getRocketName(), 1, (byte) 0);
		
		ShapedRecipe shapedRecipe = new ShapedRecipe(item);
		
		shapedRecipe.shape("ABC", "DEF", "GHI");
		
		for(RocketRecipes rocketRecipes : RocketRecipes.values()) {
			if(rocketRecipes.getMaterial() != Material.AIR) {
				shapedRecipe.setIngredient(rocketRecipes.getName(), rocketRecipes.getMaterial());
			}
		}
		
		Rocket.getInstance().getServer().addRecipe(shapedRecipe);
	}	
	
	public static void createParachuteRecipe() {
		ItemStack item = Items.getItemStack(Utils.getParachuteItem(), Utils.getParachuteName(), 1, (byte) 0);
		ShapedRecipe shapedRecipe = new ShapedRecipe(item);
		
		shapedRecipe.shape("ABC", "DEF", "GHI");
		
		for(ParachuteRecipes parachuteRecipes : ParachuteRecipes.values()) {
			if(parachuteRecipes.getMaterial() != Material.AIR) {
				shapedRecipe.setIngredient(parachuteRecipes.getName(), parachuteRecipes.getMaterial());
			}
		}
		
		Bukkit.addRecipe(shapedRecipe);
	}	
	
	public enum ParachuteRecipes {
		A(Material.AIR, 'A'),
		B(Material.WEB, 'B'),
		C(Material.AIR, 'C'),
		
		D(Material.WEB, 'D'),
		E(Material.STRING, 'E'),
		F(Material.WEB, 'F'),
		
		G(Material.STRING, 'G'),
		H(Material.AIR, 'H'),
		I(Material.STRING, 'I');
		
		Material material;
		char name;
		
		private ParachuteRecipes(Material material, char name) {
			this.material = material;
			this.name = name;
		}
		
		public char getName() {
			return name;
		}
		
		public void setMaterial(Material material) {
			this.material = material;
		}
		
		public Material getMaterial() {
			return material;
		}
		
		public String getChar() {
			return Character.toString(name);
		}
	}
	
	public enum RocketRecipes {
		A(Material.AIR, 'A'),
		B(Material.IRON_BLOCK, 'B'),
		C(Material.AIR, 'C'),
		
		D(Material.FENCE, 'D'),
		E(Material.IRON_BLOCK, 'E'),
		F(Material.FENCE, 'F'),
		
		G(Material.FENCE, 'G'),
		H(Material.FIREWORK, 'H'),
		I(Material.FENCE, 'I');
		
		Material material;
		char name;
		
		private RocketRecipes(Material material, char name) {
			this.material = material;
			this.name = name;
		}
		
		public char getName() {
			return name;
		}
		
		public void setMaterial(Material material) {
			this.material = material;
		}
		
		public Material getMaterial() {
			return material;
		}
		
		public String getChar() {
			return Character.toString(name);
		}
	}
}