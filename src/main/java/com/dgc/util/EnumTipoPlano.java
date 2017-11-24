package com.dgc.util;

public enum EnumTipoPlano {
	SPTS("S/ Prancha Terça a Sexta", "SP TS", 30), 
	SPTD("S/ Prancha Terça a Domingo", "SP TD", 30), 
	SPTS90("S/ Prancha Terça a Sexta 90 dias", "SP TS 90", 90), 
	SPTD90("S/ Prancha Terça a Domingo 90", "SP TD 90", 90), 
	CPTS("C/ Prancha Terça a Sexta", "CP TS", 30), 
	CPTD("C/ Prancha Terça a Domingo", "CP TD", 30), 
	CPTS90("C/ Prancha Terça a Sexta 90 dias", "CP TS 90", 90), 
	CPTD90("C/ Prancha Terça a Domingo 90", "CP TD 90", 90),
	SPTD0("S/ Prancha Terça a Domingo - sem limite", "SP TD0", 00);
	
	private String label;
	private String value;
	private Integer dias;

	private EnumTipoPlano(final String label, final String value, final Integer dias) {
		this.label = label;
		this.value = value;
		this.dias = dias;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getDias() {
		return dias;
	}

	public void setDias(Integer dias) {
		this.dias = dias;
	}
	
}
