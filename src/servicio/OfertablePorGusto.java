package servicio;

import dominio.*;

import java.util.Comparator;
import java.util.LinkedList;

import static dominio.TipoAtraccion.*;

public class OfertablePorGusto implements Comparator<Ofertable> {

    private final Usuario usuario;

    public OfertablePorGusto(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int compare(Ofertable o1, Ofertable o2) {
        if (o1.getGenero() == this.usuario.getGusto() && o2.getGenero() == this.usuario.getGusto()) {
            // ambas son preferidas, compara con la siguiente (promo)
            return comparaPromo(o1, o2);
        } else if (o1.getGenero() != this.usuario.getGusto() && o2.getGenero() != this.usuario.getGusto()) {
            if (o1.esPromo() && o2.esPromo()) {
                // ambas son promos compara por costo
                return comparaCostoDuracion(o1, o2);
            } else if (!o1.esPromo() && !o2.esPromo()) {
                // ninguna es promo compara por costo
                return comparaCostoDuracion(o1, o2);
            } else {
                return -Boolean.compare(o1.esPromo(), o2.esPromo());
            }
        } else {
            // una preferida y la otra no
            if (o1.getGenero() == this.usuario.getGusto())
                return -1;
            return 1;
        }
    }

    private int comparaPromo(Ofertable o1, Ofertable o2) {
        if (o1.esPromo() && o2.esPromo()) {
            //ambas son promos, compara por costo
            return comparaCostoDuracion(o1, o2);
        } else {
            return -Boolean.compare(o1.esPromo(), o2.esPromo());
        }
    }

    private int comparaCostoDuracion(Ofertable o1, Ofertable o2) {

        if (Double.compare(o1.getCosto(), o2.getCosto()) == 0) {
            // mismo costo compara por tiempo finalmente
            return -Double.compare(o1.getDuracion(), o2.getDuracion());
        } else {
            return -Double.compare(o1.getCosto(), o2.getCosto());
        }
    }
}
