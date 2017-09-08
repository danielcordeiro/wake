package com.dgc.dao.interfaces;

import java.util.List;

import com.dgc.entidade.Usuario;

public interface UsuarioDAOInterface extends DaoInterface<Usuario> {

	List<Usuario> consultarApelidoNomeTelefone(String query) throws Exception;

	List<Usuario> consultarLogin(String mail, String senha) throws Exception;

	List<Usuario> consultarPorTelefone(Usuario usuarioNovo) throws Exception;

	List<Usuario> consultarPorEmail(Usuario usuarioNovo) throws Exception;

	Integer countAll() throws Exception;

	Integer count24h() throws Exception;
}
