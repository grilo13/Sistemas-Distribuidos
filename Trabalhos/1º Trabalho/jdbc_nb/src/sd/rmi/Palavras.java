package sd.rmi;

import java.util.ArrayList;
import java.util.List;

public interface Palavras extends java.rmi.Remote {  //interface java usavel em RMI com a lista de letras
    
    public ArrayList<String> consultarCentros() throws java.rmi.RemoteException;
    
    public int consultarFila(int nomeCentro) throws java.rmi.RemoteException;
    
    public String inscricaoVac(int centrosDisponiveis,String nome, String genero, int idade) throws java.rmi.RemoteException;
    
    public int contaCentros() throws java.rmi.RemoteException;
    
    public String registoVac(int codigo,String nomevac, String data) throws java.rmi.RemoteException;
    
    public String registoEfeitosSecundarios(int codigo, String efeitos) throws java.rmi.RemoteException;
    
    public ArrayList<String> listaVacinados() throws java.rmi.RemoteException;
    
    public ArrayList<String> listaEfeitosSecundarios() throws java.rmi.RemoteException;

}
