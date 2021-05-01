package sd.rmi;

import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.rmi.server.*;

//PARA COMPILAR
/*
* RMI REGISTRY: rmiregistry -J-classpath -Jbin 9001
* SERVER: java -classpath bin sd.rmi.PalavrasServer 9001
* CLIENT: java -classpath  build/classes sd.rmi.PalavrasServer 9001
* 
*/


public class PalavrasImpl extends UnicastRemoteObject implements Palavras, java.io.Serializable {
	
	ConnectDB connect_db;

    // deve existir um construtor
    public PalavrasImpl(String host, String db, String user, String pw) throws java.rmi.RemoteException {
    	this.connect_db = new ConnectDB(host,db,user,pw);
        //super();
    }
    
    /* Função que é chamada e retorna numa arraylist todos os centros disponiveis para vacinamento (id do centro seguido do nome do mesmo)
     * Vai ver na base de dados referente aos centros criada
     */
    @Override
    public ArrayList<String> consultarCentros() throws java.rmi.RemoteException
    {
        ArrayList<String> arrayCentros = new ArrayList<>();
              
        try 
        {
            connect_db.connect();
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(PalavrasImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Statement stm = connect_db.getStatement();
       
        try
        {
            ResultSet rs = stm.executeQuery("select * from centro");
                
            while(rs.next())
            {
                String id = rs.getString("idcentro");
                String nome = rs.getString("nomecentro");
 
                arrayCentros.add(id);
                arrayCentros.add(nome);
            }
            rs.close();
            stm.close();
        }
        
        catch(SQLException e){
            e.printStackTrace();
            System.out.print("\nErro a aceder às tabelas pretendidas.\n");
        }
        
        connect_db.disconnect();
        return arrayCentros;

    }
    
    /* Função que recebe como argumento o id do centro que pretendemos ver o tamanho da fila de espera
     * Vai fazer um Count de todos os codigos registados nesse centro e retorna esse valor
     */
    @Override
    public int consultarFila(int id) throws java.rmi.RemoteException
    {
 
    	try 
        {
            connect_db.connect();
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(PalavrasImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Statement stm = connect_db.getStatement();
              
    	try {
            ResultSet rs = stm.executeQuery("select COUNT(codigo_registo) as sizeFila FROM fila_centro natural inner join centro WHERE fila_centro.idcentro = " + id);
            
            
            while(rs.next())
            {
            	String str = rs.getString("sizeFila");
            	int sizeFila = Integer.parseInt(str);
            	rs.close();
            	connect_db.disconnect();
            	return sizeFila;

            }
            
            rs.close();
            return -2;
        } catch (SQLException e) {
        	System.out.print("sql exception");
            return -1;
        }
    	

    }
    
    /* Função que recebe como argumentos um int CentrosDisponiveis(que provém de uma outra função que calcula o numero de centros existentes), uma vez que vamos
     * escolher um centro aleatório para inscrever o cliente que pede a inscrição. 
     * Para além disso recebe o nome da pessoa, o seu género e a sua idade.
     * Dentro da função, vamos inserir dentro da base de dados cliente1 os valores do nome, genero, idade e, idCliente e CódigoRegisto que serão dois valores 
     * para cada cliente inscrito (vai servir posteriormente para o registo da vacinação e para a lista de vacinados e de efeitos secundários reportados)
     * Também inserimos esse mesmo cliente então, num centro aleatório entre aqueles que se encontram disponiveis (o registo é feito pelo codigo registo do cliente)
     */
    
    @Override
    public String inscricaoVac(int centrosDisponiveis,String nome, String genero, int idade) throws java.rmi.RemoteException
    {
    	 String inscricaoDone = "Inscrição não efetuada por algum motivo.";
    	 
    	 int randomNum = ThreadLocalRandom.current().nextInt(1, centrosDisponiveis+1);
    	 System.out.print(randomNum);

    	 
         try 
         {
        	 connect_db.connect();
         } 
         
         catch (Exception ex) 
         {
             Logger.getLogger(Palavras.class.getName()).log(Level.SEVERE, null, ex);
         }
    
         Statement stm = connect_db.getStatement();

         try{
        	 ResultSet rs = stm.executeQuery("INSERT INTO cliente1 VALUES (DEFAULT,'"+nome+"','"+genero+"',"+idade+",DEFAULT) RETURNING codigo_registo");
        	 
        	 rs.next();

             int codigoRegisto = rs.getInt("codigo_registo");
             
             stm.executeUpdate("INSERT INTO fila_centro VALUES ("+randomNum+","+codigoRegisto+")");
    
             rs.close();
        	 stm.close();
        	 
        	 connect_db.disconnect();
        	 
        	 return "\nInscrição validada com sucesso no centro " + randomNum + ".\nGuarde o código seguinte (para registo de toma da vacina): " + codigoRegisto +  "\n";
         }
     
         catch(Exception e)
         {
                 e.printStackTrace();
                 System.out.print("Problems...");
         }
         
         connect_db.disconnect();
         return inscricaoDone;
    }
    
    /* Função que vai retornar o número de centros existentes na base de dados
     * Vai ser usada posteriormente para fazer a inscrição aleaoria de uma pessoa num centro
     */
    @Override
    public int contaCentros() throws RemoteException 
    {
    	try 
        {
            connect_db.connect();
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(PalavrasImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Statement stm = connect_db.getStatement();
              
    	try {
            ResultSet rs = stm.executeQuery("select COUNT(distinct id) as numeroCentrosExistentes FROM filasCentro3");

            while(rs.next())
            {
            	String str = rs.getString("numeroCentrosExistentes");
            	int contaCentros = Integer.parseInt(str);
            	connect_db.disconnect();
            	return contaCentros;

            }
            
            rs.close();
            return -2;
        } catch (SQLException e) {
        	System.out.print("sql exception");
            return -1;
        }
    	
    }
    
    /* Função que recebe como argumentos o código de registo do cliente, o nome da vacina tomada e a data da toma da mesma
     * Irá primeiro que tudo fazer um select para retornar o idCentro onde esse código ficou inscrito (para depois ser tirado da fila)
     * Iremos depois colocar esse cliente (pelo código) na lista de utentes vacinados (nova tabela da base de dados que conterá os utentes vacinados)
     * Depois disso iremos fazer um delete para eliminar da tabela fila_centro o utente que já foi vacinado (remover o utente do centro de inscrição)
     * Retorna uma string a dizer ao utente que foi registado com a vacina X no dia Y.
     */
   @Override
	public String registoVac(int codigo,String nomevac, String data) throws RemoteException {
    		String inscricaoDone = "Registo de vacinação falhou por alguma razão.";
  
	        try 
	        {
	       	 connect_db.connect();
	        } 
	        
	        catch (Exception ex) 
	        {
	            Logger.getLogger(Palavras.class.getName()).log(Level.SEVERE, null, ex);
	        }
	   
	        Statement stm = connect_db.getStatement();
	
	        try{
	       	 
	        ResultSet rs = stm.executeQuery("select idCentro as idCentro FROM fila_centro natural inner join centro WHERE codigo_registo = " + codigo);
	        
	        rs.next();
            String str = rs.getString("idCentro");
            int contaCentros = Integer.parseInt(str);

	       
	        stm.executeUpdate("INSERT INTO lista_vacinados VALUES ("+contaCentros+","+codigo+",'"+nomevac+"','"+data+"')");
	         
	        stm.executeUpdate("DELETE from fila_centro where codigo_registo=" + codigo);
	       
	       	stm.close();
	       	
	       	connect_db.disconnect();
	       	 
	       	return "\nRegisto de vacinação concluido com sucesso. Obrigado utente com o código "+ codigo +  " pelo registo da vacina " + nomevac + " no dia " + data + " .\n";	       	
	        }
	    
	        catch(Exception e)
	        {
	                e.printStackTrace();
	                System.out.print("Problems...");
	        }
	        
	        connect_db.disconnect();
	        return inscricaoDone;
	}
   
   /* Função que recebe como argumentos o código de registo e uma string com os efeitos que o utente teve
    * Irá fazer um insert na tabela efeitos_secundários, que tem o codigo registo e o efeito que o utente especificou
    */
   @Override
	public String registoEfeitosSecundarios(int codigo, String efeitos) throws RemoteException {
	   
       try 
       {
      	 connect_db.connect();
       } 
       
       catch (Exception ex) 
       {
           Logger.getLogger(Palavras.class.getName()).log(Level.SEVERE, null, ex);
       }
  
       Statement stm = connect_db.getStatement();

       try{
      	 
    	   stm.executeUpdate("INSERT INTO efeitos_secundarios VALUES ("+codigo+",'"+efeitos+"')");
    	   
      	   stm.close();
      	   
      	 connect_db.disconnect();
      	 
      	 return "\nObrigado pelo seu feedback.\nCódigo usado: " + codigo +  " com os seguintes efeitos: " + efeitos + ".\n";
      	
       }
   
       catch(Exception e)
       {
               e.printStackTrace();
               System.out.print("Problems...");
       }
       
       connect_db.disconnect();
       return "Registo não efeutado por alguma razão.";
	}
   	
   /* Função que retorna um ArrayList com o numero de vacinas de uma certa vacina existente na tabela lista_vacinados
    * Número de vacinas mais nome da vacina para todas aquelas que existam
    */
   @Override
	public ArrayList<String> listaVacinados() throws RemoteException {
	   ArrayList<String> arrayListaVacinados = new ArrayList<>();
       
       try 
       {
           connect_db.connect();
       } 
       catch (Exception ex) 
       {
           Logger.getLogger(PalavrasImpl.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       Statement stm = connect_db.getStatement();
      
       try
       {
           ResultSet rs = stm.executeQuery("select vacina, count(vacina) as numero_vacinas\n"
           		+ "from lista_vacinados\n"
           		+ "group by vacina");
               
           while(rs.next())
           {
               String nomeVacina = rs.getString("vacina");
               String numeroVacinas = rs.getString("numero_vacinas");

               arrayListaVacinados.add(numeroVacinas);
               arrayListaVacinados.add(nomeVacina);
           }
           rs.close();
           stm.close();
       }
       
       catch(Exception e){
           e.printStackTrace();
           System.out.print("Problems...");
       }
       
       connect_db.disconnect();
       return arrayListaVacinados;
	}
   
   
   /* Função que retorna um ArrayList com o numero de efeitos secundários existentes para cada vacina existente
    * fazendo um select e um count dos codigos de registo dentro da tabela efeitos_secundários e lista_vacinados conseguimos fazê-lo
    */
   @Override
	public ArrayList<String> listaEfeitosSecundarios() throws RemoteException {
	   ArrayList<String> arrayEfeitosSecundarios = new ArrayList<>();
       
       try 
       {
           connect_db.connect();
       } 
       catch (Exception ex) 
       {
           Logger.getLogger(PalavrasImpl.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       Statement stm = connect_db.getStatement();
      
       try
       {
           ResultSet rs = stm.executeQuery("with X as (select vacina, count(distinct codigo_registo) as numero_casos\n"
           		+ "from lista_vacinados NATURAL join efeitos_secundarios\n"
           		+ "           where efeitos != 'Null'\n"
           		+ "group by vacina)\n"
           		+ "\n"
           		+ "select vacina, numero_casos\n"
           		+ "from X");
               
           while(rs.next())
           {
               String nomeVacina = rs.getString("vacina");
               String numeroEfeitos = rs.getString("numero_casos");
               
               arrayEfeitosSecundarios.add(numeroEfeitos);
               arrayEfeitosSecundarios.add(nomeVacina);
               
           }
           rs.close();
           stm.close();
       }
       
       catch(Exception e){
           e.printStackTrace();
           System.out.print("Problems...");
       }
       
       connect_db.disconnect();
       return arrayEfeitosSecundarios;
	}
}
