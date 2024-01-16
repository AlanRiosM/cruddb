import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String dbURL = "jdbc:mysql://localhost:3306/Estudiantes";
        String dbUsername = "root";
        String dbPassword = "";
        Scanner scanner = new Scanner(System.in);

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            System.out.println("Seleccione la operación a realizar:");
            System.out.println("1. Insertar");
            System.out.println("2. Actualizar");
            System.out.println("3. Eliminar");
            System.out.println("4. Mostrar todos los registros");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Ingrese los datos del estudiante:");
                    System.out.print("ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Cédula: ");
                    int cedula = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Calificación 1: ");
                    float calificacion1 = scanner.nextFloat();
                    System.out.print("Calificación 2: ");
                    float calificacion2 = scanner.nextFloat();

                    String sqlInsert = "INSERT INTO calificaciones VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement insertStatement = conn.prepareStatement(sqlInsert);
                    insertStatement.setInt(1, id);
                    insertStatement.setString(2, nombre);
                    insertStatement.setInt(3, cedula);
                    insertStatement.setFloat(4, calificacion1);
                    insertStatement.setFloat(5, calificacion2);

                    int rowsInserted = insertStatement.executeUpdate();

                    if (rowsInserted > 0) {
                        System.out.println("Se insertó un nuevo registro");
                    }
                    break;

                case 2:
                    // Actualizar
                    System.out.println("Ingrese el ID del estudiante que desea actualizar: ");
                    int idToUpdate = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Nuevo Nombre: ");
                    String newNombre = scanner.nextLine();
                    System.out.print("Nueva Cédula: ");
                    int newCedula = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nueva Calificación 1: ");
                    float newCalificacion1 = scanner.nextFloat();
                    System.out.print("Nueva Calificación 2: ");
                    float newCalificacion2 = scanner.nextFloat();

                    String sqlUpdate = "UPDATE calificaciones SET nombre=?, cedula=?, calificacion1=?, calificacion2=? WHERE id=?";
                    PreparedStatement updateStatement = conn.prepareStatement(sqlUpdate);
                    updateStatement.setString(1, newNombre);
                    updateStatement.setInt(2, newCedula);
                    updateStatement.setFloat(3, newCalificacion1);
                    updateStatement.setFloat(4, newCalificacion2);
                    updateStatement.setInt(5, idToUpdate);

                    int rowsUpdated = updateStatement.executeUpdate();

                    if (rowsUpdated > 0) {
                        System.out.println("Se actualizó el registro con ID " + idToUpdate);
                    } else {
                        System.out.println("No se encontró el registro con ID " + idToUpdate);
                    }
                    break;

                case 3:
                    // Eliminar
                    System.out.println("Ingrese el ID del estudiante que desea eliminar: ");
                    int idToDelete = scanner.nextInt();

                    String sqlDelete = "DELETE FROM calificaciones WHERE id=?";
                    PreparedStatement deleteStatement = conn.prepareStatement(sqlDelete);
                    deleteStatement.setInt(1, idToDelete);

                    int rowsDeleted = deleteStatement.executeUpdate();

                    if (rowsDeleted > 0) {
                        System.out.println("Se eliminó el registro con ID " + idToDelete);
                    } else {
                        System.out.println("No se encontró el registro con ID " + idToDelete);
                    }
                    break;

                case 4:
                    // Mostrar todos los registros
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM calificaciones");

                    System.out.println("Registros en la tabla calificaciones:");
                    while (rs.next()) {
                        int retrievedId = rs.getInt("id");
                        String retrievedNombre = rs.getString("nombre");
                        System.out.println(retrievedId + " " + retrievedNombre);
                    }
                    break;

                default:
                    System.out.println("Opción no válida");
                    break;
            }

        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        } finally {
            scanner.close();
        }
    }
}
