package principal;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Conexion {

    private static String url;
    private static Connection connection;
    private static boolean isConnected;

    public Conexion(String url) {
        try {
            //jdbc:sqlserver://[serverName[\instanceName][:portNumber]][;property=value[;property=value]]
            //url = "jdbc:sqlserver://localhost:1433;database=ProyectoFinalCertificado_Database;user=Admin;password=Admin123;";
            this.url = url;
            this.connection = DriverManager.getConnection(url);
            isConnected = true;
        } catch (SQLException e) {
            e.printStackTrace();
            isConnected = false;
        }
    }

    public static String getUrl() {
        return url;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static boolean getIsConneted(){
        return isConnected;
    }
}
