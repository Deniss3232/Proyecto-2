import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class ServicioRecomendacion {
    private final ConexionNeo4j conexion;

    public ServicioRecomendacion(ConexionNeo4j conexion) {
        this.conexion = conexion;
    }

    //  Recomendaciones por filtros técnicos (tipo, uso, año)
    public List<Vehiculo> recomendarVehiculos(Usuario usuario, int anioMinimo) {
        List<Vehiculo> recomendados = new ArrayList<>();

        try (Session session = conexion.obtenerSesion()) {
            Result result = session.run(
                "MATCH (v:Vehiculo) WHERE v.uso = $uso AND v.tipo = $tipo AND v.anio >= $anioMinimo RETURN v ORDER BY v.consumo DESC",
                org.neo4j.driver.Values.parameters(
                    "uso", usuario.getPreferenciaUso(),
                    "tipo", usuario.getTipoPreferido(),
                    "anioMinimo", anioMinimo
                )
            );

            while (result.hasNext()) {
                var record = result.next().get("v");

                Vehiculo vehiculo = new Vehiculo(
                    record.get("id").asString(),
                    record.get("marca").asString(),
                    record.get("modelo").asString(),
                    record.get("tipo").asString(),
                    record.get("anio").asInt(),
                    record.get("consumo").asDouble(),
                    record.get("uso").asString()
                );

                recomendados.add(vehiculo);
            }
        } catch (Exception e) {
            System.err.println("Error al buscar recomendaciones: " + e.getMessage());
        }

        return recomendados;
    }

    // Recomendaciones colaborativas basadas en otros usuarios
    public List<Vehiculo> recomendarPorSimilitud(Usuario usuario) {
        List<Vehiculo> recomendados = new ArrayList<>();

        try (Session session = conexion.obtenerSesion()) {
            // Buscar usuarios similares y sus recomendaciones
            Result result = session.run(
                "MATCH (usuarioSimilar:Usuario)-[:RECOMENDO]->(v:Vehiculo) " +
                "WHERE usuarioSimilar.uso = $uso AND usuarioSimilar.tipo = $tipo " +
                "RETURN DISTINCT v ORDER BY v.consumo DESC LIMIT 5",
                org.neo4j.driver.Values.parameters(
                    "uso", usuario.getPreferenciaUso(),
                    "tipo", usuario.getTipoPreferido()
                )
            );

            while (result.hasNext()) {
                var record = result.next().get("v");

                Vehiculo vehiculo = new Vehiculo(
                    record.get("id").asString(),
                    record.get("marca").asString(),
                    record.get("modelo").asString(),
                    record.get("tipo").asString(),
                    record.get("anio").asInt(),
                    record.get("consumo").asDouble(),
                    record.get("uso").asString()
                );

                recomendados.add(vehiculo);
            }

        } catch (Exception e) {
            System.err.println("Error en recomendación colaborativa: " + e.getMessage());
        }

        return recomendados;
    }
}
