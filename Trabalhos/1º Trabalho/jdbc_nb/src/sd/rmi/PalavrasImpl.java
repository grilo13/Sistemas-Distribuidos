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

/**
 * CLASSE DO OBJETO REMOTO Tem a parte funcional... a implementação das
 * operações do serviço.
 */

public class PalavrasImpl extends UnicastRemoteObject implements Palavras, java.io.Serializable {
	
	ConnectDB connect_db = new ConnectDB();

    // deve existir um construtor
    public PalavrasImpl() throws java.rmi.RemoteException {
        super();
    }
    
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
        
        catch(Exception e){
            e.printStackTrace();
            System.out.print("Problems...");
        }
        
        connect_db.disconnect();
        return arrayCentros;

    }
    
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
            	return sizeFila;

            }
            
            rs.close();
            return -2;
        } catch (SQLException e) {
        	System.out.print("sql exception");
            return -1;
        }
    	

    }
    
    public String inscricaoVac(int centrosDisponiveis,String nome, String genero, int idade) throws java.rmi.RemoteException
    {
    	 String inscricaoDone = "Inscrição efetuada com sucesso.";
    	 
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
        	 
        	 //ResultSet rs = stm.executeQuery("select COUNT(id) as sizeFila FROM filasCentro3");
        	 //stm.executeUpdate("INSERT INTO cliente1 VALUES (DEFAULT,'"+nome+"','"+genero+"',"+idade+",DEFAULT)");

        	 //ResultSet rs = stm.executeQuery("INSERT INTO fila_centro VALUES ("+randomNum+", '"+nome+"') RETURNING codigoResposta");
        	 ResultSet rs = stm.executeQuery("INSERT INTO cliente1 VALUES (DEFAULT,'"+nome+"','"+genero+"',"+idade+",DEFAULT) RETURNING codigo_registo");
        	 
        	 rs.next();

             int codigoRegisto = rs.getInt("codigo_registo");
             
             stm.executeUpdate("INSERT INTO fila_centro VALUES ("+randomNum+","+codigoRegisto+")");
    
             rs.close();
        	 stm.close();
        	 
        	 return "\nInscrição validada com sucesso. Guarde o código seguinte (para registo de toma da vacina): " + codigoRegisto +  "\n";
        	 
         	 //rs = stm.executeQuery("INSERT INTO filasCentro3 VALUES(DEFAULT, " + randomNum + ",'" + nome + "'));
        	
         }
     
         catch(Exception e)
         {
                 e.printStackTrace();
                 System.out.print("Problems...");
         }
         
         connect_db.disconnect();
         return inscricaoDone;
    }
    
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
            	return contaCentros;

            }
            
            rs.close();
            return -2;
        } catch (SQLException e) {
        	System.out.print("sql exception");
            return -1;
        }
    	
    }
    
   @Override
	public String registoVac(int codigo,String nomevac, String data) throws RemoteException {
    		String inscricaoDone = "Inscrição efetuada com sucesso.";
  
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
	       	 
	       	 //ResultSet rs = stm.executeQuery("select COUNT(id) as sizeFila FROM filasCentro3");
	        ResultSet rs = stm.executeQuery("select idCentro as idCentro FROM fila_centro natural inner join centro WHERE codigo_registo = " + codigo);
	        	
	        //while(rs.next())
            //{
	        rs.next();
            String str = rs.getString("idCentro");
            int contaCentros = Integer.parseInt(str);

            //}
	       
	        stm.executeUpdate("INSERT INTO lista_vacinados VALUES ("+contaCentros+","+codigo+",'"+nomevac+"','"+data+"')");
	       
	       	 stm.close();
	       	 
	       	 return "\nRegisto de vacinação concluido com sucesso. Obrigado "/* + nome + */+ " pelo registo da vacina " + nomevac+ "\n";
	       	 
	        	 //rs = stm.executeQuery("INSERT INTO filasCentro3 VALUES(DEFAULT, " + randomNum + ",'" + nome + "'));
	       	
	        }
	    
	        catch(Exception e)
	        {
	                e.printStackTrace();
	                System.out.print("Problems...");
	        }
	        
	        connect_db.disconnect();
	        return inscricaoDone;
	}
   
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
      	 
      	 return "\nObrigado pelo seu feedback.\nCódigo usado: " + codigo +  " com os seguintes efeitos: " + efeitos + ".\n";
      	 
       	 //rs = stm.executeQuery("INSERT INTO filasCentro3 VALUES(DEFAULT, " + randomNum + ",'" + nome + "'));
      	
       }
   
       catch(Exception e)
       {
               e.printStackTrace();
               System.out.print("Problems...");
       }
       
       connect_db.disconnect();
       return "Nada a acresentar";
	}
   	
   
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

               arrayListaVacinados.add(nomeVacina);
               arrayListaVacinados.add(numeroVacinas);
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

               arrayEfeitosSecundarios.add(nomeVacina);
               arrayEfeitosSecundarios.add(numeroEfeitos);
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
   
   
   
   
   
   
   
   

	@Override
	public String primeiraPalavra(String s) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> divide(String s) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

    // ... e a implementação de
    // cada método declarado na interface remota
    
    
    /**
     * devolve a primeira palavra da frase recebida
     *
     * @param s
     * @return
     * @throws java.rmi.RemoteException
     */
    /*public String primeiraPalavra(String s) throws java.rmi.RemoteException {
        System.err.println("invocação primeiraPalavra() com: " + s);
        String s2 = s.trim();  // remove espacos no inicio ou fim
        int k = s2.indexOf(" ");  // indice de " "
        if (k < 0) // nao tem espacos
        {
            return s2;
        } else {
            return s2.substring(0, k);  // apenas a 1a palavra
        }
    }

    
     * devolve um vector com cada palavra da expressão recebida
     *
     * @param s
     * @return
     * @throws java.rmi.RemoteException
     
    public java.util.List<String> divide(String s) throws java.rmi.RemoteException {
        System.err.println("invocação divide() com: " + s);
        java.util.LinkedList<String> v = new java.util.LinkedList<>();
        String s2 = s.trim();

        String[] s2pals = s2.split(" ");

        for (int i = 0; i < s2pals.length; i++) {
            v.add(s2pals[i]);       // adiciona a palavra i
        }
        return v;
    }*/

}
