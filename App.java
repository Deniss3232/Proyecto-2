public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // Para leer entrada del usuario

        // Conexión a Neo4j — CAMBIA estos datos según tu configuración
        ConexionNeo4j conexion = new ConexionNeo4j("bolt://localhost:7687", "neo4j", "admin");

        System.out.println("Bienvenido al recomendador de vehículos.");
        System.out.print("¿Cuál es tu nombre?: ");
        String nombre = sc.nextLine(); // Leer nombre del usuario

        // Menú de opciones
        System.out.println("Selecciona tu estilo de vida:");
        System.out.println("1. Urbano");
        System.out.println("2. Todo Terreno");
        System.out.println("3. Familiar");
        System.out.print("Ingresa el número de tu elección: ");
        int opcion = sc.nextInt(); // Leer opción

        // Determinar estilo según la elección
        String estilo = switch (opcion) {
            case 1 -> "urbano";
            case 2 -> "todoTerreno";
            case 3 -> "familiar";
            default -> "urbano";
        };
        
        Usuario usuario = new Usuario(nombre, estilo); // Crear objeto Usuario

        RepositorioVehiculo repo = new RepositorioVehiculo(conexion); // Crear repositorio con Neo4j
        ServicioRecomendacion servicio = new ServicioRecomendacion(repo); // Crear servicio de recomendación

        Vehiculo recomendado = servicio.recomendarVehiculo(usuario); // Obtener recomendación

        if (recomendado != null) {
            System.out.println("\nVehículo recomendado para ti, " + usuario.getNombre() + ":");
            System.out.println(recomendado.getFichaTecnica()); // Mostrar ficha técnica
        } else {
            System.out.println("Lo sentimos, no encontramos una recomendación para tu estilo de vida.");
        }

        conexion.cerrar(); // Cerrar conexión a Neo4j
    }
}
