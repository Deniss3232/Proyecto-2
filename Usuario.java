public class Usuario {
    private String nombre;
    private String preferenciaUso;
    private String tipoPreferido;

    public Usuario(String nombre, String preferenciaUso, String tipoPreferido) {
        this.nombre = nombre;
        this.preferenciaUso = preferenciaUso;
        this.tipoPreferido = tipoPreferido;
    }

    public String getNombre() { return nombre; }
    public String getPreferenciaUso() { return preferenciaUso; }
    public String getTipoPreferido() { return tipoPreferido; }
}
