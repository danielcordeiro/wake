package com.dgc.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dgc.service.UsuarioService;

@ManagedBean(name = "dashBoardBean")
@Controller
@Scope(value = "session")
public class DashBoardBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer riderCadastrados24h;
	private Integer riderCadastrados;
	private Integer horasOntem;
	private Integer horasTotal;

	@Autowired
	private UsuarioService service;

	public void atualizarDashBoard() {
		try {
			setRiderCadastrados24h(service.count24h());
			setRiderCadastrados(service.countAll());
			setHorasTotal(service.countAllHoras());
			setHorasOntem(service.countHorasOntem());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer getRiderCadastrados24h() {
		if (riderCadastrados24h == null) {
			atualizarDashBoard();
		}
		return riderCadastrados24h;
	}

	public void setRiderCadastrados24h(Integer riderCadastrados24h) {
		this.riderCadastrados24h = riderCadastrados24h;
	}

	public Integer getRiderCadastrados() {
		if (riderCadastrados == null) {
			atualizarDashBoard();
		}
		return riderCadastrados;
	}

	public void setRiderCadastrados(Integer riderCadastrados) {
		this.riderCadastrados = riderCadastrados;
	}

	public Integer getHorasOntem() {
		return horasOntem;
	}

	public void setHorasOntem(Integer horasOntem) {
		this.horasOntem = horasOntem;
	}

	public Integer getHorasTotal() {
		return horasTotal;
	}

	public void setHorasTotal(Integer horasTotal) {
		this.horasTotal = horasTotal;
	}

}