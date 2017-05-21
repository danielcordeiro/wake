package com.dgc.entidade;

import java.io.Serializable;
import java.util.Date;

public class Entidade implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dataAlteracaoEnt;

	private Long idUsuarioEnt;

	private boolean indDeletadoEnt;

	public Date getDataAlteracao() {
		return dataAlteracaoEnt;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracaoEnt = dataAlteracao;
	}

	public Long getIdUsuario() {
		return idUsuarioEnt;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuarioEnt = idUsuario;
	}

	public boolean getIndDeletado() {
		return indDeletadoEnt;
	}

	public void setIndDeletado(boolean indDeletado) {
		this.indDeletadoEnt = indDeletado;
	}

	public String toStringLog() {
		return "";
	}

}
