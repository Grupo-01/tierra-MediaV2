package modelo;

import java.text.DecimalFormat;
import java.util.LinkedList;

public class Usuario {
    private final double costo_inicial;
    private final double tiempo_inicial;
    private final String nombre;
    private double costoActual;
    private double tiempoActual;
    private final TipoAtraccion gusto;
    private LinkedList<Ofertable> compradas; // almacena los productos que compro el usuario
    private final DecimalFormat df = new DecimalFormat("#0.00"); // para formatear los decimales a 2 cifras

    public Usuario(String nombre, int costo, double tiempo, TipoAtraccion gusto) {
        this.nombre = nombre;
        this.costoActual = costo;
        this.costo_inicial = costo;
        this.tiempoActual = tiempo;
        this.tiempo_inicial = tiempo;
        this.gusto = gusto;
        this.compradas = new LinkedList<>();
    }

    public String getNombre() {
        return this.nombre;
    }

    public double getCostoInicial() {
        return this.costo_inicial;
    }

    public double getCostoActual() {
        return this.costoActual;
    }

    public void setCostoActual(double costoActual) {
        this.costoActual = costoActual;
    }

    public double getTiempoInicial() {
        return this.tiempo_inicial;
    }

    public double getTiempoActual() {
        return this.tiempoActual;
    }

    public void setTiempoActual(double tiempoActual) {
        this.tiempoActual = tiempoActual;
    }

    public TipoAtraccion getGusto() {
        return gusto;
    }

    public boolean tieneSaldo() {
        return this.costoActual > 0;
    }

    public boolean tieneTiempo() {
        return this.tiempoActual > 0;
    }

    public void setCompradas(LinkedList<Ofertable> compradas) {
        this.compradas = compradas;
    }

    public LinkedList<Ofertable> getCompradas() {
        return compradas;
    }

    @Override
    public String toString() {
        return "[USUARIO: " + this.nombre + " | presupuesto: " + df.format(this.costoActual) + " | tiempo disponible: " + df.format(this.tiempoActual) + " | gusto: " + this.gusto + "]";
    }
}
