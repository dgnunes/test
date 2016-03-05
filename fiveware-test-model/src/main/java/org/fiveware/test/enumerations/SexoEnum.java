package org.fiveware.test.enumerations;

public enum SexoEnum {
	MASCULINO("MASCULINO"),
	FEMININO("FEMININO");
	
	private final String value;

	private SexoEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
