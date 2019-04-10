package com.alessiodp.core.common.messaging;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ADPPacket {
	@Getter private final String version;
	
	/**
	 * Build the byte array of the packet
	 *
	 * @return the result as byte array
	 */
	public abstract byte[] build();
}
