package com.alessiodp.core.common.storage;

import com.alessiodp.core.common.ADPPlugin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StorageType {
	NONE("None"),
	YAML("YAML"),
	MYSQL("MySQL"),
	SQLITE("SQLite"),
	H2("H2");
	
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
	
	public boolean initLibraries(ADPPlugin plugin) {
		boolean ret = false;
		switch (this) {
			case YAML:
				plugin.getLibraryManager().setupLibrariesForYAML();
				ret = true;
				break;
			case MYSQL:
				plugin.getLibraryManager().setupLibrariesForMySQL();
				ret = true;
				break;
			case SQLITE:
				plugin.getLibraryManager().setupLibrariesForSQLite();
				ret = true;
				break;
			case H2:
				plugin.getLibraryManager().setupLibrariesForH2();
				ret = true;
				break;
			case NONE:
			default:
				break;
		}
		return ret;
	}
}