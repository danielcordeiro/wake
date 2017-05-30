package com.dgc.dao.interfaces;

import java.util.Date;
import java.util.List;

import com.dgc.entidade.Role;

public interface RoleDAOInterface extends DaoInterface<Role> {

	List<Role> consultarAberto() throws Exception;

	List<Role> consultarFechado() throws Exception;

	List<Role> consultarRoleDoDia(Date time)throws Exception;
}
