package com.dgc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dgc.entidade.Role;
import com.dgc.entidade.Usuario;
import com.dgc.service.UsuarioService;
import com.dgc.util.Util;

@ManagedBean(name = "controleBean")
@Controller
@Scope(value = "session")
public class ControleBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Role roleNovo = new Role();
	private List<Role> listaRoles;
	private List<Role> listaRolesFechados;
	private Role roleSelecionado = new Role();

	@Autowired
	private UsuarioService service;

	public List<Usuario> autoCompleteRider(String query) {
		try {
			return service.autoCompleteUsuario(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Usuario>();
	}

	public void adicionarRole() {
		try {

			service.adicionarRole(getRoleNovo());
			consultarRoleAberto();
			Util.mensagem(FacesMessage.SEVERITY_INFO, "Role adicionado com sucesso!", "");

			setRoleNovo(new Role());
		} catch (Exception e) {
		}

	}

	public void atualizarTempo() {
	}

	public void finalizarRole() {
		try {
			service.finalizarRole(getRoleSelecionado());
			consultarRoleAberto();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void iniciarRole() {
		try {
			service.iniciarRole(getRoleSelecionado());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void adicionarHora() {
		try {
			service.adicionarHora(getRoleSelecionado());
			consultarRoleAberto();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removerRole() {
		try {
			service.removerRole(getRoleSelecionado());
			consultarRoleAberto();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void consultarRoleAberto() {
		try {
			setListaRoles(service.consultarRoleAberto());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	private void consultarRoleFechado() {
		try {
			setListaRolesFechados(service.consultarRoleFechado());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/* GET E SET */
	public UsuarioService getService() {
		return service;
	}

	public Role getRoleNovo() {
		return roleNovo;
	}

	public void setRoleNovo(Role roleNovo) {
		this.roleNovo = roleNovo;
	}

	public List<Role> getListaRoles() {
		if (listaRoles == null) {
			consultarRoleAberto();
		}
		return listaRoles;
	}

	public void setListaRoles(List<Role> listaRoles) {
		this.listaRoles = listaRoles;
	}

	public Role getRoleSelecionado() {
		return roleSelecionado;
	}

	public void setRoleSelecionado(Role roleSelecionado) {
		this.roleSelecionado = roleSelecionado;
	}

	public void getUsuarioLogado() {
		Util.obterUsuarioSessao();
	}

	public List<Role> getListaRolesFechados() {
		consultarRoleFechado();
		return listaRolesFechados;
	}

	public void setListaRolesFechados(List<Role> listaRolesFechados) {
		this.listaRolesFechados = listaRolesFechados;
	}

}