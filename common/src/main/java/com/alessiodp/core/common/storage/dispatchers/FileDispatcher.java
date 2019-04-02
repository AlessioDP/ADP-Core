package com.alessiodp.core.common.storage.dispatchers;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.storage.interfaces.IDatabaseDispatcher;
import com.alessiodp.core.common.storage.interfaces.IDatabaseFile;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class FileDispatcher implements IDatabaseDispatcher {
	@NonNull protected final ADPPlugin plugin;
	
	protected IDatabaseFile database;
	
	@Override
	public final void stop() {
		// Nothing to do
	}
	
	@Override
	public final boolean isFailed() {
		return database == null || database.isFailed();
	}
}
