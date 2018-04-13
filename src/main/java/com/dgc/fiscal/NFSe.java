package com.dgc.fiscal;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.dgc.entidade.Usuario;
import com.dgc.util.Util;
import com.dgc.util.UtilNF;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class NFSe {

	String cnpj = "21014491000108";
	String razao = "LUIZ FERNANDO GALVAO FERREIRA - ME";
	String fantasia = "LUIZ FERNANDO GALVAO FERREIRA - ME";
	String ie = "106205021";
	String im = "3914682";
	String endereco = "ALAMEDA  PAMPULHA";
	String numeroEndereco = "0";
	String bairro = "SETOR JAO";
	String codCidadeIBGE = "5208707";
	String cep = "74673200";
	String fone = "(62)92720413";
	String uf = "GO";

	public static String geraNFSe(String xmlIntegracao) {
		try {

			String url = "https://nfse.goiania.go.gov.br/ws/nfse.asmx";

			String xmlGerado = montaXML(xmlIntegracao);
			System.out.println("XML Gerado: " + xmlGerado);

			// String xmlGerado = xmlIntegracao;

			/* Guarda em stream o contedúdo da escrita do XML */
			InputStream stream = new ByteArrayInputStream(xmlGerado.getBytes(StandardCharsets.UTF_8));
			/* Pega o conteudo stream e define codificação a ele. */
			InputStreamEntity reqEntity = new InputStreamEntity(stream, -1, ContentType.TEXT_XML);
			/*
			 * Informar True, pois o tamanho da entidade é desconhecido, ou
			 * seja, pode variar dependendo do tamanho da string
			 */
			reqEntity.setChunked(true);
			/* Informa o nome da URL que será feito o POST */
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(reqEntity);
			/* Instancia um objeto httpclient */
			CloseableHttpClient httpclient = HttpClients.createDefault();
			/*
			 * Instancia um objeto httpresponse para receber o retorno da
			 * requisição
			 */
			CloseableHttpResponse response = httpclient.execute(httppost);
			/* Grava em buffer o stream do retorno da requisição */
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
			String line = "";
			/* Faz a leitura linha a linha do retorno da requisição */
			StringBuilder result = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				result.append(line.replaceAll("\t", "").replaceAll("\n", ""));
			}
			System.out.println("XML de retorno da NFS-e: " + result.toString());
			return result.toString();

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		return "";
	}

	private static String montaXML(String strXML) {
		StringBuilder str = new StringBuilder();
		str.setLength(0);
		str.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		str.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		str.append("<soap:Body>");

		// Converte XML de integração para texto
		String xmlEnvio = strXML;
		xmlEnvio = xmlEnvio.replaceAll("<", "&lt;");
		xmlEnvio = xmlEnvio.replaceAll(">", "&gt;");
		xmlEnvio = xmlEnvio.replaceAll("&", "&amp;");
		xmlEnvio = xmlEnvio.replaceAll("'", "&apos;");
		xmlEnvio = xmlEnvio.replaceAll("\"", "&quot;");

//		str.append(xmlEnvio);
		str.append(strXML);

		str.append(" </soap:Body>");
		str.append("</soap:Envelope>");
		return str.toString();
	}

	public String getCabecalho(String dataCompetencia, String nfseAtual) {
		StringBuilder sb = new StringBuilder();
		sb.append("<GerarNfseEnvio xmlns=\"http://nfse.goiania.go.gov.br/xsd/nfse_gyn_v02.xsd\">");
//		sb.append("<ArquivoXML>");
		sb.append("<RPS>");
		sb.append("<IdentificacaoRps>");
		sb.append("<Numero>" + nfseAtual + "</Numero>");
		sb.append("<Serie>UNICA</Serie>");
		sb.append("<Tipo>1</Tipo>");
		sb.append("</IdentificacaoRps>");
		sb.append("<DataEmissao>" + Util.formataDataAtualComT() + "</DataEmissao>");
		sb.append("<Status>1</Status>");
		sb.append("</RPS>");
		sb.append("<Competencia>" + Util.formataDataAtualComT() + "</Competencia>");
		return sb.toString();
	}

	public String getServico(float valorServico, String codCidadePrestadora) throws Exception {
		/* Base de cálculo */
		float bc = Float.valueOf("40");
		/* ISS */
		float iss = Float.valueOf("0.05");
		/* Valor do serviço descontando a base de cálculo */
		// float valorServicoComBC = calcularValorServicoComBC(valorServico,
		// bc);
		// float valorISS = calcularValorISS(valorServico, iss, bc);
		// float valorServicoComISSeBC =
		// calcularValorServicoComISSeBC(valorServico, iss, bc);
		StringBuilder sb = new StringBuilder();
		sb.append("<Servico>");
		sb.append("<Valores>");
		sb.append("<ValorServicos>" + String.valueOf(valorServico) + "</ValorServicos>");
		sb.append("<ValorDeducoes>0</ValorDeducoes>");
		sb.append("<ValorPis>0</ValorPis>");
		sb.append("<ValorCofins>0</ValorCofins>");
		sb.append("<ValorInss>0</ValorInss>");
		sb.append("<ValorIr>0</ValorIr>");
		sb.append("<ValorCsll>0</ValorCsll>");
		sb.append("<ValorIss>0</ValorIss>");
		sb.append("<Aliquota>0</Aliquota>");
		sb.append("<DescontoIncondicionado>0</DescontoIncondicionado>");
		sb.append("</Valores>");
		sb.append("<IssRetido>2</IssRetido>");
		sb.append("<CodigoTributacaoMunicipio>932989900</CodigoTributacaoMunicipio>");
		sb.append("<Discriminacao>WAKEBOARD, FLYBOARD E STAND UP PADDLE</Discriminacao>");
		sb.append("<CodigoMunicipio>0025300</CodigoMunicipio>");
		sb.append("<ExigibilidadeISS>1</ExigibilidadeISS>");
		sb.append("<MunicipioIncidencia>0025300</MunicipioIncidencia>");
		sb.append("</Servico>");
		return sb.toString();
	}

	public String getPrestador() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<Prestador>");
		sb.append("<CpfCnpj>");
		sb.append("<cnpj>" + cnpj + "</cnpj>");
		sb.append("</CpfCnpj>");
		sb.append("<InscricaoMunicipal>" + im + "</InscricaoMunicipal>");
		// sb.append("<Nome>" + razao + "</Nome>");
		// sb.append("<IE>" + ie + "</IE>");
		// sb.append("<enderPrest>");
		// sb.append("<TPEnd>Avenida</TPEnd>");
		// sb.append("<xLgr>" + endereco + "</xLgr>");
		// sb.append("<nro>" + numeroEndereco + "</nro>");
		// sb.append("<xBairro>" + bairro + "</xBairro>");
		// sb.append("<cMun>" + codCidadeIBGE + "</cMun>");
		// sb.append("<UF>" + uf + "</UF>");
		// sb.append("<CEP>" + cep + "</CEP>");
		// sb.append("<fone>" + fone + "</fone>");
		// sb.append("</enderPrest>");
		sb.append("</Prestador>");
		return sb.toString();
	}

	public String getTomador(Usuario usuario) {
		StringBuilder sb = new StringBuilder();
		sb.append("<Tomador>");
		sb.append("<IdentificacaoTomador>");
		sb.append("<CpfCnpj>");
		sb.append("<cpf>" + Util.retornaSemPontoHifenBarra(usuario.getCpf()) + "</cpf>");
		sb.append("</CpfCnpj>");
		sb.append("<RazaoSocial>" + usuario.getNome() + "</RazaoSocial>");
		// sb.append("<TomaEndereco>" + empresaTomadora.getEndereco() +
		// "</TomaEndereco>");
		// sb.append("<TomaNumero>" + empresaTomadora.getNumeroEndereco() +
		// "</TomaNumero>");
		// sb.append("<TomaComplemento>" +
		// empresaTomadora.getComplementoEndereco() + "</TomaComplemento>");
		// sb.append("<TomaBairro>" + empresaTomadora.getBairro() +
		// "</TomaBairro>");
		// sb.append("<TomacMun>" + codCidade + "</TomacMun>");
		// sb.append("<TomaxMun>" + descricaoCidade + "</TomaxMun>");
		// sb.append("<TomaUF>" + siglaUFTomador + "</TomaUF>");
		// sb.append("<TomaPais>BR</TomaPais>");
		// sb.append("<TomaCEP>" +
		// Util.retornaSemPontoHifen(empresaTomadora.getCep()) + "</TomaCEP>");
		sb.append("<Telefone>" + Util.substituirCaracteres(usuario.getTelefone()) + "</Telefone>");
		sb.append("<Email>" + usuario.getMail() + "</Email>");
		sb.append("</IdentificacaoTomador>");
		sb.append("</Tomador>");
		sb.append("<OptanteSimplesNacional>1</OptanteSimplesNacional>");
		return sb.toString();
	}

	public String getRodape() {
		StringBuilder sb = new StringBuilder();
		// sb.append("<Arquivo />");
		// sb.append("<ExtensaoArquivo />");
		// sb.append("</RPS>");
//		sb.append("</ArquivoXML>");
		sb.append("</GerarNfseEnvio>");
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		float valorServico = 399;
		float bc = 40.0f;
		float iss = 0.05f;
		float valorServicoComBC = calcularValorServicoComBC(valorServico, bc);
		float valorISS = calcularValorISS(valorServico, iss, bc);
		float valorServicoComISS = calcularValorServicoComISSeBC(valorServico, iss, bc);
		System.out.println("valor servico com bc " + valorServicoComBC);
		System.out.println("valor iss " + valorISS);
		System.out.println("valor serviço com iss " + valorServicoComISS);
		System.out.println(new NFSe().getServico(399f, "15945"));
	}

	public static float calcularValorBC(float valorServico, float bc) {
		float valorBC = 0f;
		valorBC = valorServico - ((valorServico * bc) / 100);
		return valorBC;
	}

	public static float calcularValorISS(float valorServico, float iss, float bc) {
		float valorISS = 0f;
		float valorServicoComBC = 0f;
		valorServicoComBC = calcularValorServicoComBC(valorServico, bc);
		valorISS = valorServicoComBC * iss;
		return valorISS;
	}

	public static float calcularValorServicoComISSeBC(float valorServico, float iss, float bc) {
		float valorFinal = 0f;
		float valorISS = 0f;
		float valorServicoComBC = 0f;
		valorServicoComBC = calcularValorServicoComBC(valorServico, bc);
		valorISS = valorServicoComBC * iss;
		valorFinal = valorServico - valorISS;
		return valorFinal;
	}

	public static float calcularValorServicoComBC(float valorServico, float bc) {
		float valorBC = 0f;
		valorBC = (valorServico * bc) / 100;
		return valorBC;
	}

	public static String geraNFSe_old(String xmlIntegracao) {
		try {
			/* Chama função que faz a escrita do SOAP */
			// String xmlGerado = UtilNF.retornaEscritaSoap(xmlIntegracao,
			// empPK, chaveComunicacao, "");
			String xmlGerado = UtilNF.retornaEscritaSoap(xmlIntegracao, "", "", "");
			/* Guarda em stream o contedúdo da escrita do XML */
			InputStream stream = new ByteArrayInputStream(xmlGerado.getBytes(StandardCharsets.UTF_8));
			/* Pega o conteudo stream e define codificação a ele. */
			InputStreamEntity reqEntity = new InputStreamEntity(stream, -1, ContentType.TEXT_XML);
			/*
			 * Informar True, pois o tamanho da entidade é desconhecido, ou
			 * seja, pode variar dependendo do tamanho da string
			 */
			reqEntity.setChunked(true);
			URI urlInvoicy = null;
			/* Informa o nome da URL que será feito o POST */
			HttpPost httppost = new HttpPost(urlInvoicy);
			httppost.setEntity(reqEntity);
			/* Instancia um objeto httpclient */
			CloseableHttpClient httpclient = HttpClients.createDefault();
			/*
			 * Instancia um objeto httpresponse para receber o retorno da
			 * requisição
			 */
			CloseableHttpResponse response = httpclient.execute(httppost);
			/* Grava em buffer o stream do retorno da requisição */
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
			String line = "";
			/* Faz a leitura linha a linha do retorno da requisição */
			StringBuilder result = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				result.append(line.replaceAll("\t", "").replaceAll("\n", ""));
			}
			System.out.println("XML de retorno da NFS-e: " + result.toString());
			/*
			 * Recupera do xml de retorno o código e a descrição do retorno da
			 * requisição.
			 */
			String codigoRetorno = Util.consultarValorAtributoXML(result.toString(), "<Codigo>");
			String descricaoRetorno = Util.consultarValorAtributoXML(result.toString(), "<Descricao>");
			/* Preenche o resumo final da NFS-e */
			JsonObject jsonRetorno = (JsonObject) new JsonParser().parse("{}");
			jsonRetorno.addProperty("codigoRetorno", codigoRetorno);
			jsonRetorno.addProperty("descricaoRetorno", descricaoRetorno);
			/* Se retornar código de sucesso, preenche o Json de resposta */
			if ("100".equals(codigoRetorno)) {
				String sitCodigo = Util.consultarValorAtributoXML(result.toString(), "<SitCodigo>");
				String sitDescricao = Util.consultarValorAtributoXML(result.toString(), "<SitDescricao>");
				String numRps = Util.consultarValorAtributoXML(result.toString(), "<DocNumero>");
				String serieNFSe = Util.consultarValorAtributoXML(result.toString(), "<DocSerie>");
				String protocoloNfse = Util.consultarValorAtributoXML(result.toString(), "<DocProtocolo>");
				String urlPdf = Util.consultarValorAtributoXML(result.toString(), "<DocPDFDownload>");
				String dataEmissao = Util.consultarValorAtributoXML(xmlIntegracao, "<dEmis>");
				String empresaTomadora = Util.consultarValorAtributoXML(xmlIntegracao, "<TomaCNPJ>");
				String valor = Util.consultarValorAtributoXML(xmlIntegracao, "<ValServicos>");
				jsonRetorno.addProperty("sitCodigo", sitCodigo);
				jsonRetorno.addProperty("sitDescricao", sitDescricao);
				jsonRetorno.addProperty("autorizacao", "");
				jsonRetorno.addProperty("numNf", numRps);
				jsonRetorno.addProperty("protocoloNfse", protocoloNfse);
				jsonRetorno.addProperty("serie", serieNFSe);
				jsonRetorno.addProperty("dataEmissao", dataEmissao);
				jsonRetorno.addProperty("destinatario", empresaTomadora);
				jsonRetorno.addProperty("valor", valor);
				jsonRetorno.addProperty("urlPdf", urlPdf);
				/*
				 * Se a SEFAZ validar o RPS em NFS-e, preenche as seguintes
				 * informações
				 */
				if ("100".equals(sitCodigo)) {
					String numNfse = Util.consultarValorAtributoXML(result.toString(), "<NFSeNumero>");
					String codVerificacaoNfse = Util.consultarValorAtributoXML(result.toString(), "<NFSeCodVerificacao>");
					String urlXml = Util.consultarValorAtributoXML(result.toString(), "<DocXMLDownload>");
					jsonRetorno.addProperty("numNfse", numNfse);
					jsonRetorno.addProperty("codVerificacaoNfse", codVerificacaoNfse);
					jsonRetorno.addProperty("urlXml", urlXml);
				}
			}
			return jsonRetorno.toString();
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		return "";
	}

}
