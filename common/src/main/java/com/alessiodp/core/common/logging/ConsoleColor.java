package com.alessiodp.core.common.logging;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConsoleColor {
	RESET("\u001B[0m"),
	BLACK("\u001B[30m"),
	BLUE("\u001B[34m"),
	CYAN("\u001B[36m"),
	GREEN("\u001B[32m"),
	PURPLE("\u001B[35m"),
	RED("\u001B[31m"),
	YELLOW("\u001B[33m"),
	WHITE("\u001B[37m");

	@Getter private final String code;
}
