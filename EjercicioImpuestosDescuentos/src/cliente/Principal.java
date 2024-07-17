package cliente;

import negocio.Carro;
import negocio.CalculadoraImpuestos;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {

    private static Scanner scanner = new Scanner(System.in);
    private static CalculadoraImpuestos calculadoraImpuestos = new CalculadoraImpuestos();

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            int opcion = obtenerOpcionMenu();

            switch (opcion) {
                case 1:
                    calcularImpuesto();
                    break;
                case 2:
                    salir = true;
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida, por favor intente de nuevo.");
            }
        }
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\nMenú de opciones:");
        System.out.println("1. Calcular Impuesto");
        System.out.println("2. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int obtenerOpcionMenu() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            scanner.nextLine();  // Consumir el salto de línea
            return -1;
        }
    }

    private static void calcularImpuesto() {
        try {
            System.out.print("Ingrese el valor del carro: ");
            double valorCarro = scanner.nextDouble();
            validarValor(valorCarro);

            System.out.print("¿Es el carro de servicio público? (Y/N): ");
            char esServicioPublicoChar = scanner.next().toUpperCase().charAt(0);
            boolean esServicioPublico = validarServicioPublico(esServicioPublicoChar);

            System.out.print("Ingrese el valor base del impuesto: ");
            double impuestoBase = scanner.nextDouble();
            validarValor(impuestoBase);

            System.out.print("Ingrese el porcentaje de descuento por pronto pago: ");
            double porcentajeProntoPago = scanner.nextDouble();
            validarPorcentaje(porcentajeProntoPago);

            double montoFijoServicioPublico = 0;
            if (esServicioPublico) {
                System.out.print("Ingrese el monto fijo de descuento por servicio público: ");
                montoFijoServicioPublico = scanner.nextDouble();
                validarMontoFijo(montoFijoServicioPublico);
            }

            System.out.print("Ingrese el porcentaje de descuento por traslado de cuenta: ");
            double porcentajeTrasladoCuenta = scanner.nextDouble();
            validarPorcentaje(porcentajeTrasladoCuenta);
            validarPorcentajeTrasladoMayorProntoPago(porcentajeTrasladoCuenta, porcentajeProntoPago);

            Carro carro = new Carro(valorCarro, esServicioPublico);

            double totalImpuesto = calculadoraImpuestos.calcularTotalImpuesto(carro, impuestoBase, porcentajeProntoPago, montoFijoServicioPublico, porcentajeTrasladoCuenta);

            System.out.println("Total de Impuestos después de descuentos: " + totalImpuesto);

        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un valor numérico.");
            scanner.nextLine();  // Consumir el salto de línea
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static boolean validarServicioPublico(char respuesta) {
        if (respuesta == 'Y') {
            return true;
        } else if (respuesta == 'N') {
            return false;
        } else {
            throw new IllegalArgumentException("Entrada inválida. Por favor, ingrese 'Y' o 'N'.");
        }
    }

    private static void validarValor(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("El valor debe ser mayor a cero.");
        }
    }

    private static void validarPorcentaje(double porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100.");
        }
    }

    private static void validarMontoFijo(double montoFijo) {
        if (montoFijo < 0) {
            throw new IllegalArgumentException("El monto fijo no puede ser negativo.");
        }
    }

    private static void validarPorcentajeTrasladoMayorProntoPago(double porcentajeTrasladoCuenta, double porcentajeProntoPago) {
        if (porcentajeTrasladoCuenta <= porcentajeProntoPago) {
            throw new IllegalArgumentException("El porcentaje de traslado de cuenta debe ser mayor que el porcentaje de pronto pago.");
        }
    }
}
