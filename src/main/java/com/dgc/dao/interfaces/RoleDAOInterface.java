package com.dgc.dao.interfaces;

import java.util.Date;
import java.util.List;

import com.dgc.entidade.Role;
import com.dgc.util.FiltroTO;

public interface RoleDAOInterface extends DaoInterface<Role> {

	List<Role> consultarAberto() throws Exception;

	List<Role> consultarFechado(Date data) throws Exception;

	List<Role> consultarRoleDoDia(Date time) throws Exception;

	List<Role> consultarRoleRelatorio(FiltroTO filtroTO) throws Exception;

	Integer countAllHoras() throws Exception;

	Integer countHorasOntem() throws Exception;
}
