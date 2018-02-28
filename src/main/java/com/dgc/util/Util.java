package com.dgc.util;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpSession;

import com.dgc.entidade.Usuario;

public class Util {

	public static final String PAGINA_LOGIN = "login.xhtml";
	public static final String PAGINA_ROLE = "role.xhtml";
	public static final String PAGINA_NOVO_RIDER = "novo.xhtml";
	public static final String PAGINA_PLANO = "plano.xhtml";
	public static final String PAGINA_EDITAR_PLANO = "planoedit.xhtml";
	public static final String PAGINA_FECHAR_CAIXA = "financeiro.xhtml";
	public static Float valor = 60f;
	private static String formatoNumero = "###,##0.00";
	private static String usuarioSession = "usuario_session";
	public static final String Dinheiro = "Dinheiro";
	public static final String Credito = "Crédito";
	public static final String Debito = "Débitoo";

	public static void mensagem(Severity tipo, String mensagem, String detalhe) {
		FacesContext context = FacesContext.getCurrentInstance();

		context.addMessage(null, new FacesMessage(tipo, mensagem, detalhe));
	}

	public static String formataHora(Date data) {
		if (data != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
			return formatter.format(data);
		} else {
			return "";
		}
	}

	public static boolean isZero(Integer integer) {
		if (integer != null && !integer.equals(0)) {
			return false;
		}
		return true;
	}

	public static boolean isNullVazio(String object) {
		if (object == null || object.equals("")) {
			return true;
		}
		return false;
	}

	public static String calcularMinutosRestante(Date dataInicio, Integer hora) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataInicio);
		calendar.add(Calendar.HOUR, hora);
		Date dataFim = calendar.getTime();

		Date horaAgora = new Date();

		long diff = dataFim.getTime() - horaAgora.getTime();// as given

		long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);

		return String.valueOf(minutes);
	}

	public static String calcularMinutosAndados(Date dataInicio, Integer hora) {
		Date horaAgora = new Date();

		long diff = horaAgora.getTime() - dataInicio.getTime();// as given

		long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);

		return String.valueOf(minutes);
	}

	public static boolean isZero(Long valor) {
		if (valor != null && !valor.equals(new Long(0))) {
			return false;
		}
		return true;
	}

	public static String decimalToDinheiro(Float valor) {
		if (isZero(valor)) {
			return "R$ 0,00";
		}
		NumberFormat twoDForm = new DecimalFormat(formatoNumero);
		// .getInstance(Locale.GERMANY);
		return "R$ " + twoDForm.format(valor);// .replace(",", ".");
	}

	public static boolean isZero(Float valor) {
		if (valor != null && !valor.equals(0f)) {
			return false;
		}
		return true;
	}

	public static void redirecionar(String pagina) {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Flash flash = facesContext.getExternalContext().getFlash();
			flash.setKeepMessages(true);
			flash.setRedirect(true);
			facesContext.getExternalContext().redirect(pagina);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void guardarUsuarioSessao(Usuario usuario) {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context != null) {
			HttpSession sessao = (HttpSession) context.getExternalContext().getSession(true);
			sessao.setAttribute(usuarioSession, usuario);

			context.getExternalContext().getSessionMap().put(usuarioSession, usuario);
		}

	}

	public static Usuario obterUsuarioSessao() {

		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuario = null;
		if (context != null) {
			HttpSession sessao = (HttpSession) context.getExternalContext().getSession(true);
			usuario = (Usuario) sessao.getAttribute(usuarioSession);
		}
		return usuario;
	}

	public static String formataDataHora(Date data) {

		if (data != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			return formatter.format(data);
		} else {
			return "";
		}

	}

	public static String formataData(Date data) {

		if (data != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			return formatter.format(data);
		} else {
			return "";
		}

	}

	public static String dataAtualString() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(new Date());
	}

	public static String consultarValorAtributoXML(String xml, String atributo) {
		String result = "";
		if (xml.indexOf(atributo) != -1) {
			String atributoFim = atributo.replaceFirst("<", "</");
			result = xml.substring(xml.indexOf(atributo), xml.indexOf(atributoFim)).replaceAll(atributo, "");
		}
		return result;
	}

	public static String formataDataAtualComT() {
		String dateInString = formataDataSql(new Date());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			dateInString = dateInString.replace(" ", "T");
			Date date = formatter.parse(dateInString);
			return formatter.format(date);
		} catch (ParseException e) {
		}
		return null;
	}

	public static String formataDataSql(Date data) {

		if (data != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return formatter.format(data);
		} else {
			return "";
		}

	}

	public static String arredondar2CasasParaBaixoRetornaString(Float valor) throws Exception {
		DecimalFormat formatter = new DecimalFormat("#0.00");
		float f1 = (float) valor;
		String f2 = formatter.format(f1).replace(",", ".");
		return f2;
	}

	public static String retornaSemPontoHifenBarra(String nome) {
		nome = nome.replace(".", "");
		nome = nome.replace("-", "");
		nome = nome.replace("/", "");
		return nome;
	}

	public static String substituirCaracteres(String CNPJ) {
		// m�scara do CNPJ: 99.999.999.9999-99
		String str = CNPJ;
		str = str.replace(" ", "");
		str = str.replace("(", "");
		str = str.replace(")", "");
		str = str.replace("-", "");
		str = str.replace(".", "");
		return str;
	}
}

// <property name="url"
// value="jdbc:postgresql://teste.c2zum0yneyi1.us-west-1.rds.amazonaws.com:5432/teste"
// />
// <property name="username" value="db_teste" />
// <property name="password" value="teste.17" />
