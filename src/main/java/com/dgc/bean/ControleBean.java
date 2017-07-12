package com.dgc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dgc.entidade.Plano;
import com.dgc.entidade.Role;
import com.dgc.entidade.Usuario;
import com.dgc.service.UsuarioService;
import com.dgc.util.AtividadeTO;
import com.dgc.util.Retorno;
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
	private List<AtividadeTO> atividades;
	private Plano plano = new Plano();
	private List<Plano> listaPlanosAbertos;

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

			Retorno retorno = service.adicionarRole(getRoleNovo(), getAtividades());
			if (retorno.isSucesso()) {

				consultarRoleDoDia();
				Util.mensagem(FacesMessage.SEVERITY_INFO, retorno.getMsg(), "");
				setRoleNovo(new Role());
			} else {
				Util.mensagem(FacesMessage.SEVERITY_WARN, retorno.getMsg(), "");

			}

		} catch (Exception e) {
		}

	}

	public void adicionarPlano() {
		try {

			Retorno retorno = service.adicionarPlano(getPlano());
			if (retorno.isSucesso()) {

				Util.mensagem(FacesMessage.SEVERITY_INFO, retorno.getMsg(), "");
				setPlano(new Plano());
			} else {
				Util.mensagem(FacesMessage.SEVERITY_WARN, retorno.getMsg(), "");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void atualizarTempo() {
	}

	public void finalizarRole() {
		try {
			service.finalizarRole(getRoleSelecionado());
			consultarRoleDoDia();
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
			consultarRoleDoDia();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removerRole() {
		try {
			service.removerRole(getRoleSelecionado());
			consultarRoleDoDia();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void consultarRoleDoDia() {
		try {
			setListaRoles(service.consultarRoleDoDia());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
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
			consultarRoleDoDia();
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

	public List<AtividadeTO> getAtividades() {
		if (atividades == null) {
			atividades = new ArrayList<AtividadeTO>();

			AtividadeTO atividade = new AtividadeTO();
			atividade.setIdAtividade(1l);
			atividade.setDescricao("H - Plano");
			atividade.setSigla("H");
			atividade.setValor(0f);
			atividades.add(atividade);

			atividade = new AtividadeTO();
			atividade.setIdAtividade(2l);
			atividade.setDescricao("C - Hora simples R$60");
			atividade.setSigla("C");
			atividade.setValor(60f);
			atividades.add(atividade);

			atividade = new AtividadeTO();
			atividade.setIdAtividade(3l);
			atividade.setDescricao("P - Hora + Prancha R$80");
			atividade.setSigla("P");
			atividade.setValor(80f);
			atividades.add(atividade);

			atividade = new AtividadeTO();
			atividade.setIdAtividade(4l);
			atividade.setDescricao("+ Hora R$40");
			atividade.setSigla("+H");
			atividade.setValor(40f);
			atividades.add(atividade);

			atividade = new AtividadeTO();
			atividade.setIdAtividade(5l);
			atividade.setDescricao("S - Stand-up R$30");
			atividade.setSigla("S");
			atividade.setValor(30f);
			atividades.add(atividade);

			atividade = new AtividadeTO();
			atividade.setIdAtividade(6l);
			atividade.setDescricao("2S - 2 Stand-up R$50");
			atividade.setSigla("2S");
			atividade.setValor(50f);
			atividades.add(atividade);

			atividade = new AtividadeTO();
			atividade.setIdAtividade(7l);
			atividade.setDescricao("Fly 20m R$100");
			atividade.setSigla("F2");
			atividade.setValor(100f);
			atividades.add(atividade);

			atividade = new AtividadeTO();
			atividade.setIdAtividade(8l);
			atividade.setDescricao("Fly 30m R$120");
			atividade.setSigla("F3");
			atividade.setValor(120f);
			atividades.add(atividade);

			atividade = new AtividadeTO();
			atividade.setIdAtividade(9l);
			atividade.setDescricao("Outros");
			atividade.setSigla("O");
			atividade.setValor(0f);
			atividades.add(atividade);
		}
		return atividades;
	}

	public void setAtividades(List<AtividadeTO> atividades) {
		this.atividades = atividades;
	}

	public Plano getPlano() {
		return plano;
	}

	public void setPlano(Plano plano) {
		this.plano = plano;
	}

	public List<Plano> getListaPlanosAbertos() {
		try {
			setListaPlanosAbertos(service.consultarPlanosAbertos());
		} catch (Exception e) {
		}
		return listaPlanosAbertos;
	}

	public void setListaPlanosAbertos(List<Plano> listaPlanosAbertos) {
		this.listaPlanosAbertos = listaPlanosAbertos;
	}

}