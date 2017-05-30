package com.dgc.dao.interfaces.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dgc.dao.interfaces.DaoModelInterface;
import com.dgc.dao.interfaces.UsuarioDAOInterface;
import com.dgc.entidade.Usuario;

@Transactional
@Repository("UsuarioDAO")
public class UsuarioDAOImpl extends DaoModelInterface<Usuario> implements UsuarioDAOInterface, Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public List<Usuario> consultarApelidoNomeTelefone(String query) throws Exception {
		Criteria criteria = null;
		criteria = createCriteria(criteria);

		criteria.add(Restrictions.or(Restrictions
				.sqlRestriction("Lower(TRANSLATE({alias}.apelido,'ÀÁáàÉÈéèÍíÓóÒòÚúÇç','AAaaEEeeIiOoOoUuCc')) LIKE  '"
						+ "%" + query.toLowerCase() + "%'"),

				Restrictions.or(Restrictions.sqlRestriction("Lower(TRANSLATE({alias}.telefone,'() -','')) LIKE  '%"
						+ query.toLowerCase().replace("(", "").replace(" ", "").replace("-", "").replace(")", "")
						+ "%'"),

						Restrictions.sqlRestriction(
								"Lower(TRANSLATE({alias}.nome,'ÀÁáàÉÈéèÍíÓóÒòÚúÇç','AAaaEEeeIiOoOoUuCc')) LIKE  '" + "%"
										+ query.toLowerCase() + "%'"))));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> consultarLogin(String mail, String senha) throws Exception {
		Criteria criteria = null;
		criteria = createCriteria(criteria);
		criteria.add(Restrictions.eq("mail", mail));
		criteria.add(Restrictions.eq("senha", senha));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> consultarPorTelefone(Usuario usuarioNovo) throws Exception {
		Criteria criteria = null;
		criteria = createCriteria(criteria);
		criteria.add(Restrictions.eq("telefone", usuarioNovo.getTelefone()));
		return criteria.list();
	}

}