package servicio;

import dominio.*;
import org.junit.Test;

import java.util.LinkedList;

import static dominio.TipoAtraccion.*;
import static org.junit.Assert.assertEquals;

public class ordenarOfertaTest {

    @Test
    public void ordenaPorGusto_Prioridad_Promociones() {
        LinkedList<Ofertable> ofertas = new LinkedList<>();
        LinkedList<Atraccion> atraccionesPromo1 = new LinkedList<>();
        LinkedList<Atraccion> atraccionesPromo2 = new LinkedList<>();
        LinkedList<Atraccion> atraccionesPromo3 = new LinkedList<>();
        // creo un usuario nuevo
        Usuario usr = new Usuario("Eowin",10,8,AVENTURA);
        // creo las atracciones para cada oferta
        atraccionesPromo1.add(new Atraccion("Bosque Negro", 3, 4, 12, AVENTURA));
        atraccionesPromo1.add(new Atraccion("Mordor", 25, 3, 4, AVENTURA));
        atraccionesPromo2.add(new Atraccion("Lothorien", 35, 1, 30, DEGUSTACION));
        atraccionesPromo2.add(new Atraccion("La Comarca", 3, 6.5, 150, DEGUSTACION));
        atraccionesPromo3.add(new Atraccion("Minas Tirith", 5, 2.5, 25, PAISAJE));
        atraccionesPromo3.add(new Atraccion("Abismo de Helm", 5, 2, 15, PAISAJE));
        atraccionesPromo3.add(new Atraccion("Erebor", 12, 3, 32, PAISAJE));
        // creo una lista de ofertas
        ofertas.add(new Atraccion("Bosque Negro", 3, 4, 12, AVENTURA));
        ofertas.add(new Atraccion("Moria",10,2,6,AVENTURA));
        ofertas.add(new Absoluta("Pack Degustacion", atraccionesPromo2,DEGUSTACION, 36));
        ofertas.add(new Porcentual("Pack Aventura", atraccionesPromo1,AVENTURA, 20));
        ofertas.add(new Atraccion("Lothorien", 35, 1, 30, DEGUSTACION));
        ofertas.add(new Atraccion("Mordor", 25, 3, 4, AVENTURA));
        ofertas.add(new AxB("Pack Paisajes",atraccionesPromo3,PAISAJE));
        ofertas.add(new Atraccion("Minas Tirith", 5, 2.5, 25, PAISAJE));
        ofertas.add(new Atraccion("La Comarca", 3, 6.5, 150, DEGUSTACION));
        ofertas.add(new Atraccion("Erebor", 12, 3, 32, PAISAJE));
        ofertas.add(new Atraccion("Abismo de Helm", 5, 2, 15, PAISAJE));

        // imprime las ofertas antes de ser ordenadas
//        for (var oferta : ofertas) {
//            System.out.println(oferta);
//        }

//        System.out.println();

        // ordena las atracciones de acuerdo al tipo de proferencia
        ofertas.sort(new OfertablePorGusto(usr));

        // imprime las ofertas despues de ser ordenadas
//        for (var oferta : ofertas) {
//            System.out.println(oferta);
//        }

        assertEquals("Pack Aventura", ofertas.getFirst().getNombre()); // prioridad por oferta de promo y gustos
        assertEquals("La Comarca", ofertas.getLast().getNombre()); // oferta que no coincide con sus intereses
    }
}
