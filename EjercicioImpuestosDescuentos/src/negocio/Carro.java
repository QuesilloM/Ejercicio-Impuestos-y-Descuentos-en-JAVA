package negocio;

public class Carro {
    private double valor;
    private boolean esServicioPublico;

    public Carro(double valor, boolean esServicioPublico) {
        if (valor <= 0) {
            throw new IllegalArgumentException("El valor del carro debe ser mayor a cero.");
        }
        this.valor = valor;
        this.esServicioPublico = esServicioPublico;
    }

    public double getValor() {
        return valor;
    }

    public boolean esServicioPublico() {
        return esServicioPublico;
    }
}
