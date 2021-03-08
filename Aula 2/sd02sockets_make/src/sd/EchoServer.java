package sd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    private int serverPort;	
    
    public EchoServer(int p) {
	serverPort= p;		
    }
    
    
    public static void main(String[] args){
	System.err.println("SERVER...");
	if (args.length<1) {
	    System.err.println("Missing parameter: port number");	
	    System.exit(1);
	}
	int p=0;
	try {
	    p= Integer.parseInt( args[0] );
	}
	catch (Exception e) {
	    e.printStackTrace();
	    System.exit(2);
	}
	
	
	EchoServer serv= new EchoServer(p);
	serv.servico();   // activa o servico, cria um socket para aceitar ligações, um server socket
    }

    
    // activa o servidor no porto indicado em "serverPort"
    public void servico() {
	
	// exercicio 2: inicializar um socket para aceitar ligacoes...
    	
    	try {
    		
    		ServerSocket s = new ServerSocket(serverPort);
    		
    		//ciclo de ligações
    		while(true) {
    			Socket data = s.accept();  //espera e aceita a nova ligação
    			//devole um socket para os dados
    			
    			//tratar as excepcoes no tratamento deste pedido
    			try {
    				 InputStream socketIn= data.getInputStream();
    	    		  //system.in pela nossa string
    	    		  byte[] b= new byte[256];
    	    		  int lidos= socketIn.read(b,0,256);  //ler até um maximo de 256 bytes
    	    		  String mensagem = new String(b,0,lidos);
    	    		  System.out.println("Recebido: " + mensagem);
    	    		  
    	    		  //responder ao cliente
    	    		  OutputStream socketOut = data.getOutputStream();
    	    		  socketOut.write(mensagem.getBytes());
    			} catch(Exception exNoAtendimento) {
    				exNoAtendimento.printStackTrace();
    			} finally {
    				//fechar o socket
    				try {
    					data.close();
    				} catch (Exception e002) {
    					
    				}
    			}
    		}
    	} catch(Exception e003) {
    		
    	}



    }


}

//Para compilar no terminal
//javac -d classes src/sd/EchoClient.java
//java -classpath classes sd.EchoClient alunos.di.uevora.pt 8080 exemplo

// ALTERNATIVA PARA ANALISAR
/* public void servico() {

// exercicio 2: inicializar um socket para aceitar ligacoes...

try {

ServerSocket s= new ServerSocket(serverPort); // fica bound ao porto 

// ciclo de ligacoes:
while (true) {
Socket data= s.accept(); // espera e aceita nova ligacao
// devolve um socket para os dados

// tratar as excepcoes no tratamento deste pedido
try {

// NOVA LEITURA:
BufferedReader breader= new BufferedReader(new InputStreamReader(data.getInputStream()));

String msg= breader.readLine();


// NOVA FORMA DE RESPONDER:
DataOutputStream sout= new DataOutputStream(data.getOutputStream());
sout.writeChars(msg);


// mostrar, por curiosidade
*///A
