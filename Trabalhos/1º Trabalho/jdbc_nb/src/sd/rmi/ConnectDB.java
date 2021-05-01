package sd.rmi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.sql.*;

public class ConnectDB {

    private String PG_HOST;//="localhost";
    private String PG_DB;//="l43012";
    private String USER;//="l43012";
    private String PWD;//="password";

    Connection con = null;
    Statement stmt = null;
    
    public ConnectDB(String host, String db, String user, String pw) {
        PG_HOST=host;
        PG_DB= db;
        USER=user;
        PWD= pw;
    }

    public void connect() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            // url = "jdbc:postgresql://host:port/database",
            con = DriverManager.getConnection("jdbc:postgresql://" + PG_HOST + ":5432/" + PG_DB,
                    USER,
                    PWD);

            stmt = con.createStatement();
            
            if (stmt != null) {
                System.out.println("Ligados à base de dados.");
            } else {
                System.out.println("Erro na ligação.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problemas na conexão.");
        }
    }

    public void disconnect() {    // importante: fechar a ligacao 'a BD
        try {
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Statement getStatement() {
        return stmt;
    }

}
