package com.dgc.dao.interfaces.impl;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dgc.dao.interfaces.DaoModelInterface;
import com.dgc.dao.interfaces.RoleDAOInterface;
import com.dgc.entidade.Role;
import com.dgc.util.FiltroTO;

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
			this.cal.set(this.cal.get(Calendar.YEAR), this.cal.get(Calendar.MONTH), this.cal.get(Calendar.DATE), 00, 00,
					01);

			final Date dtFim = this.cal.getTime();
			criteria.add(Restrictions.ge("dataInicio", dtFim));
		}

		criteria.addOrder(Order.asc("dataInicio"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Role> consultarRoleRelatorio(FiltroTO filtroTO) throws Exception {
		Criteria criteria = null;
		criteria = createCriteria(criteria);
		Calendar inicio = Calendar.getInstance();
		Calendar fim = Calendar.getInstance();

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		filtroTO.setDataInicio((Date) formatter.parse(filtroTO.getDataInicioString()));
		filtroTO.setDataFim((Date) formatter.parse(filtroTO.getDataFimString()));

		inicio.setTime(filtroTO.getDataInicio());
		inicio.set(inicio.get(Calendar.YEAR), inicio.get(Calendar.MONTH), inicio.get(Calendar.DATE), 00, 00, 01);

		fim.setTime(filtroTO.getDataFim());
		fim.set(fim.get(Calendar.YEAR), fim.get(Calendar.MONTH), fim.get(Calendar.DATE), 23, 59, 01);

		criteria.add(Restrictions.ge("dataFim", inicio.getTime()));
		criteria.add(Restrictions.le("dataFim", fim.getTime()));
		criteria.addOrder(Order.asc("dataEntrada"));
		return criteria.list();
	}

	public Integer countAllHoras() throws Exception {
		Criteria criteria = null;
		criteria = createCriteria(criteria);

		criteria.add(Restrictions.isNotNull("dataFim"));
		criteria.setProjection(Projections.rowCount());
		Long result = (Long) criteria.uniqueResult();
		return result.intValue();
	}

	public Integer countHorasOntem() throws Exception {
		Criteria criteria = null;
		criteria = createCriteria(criteria);
		criteria.add(Restrictions.isNotNull("dataFim"));
		Calendar data = Calendar.getInstance();
		data.add(Calendar.DAY_OF_MONTH, -1);
		data.set(Calendar.HOUR, 1);
		criteria.add(Restrictions.ge("dataFim", data.getTime()));

		Calendar fim = Calendar.getInstance();
		fim.set(Calendar.HOUR, 1);
		criteria.add(Restrictions.le("dataFim", fim.getTime()));

		criteria.setProjection(Projections.rowCount());
		Long result = (Long) criteria.uniqueResult();
		return result.intValue();
	}

}