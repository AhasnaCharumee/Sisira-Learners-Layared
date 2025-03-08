package lk.ijse.gdse72.sisiralearners.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dBConnection;
    private Connection connection;

    private DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/DrivingSchoolDB", "root", "mysql");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error while establishing database connection: " + e.getMessage(), e);
        }
    }

    public static synchronized DBConnection getInstance() {
        if (dBConnection == null) {
            dBConnection = new DBConnection();
        }
        return dBConnection;
    }

    public  Connection getConnection() {
        return connection;
    }

}
