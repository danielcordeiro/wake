package com.dgc.util;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import com.dgc.entidade.Usuario;

public class AuthorizationListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	public void afterPhase(PhaseEvent event) {

		FacesContext facesContext = event.getFacesContext();
		String currentPage = facesContext.getViewRoot().getViewId();
		boolean isLoginPage = (currentPage.lastIndexOf(Util.PAGINA_LOGIN) > -1);

		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		Usuario user = (Usuario) session.getAttribute("usuario_session");

		if (!(isLoginPage) && user == null) {
			if (currentPage.lastIndexOf(Util.PAGINA_NOVO_RIDER) == -1) {
				Util.redirecionar(Util.PAGINA_LOGIN);
			}
		} else if (user != null) {
			if (isLoginPage) {
				Util.redirecionar(Util.PAGINA_ROLE);
			}
		}
	}

	public void beforePhase(PhaseEvent event) {
		// poderia ter sido escrito nesse evento antes da "fase" (lembra do
		// básico do jsf, o ciclo de vida e as fases...
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}