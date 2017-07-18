package com.dgc.util;

import java.util.Date;

public class FiltroTO {

	private Date dataInicio;
	private Date dataFim;
	private String dataInicioString = Util.dataAtualString();
	private String dataFimString = Util.dataAtualString();

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getDataInicioString() {
		return dataInicioString;
	}

	public void setDataInicioString(String dataInicioString) {
		this.dataInicioString = dataInicioString;
	}

	public String getDataFimString() {
		return dataFimString;
	}

	public void setDataFimString(String dataFimString) {
		this.dataFimString = dataFimString;
	}

}
