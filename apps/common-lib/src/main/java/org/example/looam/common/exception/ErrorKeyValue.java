package org.example.looam.common.exception;

public record ErrorKeyValue<K, V>(K field, V value) {}
