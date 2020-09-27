//Éliton Lunardi
import java.util.ArrayList;
import java.util.List;


public class Classificacao {
	private String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};
	
	public String tipoDoGrafo(int[][] matrizAdjacencia) {
		String tipo = "";		
		
		if(this.ehNulo(matrizAdjacencia)) {
			return "Grafo nulo";
		}			
		
		if (this.ehMultigrafo(matrizAdjacencia)) {
			tipo +="Multigrafo\n";
		}else {
			tipo+= "Grafo simples\n";
		}
		 
		if (this.ehDirigido(matrizAdjacencia)) {
			tipo +="Dirigido\n";
		}else {
			tipo+= "Não dirigido\n";
		}
		
		if (this.ehCompleto(matrizAdjacencia)) {
			tipo += "Completo\n";
		}
		
		if (this.ehRegular(matrizAdjacencia)) {
			tipo += "Regular";
		}

		return tipo;
	}
	public String arestasDoGrafo(int[][] matrizAdjacencia) {
	  	if (!ehNulo(matrizAdjacencia)) {
			if (this.ehDirigido(matrizAdjacencia)) {
				return this.arestasDoGrafoDirigido(matrizAdjacencia);
			}else {
				return this.arestasDoGrafoNaoDirigido(matrizAdjacencia);
			}
	  	}
	  	return "";
    }
	public String grausDoVertice(int [][] matrizAdjacencia) {
		if (!ehNulo(matrizAdjacencia)) {
			String saida = "Graus do vertice:";
			if (!ehDirigido(matrizAdjacencia)) {				
				saida += this.grausDoVerticeNaoDirigido(matrizAdjacencia);
			}else {				
				saida += this.grausDoVerticeDirigido(matrizAdjacencia);
			}			
			return saida;
		}
		return "";
	}
	
	private boolean ehDirigido(int[][] matrizAdjacencia) {
		for (int i = 0; i < matrizAdjacencia.length; i++) {
			for (int j = 0; j < matrizAdjacencia[0].length; j++) {
				if (matrizAdjacencia[i][j] != matrizAdjacencia[j][i]) {
					return true;
				}
			}
		}
		return false;
	}    
	private boolean ehMultigrafo(int [][] matrizAdjacencia) {
		return this.existeLaco(matrizAdjacencia) || this.existeArestaParalela(matrizAdjacencia);
	}	
	private boolean existeLaco(int[][] matrizAdjacencia) {
		for (int i = 0; i < matrizAdjacencia.length; i++) {
			int laco = matrizAdjacencia[i][i];
			if (laco != 0) {
				return true;
			}
		}
		return false;
	}
	private boolean existeArestaParalela(int[][] matrizAdjacencia) {
		for (int i = 0; i < matrizAdjacencia.length; i++) {
			for (int j = 0; j < matrizAdjacencia[0].length; j++) {
				if (matrizAdjacencia[i][j] > 1) {
					 return true;
				}
			}
		}
		return false;
	}
	private boolean ehNulo(int[][] matrizAdjacencia) {
		
		for (int i = 0; i < matrizAdjacencia.length; i++) {
			for (int j = 0; j < matrizAdjacencia[0].length; j++) {
				if (matrizAdjacencia[i][j] != 0) {
					return false;
				}
			}
		}
		return true;
	}	
	private boolean ehCompleto(int [][] matrizAdjacencia) {

		if (ehMultigrafo(matrizAdjacencia)) {
			return false;
		}else {
			for (int i = 0; i < matrizAdjacencia.length; i++) {
				for (int j = 0; j < matrizAdjacencia[0].length; j++) {
					if (i==j)  {
						if (matrizAdjacencia[i][j] != 0) {
							return false;
						}
					}
					else {
						if (matrizAdjacencia[i][j] != 1) {
							return false;
						}
					}
				}
			}
			return true;
		}
	}
	private boolean ehRegular(int[][] matrizAdjacencia) {
		if (!ehDirigido(matrizAdjacencia)) {
			int grauDoVerticeAtual = 0;
			List<Integer> sequenciaGraus = new ArrayList<Integer>();
			
			for (int i = 0; i < matrizAdjacencia.length; i++) {				
				for (int j = 0; j < matrizAdjacencia[0].length; j++) {
					grauDoVerticeAtual += matrizAdjacencia[i][j];
				}			
				sequenciaGraus.add(grauDoVerticeAtual);
				grauDoVerticeAtual = 0;
			}
			sequenciaGraus.sort(new ComparadorInt());
			int grauBase = sequenciaGraus.get(0);		
			for (int i = 0; i < sequenciaGraus.size(); i++) {
				if (sequenciaGraus.get(i) != grauBase) {
					return false;
				}
			}
		}else {
			int[] grauEntrada = new int[matrizAdjacencia.length];
			int[] grauSaida = new int[matrizAdjacencia[0].length];
			int[] totalPorVertice = new int[matrizAdjacencia.length];		
			for (int i = 0; i < matrizAdjacencia.length; i++) {
				for (int j = 0; j < matrizAdjacencia[0].length; j++) {
					grauEntrada[j] += matrizAdjacencia[i][j];
					grauSaida[i] += matrizAdjacencia[i][j];
				}
			}
			for (int i = 0; i < grauEntrada.length; i++) {
				totalPorVertice[i] += grauEntrada[i];
			}
			for (int i = 0; i < grauSaida.length; i++) {
				totalPorVertice[i] += grauSaida[i];
			}
			ordenar(totalPorVertice);
			int grauBase = totalPorVertice[0];
			for (int i = 0; i < totalPorVertice.length; i++) {
				if (totalPorVertice[i] != grauBase) {
					return false;
				}
			}
		}
		
		return true;
	}
	private String arestasDoGrafoDirigido(int [][] matrizAdjacencia) {
		int qtdArestasTotal = 0;	
		String saidaParcial = "E = {";
		boolean primeiraVez = true;
		
		for (int i = 0; i < matrizAdjacencia.length; i++) {
			for (int j = 0; j < matrizAdjacencia[0].length; j++) {					
				if (matrizAdjacencia[i][j] > 0) {			
					String verticeLinha = this.vertices[i];
					String verticeColuna = this.vertices[j];
					qtdArestasTotal += matrizAdjacencia[i][j];
					int qtdArestasNessaPosicao = matrizAdjacencia[i][j]; 
					
					for (int k = 0; k < qtdArestasNessaPosicao; k++) {
						if (primeiraVez) {
							saidaParcial += "(" + verticeLinha + "," + verticeColuna + ")" ;
							primeiraVez = false;
						}else {
							saidaParcial += ", (" + verticeLinha + "," + verticeColuna + ")" ;
						}
					}
														
				}				
			}
		}
		saidaParcial += "}";		
		String saidaCompleta = "Quantidade Arestas: " + qtdArestasTotal + " - "+saidaParcial;
		return saidaCompleta;
	}
	private String arestasDoGrafoNaoDirigido(int [][] matrizAdjacencia) {
		int qtdArestasTotal = 0;	
		String saidaParcial = "E = {";
		boolean primeiraVez = true;
		
		for (int i = 0; i < matrizAdjacencia.length; i++) {
			for (int j = 0; j < matrizAdjacencia[0].length; j++) {					
				if (matrizAdjacencia[i][j] > 0) {			
					String verticeLinha = this.vertices[i];
					String verticeColuna = this.vertices[j];
					
					int qtdArestasNessaPosicao = matrizAdjacencia[i][j];
					qtdArestasTotal += matrizAdjacencia[i][j];
					
					if (i == j) {
						qtdArestasTotal += matrizAdjacencia[i][j];	
					}
					
					if (j >= i) {
						for (int k = 0; k < qtdArestasNessaPosicao; k++) {
							if (primeiraVez) {
								saidaParcial += "(" + verticeLinha + "," + verticeColuna + ")" ;
								primeiraVez = false;
							}else {
								saidaParcial += ", (" + verticeLinha + "," + verticeColuna + ")" ;
							}
						}
					}			
				}				
			}
		}
		saidaParcial += "}";		
		String saidaCompleta = "Quantidade Arestas: " + qtdArestasTotal / 2 + " - "+saidaParcial;
		return saidaCompleta;
	}
	private String grausDoVerticeDirigido(int[][] matrizAdjacencia) {
		String saida = "";
		
		int[] grauEntrada = new int[matrizAdjacencia.length];
		int[] grauSaida = new int[matrizAdjacencia[0].length];
		int[] totalPorVertice = new int[matrizAdjacencia.length];		
		for (int i = 0; i < matrizAdjacencia.length; i++) {
			for (int j = 0; j < matrizAdjacencia[0].length; j++) {
				grauEntrada[j] += matrizAdjacencia[i][j];
				grauSaida[i] += matrizAdjacencia[i][j];
			}
		}

		for (int i = 0; i < grauEntrada.length; i++) {
			saida += "\nEntrada: "  + this.vertices[i]  + " - " + grauEntrada[i];
			totalPorVertice[i] += grauEntrada[i];
		}
		
		for (int i = 0; i < grauSaida.length; i++) {
			saida += "\nSaida: "  + this.vertices[i]  + " - " + grauSaida[i];
			totalPorVertice[i] += grauSaida[i];
		}	
		
		saida += "\nSequência de graus: ";
		ordenar(totalPorVertice);
		for (int i = 0; i < totalPorVertice.length; i++) {
			saida += " "+ totalPorVertice[i];
		}
		
		return saida;
	}
	private String grausDoVerticeNaoDirigido(int[][] matrizAdjacencia) {
		int grauDoVerticeAtual = 0;
		String saida = "";
		List<Integer> sequenciaGraus = new ArrayList<Integer>();
		
		for (int i = 0; i < matrizAdjacencia.length; i++) {				
			for (int j = 0; j < matrizAdjacencia[0].length; j++) {
				grauDoVerticeAtual += matrizAdjacencia[i][j];
			}			
			saida += "\nVértice " + this.vertices[i] + ": " + grauDoVerticeAtual +"";
			sequenciaGraus.add(grauDoVerticeAtual);
			grauDoVerticeAtual = 0;
		}
		
		sequenciaGraus.sort(new ComparadorInt());
		saida += "\nSequência de graus: ";
		for (int k = 0; k < sequenciaGraus.size(); k++) {
			saida+= "" + sequenciaGraus.get(k) + " ";
		}
		return saida;
	}
	private void ordenar(int[] v) {
		    for(int i = 0; i < v.length - 1; i++) {
		      for(int j = 0; j < v.length - 1 - i; j++) {
		        if(v[j] > v[j + 1]) {
		          int aux = v[j];
		          v[j] = v[j + 1];
		          v[j + 1] = aux;
		        }
		      }
		    }		
	}
}
	


