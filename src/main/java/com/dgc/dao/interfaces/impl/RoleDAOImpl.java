package com.dgc.dao.interfaces.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dgc.dao.interfaces.DaoModelInterface;
import com.dgc.dao.interfaces.RoleDAOInterface;
import com.dgc.entidade.Role;

@Transactional
@Repository("RoleDAO")
public class RoleDAOImpl extends DaoModelInterface<Role> implements RoleDAOInterface, Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public List<Role> consultarAberto() throws Exception {
		Criteria criteria = null;
		criteria = createCriteria(criteria);
		criteria.add(Restrictions.isNull("dataFim"));
		criteria.addOrder(Order.asc("id"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Role> consultarFechado() throws Exception {
		Criteria criteria = null;
		criteria = createCriteria(criteria);
		criteria.add(Restrictions.isNotNull("dataFim"));
		criteria.addOrder(Order.asc("dataInicio"));
		return criteria.list();
	}

}