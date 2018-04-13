package wake;

import com.dgc.entidade.Usuario;
import com.dgc.fiscal.NFSe;
import com.dgc.util.Util;

public class TestNfse {

	public static void main(String[] args) {
		try {
			String mail = "dgodinhoc@gmail.com";
			String telefone = "(62)98159-0798";
			String cpf = "021.814.341-90";
			String nome = "Daniel Godinho Cordeiro";
			Usuario usuario = new Usuario(nome, mail, telefone, cpf);
			// System.out.println(emitirNFSeInvoicy(usuario));
			System.out.println(emitirNFSe(usuario));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String emitirNFSe(Usuario usuario) throws Exception {
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
		String tomador = nfse.getTomador(usuario);
		String rodape = nfse.getRodape();
		String xmlIntegracao = cabecalho.concat(prestador).concat(servico).concat(tomador).concat(rodape);

		return NFSe.geraNFSe(xmlIntegracao);
	}

}
