package modelo;

import java.util.LinkedList;

public class Porcentual extends Promo {

    private final double descuento;

    public Porcentual(double descuento) {
        this.descuento = descuento;
    }

    public Porcentual(String nombre, LinkedList<Atraccion> atracciones, TipoAtraccion tipo, double descuento) {
        super(nombre, atracciones, tipo);
        this.descuento = descuento;
    }

    @Override
    public double getCosto() {
        return costoTotal();
    }

    @Override
    public double costoTotal() {
        double descuentoObtenido = super.getCosto() * this.descuento / 100;
        return super.getCosto() - descuentoObtenido;
    }
}
