package modelo;

import java.text.DecimalFormat;
import java.util.LinkedList;

public abstract class Promo implements Ofertable {

    private String nombre;
    private LinkedList<Atraccion> atracciones;
    private TipoAtraccion tipo;
    private double costo, duracion;
    private final DecimalFormat df = new DecimalFormat("#0.00"); // para formatear los decimales a 2 cifras

    public Promo() {
    }

    public Promo(String nombre, LinkedList<Atraccion> atracciones, TipoAtraccion tipo) {
        this.nombre = nombre;
        this.atracciones = atracciones;
        this.tipo = tipo;
        this.costo = setCosto(atracciones);
        this.duracion = setDuracion(atracciones);
    }

    @Override
    public String getNombre() {
        return this.nombre;
    }

    private double setCosto(LinkedList<Atraccion> atracciones) {
        for (var atraccion : atracciones) {
            this.costo += atraccion.getCosto();
        }
        return this.costo;
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
        return this.duracion;
    }

    private double setDuracion(LinkedList<Atraccion> atracciones) {
        for (var atraccion : atracciones) {
            this.duracion += atraccion.getDuracion();
        }
        return this.duracion;
    }

    @Override
    public boolean esPromo() {
        return true;
    }

    public LinkedList<Atraccion> getAtracciones() {
        return this.atracciones;
    }

    // extraigo solo los nombres de las atracciones incluidas en la promo para imprimir por toString
    public LinkedList<String> nombresAtracciones(LinkedList<Atraccion> atracciones) {
        LinkedList<String> nombres = new LinkedList<>();
        for (var atraccion : atracciones) {
            nombres.add(atraccion.getNombre());
        }
        return nombres;
    }

    @Override
    public boolean hayCupo() {
        // verifica que las atracciones que esten icluidas en la promo tengan cupos disponibles
        int hayCupo = 1;
        for (var atraccion : atracciones) {
            if (!atraccion.hayCupo())
                hayCupo++;
        }
        return hayCupo == 1;
    }

    @Override
    public void reservarCupo() {
        if (hayCupo()) {
            for (var atraccion : this.atracciones) {
                atraccion.reservarCupo();
            }
        }
    }

    @Override
    public boolean yaSeCompro(Ofertable oferta) {
        boolean esPromo = false;
        for (var atraccion : atracciones) {
            if (oferta.esPromo()) {
                esPromo = oferta.yaSeCompro(atraccion);
            } else {
                esPromo = atraccion == oferta;
            }
        }
        return esPromo;
    }

    public abstract double costoTotal();

    @Override
    public String toString() {
        return "PROMO: " + getNombre() + " | atracciones: " + nombresAtracciones(getAtracciones()) + " | tipo: " + getGenero() + " |  cuesta: " + df.format(getCosto()) + " | dura: " + df.format(getDuracion());
    }
}
