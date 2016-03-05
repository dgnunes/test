package org.fiveware.test.enumerations;

public enum StatusCivilEnum {
	SOLTEIRO ("SOLTEIRO"),
	CASADO ("CASADO"),
	DIVORCIADO ("DIVORCIADO"),
	VIUVO ("VIUVO");
	
	private final String value;

	private StatusCivilEnum(String status) {
		this.value = status;
	}

	public String getStatus() {
		return value;
	}

}
