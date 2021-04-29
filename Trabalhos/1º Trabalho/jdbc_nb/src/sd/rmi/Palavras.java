package sd.rmi;

import java.util.ArrayList;
import java.util.List;

public interface Palavras extends java.rmi.Remote {  //interface java usavel em RMI com a lista de letras

    public String primeiraPalavra(String s) throws java.rmi.RemoteException;

    public List<String> divide(String s) throws java.rmi.RemoteException;
    
    public ArrayList<String> consultarCentros() throws java.rmi.RemoteException;
    
    public int consultarFila(int id) throws java.rmi.RemoteException;
    
    public String inscricaoVac(int centrosDisponiveis,String nome, String genero, int idade) throws java.rmi.RemoteException;
    
    public int contaCentros() throws java.rmi.RemoteException;
    
    public String registoVac(int codigo, String nome,String nomevac, String data, String tipo) throws java.rmi.RemoteException;

}
