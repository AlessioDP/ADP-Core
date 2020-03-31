package com.alessiodp.core.common.storage.interfaces;

import lombok.NonNull;
import org.jooq.DSLContext;

import java.util.Map;

public interface IDatabaseSQL {
	
	/**
	 * Initialize the sql database dispatcher
	 *
	 * @param placeholders SQL placeholders
	 * @param charset the charset encoding
	 */
	void initSQL(@NonNull Map<String, String> placeholders, @NonNull String charset);
	
	/**
	 * Stop the sql database dispatcher
	 */
	void stopSQL();
	
	/**
	 * Is the database failed to start?
	 *
	 * @return true if the initialization has failed
	 */
	boolean isFailed();
	
	/**
	 * Get the query builder
	 *
	 * @return the query builder
	 */
	DSLContext getQueryBuilder();
}