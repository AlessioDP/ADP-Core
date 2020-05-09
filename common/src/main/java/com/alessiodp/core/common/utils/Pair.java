package com.alessiodp.core.common.utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Pair<K, V> {
	@Getter @Setter private K key;
	@Getter @Setter private V value;
}
