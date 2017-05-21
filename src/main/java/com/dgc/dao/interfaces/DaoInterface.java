package com.dgc.dao.interfaces;

import java.util.Date;
import java.util.List;

public interface DaoInterface<T> {

	T obter(Long id) throws Exception;

	List<T> findAll() throws Exception;

	List<T> findAllEnd() throws Exception;

	List<T> findAllEndData(Date date) throws Exception;

	List<T> findAllParamLong(String parm, Long id) throws Exception;

	List<T> findAllParamListaLong(String parm, String alias, Long id) throws Exception;

	void salvar(T entity) throws Exception;

	void popular(T entity) throws Exception;

	void popularIfNaoExiste(T entity, Long id) throws Exception;

	void delete(T entity) throws Exception;

	void deleteEnd(T entity) throws Exception;

	void deleteById(Long entityId) throws Exception;

	void deleteAll(String tabela) throws Exception;

	void deleteLogicoAll() throws Exception;

	boolean possuiCadastro(Long idEmpresa) throws Exception;

}
