public class Vehiculo {
    private String nombre;
    private String tipo; // Ej: "SUV", "sedán", "pickup"
    private String descripcion;
    private String estiloIdeal;

    // Constructor
    public Vehiculo(String nombre, String tipo, String descripcion, String estiloIdeal) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.estiloIdeal = estiloIdeal;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEstiloIdeal() {
        return estiloIdeal;
    }

    // Muestra ficha técnica formateada
    public String getFichaTecnica() {
        return "Nombre: " + nombre +
               "\nTipo: " + tipo +
               "\nEstilo ideal: " + estiloIdeal +
               "\nDescripción: " + descripcion;
    }

    // Método extra: verificar si es apto para cierto estilo
    public boolean esAptoPara(String estilo) {
        return estiloIdeal.equalsIgnoreCase(estilo);
    }

    // Método para mostrar datos directamente
    public void imprimirFicha() {
        System.out.println(getFichaTecnica());
    }
}
