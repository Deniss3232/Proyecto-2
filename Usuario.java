public class Usuario {
    private String nombre;
    private String estiloDeVida; // Ej: "urbano", "todoTerreno", "familiar"

    // Constructor
    public Usuario(String nombre, String estiloDeVida) {
        this.nombre = nombre;
        this.estiloDeVida = estiloDeVida;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getEstiloDeVida() {
        return estiloDeVida;
    }

    // Setters
    public void setEstiloDeVida(String estiloDeVida) {
        this.estiloDeVida = estiloDeVida;
    }

    // Método útil para mostrar información del usuario
    public void mostrarResumen() {
        System.out.println("Usuario: " + nombre);
        System.out.println("Estilo de vida: " + estiloDeVida);
    }

    // Método ejemplo para cambiar estilo con validación
    public boolean actualizarEstilo(String nuevoEstilo) {
        if (nuevoEstilo != null && !nuevoEstilo.isEmpty()) {
            this.estiloDeVida = nuevoEstilo;
            return true;
        }
        return false;
    }
}
