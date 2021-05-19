package com.alessiodp.core.common.messaging;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public abstract class ADPPacket implements Serializable {
	@Getter private final String version;
	
	/**
	 * Get the name of the packet. Usually the name of the packet type.
	 *
	 * @return the name of the packet
	 */
	public abstract String getName();
	
	/**
	 * Serialize the packet as byte array
	 *
	 * @throws IOException if the deserialization fails
	 * @return the result as byte array
	 */
	public byte[] build() throws IOException {
		byte[] ret;
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ObjectOutputStream out = new ObjectOutputStream(baos);
			out.writeObject(this);
			out.flush();
			ret = baos.toByteArray();
		}
		return ret;
	}
}
