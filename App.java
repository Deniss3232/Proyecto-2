import java.util.Scanner;
import java.util.List;
import java.util.UUID;

public class App {
    public static void main(String[] args) {
        ConexionNeo4j conexion = new ConexionNeo4j("bolt://localhost:7687", "neo4j", "password");
        RepositorioVehiculo repo = new RepositorioVehiculo(conexion);
        ServicioRecomendacion servicio = new ServicioRecomendacion(conexion);

        //  Solo inicializar datos si no existen (PERSISTENCIA)
        if (!repo.existenDatos()) {
            System.out.println(" Inicializando datos por primera vez...");
            inicializarDatos(repo);
        } else {
            System.out.println("  - Usando datos existentes en Neo4j para una mejor experiencia.");
        }

        Scanner scanner = new Scanner(System.in);
        
        
        
        System.out.println("=== SISTEMA DE RECOMENDACIÓN DE VEHÍCULOS ===");
        System.out.print("\n Ingrese su nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("\n ¿Dónde usará el vehículo? (ciudad / campo / mixto): ");
        String uso = scanner.nextLine().toLowerCase().trim();

        System.out.print("¿Qué tipo de vehículo prefiere? (sedan / suv / pickup): ");
        String tipo = scanner.nextLine().toLowerCase().trim();

        System.out.print("¿Cuál es el año mínimo del vehículo que desea?: ");
        int anioMinimo = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        Usuario usuario = new Usuario(nombre, uso, tipo);
        
        // Guardar usuario si no existe
        repo.guardarUsuario(usuario);
        
        // Guardar la búsqueda realizada
        repo.guardarBusqueda(nombre, uso, tipo, anioMinimo);

        List<Vehiculo> recomendados = servicio.recomendarVehiculos(usuario, anioMinimo);

        System.out.println("\n Recomendaciones técnicas para " + nombre + ":");
        if (recomendados.isEmpty()) {
            System.out.println("No se encontraron vehículos que cumplan con los filtros.");
        } else {
            for (int i = 0; i < recomendados.size(); i++) {
                System.out.println((i + 1) + ". " + recomendados.get(i));
            }
        }

        List<Vehiculo> colaborativos = servicio.recomendarPorSimilitud(usuario);
        System.out.println("\n Elecciones de usuarios similares a ti (uso: " + uso + ", tipo: " + tipo + "):");
        if (colaborativos.isEmpty()) {
            System.out.println("No hay recomendaciones colaborativas disponibles para tu perfil.");
            System.out.println("Tip: Esto puede ocurrir si no hay otros usuarios con preferencias similares.");
        } else {
            int startIndex = recomendados.size();
            for (int i = 0; i < colaborativos.size(); i++) {
                System.out.println((startIndex + i + 1) + ". " + colaborativos.get(i));
            }
        }

        // Permitir al usuario seleccionar un vehículo
        List<Vehiculo> todosVehiculos = new java.util.ArrayList<>(recomendados);
        todosVehiculos.addAll(colaborativos);

        if (!todosVehiculos.isEmpty()) {
            System.out.println("\n ¿Desea seleccionar algún vehículo? (s/n): ");
            String respuesta = scanner.nextLine().toLowerCase().trim();

            if (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí")) {
                System.out.print("Ingrese el número del vehículo que desea seleccionar: ");
                try {
                    int seleccion = scanner.nextInt();
                    scanner.nextLine(); // Consumir salto de línea

                    if (seleccion >= 1 && seleccion <= todosVehiculos.size()) {
                        Vehiculo vehiculoSeleccionado = todosVehiculos.get(seleccion - 1);
                        
                        System.out.println("\n Ha seleccionado: " + vehiculoSeleccionado);
                        System.out.println("¿Confirma su selección? (s/n): ");
                        String confirmacion = scanner.nextLine().toLowerCase().trim();

                        if (confirmacion.equals("s") || confirmacion.equals("si") || confirmacion.equals("sí")) {
                            // Guardar la selección en Neo4j
                            repo.guardarSeleccion(nombre, vehiculoSeleccionado);
                            System.out.println(" ¡Excelente elección! Su selección ha sido guardada.");
                        } else {
                            System.out.println("Selección cancelada.");
                        }
                    } else {
                        System.out.println(" Número de selección inválido.");
                    }
                } catch (Exception e) {
                    System.out.println(" Por favor ingrese un número válido.");
                }
            }
        }

        System.out.println("\n¡Gracias por usar nuestro sistema de recomendación!");
        conexion.cerrar();
        scanner.close();
    }

    // Método para inicializar todos los datos de ejemplo
    private static void inicializarDatos(RepositorioVehiculo repo) {
        // Crear vehículos
        Vehiculo v1 = new Vehiculo(UUID.randomUUID().toString(), "Toyota", "RAV4 Hybrid XLE", "suv", 2022, 18.5, "mixto");
        Vehiculo v2 = new Vehiculo(UUID.randomUUID().toString(), "Kia", "Sportage EX 2.4L", "suv", 2021, 12.2, "ciudad");
        Vehiculo v3 = new Vehiculo(UUID.randomUUID().toString(), "Ford", "Escape Titanium 2.0L", "suv", 2020, 11.5, "mixto");
        Vehiculo v4 = new Vehiculo(UUID.randomUUID().toString(), "Chevrolet", "Colorado Z71 3.6L", "pickup", 2021, 10.0, "campo");
        Vehiculo v5 = new Vehiculo(UUID.randomUUID().toString(), "Hyundai", "Tucson GLS 2.0L", "suv", 2022, 14.7, "mixto");
        Vehiculo v6 = new Vehiculo(UUID.randomUUID().toString(), "Nissan", "Versa Advance 1.6L", "sedan", 2023, 18.9, "ciudad");
        Vehiculo v7 = new Vehiculo(UUID.randomUUID().toString(), "Mazda", "CX-5 Touring 2.5L", "suv", 2023, 13.4, "mixto");
        Vehiculo v8 = new Vehiculo(UUID.randomUUID().toString(), "Jeep", "Gladiator Rubicon 3.6L", "pickup", 2021, 9.5, "campo");
        Vehiculo v9 = new Vehiculo(UUID.randomUUID().toString(), "Honda", "Civic EX 2.0L", "sedan", 2022, 17.8, "ciudad");
        Vehiculo v10 = new Vehiculo(UUID.randomUUID().toString(), "Volkswagen", "Amarok V6 Highline", "pickup", 2020, 10.3, "campo");
        Vehiculo v11 = new Vehiculo(UUID.randomUUID().toString(), "Mitsubishi", "Outlander 2.4L AWD", "suv", 2021, 12.9, "mixto");
        Vehiculo v12 = new Vehiculo(UUID.randomUUID().toString(), "Suzuki", "Swift GLX 1.2L", "sedan", 2023, 19.1, "ciudad");
        Vehiculo v13 = new Vehiculo(UUID.randomUUID().toString(), "Toyota", "Corolla SE 2.0L", "sedan", 2021, 18.2, "ciudad");
        Vehiculo v14 = new Vehiculo(UUID.randomUUID().toString(), "RAM", "1500 Laramie 5.7L V8", "pickup", 2023, 8.7, "campo");
        Vehiculo v15 = new Vehiculo(UUID.randomUUID().toString(), "Renault", "Duster 1.6L 4x2", "suv", 2022, 14.5, "mixto");
        Vehiculo v16 = new Vehiculo(UUID.randomUUID().toString(), "Chevrolet", "Onix LT 1.2L", "sedan", 2023, 19.0, "ciudad");
        Vehiculo v17 = new Vehiculo(UUID.randomUUID().toString(), "Honda", "CR-V Turbo 1.5L", "suv", 2022, 13.8, "mixto");
        Vehiculo v18 = new Vehiculo(UUID.randomUUID().toString(), "Ford", "Ranger XLT 3.2L", "pickup", 2020, 9.7, "campo");
        Vehiculo v19 = new Vehiculo(UUID.randomUUID().toString(), "Hyundai", "Accent GL 1.4L", "sedan", 2022, 18.6, "ciudad");
        Vehiculo v20 = new Vehiculo(UUID.randomUUID().toString(), "Mazda", "BT-50 Pro 3.0L", "pickup", 2021, 9.9, "mixto");
        Vehiculo v21 = new Vehiculo(UUID.randomUUID().toString(), "Peugeot", "3008 Allure 1.6L", "suv", 2021, 13.6, "campo");
        Vehiculo v22 = new Vehiculo(UUID.randomUUID().toString(), "BMW", "X1 sDrive 18i 1.5L", "suv", 2022, 14.3, "campo");
        Vehiculo v23 = new Vehiculo(UUID.randomUUID().toString(), "Mercedes-Benz", "GLA 200 1.3L", "suv", 2023, 15.2, "ciudad");
        Vehiculo v24 = new Vehiculo(UUID.randomUUID().toString(), "Chevrolet", "Aveo 1.5L", "sedan", 2021, 17.0, "ciudad");
        Vehiculo v25 = new Vehiculo(UUID.randomUUID().toString(), "Toyota", "Hilux 2.8L 4x4 Diesel", "pickup", 2023, 11.8, "mixto");
        Vehiculo v26 = new Vehiculo(UUID.randomUUID().toString(), "Hyundai", "Santa Fe 2.4L", "suv", 2022, 12.9, "campo");
        Vehiculo v27 = new Vehiculo(UUID.randomUUID().toString(), "Renault", "Logan 1.6L", "sedan", 2021, 18.1, "mixto");
        Vehiculo v28 = new Vehiculo(UUID.randomUUID().toString(), "Kia", "Sorento 2.5L", "suv", 2023, 13.1, "mixto");
        Vehiculo v29 = new Vehiculo(UUID.randomUUID().toString(), "Ford", "F-150 XLT 5.0L V8", "pickup", 2022, 8.5, "mixto");
        Vehiculo v30 = new Vehiculo(UUID.randomUUID().toString(), "Honda", "Fit 1.5L", "sedan", 2020, 19.3, "ciudad");
        Vehiculo v31 = new Vehiculo(UUID.randomUUID().toString(), "Volkswagen", "T-Cross 1.0L", "suv", 2022, 15.0, "mixto");
        Vehiculo v32 = new Vehiculo(UUID.randomUUID().toString(), "Nissan", "Navara 2.5L", "pickup", 2021, 10.1, "mixto");
        Vehiculo v33 = new Vehiculo(UUID.randomUUID().toString(), "Mazda", "Mazda3 Sedan 2.0L", "sedan", 2023, 17.5, "mixto");
        Vehiculo v34 = new Vehiculo(UUID.randomUUID().toString(), "Jeep", "Compass 2.4L", "suv", 2022, 12.7, "campo");
        Vehiculo v35 = new Vehiculo(UUID.randomUUID().toString(), "RAM", "700 SLT 1.4L", "pickup", 2023, 11.3, "campo");
        Vehiculo v36 = new Vehiculo(UUID.randomUUID().toString(), "Chevrolet", "Tracker 1.2L Turbo", "suv", 2021, 14.8, "mixto");
        Vehiculo v37 = new Vehiculo(UUID.randomUUID().toString(), "Toyota", "Yaris Sedán 1.5L", "sedan", 2022, 18.8, "mixto");
        Vehiculo v38 = new Vehiculo(UUID.randomUUID().toString(), "Ford", "EcoSport 1.5L", "suv", 2020, 13.0, "ciudad");
        Vehiculo v39 = new Vehiculo(UUID.randomUUID().toString(), "Hyundai", "Creta 1.5L", "suv", 2023, 15.3, "ciudad");
        Vehiculo v40 = new Vehiculo(UUID.randomUUID().toString(), "Mitsubishi", "L200 2.4L Diesel", "pickup", 2021, 10.5, "ciudad");


        repo.guardarVehiculo(v1); repo.guardarVehiculo(v2); repo.guardarVehiculo(v3);
        repo.guardarVehiculo(v4); repo.guardarVehiculo(v5); repo.guardarVehiculo(v6);
        repo.guardarVehiculo(v7); repo.guardarVehiculo(v8); repo.guardarVehiculo(v9);
        repo.guardarVehiculo(v10); repo.guardarVehiculo(v11); repo.guardarVehiculo(v12);
        repo.guardarVehiculo(v13); repo.guardarVehiculo(v14); repo.guardarVehiculo(v15);
        repo.guardarVehiculo(v16); repo.guardarVehiculo(v17); repo.guardarVehiculo(v18);
        repo.guardarVehiculo(v19); repo.guardarVehiculo(v20); repo.guardarVehiculo(v21); repo.guardarVehiculo(v22); repo.guardarVehiculo(v23); 
        repo.guardarVehiculo(v24); repo.guardarVehiculo(v25); repo.guardarVehiculo(v26); repo.guardarVehiculo(v27); 
        repo.guardarVehiculo(v28); repo.guardarVehiculo(v29);  repo.guardarVehiculo(v30); repo.guardarVehiculo(v31); repo.guardarVehiculo(v32);
        repo.guardarVehiculo(v33); repo.guardarVehiculo(v34); repo.guardarVehiculo(v35);
        repo.guardarVehiculo(v36); repo.guardarVehiculo(v37); repo.guardarVehiculo(v38); repo.guardarVehiculo(v39); repo.guardarVehiculo(v40);

        // Crear usuarios de ejemplo
        Usuario u1 = new Usuario("Ana", "ciudad", "sedan");
        Usuario u2 = new Usuario("Luis", "campo", "pickup");
        Usuario u3 = new Usuario("Sofía", "mixto", "suv");
        Usuario u4 = new Usuario("Carlos", "ciudad", "suv");
        Usuario u5 = new Usuario("Mateo", "campo", "pickup");
        Usuario u6 = new Usuario("juan", "campo", "pickup");
        Usuario u7 = new Usuario("laura", "ciudad", "sedan");
        Usuario u8 = new Usuario("Brenda", "mixto", "suv");
        Usuario u9 = new Usuario("Diego", "mixto", "suv");
        Usuario u10 = new Usuario("Roberto", "campo", "pickup");

        // Guardar usuarios
        repo.guardarUsuario(u1); repo.guardarUsuario(u2); repo.guardarUsuario(u3);
        repo.guardarUsuario(u4); repo.guardarUsuario(u5); repo.guardarUsuario(u6);
        repo.guardarUsuario(u7); repo.guardarUsuario(u8); repo.guardarUsuario(u9);
        repo.guardarUsuario(u10);

        // Crear recomendaciones de ejemplo
        repo.recomendarVehiculo(u1, v6); repo.recomendarVehiculo(u1, v9);
        repo.recomendarVehiculo(u2, v14); repo.recomendarVehiculo(u2, v10);
        repo.recomendarVehiculo(u3, v1); repo.recomendarVehiculo(u3, v5);
        repo.recomendarVehiculo(u4, v2); repo.recomendarVehiculo(u5, v18);
        repo.recomendarVehiculo(u5, v20); repo.recomendarVehiculo(u6, v20);
        repo.recomendarVehiculo(u6, v18); repo.recomendarVehiculo(u7, v12);
        repo.recomendarVehiculo(u7, v9); repo.recomendarVehiculo(u8, v17);
        repo.recomendarVehiculo(u8, v5); repo.recomendarVehiculo(u9, v1);
        repo.recomendarVehiculo(u9, v9); repo.recomendarVehiculo(u10, v7);

        System.out.println(" Datos iniciales cargados exitosamente!");
    }
}
