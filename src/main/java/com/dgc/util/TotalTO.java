package com.dgc.util;

public class TotalTO {

	private Float totalHora;
	private Float totalPlano;
	private Float totalDinheiro;
	private Float totalDebito;
	private Float totalCredito;
	private Integer qtdHorasPagas;
	private Integer qtdHoras;
	private Float aberturaCaixa;
	private Float totalDinheiroSaldo;
	private Float totalRetirada;

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

	public Integer getQtdHorasPagas() {
		return qtdHorasPagas;
	}

	public void setQtdHorasPagas(Integer qtdHorasPagas) {
		this.qtdHorasPagas = qtdHorasPagas;
	}

	public Integer getQtdHoras() {
		return qtdHoras;
	}

	public void setQtdHoras(Integer qtdHoras) {
		this.qtdHoras = qtdHoras;
	}

	public Float getAberturaCaixa() {
		return aberturaCaixa;
	}

	public String getAberturaCaixaFormat() {
		return Util.decimalToDinheiro(aberturaCaixa);
	}

	public void setAberturaCaixa(Float aberturaCaixa) {
		this.aberturaCaixa = aberturaCaixa;
	}

	public Float getTotalDinheiroSaldo() {
		return totalDinheiroSaldo;
	}

	public String getTotalDinheiroSaldoFormat() {
		return Util.decimalToDinheiro(totalDinheiroSaldo);
	}

	public void setTotalDinheiroSaldo(Float totalDinheiroSaldo) {
		this.totalDinheiroSaldo = totalDinheiroSaldo;
	}

	public Float getTotalRetirada() {
		return totalRetirada;
	}

	public void setTotalRetirada(Float totalRetirada) {
		this.totalRetirada = totalRetirada;
	}

}
