package com.dgc.util;

import java.security.MessageDigest;

public class UtilNF {
	public static String retornaEscritaSoap(String xmlIntegracao, String EmpPK, String ChaveComunicacao, String xmlParametros) {
		String sBody = "";
		sBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:inv=\"InvoiCy\">";
		sBody += "<soapenv:Header/>";
		sBody += "<soapenv:Body>";
		sBody += "<inv:recepcao.Execute>";
		sBody += "<inv:Invoicyrecepcao>";
		sBody += "<inv:Cabecalho>";
		sBody += "<inv:EmpPK>" + EmpPK + "</inv:EmpPK>";
		// Lineariza o xml de integração com expressão regular.
		xmlIntegracao = xmlIntegracao.replaceAll("(?ism)(?<=>)[^a-z|0-9]*(?=<)", "");
		// Gera Hash MD5
		String texto = ChaveComunicacao + xmlIntegracao;
		String HashGerado = GeraHashMD5(texto);
		sBody += "<inv:EmpCK>" + HashGerado + "</inv:EmpCK>";
		sBody += "<inv:EmpCO></inv:EmpCO>";
		sBody += "</inv:Cabecalho>";
		sBody += "<inv:Informacoes>";
		sBody += "<inv:Texto></inv:Texto>";
		sBody += "</inv:Informacoes>";
		sBody += "<inv:Dados>";
		sBody += "<inv:DadosItem>";
		// Converte XML de integração para texto
		String xmlEnvio = xmlIntegracao;
		xmlEnvio = xmlEnvio.replaceAll("<", "&lt;");
		xmlEnvio = xmlEnvio.replaceAll(">", "&gt;");
		xmlEnvio = xmlEnvio.replaceAll("\"", "&quot;");
		sBody += "<inv:Documento>" + xmlEnvio + "</inv:Documento>";
		// Lineariza o xml de parâmetros com expressão regular.
		if (!Util.isNullVazio(xmlParametros)) {
			xmlParametros = xmlParametros.replaceAll("(?ism)(?<=>)[^a-z|0-9]*(?=<)", "");
			String xmlParam = xmlParametros;
			xmlParam = xmlParam.replaceAll("<", "&lt;");
			xmlParam = xmlParam.replaceAll(">", "&gt;");
			xmlParam = xmlParam.replaceAll("\"", "&quot;");
			sBody += "<inv:Parametros>" + xmlParam + "</inv:Parametros>";
		} else {
			sBody += "<inv:Parametros></inv:Parametros>";
		}
		sBody += "</inv:DadosItem>";
		sBody += "</inv:Dados>";
		sBody += "</inv:Invoicyrecepcao>";
		sBody += "</inv:recepcao.Execute>";
		sBody += "</soapenv:Body>";
		sBody += "</soapenv:Envelope>";
		return sBody;
	}

	public static String GeraHashMD5(String xml) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(xml.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < thedigest.length; i++) {
				sb.append(Integer.toHexString((thedigest[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (Exception ex) {
			return ex.toString();
		}
	}
}
