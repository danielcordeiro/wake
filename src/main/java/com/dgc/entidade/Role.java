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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.dgc.util.Util;

@Entity
@Table(name = "role", schema = "public")
public class Role extends Entidade {

	private static final long serialVersionUID = -142718630823260613L;

	@Id
	@SequenceGenerator(name = "role_id", sequenceName = "seq_role", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "role_id")
	@Column(name = "id", unique = true, nullable = false)
	private long id;

	@Column(name = "data_Entrada")
	private Date dataEntrada;
	
	@Column(name = "data_inicio")
	private Date dataInicio;

	@Column(name = "data_fim")
	private Date dataFim;

	@Column(name = "id_rider")
	private Long idRider;

	@Column(name = "valor")
	private Float valor;
	
	@Column(name = "sigla")
	private String sigla;

	@Column(name = "hora")
	private Integer hora;
	
	@Column(name = "id_atividade")
	private Long idAtividade;

	@Column(name = "plano")
	private Boolean plano;
	
	@Column(name = "obs")
	private String obs;
	
	@Column(name = "id_plano")
	private Long idPlano;
	
	@Transient
	private Float valorDiferente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_rider", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Usuario rider = new Usuario();

	@Transient
	private boolean andando;
	
	@Transient
	private boolean terminou;

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

	public Date getDataInicio() {
		return dataInicio;
	}

	public String getDataInicioFormat() {
		return Util.formataDataHora(getDataInicio());
	}
	
	public String getDataInicioFormatHora() {
		return Util.formataHora(getDataInicio());
	}

	public String getTempoRestante() {
		if (!Util.isZero(getHora()) && getDataInicio() != null) {
			return Util.calcularMinutosRestante(getDataInicio(), getHora());
		}
		return "-";
	}

	public String getTempoAndado() {
		if (!Util.isZero(getHora()) && getDataInicio() != null) {
			return Util.calcularMinutosAndados(getDataInicio(), getHora());
		}
		return "-";
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
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

	public Integer getHora() {
		return hora;
	}

	public void setHora(Integer hora) {
		this.hora = hora;
	}

	public Usuario getRider() {
		return rider;
	}

	public void setRider(Usuario rider) {
		this.rider = rider;
	}

	public Boolean getPlano() {
		return plano;
	}

	public void setPlano(Boolean plano) {
		this.plano = plano;
	}

	public boolean isAndando() {
		if (getDataInicio() != null && getDataFim() == null) {
			andando = true;
		}else{
			andando = false;
		}
		return andando;
	}

	public void setAndando(boolean andando) {
		this.andando = andando;
	}

	public boolean isNaFila() {
		return !isAndando() && !isTerminou();
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public boolean isIndDeletado() {
		return indDeletado;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Long getIdAtividade() {
		return idAtividade;
	}

	public void setIdAtividade(Long idAtividade) {
		this.idAtividade = idAtividade;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Float getValorDiferente() {
		return valorDiferente;
	}

	public void setValorDiferente(Float valorDiferente) {
		this.valorDiferente = valorDiferente;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public boolean isTerminou() {
		if(!isAndando() && getDataFim() != null){
			terminou = true;
		}else{
			terminou = false;
		}
		return terminou;
	}

	public void setTerminou(boolean terminou) {
		this.terminou = terminou;
	}

	public Long getIdPlano() {
		return idPlano;
	}

	public void setIdPlano(Long idPlano) {
		this.idPlano = idPlano;
	}
}
