package com.dgc.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dgc.entidade.Usuario;
import com.dgc.service.UsuarioService;
import com.dgc.util.Retorno;
import com.dgc.util.Util;

@ManagedBean(name = "usuarioBean")
@Controller
@Scope(value = "session")
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuarioLogin = new Usuario();
	private Usuario usuarioNovo = new Usuario();

	@Autowired
	private UsuarioService service;

	public void logar() {

		if (service.logar(getUsuarioLogin())) {
			Util.redirecionar(Util.PAGINA_ROLE);
		} else {
			Util.mensagem(FacesMessage.SEVERITY_WARN, "E-mail ou senha inválidos!", "");
		}

	}

	public void novoRider() {
		Util.redirecionar(Util.PAGINA_NOVO_RIDER);
	}

	public void salvarRider() {

		try {
			Retorno retorno = service.salvarRider(getUsuarioNovo());
			Util.mensagem(FacesMessage.SEVERITY_INFO, retorno.getMsg(), "");
			setUsuarioNovo(new Usuario());
		} catch (Exception e) {
		}

	}

	/* GET E SET */
	public UsuarioService getService() {
		return service;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public Usuario getUsuarioNovo() {
		return usuarioNovo;
	}

	public void setUsuarioNovo(Usuario usuarioNovo) {
		this.usuarioNovo = usuarioNovo;
	}

}