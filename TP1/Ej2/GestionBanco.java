import java.sql.*;

public class GestionBanco {

    Connection conn = new DBAuth().connect(2);

    public void depositar(int cuenta, double importe) throws SQLException {
        String queryCheck = "SELECT 1 FROM CUENTAS WHERE Cuenta = ?";
        String queryInsert = "INSERT INTO MOVIMIENTOS (Cuenta, Mov, Importe) VALUES (?, 'D', ?)";
        String queryUpdate = "UPDATE CUENTAS SET Saldo = Saldo + ? WHERE Cuenta = ?";

        try {
            conn.setAutoCommit(false);  // iniciar la transaccion
            PreparedStatement pstmCheck = conn.prepareStatement(queryCheck);
            PreparedStatement pstmInsert = conn.prepareStatement(queryInsert);
            PreparedStatement pstmUpdate = conn.prepareStatement(queryUpdate);
            // verificar cuenta
            pstmCheck.setInt(1, cuenta);
            ResultSet rs = pstmCheck.executeQuery();
            if (!rs.next()) {
                throw new SQLException("La cuenta " + cuenta + " no existe.");
            }
            // almacenar movimiento
            pstmInsert.setInt(1, cuenta);
            pstmInsert.setDouble(2, importe);
            pstmInsert.executeUpdate();
            // actualizar saldo
            pstmUpdate.setDouble(1, importe);
            pstmUpdate.setInt(2, cuenta);
            pstmUpdate.executeUpdate();
            conn.commit();
            System.out.println("Deposito completado. Se acreditaron $" + importe + " en la cuenta " + cuenta);
        }catch (SQLException e) {
            conn.rollback();  // no guardar
            System.out.println("Error en el deposito: " + e.getMessage());
        }
    }

    public void extraer(int cuenta, double importe) throws SQLException {
        String sqlCheck = "SELECT Saldo FROM CUENTAS WHERE Cuenta = ?";
        String sqlInsert = "INSERT INTO MOVIMIENTOS (Cuenta, Mov, Importe) VALUES (?, 'E', ?)";
        String sqlUpdate = "UPDATE CUENTAS SET Saldo = Saldo - ? WHERE Cuenta = ?";

        try { 
            conn.setAutoCommit(false); // iniciar la transaccion
            PreparedStatement pstmCheck = conn.prepareStatement(sqlCheck);
            PreparedStatement pstmInsert = conn.prepareStatement(sqlInsert);
            PreparedStatement pstmUpdate = conn.prepareStatement(sqlUpdate);
            pstmCheck.setInt(1, cuenta);
            //veriificar cuenta
            ResultSet rs = pstmCheck.executeQuery();
            if (!rs.next()) {
                throw new SQLException("La cuenta " + cuenta + " no existe.");
            }
            //verificar saldo
            double saldoActual = rs.getDouble("Saldo");
            if (importe > saldoActual) {
                throw new SQLException("Fondos insuficientes. Saldo actual: $" + saldoActual);
            }
            pstmInsert.setInt(1, cuenta);
            pstmInsert.setDouble(2, importe);
            pstmInsert.executeUpdate();
            pstmUpdate.setDouble(1, importe);
            pstmUpdate.setInt(2, cuenta);
            pstmUpdate.executeUpdate();
            conn.commit();
            System.out.println("Extraccion completada. Se debitaron $" + importe + " de la cuenta " + cuenta);

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Error en la transaccion: " + e.getMessage());
            
         }
    }

    // pruebas
    public static void main(String[] args) {
        try {
            GestionBanco banco = new GestionBanco();
            System.out.println("Prueba 1: Depósito completado");
            banco.depositar(1, 1000);
            System.out.println("\nPrueba 2: Extracción exitosa");
            banco.extraer(1, 1000);
            System.out.println("\nPrueba 3: Extracción sin saldo (rollback)");
            banco.extraer(1, 10000);
            System.out.println("\nPrueba 4: Cuenta inexistente (rollback)");
            banco.depositar(3, 1000);
        } catch (SQLException e) {
            System.out.println("Error en la transaccion: " + e.getMessage());
        }
    }
}