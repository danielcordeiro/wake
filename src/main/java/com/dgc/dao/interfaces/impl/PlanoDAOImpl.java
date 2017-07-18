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
import com.dgc.dao.interfaces.PlanoDAOInterface;
import com.dgc.entidade.Plano;
import com.dgc.util.FiltroTO;

@Transactional
@Repository("PlanoDAO")
public class PlanoDAOImpl extends DaoModelInterface<Plano> implements PlanoDAOInterface, Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public List<Plano> consultarAbertoPorRider(Long idRider) throws Exception {
		Criteria criteria = null;
		criteria = createCriteria(criteria);
		criteria.add(Restrictions.eq("idRider", idRider));
		criteria.add(Restrictions.ne("horasRestantes", 0));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Plano> consultarPlanosAbertos() throws Exception {
		Criteria criteria = null;
		criteria = createCriteria(criteria);
		criteria.add(Restrictions.ne("horasRestantes", 0));
		return criteria.list();
	}

	private Calendar cal = Calendar.getInstance();

	@SuppressWarnings("unchecked")
	public List<Plano> consultarPlanosVendidos(Date data) throws Exception {
		Criteria criteria = null;
		criteria = createCriteria(criteria);
		if (data != null) {
			this.cal.setTime(data);
			this.cal.set(this.cal.get(Calendar.YEAR), this.cal.get(Calendar.MONTH), this.cal.get(Calendar.DATE), 00, 00, 01);

			final Date dtFim = this.cal.getTime();
			criteria.add(Restrictions.ge("dataCompra", dtFim));
		}

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Plano> consultarPlanoRelatorio(FiltroTO filtroTO) throws Exception {
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
		fim.set(fim.get(Calendar.YEAR), fim.get(Calendar.MONTH), fim.get(Calendar.DATE), 00, 00, 01);

		criteria.add(Restrictions.ge("dataCompra", inicio.getTime()));
		criteria.add(Restrictions.le("dataCompra", fim.getTime()));
		criteria.addOrder(Order.asc("dataCompra"));
		return criteria.list();
	}

}