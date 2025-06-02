import org.neo4j.driver.Session;

public class RepositorioVehiculo {
    private final ConexionNeo4j conexion;

    public RepositorioVehiculo(ConexionNeo4j conexion) {
        this.conexion = conexion;
    }

    public void guardarVehiculo(Vehiculo v) {
        try (Session session = conexion.obtenerSesion()) {
            session.writeTransaction(tx -> {
                tx.run("CREATE (v:Vehiculo {id: $id, marca: $marca, modelo: $modelo, tipo: $tipo, anio: $anio, consumo: $consumo, uso: $uso})",
                        org.neo4j.driver.Values.parameters(
                                "id", v.getId(),
                                "marca", v.getMarca(),
                                "modelo", v.getModelo(),
                                "tipo", v.getTipo(),
                                "anio", v.getAnio(),
                                "consumo", v.getConsumo(),
                                "uso", v.getUso()
                        ));
                return null;
            });
        }
    }

    public void limpiarVehiculos() {
        try (Session session = conexion.obtenerSesion()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH (v:Vehiculo) DELETE v");
                return null;
            });
        }
    }
}
