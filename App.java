import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        ConexionNeo4j conexion = new ConexionNeo4j("bolt://localhost:7687", "neo4j", "password");
        RepositorioVehiculo repositorio = new RepositorioVehiculo(conexion);
        ServicioRecomendacion servicio = new ServicioRecomendacion(conexion);

        List<Vehiculo> vehiculos = new ArrayList<>();

        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Toyota", "Hilux 2.8L 4x4 Diesel", "pickup", 2022, 11.8, "campo"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Kia", "Rio 1.6L Automático", "sedán", 2021, 18.4, "ciudad"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Subaru", "Forester 2.5 AWD CVT", "SUV", 2023, 13.9, "mixto"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Ford", "F-150 XLT 3.5 EcoBoost", "pickup", 2021, 9.8, "campo"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Hyundai", "Elantra 2.0L GLS", "sedán", 2020, 17.2, "ciudad"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Mazda", "CX-5 Touring AWD", "SUV", 2022, 12.9, "mixto"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Chevrolet", "Silverado 5.3L V8", "pickup", 2023, 8.9, "campo"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Nissan", "Sentra SR CVT", "sedán", 2019, 16.5, "ciudad"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Jeep", "Compass Limited 4x4", "SUV", 2022, 12.3, "mixto"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Volkswagen", "Jetta GLI 2.0 Turbo", "sedán", 2021, 18.2, "ciudad"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Mitsubishi", "L200 Katana CR Diésel", "pickup", 2021, 10.9, "campo"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Honda", "Civic Touring 1.5 Turbo", "sedán", 2022, 18.9, "ciudad"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Renault", "Duster Iconic CVT 1.6", "SUV", 2023, 12.7, "mixto"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Isuzu", "D-Max 3.0L Turbo Diésel", "pickup", 2022, 10.2, "campo"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Peugeot", "301 Active 1.6", "sedán", 2020, 16.8, "ciudad"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Ford", "Escape SE Hybrid", "SUV", 2021, 18.5, "mixto"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Ram", "1500 Laramie HEMI V8", "pickup", 2023, 8.2, "campo"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Suzuki", "Ciaz GLX 1.4L", "sedán", 2021, 19.0, "ciudad"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Chevrolet", "Trailblazer LT AWD", "SUV", 2023, 13.1, "mixto"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Nissan", "Frontier LE Diésel 4x4", "pickup", 2022, 11.2, "campo"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Hyundai", "Accent Hatchback 1.6", "sedán", 2020, 17.8, "ciudad"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Toyota", "RAV4 LE Hybrid AWD", "SUV", 2022, 17.2, "mixto"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Mazda", "BT-50 Pro Diésel 3.2", "pickup", 2021, 9.5, "campo"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Volkswagen", "Virtus Comfortline", "sedán", 2021, 18.6, "ciudad"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Kia", "Seltos EX 2.0L CVT", "SUV", 2023, 14.0, "mixto"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Great Wall", "Wingle 7 4x4 Diésel", "pickup", 2023, 11.4, "campo"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Changan", "Alsvin Elite 1.5L", "sedán", 2022, 19.3, "ciudad"));
        vehiculos.add(new Vehiculo(UUID.randomUUID().toString(), "Ford", "Edge Titanium AWD", "SUV", 2021, 13.0, "mixto"));

        for (Vehiculo v : vehiculos) {
            repositorio.guardarVehiculo(v);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("🧠 Bienvenido al sistema de recomendación de vehículos\n");

        System.out.print("1️⃣ ¿En qué entorno usará el vehículo? (ciudad/campo/mixto): ");
        String entorno = scanner.nextLine().toLowerCase();

        System.out.print("2️⃣ ¿Prefiere un vehículo con buen consumo de combustible? (sí/no): ");
        String eficiente = scanner.nextLine().toLowerCase();

        System.out.print("3️⃣ ¿Viaja con familia o suele ir solo? (familia/solo): ");
        String compania = scanner.nextLine().toLowerCase();

        System.out.print("4️⃣ ¿Necesita espacio de carga (maletero o cama)? (sí/no): ");
        String carga = scanner.nextLine().toLowerCase();

        List<Vehiculo> recomendados = servicio.recomendarVehiculos(entorno);
        List<Vehiculo> filtrados = new ArrayList<>();

        for (Vehiculo v : recomendados) {
            boolean coincide = true;

            if (eficiente.equals("sí") && v.getConsumo() < 15.0) {
                coincide = false;
            }

            if (compania.equals("familia") && v.getTipo().equals("pickup")) {
                coincide = false;
            }

            if (carga.equals("sí") && v.getTipo().equals("sedán")) {
                coincide = false;
            }

            if (coincide) {
                filtrados.add(v);
            }
        }

        System.out.println("\n🚗 Vehículos recomendados para usted:");
        if (filtrados.isEmpty()) {
            System.out.println("Lo sentimos, no encontramos coincidencias exactas. Aquí algunas opciones similares:");
            for (Vehiculo v : recomendados) {
                System.out.println(v);
            }
        } else {
            for (Vehiculo v : filtrados) {
                System.out.println(v);
            }
        }

        conexion.cerrar();
        scanner.close();
    }
}

