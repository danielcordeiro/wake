package com.dgc.dao.interfaces;

import java.util.Date;
import java.util.List;

import com.dgc.entidade.Plano;
import com.dgc.util.FiltroTO;

public interface PlanoDAOInterface extends DaoInterface<Plano> {

	List<Plano> consultarAbertoPorRider(Long idRider) throws Exception;

	List<Plano> consultarPlanosAbertos() throws Exception;

	List<Plano> consultarPlanosVendidos(Date date) throws Exception;

	List<Plano> consultarPlanoRelatorio(FiltroTO filtroRelatorio) throws Exception;

}
