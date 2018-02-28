package com.dgc.fiscal;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.dgc.properties.AccessProperties;
import com.dgc.util.Util;
import com.dgc.util.UtilNF;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class NFSe {

	String cnpj = "";
	String razao;
	String fantasia;
	String ie;
	String im;
	String endereco;
	String numeroEndereco;
	String bairro;
	String codCidadeIBGE;
	String cep;
	String fone;
	String uf;

	public static String geraNFSe(String xmlIntegracao) {
		try {
			/* Chama função que faz a escrita do SOAP */
			String xmlGerado = UtilNF.retornaEscritaSoap(xmlIntegracao, empPK, chaveComunicacao, "");
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

	public String getCabecalho(String dataCompetencia, String nfseAtual) {
		StringBuilder sb = new StringBuilder();
		sb.append("<Envio>");
		sb.append("<ModeloDocumento>NFSe</ModeloDocumento>");
		sb.append("<Versao>1.00</Versao>");
		sb.append("<RPS>");
		sb.append("<RPSNumero>" + nfseAtual + "</RPSNumero>");
		// TODO Alterar para 'UNICA' quando for enviar pelo município de Goiânia
		// sb.append("<RPSSerie>LAD</RPSSerie>");
		sb.append("<RPSSerie>UNICA</RPSSerie>");
		sb.append("<RPSTipo>1</RPSTipo>");
		sb.append("<LocalPrestServ>1</LocalPrestServ>");
		sb.append("<dEmis>" + Util.formataDataAtualComT() + "</dEmis>");
		sb.append("<dCompetencia>" + dataCompetencia + "</dCompetencia>");
		sb.append("<natOp>1</natOp>");
		sb.append("<Operacao>A</Operacao>");
		sb.append("<RegEspTrib>01</RegEspTrib>");
		sb.append("<OptSN>2</OptSN>");
		sb.append("<IncCult>2</IncCult>");
		sb.append("<Status>1</Status>");
		sb.append("<tpAmb>1</tpAmb>");
		return sb.toString();
	}

	public String getCabecalhoConsultaNFSe(String modeloDocumento, String numInicial, String numFinal, String cnpjEmissor, String serie) {
		StringBuilder sb = new StringBuilder();
		sb.append("<Consulta>");
		sb.append("<ModeloDocumento>" + modeloDocumento + "</ModeloDocumento>");
		sb.append("<Versao>1.00</Versao>");
		sb.append("<tpAmb>2</tpAmb>");
		sb.append("<CnpjEmissor>" + cnpjEmissor + "</CnpjEmissor>");
		sb.append("<NumeroInicial>" + numInicial + "</NumeroInicial>");
		sb.append("<NumeroFinal>" + numFinal + "</NumeroFinal>");
		sb.append("<Serie>" + serie + "</Serie>");
		sb.append("<ChaveAcesso/>");
		sb.append("<DataEmissaoInicial/>");
		sb.append("<DataEmissaoFinal/>");
		sb.append("</Consulta>");
		return sb.toString();
	}

	public String getParametrosConsultaNFSe() {
		StringBuilder sb = new StringBuilder();
		sb.append("<ParametrosConsulta>");
		sb.append("<Situacao>S</Situacao>");
		sb.append("<XMLCompleto>S</XMLCompleto>");
		sb.append("<XMLLink>S</XMLLink>");
		sb.append("<PDFBase64>S</PDFBase64>");
		sb.append("<PDFLink>S</PDFLink>");
		sb.append("<Eventos>N</Eventos>");
		sb.append("</ParametrosConsulta>");
		sb.toString();
		return sb.toString();
	}

	public String getPrestador(String codCidadePrestadora) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<Prestador>");
		sb.append("<CNPJ_prest>" + cnpj + "</CNPJ_prest>");
		sb.append("<xNome>" + razao + "</xNome>");
		sb.append("<xFant>" + fantasia + "</xFant>");
		sb.append("<IE>" + ie + "</IE>");
		sb.append("<IM>" + im + "</IM>");
		sb.append("<enderPrest>");
		sb.append("<TPEnd>Avenida</TPEnd>");
		sb.append("<xLgr>" + endereco + "</xLgr>");
		sb.append("<nro>" + numeroEndereco + "</nro>");
		sb.append("<xBairro>" + bairro + "</xBairro>");
		sb.append("<cMun>" + codCidadePrestadora + "</cMun>");
		sb.append("<UF>" + uf + "</UF>");
		sb.append("<CEP>" + cep + "</CEP>");
		sb.append("<fone>" + fone + "</fone>");
		sb.append("</enderPrest>");
		sb.append("</Prestador>");
		return sb.toString();
	}

	public String getTomador(String email, String telefone, String cpf, String razao) {
		StringBuilder sb = new StringBuilder();
		sb.append("<Tomador>");
		sb.append("<TomaCPF>" + Util.retornaSemPontoHifenBarra(cpf) + "</TomaCPF>");
		sb.append("<TomaRazaoSocial>" + razao + "</TomaRazaoSocial>");
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
		sb.append("<TomaTelefone>" + Util.substituirCaracteres(telefone) + "</TomaTelefone>");
		sb.append("<TomaEmail>" + email + "</TomaEmail>");
		sb.append("</Tomador>");
		return sb.toString();
	}

	public String getServico(float valorServico, String codCidadePrestadora) throws Exception {
		/* Base de cálculo */
		float bc = Float.valueOf("40");
		/* ISS */
		float iss = Float.valueOf("0.05");
		/* Valor do serviço descontando a base de cálculo */
		float valorServicoComBC = calcularValorServicoComBC(valorServico, bc);
		float valorISS = calcularValorISS(valorServico, iss, bc);
		float valorServicoComISSeBC = calcularValorServicoComISSeBC(valorServico, iss, bc);
		StringBuilder sb = new StringBuilder();
		sb.append("<ListaItens>");
		sb.append("<Item>");
		sb.append("<ItemSeq>1</ItemSeq>");
		sb.append("<ItemCod>1</ItemCod>");
		sb.append("<ItemQtde>1.00</ItemQtde>");
		sb.append("<ItemvUnit>" + String.valueOf(valorServico) + "</ItemvUnit>");
		sb.append("<ItemuMed>UN</ItemuMed>");
		sb.append("<ItemvlDed>0.00</ItemvlDed>");
		sb.append("<ItemTributavel>S</ItemTributavel>");
		/*
		 * 6209-1/00 Suporte técnico, manutenção e outros serviços em tecnologia
		 * da informação Atividade Permitida
		 */
		sb.append("<ItemcCnae>631940000</ItemcCnae>");
		sb.append("<ItemTributMunicipio>1.05</ItemTributMunicipio>");
		sb.append("<ItemvIss>" + Util.arredondar2CasasParaBaixoRetornaString(valorISS) + "</ItemvIss>");
		sb.append("<ItemvDesconto>0.00</ItemvDesconto>");
		sb.append("<ItemAliquota>" + String.valueOf(iss) + "</ItemAliquota>");
		sb.append("<ItemVlrTotal>" + String.valueOf(valorServico) + "</ItemVlrTotal>");
		sb.append("<ItemBaseCalculo>" + Util.arredondar2CasasParaBaixoRetornaString(valorServicoComBC) + "</ItemBaseCalculo>");
		sb.append("<ItemvlrISSRetido>0.00</ItemvlrISSRetido>");
		sb.append("<ItemIssRetido>0.00</ItemIssRetido>");
		sb.append("<ItemRespRetencao>3</ItemRespRetencao>");
		sb.append("<ItemIteListServico>1.05</ItemIteListServico>");
		sb.append("<ItemExigibilidadeISS>0</ItemExigibilidadeISS>");
		sb.append("<ItemcMunIncidencia>" + codCidadePrestadora + "</ItemcMunIncidencia>");
		sb.append("<ItemDedNFRef>0</ItemDedNFRef>");
		sb.append("<ItemDedvlTotRef>0.00</ItemDedvlTotRef>");
		sb.append("<ItemDedPer>0.00</ItemDedPer>");
		sb.append("<ItemDedValor>0.00</ItemDedValor>");
		sb.append("<ItemVlrLiquido>" + Util.arredondar2CasasParaBaixoRetornaString(valorServicoComISSeBC) + "</ItemVlrLiquido>");
		sb.append("<ItemValAliqINSS>0.00</ItemValAliqINSS>");
		sb.append("<ItemValINSS>0.00</ItemValINSS>");
		sb.append("<ItemValAliqIR>0.00</ItemValAliqIR>");
		sb.append("<ItemValIR>0.00</ItemValIR>");
		sb.append("<ItemValAliqCOFINS>0.00</ItemValAliqCOFINS>");
		sb.append("<ItemValCOFINS>0.00</ItemValCOFINS>");
		sb.append("<ItemValAliqCSLL>0.00</ItemValAliqCSLL>");
		sb.append("<ItemValCSLL>0.00</ItemValCSLL>");
		sb.append("<ItemValAliqPIS>0.00</ItemValAliqPIS>");
		sb.append("<ItemValPIS>0.00</ItemValPIS>");
		sb.append("<ItemRedBCRetido>0.00</ItemRedBCRetido>");
		sb.append("<ItemBCRetido>0.00</ItemBCRetido>");
		sb.append("<ItemValAliqISSRetido>0.00</ItemValAliqISSRetido>");
		sb.append("</Item>");
		sb.append("</ListaItens>");
		sb.append("<Servico>");
		sb.append("<Valores>");
		sb.append("<ValServicos>" + String.valueOf(valorServico) + "</ValServicos>");
		sb.append("<ValLiquido>" + Util.arredondar2CasasParaBaixoRetornaString(valorServicoComISSeBC) + "</ValLiquido>");
		sb.append("<ISSRetido>2</ISSRetido>");
		sb.append("<ValISS>" + Util.arredondar2CasasParaBaixoRetornaString(valorISS) + "</ValISS>");
		sb.append("<ValISSRetido>0.00</ValISSRetido>");
		sb.append("<ValOutrasRetencoes>0.00</ValOutrasRetencoes>");
		sb.append("<ValBaseCalculo>" + Util.arredondar2CasasParaBaixoRetornaString(valorServicoComBC) + "</ValBaseCalculo>");
		sb.append("<ValAliqISS>" + String.valueOf(iss) + "</ValAliqISS>");
		sb.append("<ValDeducoes>0.00</ValDeducoes>");
		sb.append("<ValPIS>0.00</ValPIS>");
		sb.append("<ValCOFINS>0.00</ValCOFINS>");
		sb.append("<ValINSS>0.00</ValINSS>");
		sb.append("<ValIR>0.00</ValIR>");
		sb.append("<ValCSLL>0.00</ValCSLL>");
		sb.append("<RespRetencao>3</RespRetencao>");
		sb.append("<Tributavel>S</Tributavel>");
		sb.append("<ValAliqISS>" + String.valueOf(iss) + "</ValAliqISS>");
		sb.append("<ValAliqPIS>0.0000</ValAliqPIS>");
		sb.append("<ValAliqCOFINS>0.0000</ValAliqCOFINS>");
		sb.append("<ValAliqIR>0.0000</ValAliqIR>");
		sb.append("<ValAliqCSLL>0.0000</ValAliqCSLL>");
		sb.append("<ValAliqINSS>0.0000</ValAliqINSS>");
		sb.append("<ValDescIncond>0.00</ValDescIncond>");
		sb.append("<ValDescCond>0.00</ValDescCond>");
		sb.append("<ValAliqISSoMunic>0.0000</ValAliqISSoMunic>");
		sb.append("<InfValPIS>0.00</InfValPIS>");
		sb.append("<InfValCOFINS>0.00</InfValCOFINS>");
		sb.append("<ValLiqFatura>0.00</ValLiqFatura>");
		sb.append("<ValBCISSRetido>0.00</ValBCISSRetido>");
		sb.append("</Valores>");
		sb.append("<IteListServico>1.05</IteListServico>");
		sb.append("<Cnae>631940000</Cnae>");
		sb.append("<Discriminacao>LICENCA PARA USO DO PORTAL LADFOOD</Discriminacao>");
		sb.append("<cMun>" + codCidadePrestadora + "</cMun>");
		sb.append("<cMunIncidencia>" + codCidadePrestadora + "</cMunIncidencia>");
		sb.append("<fPagamento>1</fPagamento>");
		sb.append("<TributMunicipio>631940000</TributMunicipio>");
		sb.append("<SerQuantidade>1.00</SerQuantidade>");
		sb.append("<SerUnidade>UN</SerUnidade>");
		sb.append("<ObrigoMunic>2</ObrigoMunic>");
		sb.append("<TributacaoISS>1</TributacaoISS>");
		sb.append("</Servico>");
		return sb.toString();
	}

	public String getRodape() {
		StringBuilder sb = new StringBuilder();
		// sb.append("<Arquivo />");
		// sb.append("<ExtensaoArquivo />");
		sb.append("</RPS>");
		sb.append("</Envio>");
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

}
