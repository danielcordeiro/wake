package com.dgc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dgc.entidade.Caixa;
import com.dgc.entidade.Plano;
import com.dgc.entidade.Retirada;
import com.dgc.entidade.Role;
import com.dgc.entidade.Usuario;
import com.dgc.service.UsuarioService;
import com.dgc.util.AtividadeTO;
import com.dgc.util.FiltroTO;
import com.dgc.util.Retorno;
import com.dgc.util.TotalTO;
import com.dgc.util.Util;

@ManagedBean(name = "controleBean")
@Controller
@Scope(value = "session")
public class ControleBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Role roleNovo = new Role();
	private List<Role> listaRoles;
	private Role roleSelecionado = new Role();
	private List<AtividadeTO> atividades;
	private Plano plano = new Plano();
	private List<Plano> listaPlanosAbertos;
	private List<Role> listaRolesFechadosDia;
	private List<Plano> listaPlanosVendidosDia;
	private TotalTO total;
	private TotalTO totalFiltro;
	private List<Plano> listaPlanosVendidos;
	private List<Role> listaRolesFechados;
	private Usuario riderSelecionado = new Usuario();
	private List<Usuario> listaRiders;
	private List<Retirada> listaRetiradas;

	private List<Plano> listaPlanosVendidosFiltro = new ArrayList<Plano>();
	private List<Role> listaRolesFechadosFiltro = new ArrayList<Role>();
	private FiltroTO filtroRelatorio = new FiltroTO();
	private TotalTO totalRelatorio;
	private Plano planoSelecionado;

	private List<String> formas;

	private Caixa caixa;
	private Retirada retirada;

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

			Retorno retorno = service.adicionarRole(getRoleNovo(), getAtividades(), getListaRoles());
			if (retorno.isSucesso()) {
				setRoleSelecionado(getRoleNovo());
				iniciarRole();

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

			Retorno retorno = service.adicionarPlano(getPlano(), true);
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
	
	public void alterarPlano() {
		try {
			
			Retorno retorno = service.adicionarPlano(getPlano(), false);
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
		if (getListaRoles().isEmpty()) {
			consultarRoleDoDia();
		}
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

	public void consultarRoleDoDia() {
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

	private void consultarRoleFechado(Date date) {
		try {
			if (date == null) {
				setListaRolesFechados(service.consultarRoleFechado(date));
			} else {
				setListaRolesFechadosDia(service.consultarRoleFechado(date));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void consultarPlanosVedidos(Date date) {
		try {
			if (date == null) {
				setListaPlanosVendidos(service.consultarPlanosVendidos(date));
			} else {
				setListaPlanosVendidosDia(service.consultarPlanosVendidos(date));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void consultaRelatorioDiario() {
		try {
			setFiltroRelatorio(new FiltroTO());
			setListaRolesFechadosFiltro(service.consultarRoleRelatorio(getFiltroRelatorio()));
			setListaPlanosVendidosFiltro(service.consultarPlanoRelatorio(getFiltroRelatorio()));
			setTotalRelatorio(service.calcularTotais(getListaPlanosVendidosFiltro(), getListaRolesFechadosFiltro()));
			setListaRetiradas(service.consultarRetiradas(getFiltroRelatorio()));

			if (!getListaRolesFechadosDia().isEmpty() || !getListaPlanosVendidosDia().isEmpty()) {
				setTotal(service.calcularTotais(getListaPlanosVendidosDia(), getListaRolesFechadosDia()));
				FiltroTO filtro = new FiltroTO();
				filtro.setDataInicio(new Date());
				filtro.setDataFim(new Date());
				setTotal(service.calcularTotaisCaixaRelatorio(getListaPlanosVendidosDia(), getListaRolesFechadosDia(), getTotal(), filtro));
			} else {
				setTotal(new TotalTO());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void consultaRelatorio() {
		try {
			setListaRolesFechadosFiltro(service.consultarRoleRelatorio(getFiltroRelatorio()));
			setListaPlanosVendidosFiltro(service.consultarPlanoRelatorio(getFiltroRelatorio()));
			setTotalRelatorio(service.calcularTotais(getListaPlanosVendidosFiltro(), getListaRolesFechadosFiltro()));
			setListaRetiradas(service.consultarRetiradas(getFiltroRelatorio()));
			
			if (!getListaRolesFechadosFiltro().isEmpty() || !getListaPlanosVendidosFiltro().isEmpty()) {
				setTotalRelatorio(service.calcularTotais(getListaPlanosVendidosFiltro(), getListaRolesFechadosFiltro()));
				setTotalRelatorio(service.calcularTotaisCaixaRelatorio(getListaPlanosVendidosFiltro(), getListaRolesFechadosFiltro(), getTotalRelatorio(), getFiltroRelatorio()));
			} else {
				setTotal(new TotalTO());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void editarPlano() {
		setPlano(getPlanoSelecionado());
		Util.redirecionar(Util.PAGINA_EDITAR_PLANO);
	}

	public void abrirPaginaCaixa() {
		try {
			if (service.abrirPaginaCaixa()) {
				setRetirada(new Retirada());
				Util.redirecionar(Util.PAGINA_FECHAR_CAIXA);
			} else {

				setCaixa(service.instanciarCaixa());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void abrirCaixa() {
		try {

			Retorno retorno = service.abrirCaixa(getCaixa());
			if (retorno.isSucesso()) {

				Util.mensagem(FacesMessage.SEVERITY_INFO, retorno.getMsg(), "");
				Util.redirecionar(Util.PAGINA_ROLE);
			} else {
				Util.mensagem(FacesMessage.SEVERITY_WARN, retorno.getMsg(), "");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void salvarRetirada() {
		try {

			Retorno retorno = service.salvarRetirada(getRetirada());
			if (retorno.isSucesso()) {
				setRetirada(new Retirada());
				Util.mensagem(FacesMessage.SEVERITY_INFO, retorno.getMsg(), "");
				Util.redirecionar(Util.PAGINA_ROLE);
			} else {
				Util.mensagem(FacesMessage.SEVERITY_WARN, retorno.getMsg(), "");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fecharCaixa() {
		try {

			consultaRelatorio();
			Retorno retorno = service.fecharCaixa(getTotal(), getListaRetiradas());
			if (retorno.isSucesso()) {

				Util.mensagem(FacesMessage.SEVERITY_INFO, retorno.getMsg(), "");
				Util.redirecionar(Util.PAGINA_ROLE);
			} else {
				Util.mensagem(FacesMessage.SEVERITY_WARN, retorno.getMsg(), "");

			}

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
		consultarRoleFechado(null);
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
			atividade.setDescricao("Fly 20m R$120");
			atividade.setSigla("F2");
			atividade.setValor(120f);
			atividades.add(atividade);

			atividade = new AtividadeTO();
			atividade.setIdAtividade(8l);
			atividade.setDescricao("Fly 30m R$150");
			atividade.setSigla("F3");
			atividade.setValor(150f);
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

	public List<Role> getListaRolesFechadosDia() {
		consultarRoleFechado(new Date());
		return listaRolesFechadosDia;
	}

	public void setListaRolesFechadosDia(List<Role> listaRolesFechadosDia) {
		this.listaRolesFechadosDia = listaRolesFechadosDia;
	}

	public List<Plano> getListaPlanosVendidosDia() {
		consultarPlanosVedidos(new Date());
		return listaPlanosVendidosDia;
	}

	public void setListaPlanosVendidosDia(List<Plano> listaPlanosVendidosDia) {
		this.listaPlanosVendidosDia = listaPlanosVendidosDia;
	}

	public List<String> getFormas() {
		if (formas == null) {
			formas = new ArrayList<>();
			formas.add(Util.Dinheiro);
			formas.add(Util.Credito);
			formas.add(Util.Debito);
		}
		return formas;
	}

	public void setFormas(List<String> formas) {
		this.formas = formas;
	}

	public TotalTO getTotal() {
		// setTotal(service.calcularTotais(getListaPlanosVendidosDia(),
		// getListaRolesFechadosDia()));
		return total;
	}

	public void setTotal(TotalTO total) {
		this.total = total;
	}

	public Usuario getRiderSelecionado() {
		return riderSelecionado;
	}

	public void setRiderSelecionado(Usuario riderSelecionado) {
		this.riderSelecionado = riderSelecionado;
	}

	public TotalTO getTotalFiltro() {
		if (totalFiltro == null) {
			setTotalFiltro(service.calcularTotais(getListaPlanosVendidos(), getListaRolesFechados()));
		}
		return totalFiltro;
	}

	public void setTotalFiltro(TotalTO totalFiltro) {
		this.totalFiltro = totalFiltro;
	}

	public List<Plano> getListaPlanosVendidos() {
		consultarPlanosVedidos(null);
		return listaPlanosVendidos;
	}

	public void setListaPlanosVendidos(List<Plano> listaPlanosVendidos) {
		this.listaPlanosVendidos = listaPlanosVendidos;
	}

	public List<Role> getListaRolesFechadosFiltro() {
		return listaRolesFechadosFiltro;
	}

	public void setListaRolesFechadosFiltro(List<Role> listaRolesFechadosFiltro) {
		this.listaRolesFechadosFiltro = listaRolesFechadosFiltro;
	}

	public FiltroTO getFiltroRelatorio() {
		return filtroRelatorio;
	}

	public void setFiltroRelatorio(FiltroTO filtroRelatorio) {
		this.filtroRelatorio = filtroRelatorio;
	}

	public List<Plano> getListaPlanosVendidosFiltro() {
		return listaPlanosVendidosFiltro;
	}

	public void setListaPlanosVendidosFiltro(List<Plano> listaPlanosVendidosFiltro) {
		this.listaPlanosVendidosFiltro = listaPlanosVendidosFiltro;
	}

	public TotalTO getTotalRelatorio() {
		return totalRelatorio;
	}

	public void setTotalRelatorio(TotalTO totalRelatorio) {
		this.totalRelatorio = totalRelatorio;
	}

	public Plano getPlanoSelecionado() {
		return planoSelecionado;
	}

	public void setPlanoSelecionado(Plano planoSelecionado) {
		this.planoSelecionado = planoSelecionado;
	}

	public List<Usuario> getListaRiders() {
		try {
			listaRiders = service.findAllUsuario();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaRiders;
	}

	public void setListaRiders(List<Usuario> listaRiders) {
		this.listaRiders = listaRiders;
	}

	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public Retirada getRetirada() {
		return retirada;
	}

	public void setRetirada(Retirada retirada) {
		this.retirada = retirada;
	}

	public List<Retirada> getListaRetiradas() {
		return listaRetiradas;
	}

	public void setListaRetiradas(List<Retirada> listaRetiradas) {
		this.listaRetiradas = listaRetiradas;
	}

}