package modelo;

import java.util.LinkedList;

public class Absoluta extends Promo {

    private double costoTotal;

    public Absoluta() {
    }

    public Absoluta(String nombre, LinkedList<Atraccion> atracciones, TipoAtraccion tipo, double costoTotal) {
        super(nombre, atracciones, tipo);
        this.costoTotal = costoTotal;
    }
    // costo total promo absoluta
    public double getCostoTotal() {
        return this.costoTotal;
    }

    @Override
    public double getCosto() {
        return costoTotal();
    }

    @Override
    public double costoTotal() {
        return getCostoTotal();
    }

    public static void main(String[] args) {
        LinkedList<Promo> promociones = new LinkedList<>();
        LinkedList<Atraccion> atraccionesPromo = new LinkedList<>();

        var atraccion1 = new Atraccion("Lothorien",35,1,30, TipoAtraccion.DEGUSTACION);
        var atraccion2 = new Atraccion("La Comarca",3,6.5,150, TipoAtraccion.DEGUSTACION);

        atraccionesPromo.add(atraccion1);
        atraccionesPromo.add(atraccion2);

        Promo promo = new Absoluta("Pack Degustacion", atraccionesPromo, TipoAtraccion.DEGUSTACION, 36);
        promociones.add(promo);

        for (var promocion : promociones) {
            System.out.println(promocion);
        }
    }
}
