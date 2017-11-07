package com.dgc.service;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dgc.dao.interfaces.CaixaDAOInterface;
import com.dgc.dao.interfaces.PlanoDAOInterface;
import com.dgc.dao.interfaces.RetiradaDAOInterface;
import com.dgc.dao.interfaces.RoleDAOInterface;
import com.dgc.dao.interfaces.UsuarioDAOInterface;
import com.dgc.entidade.Caixa;
import com.dgc.entidade.Plano;
import com.dgc.entidade.Retirada;
import com.dgc.entidade.Role;
import com.dgc.entidade.Usuario;
import com.dgc.util.AtividadeTO;
import com.dgc.util.FiltroTO;
import com.dgc.util.Retorno;
import com.dgc.util.TotalTO;
import com.dgc.util.Util;

@Service
public class UsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UsuarioDAOInterface usuarioDAO;

	@Autowired
	private RoleDAOInterface roleDAO;

	@Autowired
	private PlanoDAOInterface planoDAO;

	@Autowired
	private CaixaDAOInterface caixaDAO;

	@Autowired
	private RetiradaDAOInterface retiradaDAO;

	public List<Role> consultarRoleAberto() throws Exception {
		return roleDAO.consultarAberto();
	}

	public Retorno adicionarRole(Role roleNovo, List<AtividadeTO> atividades, List<Role> roles) throws Exception {
		Retorno retorno = new Retorno();
		if (Util.isZero(roleNovo.getIdRider())) {
			retorno.setSucesso(false);
			retorno.setMsg("Informe o Rider");
			return retorno;
		}

		roleNovo.setRider(usuarioDAO.obter(roleNovo.getIdRider()));
		if (!Util.isZero(roleNovo.getValorDiferente())) {
			roleNovo.setValor(roleNovo.getValorDiferente());
		}

		if (roleNovo.getIdAtividade().equals(1l)) {
			List<Plano> plano = planoDAO.consultarAbertoPorRider(roleNovo.getIdRider());
			if (plano.isEmpty()) {
				retorno.setSucesso(false);
				retorno.setMsg("Rider não possui plano disponível");
				return retorno;
			}

			Plano plan = plano.iterator().next();
			if (!verificarSeEstaAndando(plan, roles)) {
				retorno.setSucesso(false);
				retorno.setMsg("Rider não possui mais horas abertas");
				return retorno;
			}

			if (!validarPlanoFds(plano, plan)) {
				retorno.setSucesso(false);
				retorno.setMsg("Rider não possui plano para FIM DE SEMANA");
				return retorno;
			}

			roleNovo.setIdPlano(plan.getId());
			roleNovo.setValor(0f);
		}

		roleNovo.setDataEntrada(new Date());

		for (AtividadeTO atividadeTO : atividades) {
			if (roleNovo.getIdAtividade().equals(atividadeTO.getIdAtividade())) {
				roleNovo.setValor(atividadeTO.getValor());
				roleNovo.setSigla(atividadeTO.getSigla());
				break;
			}

		}
		roleNovo.setHora(1);
		roleDAO.salvar(roleNovo);

		retorno.setMsg("Role adicionado com sucesso!");
		retorno.setSucesso(true);
		return retorno;
	};

	private boolean verificarSeEstaAndando(Plano plan, List<Role> roles) {
		int horas = 0;
		for (Role role : roles) {
			if (!Util.isZero(role.getIdPlano()) && role.getIdPlano().equals(plan.getId())) {
				horas++;
			}
		}

		int result = plan.getHorasRestantes() - horas;
		if (result >= 1) {
			return true;
		}
		return false;
	}

	private boolean validarPlanoFds(List<Plano> plano, Plano plan) {
		if (!plano.isEmpty()) {

			// for (Plano plan : plano) {
			if (Plano.isWeekendPlan(plan.getTipo())) {
				return true;
			}
			// }

			Calendar day = Calendar.getInstance();
			if (day.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				return false;
			}
			return true;
		}
		return false;
	}

	public Retorno salvarRider(Usuario usuarioNovo) throws Exception {
		Retorno retorno = new Retorno();
		List<Usuario> usuarios = usuarioDAO.consultarPorTelefone(usuarioNovo);
		if (!usuarios.isEmpty()) {
			retorno.setMsg("Já existe um usuário cadastrado com esse telefone.");
			retorno.setSucesso(false);
			return retorno;
		}

		usuarios = usuarioDAO.consultarPorEmail(usuarioNovo);
		if (!usuarios.isEmpty()) {
			retorno.setMsg("Já existe um usuário cadastrado com esse e-mail.");
			retorno.setSucesso(false);
			return retorno;
		}

		if (!usuarioNovo.isTermo() || !usuarioNovo.isTermo2() || !usuarioNovo.isTermo3() || !usuarioNovo.isTermo4() || !usuarioNovo.isTermo5() || !usuarioNovo.isTermo6() || !usuarioNovo.isTermo7() || !usuarioNovo.isTermo8() || !usuarioNovo.isTermo9() || !usuarioNovo.isTermo10()
				|| !usuarioNovo.isTermo11()) {

			retorno.setMsg("Você deve concordar com todos os termos.");
			retorno.setSucesso(false);
			return retorno;
		}

		if (!Util.isNullVazio(usuarioNovo.getMail())) {
			usuarioNovo.setMail(usuarioNovo.getMail().toLowerCase());
		}
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		usuarioNovo.setDataNascimento((Date) formatter.parse(usuarioNovo.getDataNascimentoString()));

		usuarioNovo.setNome(usuarioNovo.getNome().toUpperCase());
		usuarioNovo.setApelido(usuarioNovo.getApelido().toUpperCase());

		retorno.setMsg("Rider salvo com sucesso");
		retorno.setSucesso(true);
		if (Util.isZero(usuarioNovo.getId())) {
			usuarioNovo.setDataCadastro(new Date());
		}
		usuarioDAO.salvar(usuarioNovo);
		return retorno;
	}

	public List<Usuario> autoCompleteUsuario(String query) throws Exception {
		return usuarioDAO.consultarApelidoNomeTelefone(query);
	}

	public void finalizarRole(Role roleSelecionado) throws Exception {
		roleSelecionado.setDataFim(new Date());

		if (!Util.isZero(roleSelecionado.getIdPlano())) {
			Plano plano = planoDAO.obter(roleSelecionado.getIdPlano());
			plano.setHorasRestantes(plano.getHorasRestantes() - roleSelecionado.getHora());
			planoDAO.salvar(plano);
		}
		if (!isCaixaAberto()) {
			Caixa caixa = instanciarCaixa();
			abrirCaixa(caixa);
		} else {
			List<Caixa> caixas = caixaDAO.consultarCaixaAberto();
			Caixa caixa = caixas.iterator().next();
			int dia = caixa.getData().getDay();
			Date dtHoje = new Date();
			int hoje = dtHoje.getDay();
			if (dia != hoje) {
				TotalTO total = consultaRelatorio(caixa.getData());
				FiltroTO filtro = new FiltroTO();
				filtro.setDataInicio(caixa.getData());
				filtro.setDataFim(caixa.getData());
				List<Retirada> retiradas = retiradaDAO.consultarRetiradas(filtro);
				fecharCaixa(total, retiradas);

				Caixa caixaNovo = instanciarCaixa();
				abrirCaixa(caixaNovo);
			}
		}

		roleDAO.salvar(roleSelecionado);

	}

	public TotalTO consultaRelatorio(Date date) {

		try {

			List<Plano> planosVendidosDia = consultarPlanosVendidos(date);
			List<Role> listaRolesFechadosDia = consultarRoleFechado(date);
			if (!listaRolesFechadosDia.isEmpty() || !planosVendidosDia.isEmpty()) {
				TotalTO total = calcularTotais(planosVendidosDia, listaRolesFechadosDia);
				FiltroTO filtro = new FiltroTO();
				filtro.setDataInicio(date);
				filtro.setDataFim(date);
				total = calcularTotaisCaixaRelatorio(planosVendidosDia, listaRolesFechadosDia, total, filtro);
				return total;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new TotalTO();
	}

	public void iniciarRole(Role roleSelecionado) throws Exception {
		roleSelecionado.setDataInicio(new Date());

		roleDAO.salvar(roleSelecionado);

	}

	public void adicionarHora(Role roleSelecionado) throws Exception {
		roleSelecionado.setHora(roleSelecionado.getHora() + 1);
		roleDAO.salvar(roleSelecionado);

	}

	public void removerRole(Role roleSelecionado) throws Exception {
		roleDAO.delete(roleSelecionado);

	}

	public boolean logar(Usuario usuarioLogin) {
		try {
			List<Usuario> usuario;
			usuario = usuarioDAO.consultarLogin(usuarioLogin.getMail(), usuarioLogin.getSenha());
			if (!usuario.isEmpty()) {
				Util.guardarUsuarioSessao(usuario.iterator().next());
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Role> consultarRoleFechado(Date data) throws Exception {
		return roleDAO.consultarFechado(data);
	}

	public List<Role> consultarRoleDoDia() throws Exception {
		Calendar hoje = Calendar.getInstance();
		hoje.set(hoje.get(Calendar.YEAR), hoje.get(Calendar.MONTH), hoje.get(Calendar.DATE), 01, 00, 00);
		return roleDAO.consultarRoleDoDia(hoje.getTime());
	}

	public Retorno adicionarPlano(Plano plano) throws Exception {
		Retorno retorno = new Retorno();
		retorno.setSucesso(true);

		if (plano.getValor() == null) {
			plano.setValor(0f);
		}

		plano.setRider(usuarioDAO.obter(plano.getIdRider()));
		if (Util.isZero(plano.getId())) {
			plano.setDataCompra(new Date());
		}
		planoDAO.salvar(plano);
		retorno.setMsg("Plano adicionado com sucesso!");
		return retorno;
	}

	public List<Plano> consultarPlanosAbertos() throws Exception {
		return planoDAO.consultarPlanosAbertos();
	}

	public List<Plano> consultarPlanosVendidos(Date date) throws Exception {
		return planoDAO.consultarPlanosVendidos(date);
	}

	public TotalTO calcularTotais(List<Plano> listaPlanosVendidosDia, List<Role> listaRolesFechadosDia) {
		TotalTO totalTO = new TotalTO();
		List<Role> temp = listaRolesFechadosDia;
		Float valor = 0f;
		Float valorDinheiro = 0f;
		Float valorCredito = 0f;
		Float valorDebito = 0f;
		Integer qtdPagas = 0;
		for (Role role : temp) {
			valor = valor + role.getValor();
			if (!Util.isNullVazio(role.getForma())) {
				if (role.getForma().equals(Util.Dinheiro)) {
					valorDinheiro = valorDinheiro + role.getValor();
				}
				if (role.getForma().equals(Util.Credito)) {
					valorCredito = valorCredito + role.getValor();
				}
				if (role.getForma().equals(Util.Debito)) {
					valorDebito = valorDebito + role.getValor();
				}
			}
			if (!Util.isZero(role.getValor())) {
				qtdPagas++;
			}
		}
		totalTO.setTotalHora(valor);
		totalTO.setQtdHorasPagas(qtdPagas);
		totalTO.setQtdHoras(listaRolesFechadosDia.size());
		List<Plano> tempP = listaPlanosVendidosDia;
		valor = 0f;
		for (Plano plano : tempP) {
			valor = valor + plano.getValor();
			if (!Util.isNullVazio(plano.getForma())) {
				if (plano.getForma().equals(Util.Dinheiro)) {
					valorDinheiro = valorDinheiro + plano.getValor();
				}
				if (plano.getForma().equals(Util.Credito)) {
					valorCredito = valorCredito + plano.getValor();
				}
				if (plano.getForma().equals(Util.Debito)) {
					valorDebito = valorDebito + plano.getValor();
				}
			}
		}
		totalTO.setTotalPlano(valor);

		totalTO.setTotalDinheiro(valorDinheiro);
		totalTO.setTotalDebito(valorDebito);
		totalTO.setTotalCredito(valorCredito);

		return totalTO;
	}

	public List<Role> consultarRoleRelatorio(FiltroTO filtroTO) throws Exception {
		return roleDAO.consultarRoleRelatorio(filtroTO);
	}

	public List<Plano> consultarPlanoRelatorio(FiltroTO filtroRelatorio) throws Exception {
		return planoDAO.consultarPlanoRelatorio(filtroRelatorio);
	}

	public Integer countAll() throws Exception {
		return usuarioDAO.countAll();
	}

	public Integer count24h() throws Exception {
		return usuarioDAO.count24h();
	}

	public Integer countAllHoras() throws Exception {
		return roleDAO.countAllHoras();
	}

	public Integer countHorasOntem() throws Exception {
		return roleDAO.countHorasOntem();
	}

	public List<Usuario> findAllUsuario() throws Exception {
		return usuarioDAO.consultarTodos();
	}

	public Retorno abrirCaixa(Caixa caixa) throws Exception {
		Retorno retorno = new Retorno();

		List<Caixa> caixas = caixaDAO.consultarCaixaAberto();
		if (!caixas.isEmpty()) {
			retorno.setSucesso(false);
			retorno.setMsg("Já possui um caixa aberto!");
			return retorno;
		}

		caixa.setData(new Date());
		caixa.setIndAberto(true);
		caixaDAO.salvar(caixa);
		retorno.setSucesso(true);
		retorno.setMsg("Caixa aberto com sucesso!");
		return retorno;
	}

	public boolean isCaixaAberto() throws Exception {
		List<Caixa> caixas = caixaDAO.consultarCaixaAberto();
		if (!caixas.isEmpty()) {
			return true;
		}
		return false;
	}

	public Retorno fecharCaixa(TotalTO totalTO, List<Retirada> retiradas) throws Exception {
		Retorno retorno = new Retorno();
		retorno.setSucesso(false);
		retorno.setMsg("Caixa não encontrado");

		List<Caixa> caixas = caixaDAO.consultarCaixaAberto();
		if (!caixas.isEmpty()) {
			Caixa caixa = caixas.iterator().next();
			caixa.setValorFechamento(totalTO.getTotalDinheiroSaldo());
			caixa.setValorCredito(totalTO.getTotalCredito());
			caixa.setValorDebito(totalTO.getTotalDebito());
			Float valorRetirada = 0f;
			for (Retirada retirada : retiradas) {
				valorRetirada = valorRetirada + retirada.getValor();
			}
			caixa.setValorSaida(valorRetirada);
			caixa.setIndAberto(false);
			caixaDAO.salvar(caixa);

			retorno.setSucesso(true);
			retorno.setMsg("Caixa fechado com sucesso!");
		}
		return retorno;
	}

	public Retorno salvarRetirada(Retirada retirada) throws Exception {
		Retorno retorno = new Retorno();
		retorno.setMsg("Caixa não encontrado!");
		retorno.setSucesso(false);

		List<Caixa> caixas = caixaDAO.consultarCaixaAberto();
		if (!caixas.isEmpty()) {
			Caixa caixa = caixas.iterator().next();
			retirada.setIdCaixa(caixa.getId());
			retirada.setData(new Date());
			retiradaDAO.salvar(retirada);
			retorno.setMsg("Retirada salva com sucesso!");
			retorno.setSucesso(true);

			return retorno;
		}
		return retorno;
	}

	public List<Retirada> consultarRetiradas(FiltroTO filtroRelatorio) throws Exception {
		return retiradaDAO.consultarRetiradas(filtroRelatorio);
	}

	public TotalTO calcularTotaisCaixaRelatorio(List<Plano> listaPlanosVendidosDia, List<Role> listaRolesFechadosDia, TotalTO total, FiltroTO filtroTO) throws Exception {
		List<Caixa> caixas = caixaDAO.consultarCaixaAberto();
		if (!caixas.isEmpty()) {
			float valor = 0f;
			for (Caixa caixa : caixas) {
				valor = valor + caixa.getValorAbertura();
			}

			total.setAberturaCaixa(valor);
		}

		FiltroTO filtro = new FiltroTO();
		filtro.setDataInicio(filtroTO.getDataInicio());
		filtro.setDataFim(filtroTO.getDataFim());
		List<Retirada> retiradas = retiradaDAO.consultarRetiradas(filtro);
		Float valorRetirada = 0f;
		for (Retirada retirada : retiradas) {
			valorRetirada = valorRetirada + (retirada.getValor() * -1);
		}

		total.setTotalRetirada(valorRetirada);

		total.setTotalDinheiroSaldo(total.getAberturaCaixa() + total.getTotalDinheiro() + total.getTotalRetirada());
		return total;
	}

	public Caixa instanciarCaixa() throws Exception {
		Caixa caixa = new Caixa();
		Caixa ultimoCaixa = caixaDAO.consultarUltimoCaixa();
		if (ultimoCaixa != null) {
			caixa.setValorAbertura(ultimoCaixa.getValorFechamento());
		}
		return caixa;
	}
}
