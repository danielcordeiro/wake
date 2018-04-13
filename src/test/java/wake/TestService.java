package wake;

import java.util.ArrayList;
import java.util.List;

import com.dgc.entidade.Caixa;
import com.dgc.entidade.Plano;
import com.dgc.entidade.Retirada;
import com.dgc.entidade.Role;
import com.dgc.util.FiltroTO;
import com.dgc.util.TotalTO;

public class TestService {

//	public static void main(String[] args) {
//		List<String> items = new ArrayList<>();
//		items.add("A");
//		items.add("B");
//		items.add("C");
//		items.add("D");
//		items.add("E");
//
//		// lambda
//		// Output : A,B,C,D,E
//		items.forEach(item -> System.out.println(item));
//
//		// Output : C
//		items.forEach(item -> {
//			if ("C".equals(item)) {
//				System.out.println(item);
//			}
//		});
//
//		// method reference
//		// Output : A,B,C,D,E
//		items.forEach(System.out::println);
//
//		// Stream and filter
//		// Output : B
//		items.stream().filter(s -> s.contains("B")).forEach(System.out::println);
//		
//		List<Retirada> retiradas = new ArrayList<Retirada>();
//		Retirada temp = new Retirada();
//		temp.setValor(10f);
//		retiradas.add(temp);
//		Float valorRetirada = 0f;
//		retiradas.forEach(retirada -> retirada.getValor());
//		
//	}
//
//	public TotalTO calcularTotaisCaixaRelatorio(List<Plano> listaPlanosVendidosDia, List<Role> listaRolesFechadosDia, TotalTO total, FiltroTO filtroTO) throws Exception {
//		List<Caixa> caixas = new ArrayList<>();
//		if (!caixas.isEmpty()) {
//			float valor = 0f;
//			// for (Caixa caixa : caixas) {
//			// valor = valor + caixa.getValorAbertura();
//			// }
//			valor = valor + caixas.iterator().next().getValorAbertura();
//
//			total.setAberturaCaixa(valor);
//		}
//
//		List<Retirada> retiradas = new ArrayList<Retirada>();
//		Retirada temp = new Retirada();
//		temp.setValor(10f);
//		retiradas.add(temp);
//		Float valorRetirada = 0f;
//		retiradas.forEach(retirada -> retirada.getValor());
//		for (Retirada retirada : retiradas) {
//			valorRetirada = valorRetirada + (retirada.getValor() * -1);
//		}
//
//		total.setTotalRetirada(valorRetirada);
//
//		total.setTotalDinheiroSaldo(total.getAberturaCaixa() + total.getTotalDinheiro() + total.getTotalRetirada());
//		return total;
//	}
}
