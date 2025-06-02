import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Value;
import java.util.ArrayList;
import java.util.List;

public class ServicioRecomendacion {
    private final ConexionNeo4j conexion;

    public ServicioRecomendacion(ConexionNeo4j conexion) {
        this.conexion = conexion;
    }

    public List<Vehiculo> recomendarVehiculos(Usuario usuario) {
        List<Vehiculo> recomendados = new ArrayList<>();

        try (Session session = conexion.obtenerSesion()) {
            Result result = session.run("MATCH (v:Vehiculo) WHERE v.uso = $uso AND v.tipo = $tipo RETURN v",
                    org.neo4j.driver.Values.parameters(
                            "uso", usuario.getPreferenciaUso(),
                            "tipo", usuario.getTipoPreferido()
                    ));

            while (result.hasNext()) {
                var record = result.next().get("v");
                
                // Extraer valores de forma segura
                String id = record.get("id").asString();
                String marca = record.get("marca").asString();
                String modelo = record.get("modelo").asString();
                String tipo = record.get("tipo").asString();
                int anio = record.get("anio").asInt();
                double consumo = record.get("consumo").asDouble();
                String uso = record.get("uso").asString();
                
                Vehiculo vehiculo = new Vehiculo(id, marca, modelo, tipo, anio, consumo, uso);
                recomendados.add(vehiculo);
            }
        } catch (Exception e) {
            System.err.println("Error al buscar recomendaciones: " + e.getMessage());
        }

        return recomendados;
    }
}
