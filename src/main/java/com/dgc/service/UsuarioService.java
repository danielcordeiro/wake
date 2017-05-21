package com.dgc.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dgc.dao.interfaces.RoleDAOInterface;
import com.dgc.dao.interfaces.UsuarioDAOInterface;
import com.dgc.entidade.Role;
import com.dgc.entidade.Usuario;
import com.dgc.util.Retorno;
import com.dgc.util.Util;

@Service
public class UsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UsuarioDAOInterface usuarioDAO;

	@Autowired
	private RoleDAOInterface roleDAO;

	public List<Role> consultarRoleAberto() throws Exception {
		return roleDAO.consultarAberto();
	}

	public Role adicionarRole(Role roleNovo) throws Exception {
		roleNovo.setRider(usuarioDAO.obter(roleNovo.getIdRider()));

		roleDAO.salvar(roleNovo);
		return roleNovo;
	};

	public Retorno salvarRider(Usuario usuarioNovo) throws Exception {
		Retorno retorno = new Retorno();
		// TODO validar usuário já cadastrado
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
		if (!roleSelecionado.getPlano()) {
			roleSelecionado.setValor(Util.valor * roleSelecionado.getHora());
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

	public List<Role> consultarRoleFechado() throws Exception {
		return roleDAO.consultarFechado();
	}

}
