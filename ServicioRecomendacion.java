public class ServicioRecomendacion {
    private ConexionNeo4j conexion;

    public List<Vehiculo> recomendarVehiculos(Usuario usuario);
    public List<Vehiculo> buscarPorEstiloDeVida(Usuario usuario);
}
