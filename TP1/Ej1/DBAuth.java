import java.sql.*;
class DBAuth {
    
    private final String URL = "jdbc:mysql://maglev.proxy.rlwy.net:14506/tp"; //RAILWAY DB EJ1
    private final String URL2 = "jdbc:mysql://maglev.proxy.rlwy.net:14506/tp1_ej2"; //RAILWAY DB EJ2
    private final String USER = "root";
    private final String PW = "wTDMjgCDtrtamEhuTFZnUZVBogQdAJuW";

    public Connection connect(int db) {
        try {
            if (db == 1) return DriverManager.getConnection(URL, USER, PW);
            else return DriverManager.getConnection(URL2, USER, PW);} 
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}

