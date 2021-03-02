import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class aula1 {
	
	//insertion sort
	public static void orderedInsert(String n, List<String> ordenados) {
		int posicaoAInserir = 0;
		
		//comparar com elementos da lista
		
		while( posicaoAInserir < ordenados.size() && ordenados.get(posicaoAInserir).compareTo(n) < 0) {
			posicaoAInserir++;
		}
		
		ordenados.addAll(posicaoAInserir, ordenados);
	}
	
	public static void main(String[] args) throws IOException {
		
		//Aula 1 exercicio 0
		
		/*System.out.println("System java version: "+System.getProperty("java.version"));
		System.out.println("System java vendor: "+System.getProperty("java.vendor"));
		System.out.println("System classpath: "+System.getProperty("java.class.path"));
		System.out.println("System classpath: "+System.getProperties());*/
		
		//não irá dar nada
		//exercicio 1
		//mas na linha de comandos irá dar os argumentos dados
		
		//para fazer na consola
		//javac -d bin/ -cp src src/aula1.java
		// java -cp bin aula1
		
		
		/*byte[] b= new byte[128];
		int lidos= System.in.read(b);
		String s= new String(b, 0, lidos -1); // ou -2 no windows
		System.out.println("lido: "+lidos);
		System.out.println("s: "+s+"\n\n");
		int valor= Integer.parseInt(s.substring(0,lidos-1));
		System.out.println("valor: "+valor); */
		
		
		//Exercicio excepções
		/*if(args.length < 0) {
			System.out.println("Não temos argumentos.");
			
		} else {
			
			try {
				for (int i=0; i < args.length; i++) {
				    System.out.println("Hello " + args[i]);
				 }
				
				int valor= Integer.parseInt(args[0]);
				valor++;
				//mostra o sucessor do primeiro argumento
				System.out.println("valor: "+valor);
				
				} 
				catch (NumberFormatException e) { // so entra aqui com uma Exception deste tipo
				  System.out.print("Formato do input não encontrado.");
				} 
		}*/
		
		
		List<String>  nomes = new LinkedList<>();
		
		for (int i = 0; i < args.length; i++) {
	        System.out.println("Argument " + i + ": " + args[i]);
	        nomes.add(args[i]);
	    }
		
		System.out.print("Antes da ordenação: " + nomes.toString());
		
		for(int j = 0; j < nomes.size(); j++) {
			orderedInsert(args[j], nomes);
			
		}
		
		System.out.print("Depois da ordenação: " + nomes.toString());
	}
}
