package com.alessiodp.core.bukkit.utils;

import com.alessiodp.core.common.utils.Color;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {
	private final ItemStack itemStack;
	private final ItemMeta itemMeta;
	
	public ItemBuilder(@NonNull ItemStack itemStack) {
		this.itemStack = itemStack;
		itemMeta = itemStack.getItemMeta() != null ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
	}
	
	public ItemBuilder(@NonNull Material material) {
		this(new ItemStack(material));
	}
	
	public ItemBuilder(@NonNull String materialName, Material fallbackMaterial) {
		this(new ItemStack(parseMaterial(materialName, fallbackMaterial)));
	}
	
	public static Material parseMaterial(String materialName, Material fallbackMaterial) {
		Material ret = fallbackMaterial;
		try {
			ret = Material.valueOf(materialName);
		} catch (Exception ignored) {}
		return ret;
	}
	
	public ItemStack build() {
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
	
	public ItemBuilder setAmount(int amount) {
		itemStack.setAmount(amount);
		return this;
	}
	
	public ItemBuilder setName(@NonNull String name) {
		return setName(name, true);
	}
	
	public ItemBuilder setName(@NonNull String name, boolean colorTranslation) {
		itemMeta.setDisplayName(colorTranslation ? Color.translateAlternateColorCodes(name) : name);
		return this;
	}
	
	public ItemBuilder setLore(@NonNull String... lore) {
		return setLore(Arrays.asList(lore), true);
	}
	
	public ItemBuilder setLore(@NonNull List<String> lore) {
		return setLore(lore, true);
	}
	
	public ItemBuilder setLore(@NonNull List<String> lore, boolean colorTranslation) {
		if (lore.size() > 0) {
			List<String> temp = new ArrayList<>();
			lore.forEach((line) -> temp.add(colorTranslation ? Color.translateAlternateColorCodes(line) : line));
			itemMeta.setLore(temp);
		}
		return this;
	}
	
	public ItemBuilder setGlow(boolean value) {
		if (value) {
			itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 100, true);
			itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		} else {
			itemMeta.removeEnchant(Enchantment.ARROW_DAMAGE);
			itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		return this;
	}
}
