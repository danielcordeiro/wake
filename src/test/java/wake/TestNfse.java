package wake;

import com.dgc.fiscal.NFSe;
import com.dgc.util.Util;

public class TestNfse {

	public String emitirNFSeLad() throws Exception {
			String dataCompetencia = Util.formataDataAtualComT();
			String codCidadePrestadora = "5208707";
//			String codCidadeTomadora = getUtilService().consultarCodCidadeIBGE(empresaTomadora.getCodCidade());
			String descricaoCidadeTomadora = "Goiânia";
			String siglaUFTomador = "GO";
//			String codUFPrestadora = String.valueOf(getUtilService().consultarCodEstadoIBGE(empresaPrestadora.getCodUf()));
			/* Calcula a próxima NFS-e a ser gerada */
			Long nfseAtual = 1l; //TODO
			NFSe nfse = new NFSe();
			String cabecalho = nfse.getCabecalho(dataCompetencia, String.valueOf(nfseAtual));
			String prestador = nfse.getPrestador(codCidadePrestadora);
			String servico = nfse.getServico(60f, codCidadePrestadora); // TODO
			String email; 
			String telefone; 
			String cpf; 
			String razao;
			String tomador = nfse.getTomador(email, telefone,cpf, razao);
			String rodape = nfse.getRodape();
			String xmlIntegracao = cabecalho.concat(prestador).concat(servico).concat(tomador).concat(rodape);
			return NFSe.geraNFSe(xmlIntegracao, parametroPrestadora.getChaveAcessoNfe());
		}return"";
}

}
