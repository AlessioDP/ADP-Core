package com.alessiodp.core.bukkit.utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Field;

public class EnchantmentGlow extends EnchantmentWrapper {
	private volatile static Enchantment glow;
	private static final Object lock = new Object();
	
	public EnchantmentGlow() {
		super("enchantment_glow");
	}
	
	@Override
	public boolean canEnchantItem(@NonNull ItemStack item) {
		return true;
	}
	
	@Override
	public boolean conflictsWith(@NonNull Enchantment other) {
		return false;
	}
	
	@SuppressWarnings("deprecation")
	@Override @NonNull
	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.ALL;
	}
	
	@Override
	public int getMaxLevel() {
		return 1;
	}
	
	@Override @NonNull
	public String getName() {
		return "Glow";
	}
	
	@Override
	public int getStartLevel() {
		return 1;
	}
	
	public static Enchantment getGlow() {
		if (glow == null) {
			synchronized (lock) {
				if (glow == null) {
					try {
						Field f = Enchantment.class.getDeclaredField("acceptingNew");
						f.setAccessible(true);
						f.set(null, true);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					Enchantment eg = new EnchantmentGlow();
					if (EnchantmentGlow.getByKey(eg.getKey()) != null) {
						eg = EnchantmentGlow.getByKey(eg.getKey());
					} else {
						Enchantment.registerEnchantment(eg);
					}
					glow = eg;
				}
			}
		}
		return glow;
	}
	
	public static void addGlow(ItemStack item, ItemMeta meta) {
		item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	}
	
	public static void removeGlow(ItemStack item) {
		Enchantment glow = getGlow();
		item.removeEnchantment(glow);
	}
}
