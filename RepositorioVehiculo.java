import org.neo4j.driver.*;
public class RepositorioVehiculo {
    private ConexionNeo4j conexion;

    // Constructor que recibe una instancia de la conexión
    public RepositorioVehiculo(ConexionNeo4j conexion) {
        this.conexion = conexion; // Guarda la conexión
    }
// Busca un vehículo cuyo estiloIdeal coincida
    public Vehiculo recomendarPorEstilo(String estilo) {
        try (Session sesion = conexion.obtenerSesion()) { // Abre sesión en Neo4j
            String query = "MATCH (v:Vehiculo) WHERE toLower(v.estiloIdeal) = toLower($estilo) RETURN v.nombre AS nombre, v.tipo AS tipo, v.descripcion AS descripcion, v.estiloIdeal AS estiloIdeal LIMIT 1";

            // Ejecuta la consulta y extrae los resultados
            Result resultado = sesion.run(query, Values.parameters("estilo", estilo));

            if (resultado.hasNext()) {
                Record registro = resultado.next(); // Toma el primer resultado
                return new Vehiculo(
                    registro.get("nombre").asString(),      // nombre del vehículo
                    registro.get("tipo").asString(),        // tipo (SUV, sedán, etc.)
                    registro.get("descripcion").asString(), // descripción del vehículo
                    registro.get("estiloIdeal").asString()  // estilo ideal
                );
            }
        }
        return null; // Si no se encuentra vehículo
    }
}
