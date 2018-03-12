package wake;

import com.dgc.fiscal.NFSe;
import com.dgc.util.Util;

public class TestNfse {

	public static void main(String[] args) {
		try {
			emitirNFSeLad();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String emitirNFSeLad() throws Exception {
		String dataCompetencia = Util.formataDataAtualComT();
		String codCidadePrestadora = "5208707";
		// String codCidadeTomadora =
		// getUtilService().consultarCodCidadeIBGE(empresaTomadora.getCodCidade());
		String descricaoCidadeTomadora = "Goiânia";
		String siglaUFTomador = "GO";
		// String codUFPrestadora =
		// String.valueOf(getUtilService().consultarCodEstadoIBGE(empresaPrestadora.getCodUf()));
		/* Calcula a próxima NFS-e a ser gerada */
		Long nfseAtual = 1l; // TODO
		NFSe nfse = new NFSe();
		String cabecalho = nfse.getCabecalho(dataCompetencia, String.valueOf(nfseAtual));
		String prestador = nfse.getPrestador();
		String servico = nfse.getServico(60f, codCidadePrestadora); // TODO
		String email = "dgodinhoc@gmail.com";
		String telefone = "(62)98159-0798";
		String cpf = "021.814.341-90";
		String razao = "Daniel Godinho Cordeiro";
		String tomador = nfse.getTomador(email, telefone, cpf, razao);
		String rodape = nfse.getRodape();
		String xmlIntegracao = cabecalho.concat(prestador).concat(servico).concat(tomador).concat(rodape);
		// return NFSe.geraNFSe(xmlIntegracao,
		// parametroPrestadora.getChaveAcessoNfe());
		return NFSe.geraNFSe(xmlIntegracao);
	}

}
