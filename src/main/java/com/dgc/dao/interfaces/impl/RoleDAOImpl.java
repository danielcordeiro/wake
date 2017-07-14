package com.dgc.dao.interfaces.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
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

	@SuppressWarnings("unchecked")
	public List<Role> consultarRoleDoDia(Date time) throws Exception {
		Criteria criteria = null;
		criteria = createCriteria(criteria);
		criteria.add(Restrictions.ge("dataEntrada", time));
		criteria.addOrder(Order.asc("dataEntrada"));
		return criteria.list();
	}

	private Calendar cal = Calendar.getInstance();

	@SuppressWarnings("unchecked")
	public List<Role> consultarFechado(Date data) throws Exception {
		Criteria criteria = null;
		criteria = createCriteria(criteria);

		if (data != null) {
			this.cal.setTime(data);
			this.cal.set(this.cal.get(Calendar.YEAR), this.cal.get(Calendar.MONTH), this.cal.get(Calendar.DATE), 00, 00, 01);

			final Date dtFim = this.cal.getTime();
			criteria.add(Restrictions.ge("dataInicio", dtFim));
		}

		criteria.addOrder(Order.asc("dataInicio"));
		return criteria.list();
	}

}