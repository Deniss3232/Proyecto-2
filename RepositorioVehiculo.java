public class RepositorioVehiculo {
    private ConexionNeo4j conexion;

    public void insertarVehiculo(Vehiculo vehiculo);
    public List<Vehiculo> obtenerTodos();
    public void eliminarVehiculo(String nombre);
}

