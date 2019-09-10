package db;

import exceptions.ReturnConnectionFailedException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionPool {
    private static ConnectionPool instance = new ConnectionPool();// instance limit the creation of this obj to 1
    private ArrayList<Connection> connections = new ArrayList<>();
    private static final int MAX_CONNECTIONS = 5;

    private ConnectionPool() {
        try {
            for (int i = 0; i < MAX_CONNECTIONS; i++) {
                Connection con;
                /**
                 * no driver needed JDBC handles it by itself
                 */
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coupon?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                connections.add(con);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public synchronized Connection getConnection() {
        if (connections.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Brutal wakeup! CHECK WHY!!!");
            }
        }
        return connections.get(0);
    }

    public void returnConnection(Connection con) throws ReturnConnectionFailedException {
        connections.add(con);
        if (!connections.add(con)) {
            throw new ReturnConnectionFailedException();
        }
    }

    public void closeAllConnections() {
        System.out.println("All connections terminated");
        for (int i = 0; i < MAX_CONNECTIONS; i++) {
            Connection c = connections.get(i);
            try {
                c.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
