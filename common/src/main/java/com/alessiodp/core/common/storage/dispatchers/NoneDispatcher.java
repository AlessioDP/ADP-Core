package com.alessiodp.core.common.storage.dispatchers;

import com.alessiodp.core.common.storage.StorageType;
import com.alessiodp.core.common.storage.interfaces.IDatabaseDispatcher;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoneDispatcher implements IDatabaseDispatcher {
	@Override
	public void init(StorageType type) {
		// Nothing to initialize
	}
	
	@Override
	public void stop() {
		// Nothing to stop
	}
	
	@Override
	public boolean isFailed() {
		return false;
	}
}
