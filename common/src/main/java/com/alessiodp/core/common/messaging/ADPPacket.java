package com.alessiodp.core.common.messaging;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public abstract class ADPPacket {
	@Getter private final String version;
	
	/**
	 * Get the name of the packet. Usually the name of the packet type.
	 *
	 * @return the name of the packet
	 */
	public abstract String getName();
	
	/**
	 * Build the byte array of the packet
	 *
	 * @return the result as byte array
	 */
	public abstract byte[] build();
}
