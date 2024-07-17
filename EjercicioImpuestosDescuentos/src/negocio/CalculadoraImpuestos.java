package negocio;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculadoraImpuestos {
    private CalculadoraDescuentos calculadoraDescuentos;

    public CalculadoraImpuestos() {
        this.calculadoraDescuentos = new CalculadoraDescuentos();
    }

    public double calcularTotalImpuesto(Carro carro, double impuestoBase, double porcentajeProntoPago, double montoFijoServicioPublico, double porcentajeTrasladoCuenta) {
        if (impuestoBase <= 0) {
            throw new IllegalArgumentException("El impuesto base debe ser mayor a cero.");
        }
        if (porcentajeTrasladoCuenta <= porcentajeProntoPago) {
            throw new IllegalArgumentException("El porcentaje de traslado de cuenta debe ser mayor que el porcentaje de pronto pago.");
        }

        double valorDescuento = carro.getValor();
        valorDescuento = calculadoraDescuentos.aplicarDescuentoProntoPago(valorDescuento, porcentajeProntoPago);

        if (carro.esServicioPublico()) {
            valorDescuento = calculadoraDescuentos.aplicarDescuentoServicioPublico(valorDescuento, montoFijoServicioPublico);
        }

        double impuesto = calcularImpuestoBase(valorDescuento, impuestoBase);
        impuesto = aplicarDescuentoTrasladoCuenta(impuesto, porcentajeTrasladoCuenta);

        return redondear(impuesto, 2);
    }

    private double calcularImpuestoBase(double valorDescuento, double impuestoBase) {
        return valorDescuento * (impuestoBase / 100);
    }

    private double aplicarDescuentoTrasladoCuenta(double impuesto, double porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("El porcentaje de traslado de cuenta debe estar entre 0 y 100.");
        }
        return impuesto - (impuesto * (porcentaje / 100));
    }

    private double redondear(double valor, int decimales) {
        BigDecimal bd = new BigDecimal(valor);
        bd = bd.setScale(decimales, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
