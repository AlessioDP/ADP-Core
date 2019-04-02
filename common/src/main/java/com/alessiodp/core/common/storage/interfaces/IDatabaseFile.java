package com.alessiodp.core.common.storage.interfaces;

import ninja.leaping.configurate.ConfigurationNode;

import java.io.IOException;
import java.nio.file.Path;

public interface IDatabaseFile {
	
	/**
	 * Initialize the file database dispatcher
	 */
	void initFile();
	
	/**
	 * Save the database to file
	 *
	 * @throws IOException if save fails
	 */
	void saveFile() throws IOException;
	
	/**
	 * Create the database file if not exists
	 *
	 * @return the path of the data file
	 */
	Path createDataFile();
	
	/**
	 * Is the database failed to start?
	 *
	 * @return true if the initialization has failed
	 */
	boolean isFailed();
	
	/**
	 * Root of the database
	 *
	 * @return the root node
	 */
	ConfigurationNode getRootNode();
}
