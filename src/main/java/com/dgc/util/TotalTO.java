package com.dgc.util;

public class TotalTO {

	private Float totalHora;
	private Float totalPlano;
	private Float totalDinheiro;
	private Float totalDebito;
	private Float totalCredito;

	public Float getTotalHora() {

		return totalHora;
	}

	public String getTotalHoraFormat() {
		return Util.decimalToDinheiro(getTotalHora());
	}

	public void setTotalHora(Float totalHora) {
		this.totalHora = totalHora;
	}

	public String getTotalPlanoFormat() {
		return Util.decimalToDinheiro(getTotalPlano());
	}

	public Float getTotalPlano() {

		return totalPlano;
	}

	public void setTotalPlano(Float totalPlano) {
		this.totalPlano = totalPlano;
	}

	public Float getTotalDinheiro() {
		return totalDinheiro;
	}

	public String getTotalDinheiroFormat() {
		return Util.decimalToDinheiro(totalDinheiro);
	}

	public void setTotalDinheiro(Float totalDinheiro) {
		this.totalDinheiro = totalDinheiro;
	}

	public Float getTotalDebito() {
		return totalDebito;
	}

	public String getTotalDebitoFormat() {
		return Util.decimalToDinheiro(totalDebito);
	}

	public void setTotalDebito(Float totalDebito) {
		this.totalDebito = totalDebito;
	}

	public Float getTotalCredito() {
		return totalCredito;
	}

	public String getTotalCreditoFormat() {
		return Util.decimalToDinheiro(totalCredito);
	}

	public void setTotalCredito(Float totalCredito) {
		this.totalCredito = totalCredito;
	}

}
