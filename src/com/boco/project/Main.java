package com.boco.project;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
	}

	@Override
	public void onDisable() {
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("M")) {
			if(args.length == 0) {
				sender.sendMessage("[ Boco Mob ] /M create [name]");
				sender.sendMessage("[ Boco Mob ] /M name [name] [text]");
				sender.sendMessage("[ Boco Mob ] /M health [name] [number]");
				sender.sendMessage("[ Boco Mob ] /M spawn [name]");
				return false;
			}
			if(args[0].equalsIgnoreCase("create")) {
				if(args.length == 1) { 
					sender.sendMessage("[ Boco Mob ] /M create [name]");
					return false;
				}
				File arg1 = new File("plugins//BocoMob//Mobs//" + args[1] + ".yml");
				if(arg1.exists()) {
					sender.sendMessage("[ Boco Mob ] 이미 존재하는 몹입니다");
					return false;
				}
				YamlConfiguration yaml = YamlConfiguration.loadConfiguration(arg1);
				yaml.set("Mob.Type", "ZOMBIE");
				yaml.set("Mob.Name", "&8Test_Zombie");
				yaml.set("Mob.Damage", 3);
				yaml.set("Mob.Health", 20);
				yaml.set("Mob.Item.Hand", "diamond_sword");
				yaml.set("Mob.Item.Helmet", "diamond_helmet");
				yaml.set("Mob.Item.Chestplate", "diamond_chestplate");
				yaml.set("Mob.Item.Leggings", "diamond_leggings");
				yaml.set("Mob.Item.Boots", "diamond_boots");
				try {
					yaml.save(arg1);
					sender.sendMessage("파일이 생성되었습니다. plugins/BocoMob/Mobs/" + args[1] + ".yml 경로에 들어가셔서 확인해주세요!");
					return false;
					
				} catch (IOException e) {
					sender.sendMessage("알 수 없는 오류로 생성에 실패했습니다");
					return false;
				}
			}
			if(args[0].equalsIgnoreCase("spawn")) {
				if(args.length == 1) {
					sender.sendMessage("[ Boco Mob ] /M spawn [name]");
					return false;
				}
				World w = Bukkit.getWorld("world");
				Player player = (Player) sender;
				Location loc = player.getLocation();
				File file = new File("plugins//BocoMob//Mobs//" + args[1] + ".yml");
				if(!file.exists()) {
					sender.sendMessage("없는 몹이름입니다 다시시도해주세요");
					return false;
				}
				YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);	
				String a = yaml.getString("Mob.Type");
				String name = yaml.getString("Mob.Name");
				String hand = yaml.getString("Mob.Item.Hand");
				String Armor1 = yaml.getString("Mob.Item.Helmet");
				String Armor2 = yaml.getString("Mob.Item.Chestplate");
				String Armor3 = yaml.getString("Mob.Item.Leggings");
				String Armor4 = yaml.getString("Mob.Item.Boots");
			
				int health = yaml.getInt("Mob.Health");
				try {
					Entity Spawn = w.spawnEntity(loc, EntityType.valueOf(a));
					Spawn.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
					Spawn.setCustomNameVisible(true); 
					((LivingEntity) Spawn).setMaxHealth(health);
					((LivingEntity) Spawn).getEquipment().setItemInHand(new ItemStack(Material.valueOf(hand)));
					((LivingEntity) Spawn).getEquipment().setHelmet(new ItemStack(Material.valueOf(Armor1)));
					((LivingEntity) Spawn).getEquipment().setChestplate(new ItemStack(Material.valueOf(Armor2)));
					((LivingEntity) Spawn).getEquipment().setLeggings(new ItemStack(Material.valueOf(Armor3)));
					((LivingEntity) Spawn).getEquipment().setBoots(new ItemStack(Material.valueOf(Armor4)));
					return false;
				} catch(Exception e) {
					sender.sendMessage("몹 스폰중 오류가 발생했습니다");
					return false;
				}
			}
		}
		return false;
	}
}
