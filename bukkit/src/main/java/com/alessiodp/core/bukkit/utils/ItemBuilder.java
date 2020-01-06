package com.alessiodp.core.bukkit.utils;

import com.alessiodp.core.common.ADPPlugin;
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
	private final ADPPlugin plugin;
	private final ItemStack itemStack;
	private final ItemMeta itemMeta;
	
	public ItemBuilder(@NonNull ADPPlugin plugin, @NonNull ItemStack itemStack) {
		this.plugin = plugin;
		this.itemStack = itemStack;
		itemMeta = itemStack.getItemMeta() != null ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
	}
	
	public ItemBuilder(ADPPlugin plugin, @NonNull Material material) {
		this(plugin, new ItemStack(material));
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
		itemMeta.setDisplayName(colorTranslation ? plugin.getColorUtils().convertColors(name) : name);
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
			lore.forEach((line) -> temp.add(colorTranslation ? plugin.getColorUtils().convertColors(line) : line));
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
