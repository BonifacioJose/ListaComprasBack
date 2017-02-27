package br.com.listacomprasback.genericdao.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bonifácio
 */
public abstract class DatabaseUtil {

    public static Connection getConnection() {
        return getConnection("listacompras");
    }

    public static Connection getConnection(String banco) {
        Connection conn = null;

        try {
            DriverManager.registerDriver(
                    (Driver) Class.forName("org.postgresql.Driver").newInstance());

            conn = DriverManager.getConnection("jdbc:postgresql://" + "localhost:5432" + "/" + banco, "postgres", "sql");

        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DatabaseUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            System.out.println("Problemas ao conectar no banco de dados " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("O driver não foi configurado corretamente");
        }
        return conn;
    }
}
