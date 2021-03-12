package principal;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Conexion {

    private String url;
    private Connection connection;

    public Conexion(String url) {
        try {
            //jdbc:sqlserver://[serverName[\instanceName][:portNumber]][;property=value[;property=value]]
            //url = "jdbc:sqlserver://localhost:1433;database=ProyectoFinalCertificado_Database;user=Admin;password=Admin123;";
            this.url = url;
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
