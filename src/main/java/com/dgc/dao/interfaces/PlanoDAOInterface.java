package com.dgc.dao.interfaces;

import java.util.List;

import com.dgc.entidade.Plano;

public interface PlanoDAOInterface extends DaoInterface<Plano> {

	List<Plano> consultarAbertoPorRider(Long idRider) throws Exception;

	List<Plano> consultarPlanosAbertos() throws Exception;;

}
