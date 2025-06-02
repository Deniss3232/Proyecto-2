import java.util.Scanner;
import java.util.List;
import java.util.UUID;

public class App {
    public static void main(String[] args) {
        ConexionNeo4j conexion = new ConexionNeo4j("bolt://localhost:7687", "neo4j", "password");
        RepositorioVehiculo repo = new RepositorioVehiculo(conexion);
        ServicioRecomendacion servicio = new ServicioRecomendacion(conexion);

        // Limpiar base de datos antes de insertar
        repo.limpiarVehiculos();

        // Vehículos simulados
        Vehiculo v1 = new Vehiculo(UUID.randomUUID().toString(), "Toyota", "Hilux", "pickup", 2022, 12.0, "campo");
        Vehiculo v2 = new Vehiculo(UUID.randomUUID().toString(), "Kia", "Rio", "sedan", 2021, 18.0, "ciudad");
        Vehiculo v3 = new Vehiculo(UUID.randomUUID().toString(), "Subaru", "Forester", "SUV", 2023, 14.0, "mixto");

        repo.guardarVehiculo(v1);
        repo.guardarVehiculo(v2);
        repo.guardarVehiculo(v3);

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== SISTEMA DE RECOMENDACIÓN DE VEHÍCULOS ===");
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("¿Dónde usará el vehículo? (ciudad / campo / mixto): ");
        String uso = scanner.nextLine().toLowerCase().trim();

        System.out.print("¿Qué tipo de vehículo prefiere? (sedan / SUV / pickup): ");
        String tipo = scanner.nextLine().toLowerCase().trim();

        Usuario usuario = new Usuario(nombre, uso, tipo);
        List<Vehiculo> recomendados = servicio.recomendarVehiculos(usuario);

        System.out.println("\n🚗 Recomendaciones para " + nombre + ":");
        if (recomendados.isEmpty()) {
            System.out.println("No se encontraron vehículos que coincidan con sus preferencias.");
        } else {
            for (Vehiculo v : recomendados) {
                System.out.println("- " + v);
            }
        }

        conexion.cerrar();
        scanner.close();
    }
}
