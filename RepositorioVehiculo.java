import org.neo4j.driver.Session;

public class RepositorioVehiculo {
    private final ConexionNeo4j conexion;

    public RepositorioVehiculo(ConexionNeo4j conexion) {
        this.conexion = conexion;
    }

    public void guardarVehiculo(Vehiculo vehiculo) {
        try (Session session = conexion.obtenerSesion()) {
            session.run("MERGE (v:Vehiculo {id: $id}) SET v.marca = $marca, v.modelo = $modelo, v.tipo = $tipo, v.anio = $anio, v.consumo = $consumo, v.uso = $uso",
                org.neo4j.driver.Values.parameters(
                    "id", vehiculo.getId(),
                    "marca", vehiculo.getMarca(),
                    "modelo", vehiculo.getModelo(),
                    "tipo", vehiculo.getTipo(),
                    "anio", vehiculo.getAnio(),
                    "consumo", vehiculo.getConsumo(),
                    "uso", vehiculo.getUso()
                )
            );
        }
    }

    public void limpiarVehiculos() {
        try (Session session = conexion.obtenerSesion()) {
            session.run("MATCH (n) DETACH DELETE n");
        }
    }

    //  Verificar si ya existen datos en la base
    public boolean existenDatos() {
        try (Session session = conexion.obtenerSesion()) {
            var result = session.run("MATCH (v:Vehiculo) RETURN count(v) as total");
            if (result.hasNext()) {
                int total = result.next().get("total").asInt();
                return total > 0;
            }
        } catch (Exception e) {
            System.err.println("Error al verificar datos existentes: " + e.getMessage());
        }
        return false;
    }


    //  Guardar usuario en Neo4j
    public void guardarUsuario(Usuario usuario) {
        try (Session session = conexion.obtenerSesion()) {
            session.run("MERGE (u:Usuario {nombre: $nombre, uso: $uso, tipo: $tipo})",
                org.neo4j.driver.Values.parameters(
                    "nombre", usuario.getNombre(),
                    "uso", usuario.getPreferenciaUso(),
                    "tipo", usuario.getTipoPreferido()
                )
            );
        }
    }

    //  Relación RECOMENDO entre Usuario y Vehiculo 
    public void recomendarVehiculo(Usuario usuario, Vehiculo vehiculo) {
        try (Session session = conexion.obtenerSesion()) {
            session.run(
                "MATCH (u:Usuario {nombre: $nombre}), (v:Vehiculo {id: $vehiculoId}) " +
                "MERGE (u)-[:RECOMENDO]->(v)",
                org.neo4j.driver.Values.parameters(
                    "nombre", usuario.getNombre(),
                    "vehiculoId", vehiculo.getId()
                )
            );
        }
    }

    //  Guardar búsqueda del usuario
    public void guardarBusqueda(String nombreUsuario, String uso, String tipo, int anioMinimo) {
        try (Session session = conexion.obtenerSesion()) {
            session.run(
                "MATCH (u:Usuario {nombre: $nombre}) " +
                "CREATE (b:Busqueda {uso: $uso, tipo: $tipo, anioMinimo: $anioMinimo, timestamp: datetime()}) " +
                "CREATE (u)-[:REALIZO_BUSQUEDA]->(b)",
                org.neo4j.driver.Values.parameters(
                    "nombre", nombreUsuario,
                    "uso", uso,
                    "tipo", tipo,
                    "anioMinimo", anioMinimo
                )
            );
        } catch (Exception e) {
            System.err.println("Error al guardar búsqueda: " + e.getMessage());
        }
    }

    //  Guardar selección del usuario
    public void guardarSeleccion(String nombreUsuario, Vehiculo vehiculo) {
        try (Session session = conexion.obtenerSesion()) {
            session.run(
                "MATCH (u:Usuario {nombre: $nombre}), (v:Vehiculo {id: $vehiculoId}) " +
                "CREATE (s:Seleccion {timestamp: datetime()}) " +
                "CREATE (u)-[:SELECCIONO]->(s)-[:VEHICULO_SELECCIONADO]->(v)",
                org.neo4j.driver.Values.parameters(
                    "nombre", nombreUsuario,
                    "vehiculoId", vehiculo.getId()
                )
            );
            System.out.println(" Selección guardada exitosamente en Neo4j");
        } catch (Exception e) {
            System.err.println("Error al guardar selección: " + e.getMessage());
        }
    }
}
