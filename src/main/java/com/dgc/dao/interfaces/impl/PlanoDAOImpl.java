package com.dgc.dao.interfaces.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dgc.dao.interfaces.DaoModelInterface;
import com.dgc.dao.interfaces.PlanoDAOInterface;
import com.dgc.entidade.Plano;

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

}