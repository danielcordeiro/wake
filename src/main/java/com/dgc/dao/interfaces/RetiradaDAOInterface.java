package com.dgc.dao.interfaces;

import java.util.List;

import com.dgc.entidade.Retirada;
import com.dgc.util.FiltroTO;

public interface RetiradaDAOInterface extends DaoInterface<Retirada> {

	List<Retirada> consultarRetiradas(FiltroTO filtroRelatorio) throws Exception;

}
