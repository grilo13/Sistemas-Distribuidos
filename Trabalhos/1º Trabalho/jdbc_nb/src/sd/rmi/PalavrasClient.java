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
                            for(int i = 1; i < arrayCentros.size(); i = i+2)
                            {
                                System.out.println(arrayCentros.get(i));
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
	                    break;
	                    
	                case "3":
	                	System.out.println("Fazendo inscrição para vacinação...");
	                	System.out.println("Insira o seu nome, idade e genero:");
	                	Scanner scanner3 = new Scanner(System.in);
	                	String nome = scanner3.nextLine();
	                	int idade = scanner3.nextInt();
	                	String genero = scanner3.next();
	                	
	                	int numerodeCentros = obj.contaCentros();
	                	System.out.println("Numero de centros: " + numerodeCentros);
	                	
	                	String inscricao = obj.inscricaoVac(numerodeCentros,nome,genero, idade);
	                	
	                	System.out.print(inscricao);
	                	
	                    break;
	                case "4":
	                	System.out.println("Registando a vacinação...");
	                	System.out.println("Insira o codigo de resposta, nome da vacina e data da toma da mesma:");
	                	Scanner scanner4 = new Scanner(System.in);
	                	int codigo = scanner4.nextInt();
	                	String nomeVac = scanner4.next();
	                	String dataVac = scanner4.next();
	                	//String tipoVac = scanner4.nextLine();
	                 
	                	
	                	String registo = obj.registoVac(codigo, nomeVac, dataVac);
	                	
	                	System.out.print(registo);
	                	break;
	                case "5":
	                	System.out.println("Reportando efeitos secundários...");
	                	System.out.println("Insira o codigo com os efeitos secundários que teve:");
	                	Scanner scanner5 = new Scanner(System.in);
	                	int codigo1 = scanner5.nextInt();
	                	String efeitos = scanner5.next();
	                	efeitos+=scanner5.nextLine();
	                	
	                	String efeitos_obtidos = obj.registoEfeitosSecundarios(codigo1, efeitos);
	                	System.out.println(efeitos_obtidos);
	                    break;
	                case "6":
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
                            	}
                            }
                        }

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
	
		if (args.length !=2) { // requer 3 argumentos
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
