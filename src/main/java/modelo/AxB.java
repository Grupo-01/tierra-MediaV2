package modelo;

import java.util.LinkedList;

public class AxB extends Promo {

    private Atraccion atraccionGratis;

    public AxB() {

    }

    public AxB(String nombre, LinkedList<Atraccion> atracciones, TipoAtraccion tipo) {
        super(nombre, atracciones, tipo);
    }

    public Atraccion getAtraccionGratis() {
        return this.atraccionGratis;
    }

    @Override
    public double getCosto() {
        return costoTotal();
    }

    @Override
    public double costoTotal() {
        int index = getAtracciones().size() - 1;
        this.atraccionGratis = getAtracciones().get(index);
        return super.getCosto() - this.atraccionGratis.getCosto();
    }

    @Override
    public String toString() {
        return super.toString() + " | atraccion gratis: " + getAtraccionGratis().getNombre();
    }

    public static void main(String[] args) {
        LinkedList<Promo> promociones = new LinkedList<>();
        LinkedList<Atraccion> atraccionesPromo = new LinkedList<>();

        var atraccion1 = new Atraccion("Minas Tirith", 5, 2.5, 25, TipoAtraccion.PAISAJE);
        var atraccion2 = new Atraccion("Abismo de Helm", 5, 2, 15, TipoAtraccion.PAISAJE);
        var atraccion3 = new Atraccion("Erebor", 12, 3, 32, TipoAtraccion.PAISAJE);

        atraccionesPromo.add(atraccion1);
        atraccionesPromo.add(atraccion2);
        atraccionesPromo.add(atraccion3);

        Promo promo = new AxB("Pack Paisaje", atraccionesPromo, TipoAtraccion.PAISAJE);
        promociones.add(promo);

        for (var promocion : promociones) {
            System.out.println(promocion);
        }
    }
}
