package pizzaria.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;

public class ConnectionFactory {
    private static Properties properties;

    private ConnectionFactory() {}

    public static Connection getConnection() throws SQLException, IOException {
        readProperties();
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String pwd = properties.getProperty("db.pwd");

        System.out.println("Tentando conectar com:");
        System.out.println("URL: " + url);
        System.out.println("User: " + user);

        // Adicione parâmetros de conexão explícitos
        Properties connProps = new Properties();
        connProps.setProperty("user", user);
        connProps.setProperty("password", pwd);
        connProps.setProperty("ssl", "false");

        return DriverManager.getConnection(url, connProps);
    }

    private static void readProperties() throws IOException {
        if (properties == null) {
            Properties props = new Properties();
            FileInputStream file = new FileInputStream(
                    "src/main/resources/DataBase.properties");
            props.load(file);
            properties = props;
        }
    }
}