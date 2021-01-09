package com.alessiodp.core.common.storage.sql.connection;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.addons.ADPLibrary;
import com.alessiodp.core.common.storage.StorageType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;

public class H2ConnectionFactory implements ConnectionFactory {
	private final ADPPlugin plugin;
	@Setter private String databaseUrl;
	@Setter @Getter private String tablePrefix = "";
	@Getter private DataSource dataSource;
	@Getter private Jdbi jdbi;
	@Getter private boolean failed;
	
	public H2ConnectionFactory(@Nullable ADPPlugin plugin, @NonNull String databaseUrl) {
		this.plugin = plugin;
		this.databaseUrl = databaseUrl;
	}
	
	public H2ConnectionFactory(@Nullable ADPPlugin plugin, @NonNull Path databasePath) {
		this.plugin = plugin;
		this.databaseUrl = "jdbc:h2:" + databasePath.toString() + ";IGNORECASE=TRUE";
	}
	
	@Override
	public void init() {
		failed = true;
		ClassLoader classLoader;
		if (plugin != null) {
			// Load it via library manager
			classLoader = plugin.getLibraryManager().getIsolatedClassLoaderOf(ADPLibrary.H2);
		} else {
			// Load it normally (for test cases)
			classLoader = getClass().getClassLoader();
		}
		try {
			Class<?> dataSourceClass = classLoader.loadClass("org.h2.jdbcx.JdbcDataSource");
			dataSource = (DataSource) dataSourceClass.getConstructor().newInstance();
			dataSourceClass.getMethod("setUrl", String.class).invoke(dataSource, databaseUrl);
		} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
			throw new RuntimeException(e);
		}
		
		jdbi = Jdbi.create(dataSource);
		jdbi.installPlugin(new SqlObjectPlugin());
		jdbi.define("prefix", tablePrefix);
		
		failed = false;
	}
	
	@Override
	public void stop() {
		// Nothing to do
	}
	
	@Override
	public StorageType getType() {
		return StorageType.H2;
	}
}
