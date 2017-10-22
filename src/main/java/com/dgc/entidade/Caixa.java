package com.dgc.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "caixa", schema = "public")
public class Caixa extends Entidade {

	private static final long serialVersionUID = -142718630823260613L;

	@Id
	@SequenceGenerator(name = "caixa_id", sequenceName = "seq_caixa", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "caixa_id")
	@Column(name = "id", unique = true, nullable = false)
	private long id;

	@Column(name = "data")
	private Date data;

	@Column(name = "valor_abertura")
	private Float valorAbertura;

	@Column(name = "valor_saida")
	private Float valorSaida;

	@Column(name = "valor_fechamento")
	private Float valorFechamento;

	@Column(name = "valor_debito")
	private Float valorDebito;

	@Column(name = "valor_credito")
	private Float valorCredito;

	@Column(name = "ind_aberto")
	private boolean indAberto;

	@Column(name = "ind_deletado")
	private boolean indDeletado;

	public void setIndDeletado(boolean indDeletado) {
		super.setIndDeletado(indDeletado);
		this.indDeletado = indDeletado;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Float getValorAbertura() {
		return valorAbertura;
	}

	public void setValorAbertura(Float valorAbertura) {
		this.valorAbertura = valorAbertura;
	}

	public Float getValorSaida() {
		return valorSaida;
	}

	public void setValorSaida(Float valorSaida) {
		this.valorSaida = valorSaida;
	}

	public Float getValorFechamento() {
		return valorFechamento;
	}

	public void setValorFechamento(Float valorFechamento) {
		this.valorFechamento = valorFechamento;
	}

	public Float getValorDebito() {
		return valorDebito;
	}

	public void setValorDebito(Float valorDebito) {
		this.valorDebito = valorDebito;
	}

	public Float getValorCredito() {
		return valorCredito;
	}

	public void setValorCredito(Float valorCredito) {
		this.valorCredito = valorCredito;
	}

	public boolean isIndAberto() {
		return indAberto;
	}

	public void setIndAberto(boolean indAberto) {
		this.indAberto = indAberto;
	}

	public boolean isIndDeletado() {
		return indDeletado;
	}

}
