package com.dgc.dao.interfaces;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dgc.entidade.Entidade;

@Repository
@Transactional
public abstract class DaoModelInterface<T extends Entidade> {

	private Class<T> clazz;

	public Criteria criteria;

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public EntityManager getEntityManager() {
		this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		// if (clazz.getPackage().getName().equals("com.lad.entidade")) {
		return entityManager;
		// }
	}

	public void setClazz(Class<T> clazzToSet) {
		this.clazz = clazzToSet;
	}

	public T obter(Long id) throws Exception {
		return getEntityManager().find(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() throws Exception {

		Criteria criteria = null;
		criteria = createCriteria(criteria);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllEnd() throws Exception {
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteriaEnd = session.createCriteria(clazz);
		return criteriaEnd.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllEndData(Date data) throws Exception {
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteriaEnd = session.createCriteria(clazz);
		criteriaEnd.add(Restrictions.le("dataAlteracao", data));
		return criteriaEnd.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllParamLong(String parm, Long id) throws Exception {
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteriaEnd = session.createCriteria(clazz);
		criteriaEnd.add(Restrictions.eq(parm, id));
		return criteriaEnd.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllParamListaLong(String parm, String alias, Long id) throws Exception {
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteriaEnd = session.createCriteria(clazz);
		criteriaEnd.createAlias(alias, alias);
		criteriaEnd.add(Restrictions.eq(parm, id));
		return criteriaEnd.list();
	}

	public void salvar(T entity) throws Exception {
		// Usuario usu = SessionUtil.obterUsuarioSessao();
		// if (usu != null) {
		// entity.setIdUsuario(usu.getId());
		// entity.setDataAlteracao(UtilLad.dateByTimezone());
		// }
		entity.setIndDeletado(false);
		Session session = (Session) getEntityManager().getDelegate();
		// if (entity.getDataAlteracao() == null) {
		session.saveOrUpdate(entity);
		// } else {
		// entity.setDataAlteracao(Util.dateByTimezone());
		// session.update(entity);
		// }
	}

	public void popular(T entity) throws Exception {
		Session session = (Session) getEntityManager().getDelegate();
		session.save(entity);
	}

	public void popularIfNaoExiste(T entity, Long id) throws Exception {
		T retorno = obter(id);
		Session session = (Session) getEntityManager().getDelegate();
		if (retorno == null) {
			session.save(entity);
		} else {
			session.merge(entity);
		}
	}

	public void delete(T entity) throws Exception {
		entity.setIndDeletado(true);
		Session session = (Session) getEntityManager().getDelegate();
		session.saveOrUpdate(entity);

	}

	public void deleteEnd(T entity) throws Exception {
		try {
			getEntityManager().remove(getEntityManager().merge(entity));
		} catch (javax.persistence.EntityNotFoundException e) {
		} catch (javax.persistence.RollbackException e) {
		} catch (org.springframework.transaction.TransactionSystemException e) {
		}
	}

	public void deleteById(Long entityId) throws Exception {
		T entity = obter(entityId);
		delete(entity);

	}

	public void deleteAll(String tabela) throws Exception {
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createQuery("delete " + tabela);
		query.executeUpdate();
	}

	public void deleteLogicoAll() throws Exception {
		List<T> registros = findAll();
		for (T obj : registros) {
			delete(obj);
		}
	}

	public boolean possuiCadastro(Long idEmpresa) throws Exception {
		Criteria criteria = null;
		criteria = createCriteria(criteria);
		criteria.add(Restrictions.eq("idEmpresa", idEmpresa));
		criteria.setMaxResults(1);
		return !criteria.list().isEmpty();
	}

	// public void createCriteria() throws Exception {
	// Session session = (Session) getEntityManager().getDelegate();
	// criteria = session.createCriteria(clazz);
	// criteria.add(Restrictions.eq("indDeletado", false));
	//
	// }
	public Criteria createCriteria(Criteria crit) throws Exception {
		Session session = (Session) getEntityManager().getDelegate();
		crit = session.createCriteria(clazz);
		crit.add(Restrictions.eq("indDeletado", false));
		return crit;
	}

	public Criteria createCriteriaWithIndDeletado(Criteria crit) throws Exception {
		Session session = (Session) getEntityManager().getDelegate();
		crit = session.createCriteria(clazz);
		return crit;
	}

}
