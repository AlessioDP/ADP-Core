package com.alessiodp.core.common.storage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StorageType {
	NONE("None"),
	YAML("YAML"),
	MYSQL("MySQL"),
	SQLITE("SQLite");
	
	@Getter private final String formattedName;
	
	/**
	 * Get storage type by its name
	 *
	 * @param storageType the name of the storage type to get
	 * @return the storage type found
	 */
	public static StorageType getEnum(String storageType) {
		StorageType ret = null;
		for (StorageType st : StorageType.values()) {
			if (st.name().equalsIgnoreCase(storageType)) {
				ret = st;
				break;
			}
		}
		return ret;
	}
}