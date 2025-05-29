public class ServicioRecomendacion {
    private final RepositorioVehiculo repositorio; // Almacena el repositorio

    public ServicioRecomendacion(RepositorioVehiculo repo) {
        this.repositorio = repo; // Constructor
    }

    public Vehiculo recomendarVehiculo(Usuario usuario) {
        return repositorio.recomendarPorEstilo(usuario.getEstiloDeVida()); // Recomendación basada en estilo
    }
}
