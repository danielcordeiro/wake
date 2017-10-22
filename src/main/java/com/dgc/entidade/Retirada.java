package com.dgc.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.dgc.util.Util;

@Entity
@Table(name = "retirada", schema = "public")
public class Retirada extends Entidade {

	private static final long serialVersionUID = -142718630823260613L;

	@Id
	@SequenceGenerator(name = "retirada_id", sequenceName = "seq_retirada", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "retirada_id")
	@Column(name = "id", unique = true, nullable = false)
	private long id;

	@Column(name = "data")
	private Date data;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "valor")
	private Float valor;

	@Column(name = "id_caixa")
	private Long idCaixa;

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

	public boolean isIndDeletado() {
		return indDeletado;
	}

	public Float getValor() {
		return valor;
	}

	public String getValorFormat() {
		return Util.decimalToDinheiro(getValor());
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public Long getIdCaixa() {
		return idCaixa;
	}

	public void setIdCaixa(Long idCaixa) {
		this.idCaixa = idCaixa;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
