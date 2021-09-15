package dominio;

import java.text.DecimalFormat;

public class Atraccion implements Ofertable {

    private final String nombre;
    private int cupo;
    private final double tiempo;
    private final double costo;
    private final TipoAtraccion tipo;
    private final DecimalFormat df = new DecimalFormat("#0.00"); // para formatear los decimales a 2 cifras

    public Atraccion(String nombre, int costo, double tiempo, int cupo, TipoAtraccion tipo) {
        this.nombre = nombre;
        this.costo = costo;
        this.cupo = cupo;
        this.tiempo = tiempo;
        this.tipo = tipo;
    }

    @Override
    public boolean hayCupo() {
        return this.cupo > 0;
    }

    @Override
    public boolean yaSeCompro(Ofertable oferta) {
        if (oferta.esPromo())
            return oferta.yaSeCompro(this);
        return this == oferta;
    }

    @Override
    public void reservarCupo() {
        if (hayCupo())
            this.cupo--;
    }

    @Override
    public String getNombre() {
        return this.nombre;
    }

    @Override
    public double getCosto() {
        return this.costo;
    }

    @Override
    public TipoAtraccion getGenero() {
        return this.tipo;
    }

    @Override
    public double getDuracion() {
        return this.tiempo;
    }

    @Override
    public boolean esPromo() {
        return false;
    }

    @Override
    public String toString() {
        return "ATRACCION: " + getNombre() + " | cuesta: "  + df.format(getCosto()) +  " |  dura: " +  df.format(getDuracion()) + " | tipo: " + getGenero();
    }
}
