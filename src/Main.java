//Éliton Lunardi
public class Main {

	public static void main(String[] args) {
		int[][] matrizAdjacencia = {
				{0,2,0},
				{0,0,1},
				{1,0,0}
		};
		 Classificacao classificacao = new Classificacao();
		 String arestas = classificacao.arestasDoGrafo(matrizAdjacencia);
		 String tipoDoGrafo = classificacao.tipoDoGrafo(matrizAdjacencia);
		 System.out.println(tipoDoGrafo);
		 System.out.println("------------------------------------------------------------------------------------------------------");
		 System.out.println(arestas);
		 System.out.println("------------------------------------------------------------------------------------------------------");
		 System.out.println(classificacao.grausDoVertice(matrizAdjacencia));
	}

}
