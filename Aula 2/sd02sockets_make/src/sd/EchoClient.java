package sd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {

    private String address= null;
    private int sPort= 0;
    
    public EchoClient(String add, int p) {
	address= add;
	sPort=p;
    }
    
    
    public static void main(String[] args){
    	// exigir os argumentos necessarios
    	if (args.length < 3) {
    	    System.err.println("Argumentos insuficientes:  java EchoClient ADDRESS PORT MSG");
    	    System.exit(1);
    	}
    	
    	try {
    	    String addr= args[0];
    	    int p= Integer.parseInt(args[1]);	
    	    
    	    EchoClient cl= new EchoClient(addr,p);
    	    
    	    cl.go(args[2]);
    	    
    	    // ler o texto a enviar ao servidor
    	    //String s= "mensagem_que_deve_vir_como_argumento";
    	    //cl.go( s );   // faz o pretendido
    	}
    	catch (Exception e) {
    	    System.out.println("Problema no formato dos argumentos");
    	    e.printStackTrace();
    	}
    }
        
        
        
    public void go(String msg) throws IOException {
    	
    	// exercicio 1: mostrar a mensagem que vai ser enviada
    	// ...
    	
          System.out.println("Mensagem enviada ao servidor pelo cliente: " + msg);
        // ... exercicio 3
          
          try {
        	  Socket s= new Socket(address, sPort);
    		  // ja esta connected
    		  
    		  // escrever a mensagem?
    		  OutputStream socketOut= s.getOutputStream();  
    		  socketOut.write(msg.getBytes());  //devolve um array de bytes
    		  
    		  //ler a resposta que o servidor devolve para este lado
    		  InputStream socketIn= s.getInputStream();
    		  //system.in pela nossa string
    		  byte[] b= new byte[256];
    		  int lidos= socketIn.read(b,0,256);  //ler até um maximo de 256 bytes
    		  String resposta = new String(b,0,lidos);
    		  System.out.println("Resposta: " + resposta);
        	  
          }
          catch (UnknownHostException e1) {
        	  System.err.println("Problema: " + e1.getMessage());
        	  e1.printStackTrace();
          }
          catch(Exception e2) {
        	  System.err.println("Problema nos formatos dos argumentos.");
        	  e2.printStackTrace();
          }
	  


	
    }
    
}
