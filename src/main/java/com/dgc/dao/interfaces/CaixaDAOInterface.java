package com.dgc.dao.interfaces;

import java.util.List;

import com.dgc.entidade.Caixa;

public interface CaixaDAOInterface extends DaoInterface<Caixa> {

	List<Caixa> consultarCaixaAberto() throws Exception;

	List<Caixa> consultarCaixaDia() throws Exception;

	Caixa consultarUltimoCaixa() throws Exception;

}
