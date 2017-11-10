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

import com.dgc.dao.interfaces.CaixaDAOInterface;
import com.dgc.dao.interfaces.DaoModelInterface;
import com.dgc.entidade.Caixa;
import com.dgc.util.FiltroTO;

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

	@SuppressWarnings("unchecked")
	public List<Caixa> consultarCaixaDia() throws Exception {
		Criteria criteria = null;
		criteria = createCriteria(criteria);

		Calendar inicio = Calendar.getInstance();
		Calendar fim = Calendar.getInstance();

		inicio.setTime(new Date());
		inicio.set(inicio.get(Calendar.YEAR), inicio.get(Calendar.MONTH), inicio.get(Calendar.DATE), 00, 00, 01);

		fim.setTime(new Date());
		fim.set(fim.get(Calendar.YEAR), fim.get(Calendar.MONTH), fim.get(Calendar.DATE), 23, 59, 01);

		criteria.add(Restrictions.ge("data", inicio.getTime()));
		criteria.add(Restrictions.le("data", fim.getTime()));

		return criteria.list();
	}

	public Caixa consultarUltimoCaixa() throws Exception {
		Criteria criteria = null;
		criteria = createCriteria(criteria);
		criteria.addOrder(Order.desc("data"));
		criteria.setMaxResults(1);
		return (Caixa) criteria.uniqueResult();
	}

	@Override
	public List<Caixa> consultarCaixa(FiltroTO filtroTO) throws Exception {
		Criteria criteria = null;
		criteria = createCriteria(criteria);

		Calendar inicio = Calendar.getInstance();
		Calendar fim = Calendar.getInstance();

		inicio.setTime(filtroTO.getDataInicio());
		inicio.set(inicio.get(Calendar.YEAR), inicio.get(Calendar.MONTH), inicio.get(Calendar.DATE), 00, 00, 01);

		fim.setTime(filtroTO.getDataFim());
		fim.set(fim.get(Calendar.YEAR), fim.get(Calendar.MONTH), fim.get(Calendar.DATE), 23, 59, 01);

		criteria.add(Restrictions.ge("data", inicio.getTime()));
		criteria.add(Restrictions.le("data", fim.getTime()));

		return criteria.list();
	}
}