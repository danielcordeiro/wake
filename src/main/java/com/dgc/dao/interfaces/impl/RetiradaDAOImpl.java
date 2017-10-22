package com.dgc.dao.interfaces.impl;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dgc.dao.interfaces.DaoModelInterface;
import com.dgc.dao.interfaces.RetiradaDAOInterface;
import com.dgc.entidade.Retirada;
import com.dgc.util.FiltroTO;

@Transactional
@Repository("RetiradaDAO")
public class RetiradaDAOImpl extends DaoModelInterface<Retirada> implements RetiradaDAOInterface, Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public List<Retirada> consultarRetiradas(FiltroTO filtroTO) throws Exception {
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

		criteria.add(Restrictions.ge("data", inicio.getTime()));
		criteria.add(Restrictions.le("data", fim.getTime()));
		criteria.addOrder(Order.asc("data"));
		return criteria.list();
	}

}