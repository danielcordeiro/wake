package com.dgc.dao.interfaces.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dgc.dao.interfaces.CaixaDAOInterface;
import com.dgc.dao.interfaces.DaoModelInterface;
import com.dgc.entidade.Caixa;

@Transactional
@Repository("CaixaDAO")
public class CaixaDAOImpl extends DaoModelInterface<Caixa> implements CaixaDAOInterface, Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public List<Caixa> consultarCaixaAberto() throws Exception {
		Criteria criteria = null;
		criteria = createCriteria(criteria);
		criteria.add(Restrictions.eq("indAberto", true));
		return criteria.list();
	}
}