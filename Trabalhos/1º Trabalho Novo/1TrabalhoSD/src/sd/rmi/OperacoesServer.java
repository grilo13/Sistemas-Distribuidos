package sd.rmi;

//PARA COMPILAR
/*
 * RMI REGISTRY: rmiregistry -J-classpath -Jbin 9001
 * SERVER: java -classpath bin sd.rmi.OperacoesServer 9001
 * CLIENT: java -classpath  build/classes sd.rmi.OperacoesServer 9001
 * 
 */

/**
 * Classe que ativa o serviço via RMI.
 * 
 */
public class OperacoesServer {  //faz o registo do registo nome no binder

    public static void main(String args[]) {

		int regPort= 1099; // default RMIRegistry port
	
		if (args.length !=5) { // obrigar à presenca de um argumento
		    System.out.println("Usage: java sd.rmi.OperacoesServer registryPort registryHost databaseName user password");
		    System.exit(1);
		}
		
		try {
		    // ATENÇÃO: este porto é relativo ao binder e não ao servidor RMI
		    regPort=Integer.parseInt(args[0]);
	
	
		    // criar um Objeto Remoto
		    Operacoes obj= new OperacoesImpl(args[1], args[2], args[3], args[4]);       
	            
		    // usar o Registry local (mesma máquina) e porto regPort
		    java.rmi.registry.Registry registry = java.rmi.registry.LocateRegistry.getRegistry(regPort);
	
	            
		    // Bind. Args: nome do serviço e objeto remoto
		    registry.rebind("Operacoes", obj);
	            
		    System.out.println("RMI object bound to service in registry");
	
	        System.out.println("servidor pronto");
		} 
		catch (Exception ex) {
		    ex.printStackTrace();
		}
    }
    
}
