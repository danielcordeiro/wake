package com.dgc.service;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dgc.dao.interfaces.PlanoDAOInterface;
import com.dgc.dao.interfaces.RoleDAOInterface;
import com.dgc.dao.interfaces.UsuarioDAOInterface;
import com.dgc.entidade.Plano;
import com.dgc.entidade.Role;
import com.dgc.entidade.Usuario;
import com.dgc.util.AtividadeTO;
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

	public List<Role> consultarRoleAberto() throws Exception {
		return roleDAO.consultarAberto();
	}

	public Retorno adicionarRole(Role roleNovo, List<AtividadeTO> atividades) throws Exception {
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

			roleNovo.setIdPlano(plano.iterator().next().getId());
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

	public Retorno salvarRider(Usuario usuarioNovo) throws Exception {
		Retorno retorno = new Retorno();
		List<Usuario> usuarios = usuarioDAO.consultarPorTelefone(usuarioNovo);
		if (!usuarios.isEmpty()) {
			retorno.setMsg("Já existe um usuário cadastrado com esse telefone.");
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
		if (Util.isZero(roleSelecionado.getValor()) && !roleSelecionado.getIdAtividade().equals(1l)) {
			roleSelecionado.setValor(Util.valor * roleSelecionado.getHora());
		}

		if (!Util.isZero(roleSelecionado.getIdPlano())) {
			Plano plano = planoDAO.obter(roleSelecionado.getIdPlano());
			plano.setHorasRestantes(plano.getHorasRestantes() - roleSelecionado.getHora());
			planoDAO.salvar(plano);
		}
		roleDAO.salvar(roleSelecionado);

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

		plano.setRider(usuarioDAO.obter(plano.getIdRider()));
		plano.setDataCompra(new Date());
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
		}
		totalTO.setTotalHora(valor);

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

}
