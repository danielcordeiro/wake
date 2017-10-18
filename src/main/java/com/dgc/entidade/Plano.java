package com.dgc.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.dgc.util.Util;

@Entity
@Table(name = "plano", schema = "public")
public class Plano extends Entidade {

	private static final long serialVersionUID = -142718630823260613L;

	@Id
	@SequenceGenerator(name = "plano_id", sequenceName = "seq_plano", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "plano_id")
	@Column(name = "id", unique = true, nullable = false)
	private long id;

	@Column(name = "data_compra")
	private Date dataCompra;

	@Column(name = "id_rider")
	private Long idRider;

	@Column(name = "horas_restantes")
	private Integer horasRestantes;

	@Column(name = "valor")
	private Float valor;

	@Column(name = "forma")
	private String forma;

	@Column(name = "tipo")
	private String tipo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_rider", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Usuario rider = new Usuario();

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

	public Date getDataCompra() {
		return dataCompra;
	}

	public String getDataCompraFormat() {
		return Util.formataDataHora(getDataCompra());
	}

	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}

	public Long getIdRider() {
		return idRider;
	}

	public void setIdRider(Long idRider) {
		this.idRider = idRider;
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

	public boolean isIndDeletado() {
		return indDeletado;
	}

	public Integer getHorasRestantes() {
		return horasRestantes;
	}

	public void setHorasRestantes(Integer horasRestantes) {
		this.horasRestantes = horasRestantes;
	}

	public Usuario getRider() {
		return rider;
	}

	public void setRider(Usuario rider) {
		this.rider = rider;
	}

	public String getForma() {
		return forma;
	}

	public void setForma(String forma) {
		this.forma = forma;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public static boolean isWeekendPlan(String tipo2) {
		return tipo2.contains("TD");
	}

}
