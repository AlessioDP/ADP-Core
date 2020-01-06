package com.alessiodp.core.bukkit.gui;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
public class MenuSlot {
	@Getter private int x;
	@Getter private int y;
	
	public int calculateSlot(int lengthRow) {
		return x + (y * lengthRow);
	}
	
	public static MenuSlot makeMenuSlot(int slot, int lengthRow) {
		int y = lengthRow > 0 ? slot / lengthRow : 0;
		int x = lengthRow > 0 ? slot % lengthRow : slot;
		return new MenuSlot(x, y);
	}
	
	@Override
	public boolean equals(Object object) {
		return (object instanceof MenuSlot
				&& x == ((MenuSlot) object).x
				&& y == ((MenuSlot) object).y);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
