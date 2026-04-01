import java.sql.*;
import java.util.Scanner;

public class App {
    private static DBAuth db = new DBAuth();

    public static void main(String[] args) throws SQLException {
        int op;
        Scanner sc = new Scanner(System.in);
        Connection conn = db.connect(1);
        do {
            System.out.println("\n------------------------------------------------------");
            System.out.println("            MENU DE GESTION DE sistemas");
            System.out.println("------------------------------------------------------");
            System.out.println("1. Crear Sistema");
            System.out.println("2. Eliminar Sistema");
            System.out.println("3. Modificar Sistema");
            System.out.println("4. Buscar Sistema");
            System.out.println("5. Visualizar sistemas");
            System.out.println("0. Salir");
            System.out.print("Ingresa una opcion: ");
           
            op = sc.nextInt();
            sc.nextLine(); 

            switch(op){
                case 1: create(sc, conn); break;
                case 2: delete(sc, conn); break;
                case 3: edit(sc, conn); break;
                case 4: search(sc, conn); break;
                case 5: list(conn); break;
                case 0: conn.close();break;
                default: System.out.println("La opcion no es valida.");
            }
        } while (op != 0);
    }  
       
    public static void create(Scanner sc, Connection conn) {
        System.out.println("\n--- Nuevo Sistema ---");
        System.out.print("Nombre: "); String nom = sc.nextLine();
        System.out.print("Autor: "); String aut = sc.nextLine();
        System.out.print("Descripción: "); String desc = sc.nextLine();
        System.out.print("Año: "); int anio = sc.nextInt();
        System.out.print("ID Oficina: "); int idOfi = sc.nextInt();

        String sql = "INSERT INTO sistemas (nombre, autor, descripcion, año, oficina_id) VALUES (?, ?, ?, ?, ?)";
       
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, nom);
            pstm.setString(2, aut);
            pstm.setString(3, desc);
            pstm.setInt(4, anio);
            pstm.setInt(5, idOfi);
            pstm.executeUpdate();
            System.out.println("Sistema guardado.");
        } catch (SQLException e) {
            System.out.println("Error al crear: " + e.getMessage());
        }
    }

    public static void delete(Scanner sc, Connection conn) {
        System.out.print("\nIngrese el ID del sistema a eliminar: ");
        int id = sc.nextInt();
        String sql = "DELETE FROM sistemas WHERE id = ?";

        try { 
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            int rows = pstm.executeUpdate();
            if (rows > 0) System.out.println("Sistema eliminado.");
            else System.out.println("No se encontró un sistema con ese ID.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }

    public static void edit(Scanner sc, Connection conn) {
        System.out.print("\nID del sistema a modificar (Ingrese - para no realizar cambios): ");
        String input = sc.nextLine(); 
        if ("-".equals(input)) {
            System.out.println("No se realizaron cambios.");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("ID debe ser un numero.");
            return;
        }
        System.out.print("Nuevo Nombre (o -): "); String nom = sc.nextLine();
        System.out.print("Nueva Descripción (o -): "); String desc = sc.nextLine();
        if ("-".equals(nom) && "-".equals(desc)) {
            System.out.println("No se realizaron cambios.");
        }
        String sql;
        try {
            if ("-".equals(nom)) {
                sql = "UPDATE sistemas SET descripcion = ? WHERE ID = ?";
            } else if ("-".equals(desc)) {
                sql = "UPDATE sistemas SET nombre = ? WHERE ID = ?";
            } else {
                sql = "UPDATE sistemas SET nombre = ?, descripcion = ? WHERE ID = ?";
            }
            PreparedStatement pstm = conn.prepareStatement(sql);
            if ("-".equals(nom)) {
                pstm.setString(1, desc);
                pstm.setInt(2, id);
            } else if ("-".equals(desc)) {
                pstm.setString(1, nom);
                pstm.setInt(2, id);
            } else {
                pstm.setString(1, nom);
                pstm.setString(2, desc);
                pstm.setInt(3, id);
            }
            int rows = pstm.executeUpdate();
            if (rows > 0) System.out.println("Datos actualizados.");
            else System.out.println("No se encontró el ID.");

        } catch (SQLException e) {
            System.out.println("Error al editar: " + e.getMessage());
        }
    }

    public static void search(Scanner sc, Connection conn) {
        System.out.print("\nIngrese el nombre del sistema a buscar: ");
        String nom = sc.nextLine();
        String sql = "SELECT * FROM sistemas WHERE nombre LIKE ?";

        try { 
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, "%" + nom + "%");
            ResultSet rs = pstm.executeQuery();
            System.out.println("\n                                          SISTEMAS ENCONTRADOS");
            System.out.println("--------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-3s | %-15s | %-15s | %-50s | %s\n","ID", "NOMBRE", "AUTOR", "DESCRIPCION", "AÑO");
            System.out.println("--------------------------------------------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-3d | %-15s | %-15s | %-50s | %d\n",
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("autor"),
                    rs.getString("descripcion"),
                    rs.getInt("año"));
            }
        } catch (SQLException e) {
            System.out.println("Error en búsqueda: " + e.getMessage());
        }
    }

    public static void list(Connection conn) {
        String sql = "SELECT * FROM sistemas";
        try { 
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("\n                                          LISTADO DE SISTEMAS");
            System.out.println("--------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-3s | %-15s | %-15s | %-50s | %s\n","ID", "NOMBRE", "AUTOR", "DESCRIPCION", "AÑO");
            System.out.println("--------------------------------------------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-3d | %-15s | %-15s | %-50s | %d\n",
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("autor"),
                    rs.getString("descripcion"),
                    rs.getInt("año"));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
    }
}
