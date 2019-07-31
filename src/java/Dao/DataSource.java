package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private Connection con;
    
    public DataSource(String bd) {
        String url, user, pass;
        url = "jdbc:mysql://localhost:3306/" + bd;
        user = "root";
        pass = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            System.err.println("Falha na Conexao com o BD");
            System.err.println(e);
            System.exit(1);
        } catch (ClassNotFoundException e) {
            System.err.println("Falha Classe não encontrada");
            System.out.println(e);
        }
    }
    
    public Connection getCon() {
        return con;
    }
}
