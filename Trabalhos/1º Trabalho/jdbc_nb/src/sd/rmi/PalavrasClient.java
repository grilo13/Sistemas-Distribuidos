package sd.rmi;

import java.rmi.RemoteException;
import java.util.ArrayList;

//PARA COMPILAR
/*
* RMI REGISTRY: rmiregistry -J-classpath -Jbin 9001
* SERVER: java -classpath bin sd.rmi.PalavrasServer 9001
* CLIENT: java -classpath  build/classes sd.rmi.PalavrasServer 9001
* 
*/

import java.util.List;
import java.util.Scanner;

public class PalavrasClient {
	
	
	/*
	 * Função menu que vai ser mostrada ao cliente todas as vezes até ele desejar não executar mais nenhuma opção	 * 
	 */
	 public static void menu(Palavras obj) throws RemoteException {
		 while (true) {
	            System.out.print("\n------------MENU------------\nSelecione a opção que deseja:\n"
	                    + "1- Consultar centros de vacinação\n"
	            		+ "2- Consultar comprimento da fila de espera num centro\n"
	                    + "3- Inscrição para vacina num centro(nome, género e idade)\n"
	                    + "4- Registar vacinação (data e tipo de vacina)\n"
	                    + "5- Reportar efeitos secundários na toma da vacina\n"
	                    + "6- Listar nº total de vacinados por tipo de vacina e nº de casos com e efeitos secundários por tipo de vacina\n"
	                    + "0- Sair\n>> ");
	            
	            Scanner scanner = new Scanner(System.in);
	            
	            String menuChoice = scanner.nextLine();
	            
	            System.out.println("MENU CHOICE: " + menuChoice);
	            
	            switch (menuChoice) {
	                case "1":
	                	System.out.println("\n--->  Centros de Vacinação Disponíveis  <---");
	                	
	                	ArrayList<String> arrayCentros = obj.consultarCentros();
	                      
                        if(arrayCentros.isEmpty() == true)
                        {
                            System.out.println("Não existem centros de vacinação no momento.");
                        }

                        else
                        {
                            for(int i = 0; i < arrayCentros.size(); i = i+1)
                            {
                                
                                if(i % 2 != 0) {
                                	System.out.println("  Nome do centro: " +arrayCentros.get(i));
                                } else {
                                	System.out.print("Id do centro: " + arrayCentros.get(i));
                                }
                            }
                        }

	                    break;
	                    
	                case "2":
	                	System.out.println("\n--->  Consulta das listas de espera dos Centros Disponíveis  <---\n");
	                	
	                	int auxiliar = 0;
	                	
	                	while(auxiliar == 0) {
	                		System.out.println("Insira o nome do centro que quer ver a fila: ");
		                	Scanner scanner2 = new Scanner(System.in);
		                	int verCentro = scanner.nextInt();
		                	System.out.println("A verificar " + verCentro + " ....");
		                	
		                	int tamanhoFila = obj.consultarFila(verCentro);
		                	
		                	if(tamanhoFila == 0) {
		                		System.out.println("\nConsulta de fila de espera para o centro pretendido não existe no momento.");
		                		break;
		                	} else {
		                		System.out.println("O Centro escolhido tem uma lista de espera de " + tamanhoFila + " utentes.");
			                	
			                	System.out.println("\nDeseja consultar mais algum centro? S (sim) ou N (não)");
			                	String escolha = scanner2.next();
			                	
			                	if(escolha.equals("N")) {
			                		auxiliar = 1;
			                	} else if(escolha.equals("S")) {
			                		continue;
			                	}else {
			                		System.out.println("Formato de resposta não reconhecido.");
			                		break;
			                	}
		                	}
		                	
		              
	                	}
	                    break;
	                    
	                case "3":
	                	System.out.println("\n--->  Inscrição para Vacinação  <---\n");
	                	System.out.println("Insira o seu nome, idade e genero: (nome enter, idade enter, etc)");
	                	Scanner scanner3 = new Scanner(System.in);
	                	String nome = scanner3.nextLine();
	                	int idade = scanner3.nextInt();
	                	String genero = scanner3.next();
	                	
	                	int numerodeCentros = obj.contaCentros();
	                	
	                	String inscricao = obj.inscricaoVac(numerodeCentros,nome,genero, idade);
	                	
	                	System.out.print(inscricao);
	                    break;
	                    
	                case "4":
	                	System.out.println("\n--->  Registo de Vacinação Efetuada  <---\n");
	                	System.out.println("Insira o codigo de resposta seguido do nome da vacina e data da toma da mesma (enter depois de cada um):");
	                	Scanner scanner4 = new Scanner(System.in);
	                	int codigo = scanner4.nextInt();
	                	String nomeVac = scanner4.next();
	                	String dataVac = scanner4.next(); 
	               
	                	String registo = obj.registoVac(codigo, nomeVac, dataVac);
	                	
	                	System.out.print(registo);
	                	break;
	                	
	                case "5":
	                	System.out.println("\n--->  Reporte de Efeitos Secundários Após a Toma da Vacina  <---\n");
	                	System.out.println("Insira o código seguido dos efeitos secundários que teve (se não teve efeitos secundários não engane):\n");
	                	Scanner scanner5 = new Scanner(System.in);
	                	int codigo1 = scanner5.nextInt();
	                	String efeitos = scanner5.next();
	                	efeitos+=scanner5.nextLine();
	                	
	                	String efeitos_obtidos = obj.registoEfeitosSecundarios(codigo1, efeitos);
	                	
	                	System.out.println(efeitos_obtidos);
	                    break;
	                    
	                case "6":
	                	int total = 0;
	                	int total2 = 0;
	                	System.out.println("\n--->  Lista de vacinados  <---\n");
	                	ArrayList<String> arrayListaVacinados = obj.listaVacinados();
	                      
                        if(arrayListaVacinados.isEmpty() == true)
                        {
                            System.out.println("Não existem centros de vacinação no momento.");
                        }

                        else
                        {
                            for(int i = 0; i < arrayListaVacinados.size(); i = i+1)
                            {
                            	if(i % 2 != 0) {
                            		System.out.println(arrayListaVacinados.get(i));
                            	} else  {
                            		System.out.print(arrayListaVacinados.get(i) + " pessoas vacinadas com ");
                            		total = total + Integer.parseInt(arrayListaVacinados.get(i));
                            	}
                            }
                        }

                        System.out.println("\n--->  Lista de Efeitos Secundários por vacina  <---\n");
                        
                        ArrayList<String> arrayEfeitosSecundarios = obj.listaEfeitosSecundarios();
	                      
                        if(arrayEfeitosSecundarios.isEmpty() == true)
                        {
                            System.out.println("Não existem centros de vacinação no momento.");
                        }

                        else
                        {
                            for(int i = 0; i < arrayEfeitosSecundarios.size(); i = i+1)
                            {
                            	if(i % 2 != 0) {
                            		System.out.println(arrayEfeitosSecundarios.get(i));
                            	} else {
                            		System.out.print(arrayEfeitosSecundarios.get(i) + " pessoas tiveram efeitos secundários com a vacina da ");
                            		total2 = total2 + Integer.parseInt(arrayEfeitosSecundarios.get(i));
                            	}
                            }
                        }
                        
                        
                        System.out.println("\n-->   Estatísticas  <---");
                        System.out.println("\nTotal de pessoas vacinadas: " + total);
                        System.out.println("Total de pessoas com efeitos secundários após a toma da vacina: " + total2);
                        int percentagem = (100 * total2) / total;
                        int percentagem2 = 100 - percentagem;
                        System.out.println(percentagem + "% das pessoas que foram vacinadas tiveram efeitos secundários");
                        System.out.println(percentagem2 + "% das pessoas que foram vacinadas não tiveram efeitos secundários");

	                    break;
	                case "0":
	                    System.exit(0);
	                default:
	                    System.out.println("\n---Opção inválida---\n");
	                    break;
            }
	            
		 
		 }
	 }

	public static void main(String args[]) {
		String regHost = "localhost";
		String regPort = "9000";  // porto do binder
	
		if (args.length !=2) { // requer 2 argumentos
		    System.out.println
			("Usage: java sd.rmi.PalavrasClient registryHost registryPort");
		    System.exit(1);
		}
		regHost= args[0];
		regPort= args[1];
	
	
		try {
		    // objeto que fica associado ao proxy para objeto remoto
		    Palavras obj = (Palavras) java.rmi.Naming.lookup("rmi://" + regHost +":"+regPort +"/palavras");
		    
	
		    // invocacao de métodos remotos
		    menu(obj);
	
		} 
		catch (Exception ex) {
		    ex.printStackTrace();
		}
    }
}
