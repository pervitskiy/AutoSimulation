package utils;
import java.sql.*;
import java.text.SimpleDateFormat;

public class workDB {
    private static final String url = "jdbc:mysql://localhost:3306/simulation?useSSL=false";
    private static final String user = "root";
    private static final String password = "root1234";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;

    public synchronized static void add(String message, Timestamp timestamp ) {
        /*
        try {
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
            String insertTableSql = String.format("INSERT INTO record(message, time) values('%s', '%s')", message, time);
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            stmt.execute(insertTableSql);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything * }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything * }
        }

         */
    }

}

