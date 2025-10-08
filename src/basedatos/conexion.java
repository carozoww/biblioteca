package basedatos;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class conexion {
    private static conexion instancia;
    private Connection connection;
    private static Properties reader = new Properties();

    static {
        try {
            System.out.println("=== 🔍 INICIANDO CONEXIÓN ===");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("Driver MySQL no encontrado", e);
            }

            // 2. CARGAR PROPIEDADES
            InputStream input = conexion.class.getClassLoader().getResourceAsStream("db.properties");
            if (input == null) {
                throw new RuntimeException("Archivo db.properties no encontrado en classpath");
            }

            reader.load(input);
            input.close();

            // 3. VERIFICAR PROPIEDADES
            String url = reader.getProperty("db.url");
            String user = reader.getProperty("db.user");
            String password = reader.getProperty("db.password");

            System.out.println("URL: " + url);
            System.out.println("User: " + user);
            System.out.println("Password: " + (password != null ? "***" : "null"));

            if (url == null || user == null) {
                throw new RuntimeException("Propiedades db.url o db.user faltantes");
            }

        } catch (Exception e) {
            System.out.println("❌ ERROR en inicialización: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error inicializando conexión", e);
        }
    }

    private conexion() throws SQLException {
        try {
            String url = reader.getProperty("db.url");
            String user = reader.getProperty("db.user");
            String password = reader.getProperty("db.password");

            System.out.println("Estableciendo conexión a: " + url);

            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("✅ CONEXIÓN ESTABLECIDA!");

        } catch (SQLException e) {
            System.out.println("❌ ERROR en conexión: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static conexion getInstancia() throws SQLException {
        if (instancia == null) {
            instancia = new conexion();
        } else if (instancia.connection == null || instancia.connection.isClosed()) {
            instancia = new conexion();
        }
        return instancia;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = reader.getProperty("db.url");
            String user = reader.getProperty("db.user");
            String password = reader.getProperty("db.password");
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }
}