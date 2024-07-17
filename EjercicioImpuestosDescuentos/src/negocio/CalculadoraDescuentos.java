package negocio;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculadoraDescuentos {
    public double aplicarDescuentoProntoPago(double valor, double porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("El porcentaje de pronto pago debe estar entre 0 y 100.");
        }
        return valor - (valor * (porcentaje / 100));
    }

    public double aplicarDescuentoServicioPublico(double valor, double montoFijo) {
        if (montoFijo < 0) {
            throw new IllegalArgumentException("El monto fijo de descuento no puede ser negativo.");
        }
        return valor - montoFijo;
    }

    private double redondear(double valor, int decimales) {
        BigDecimal bd = new BigDecimal(valor);
        bd = bd.setScale(decimales, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
