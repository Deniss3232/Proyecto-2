public class Vehiculo {
    private String id;
    private String marca;
    private String modelo;
    private String tipo;
    private int anio;
    private double consumo;
    private String uso;

    public Vehiculo(String id, String marca, String modelo, String tipo, int anio, double consumo, String uso) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.anio = anio;
        this.consumo = consumo;
        this.uso = uso;
    }

    public String getId() { return id; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getTipo() { return tipo; }
    public int getAnio() { return anio; }
    public double getConsumo() { return consumo; }
    public String getUso() { return uso; }

    @Override
    public String toString() {
        return marca + " " + modelo + " (" + anio + ") - Tipo: " + tipo + ", Consumo: " + consumo + " km/L, Uso: " + uso;
    }
}
