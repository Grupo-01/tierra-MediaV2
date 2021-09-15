package datos;

import Excepciones.LecturaDatosException;
import dominio.*;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

import static dominio.TipoAtraccion.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ManejoArchivosTest {

    ManejoArchivosImpl datos;

    @Before
    public void setUp() throws Exception {
        datos = new ManejoArchivosImpl();
    }

    @Test
    public void leerUsuarios() throws LecturaDatosException {
        Queue<Usuario> usuariosCargadosPorArchivo = datos.listarUsuarios("usuarios.csv");
        LinkedList<Usuario> listaUsuarios = new LinkedList<>();

        listaUsuarios.add(new Usuario("Eowin", 100, 30, AVENTURA));
        listaUsuarios.add(new Usuario("Gandalf", 20, 50, PAISAJE));
        listaUsuarios.add(new Usuario("Sam", 36, 8, DEGUSTACION));
        listaUsuarios.add(new Usuario("Galadriel", 120, 1, PAISAJE));

        for (Usuario usr : listaUsuarios) {
            assertEquals(usr.toString(), Objects.requireNonNull(usuariosCargadosPorArchivo.poll()).toString());
        }
    }

    @Test
    public void leerAtracciones() throws LecturaDatosException {
        Map<String, Atraccion> atraccionesCargadasPorArchivo = datos.listarAtracciones("atracciones.csv");
        LinkedList<Atraccion> listaAtracciones = new LinkedList<>();

        // creo atracciones
        Atraccion atraccion1 = new Atraccion("Moria", 10, 2, 6, AVENTURA);
        Atraccion atraccion2 = new Atraccion("Minas Tirith", 5, 2.5, 25, PAISAJE);
        Atraccion atraccion3 = new Atraccion("La Comarca", 3, 6.5, 150, DEGUSTACION);
        Atraccion atraccion4 = new Atraccion("Mordor", 25, 3, 4, AVENTURA);
        Atraccion atraccion5 = new Atraccion("Abismo de Helm", 5, 2, 15, PAISAJE);
        Atraccion atraccion6 = new Atraccion("Lothorien", 35, 1, 30, DEGUSTACION);
        Atraccion atraccion7 = new Atraccion("Erebor", 12, 3, 32, PAISAJE);
        Atraccion atraccion8 = new Atraccion("Bosque Negro", 3, 4, 12, AVENTURA);

        // las agrego a una lista de atracciones
        listaAtracciones.add(atraccion1);
        listaAtracciones.add(atraccion2);
        listaAtracciones.add(atraccion3);
        listaAtracciones.add(atraccion4);
        listaAtracciones.add(atraccion5);
        listaAtracciones.add(atraccion6);
        listaAtracciones.add(atraccion7);
        listaAtracciones.add(atraccion8);

        // verifico que los nombres y los tipos coincidan con los que se pasa por archivo
        // si coinciden son iguales
        // si no caso contrario
        for (Atraccion unaAtraccion : listaAtracciones) {
            for (Map.Entry<String, Atraccion> listaAtraccion : atraccionesCargadasPorArchivo.entrySet()) {
                if (listaAtraccion.getKey().contains(unaAtraccion.getNombre()) && listaAtraccion.getValue().getGenero().equals(unaAtraccion.getGenero()))
                    assertEquals(listaAtraccion.getValue().toString(), unaAtraccion.toString());
                else
                    assertNotEquals(listaAtraccion.getValue(), unaAtraccion);
            }
        }
    }

    @Test
    public void leerPromociones() throws LecturaDatosException {
        Map<String, Atraccion> atraccionesCargadasPorArchivo = datos.listarAtracciones("atracciones.csv");
        LinkedList<Promo> promosCargadasPorArchivo = datos.listarPromociones("promociones.csv", atraccionesCargadasPorArchivo);
        LinkedList<Promo> promos = new LinkedList<>();

        // creo atracciones
        Atraccion atraccion1 = new Atraccion("Bosque Negro", 3, 4, 12, AVENTURA);
        Atraccion atraccion2 = new Atraccion("Mordor", 25, 3, 4, AVENTURA);
        Atraccion atraccion3 = new Atraccion("Lothorien", 35, 1, 30, DEGUSTACION);
        Atraccion atraccion4 = new Atraccion("La Comarca", 3, 6.5, 150, DEGUSTACION);
        Atraccion atraccion5 = new Atraccion("Minas Tirith", 5, 2.5, 25, PAISAJE);
        Atraccion atraccion6 = new Atraccion("Abismo de Helm", 5, 2, 15, PAISAJE);
        Atraccion atraccion7 = new Atraccion("Erebor", 12, 3, 32, PAISAJE);

        // creo packs para guardar esas atracciones
        LinkedList<Atraccion> packAtracciones1 = new LinkedList<>();
        LinkedList<Atraccion> packAtracciones2 = new LinkedList<>();
        LinkedList<Atraccion> packAtracciones3 = new LinkedList<>();

        // los almaceno en 3 lista diferentes para despues pasarselo al constructor de los nuevos objetos promo
        packAtracciones1.add(atraccion1);
        packAtracciones1.add(atraccion2);
        packAtracciones2.add(atraccion3);
        packAtracciones2.add(atraccion4);
        packAtracciones3.add(atraccion5);
        packAtracciones3.add(atraccion6);
        packAtracciones3.add(atraccion7);

        // creo 3 promos
        Promo promo1 = new Porcentual("Pack Aventura", packAtracciones1, AVENTURA, 20);
        Promo promo2 = new Absoluta("Pack Degustacion", packAtracciones2, DEGUSTACION, 36);
        Promo promo3 = new AxB("Pack Paisaje", packAtracciones3, PAISAJE);

        // las agrego en una lista
        promos.add(promo1);
        promos.add(promo2);
        promos.add(promo3);

        for (Promo promo : promosCargadasPorArchivo) {
            for (Promo p : promos) {
                if (p.getAtracciones().equals(promo.getAtracciones()))
                    assertEquals(p, promo);
            }
        }
    }

    // excepciones usuarios
    @Test(expected = LecturaDatosException.class)
    public void leerUsuariosCuando_NoExisteArchivo() throws LecturaDatosException {
        Queue<Usuario> usuariosCargadosPorArchivo = datos.listarUsuarios("usuari.csv");
    }

    @Test (expected = LecturaDatosException.class)
    public void leerUsuarios_ConMasDatos_DeLosEsperados() throws LecturaDatosException {
        Queue<Usuario> usuariosCargadosPorArchivo = datos.listarUsuarios("tests/datos/usuariosMASdatos.in");
        assertEquals(3, usuariosCargadosPorArchivo.size());
    }

    @Test (expected = LecturaDatosException.class)
    public void leerUsuarios_ConMenosDatos_DeLosEsperados() throws LecturaDatosException {
        Queue<Usuario> usuariosCargadosPorArchivo = datos.listarUsuarios("tests/datos/usuariosMENOSdatos.in");
        assertEquals(3,usuariosCargadosPorArchivo.size());
    }

    @Test (expected = LecturaDatosException.class)
    public void leerUsuarios_tipoDeDato_noCoincide() throws LecturaDatosException {
        Queue<Usuario> usuariosCargadosPorArchivo = datos.listarUsuarios("tests/datos/usuariosTIPOdesconocido.csv");
        assertEquals(3,usuariosCargadosPorArchivo.size());
    }

    // excepciones atracciones
    @Test (expected = LecturaDatosException.class)
    public void leerAtraccionesCuando_NoExisteArchivo() throws LecturaDatosException {
        Map<String, Atraccion> atraccionesCargadasPorArchivo = datos.listarAtracciones("atraccio.csv");
    }

    @Test (expected = LecturaDatosException.class)
    public void leerAtracciones_ConMasDatos_DeLosEsperados() throws LecturaDatosException {
        Map<String, Atraccion> atraccionesCargadasPorArchivo = datos.listarAtracciones("tests/datos/atraccionesMASdatos.in");
        assertEquals(4,atraccionesCargadasPorArchivo.size());
    }

    @Test (expected = LecturaDatosException.class)
    public void leerAtracciones_ConMenosDatos_DeLosEsperados() throws LecturaDatosException {
        Map<String, Atraccion> atraccionesCargadasPorArchivo = datos.listarAtracciones("tests/datos/atraccionesMENOSdatos.in");
        assertEquals(4,atraccionesCargadasPorArchivo.size());
    }

    // excepciones promos
    @Test (expected = LecturaDatosException.class)
    public void leerPromosCuando_NoExisteArchivo() throws LecturaDatosException {
        Map<String, Atraccion> atracciones = datos.listarAtracciones("atracciones.csv");
        LinkedList<Promo> promociones = datos.listarPromociones("promocion.in", atracciones);
    }

    @Test (expected = LecturaDatosException.class)
    public void leerPromos_ConMasDatos_DeLosEsperados() throws LecturaDatosException {
        Map<String, Atraccion> atracciones = datos.listarAtracciones("atracciones.csv");
        LinkedList<Promo> promociones = datos.listarPromociones("tests/datos/promosMASdatos.in", atracciones);
    }

    @Test (expected = LecturaDatosException.class)
    public void leerPromos_ConMenosDatos_DeLosEsperados() throws LecturaDatosException {
        Map<String, Atraccion> atracciones = datos.listarAtracciones("atracciones.csv");
        LinkedList<Promo> promociones = datos.listarPromociones("tests/datos/promosMENOSdatos.in", atracciones);
    }

    @Test
    public void leerPromo_CuandoNoSon_DelMismoTipo() throws LecturaDatosException {
        Map<String, Atraccion> atracciones = datos.listarAtracciones("atracciones.csv");
        LinkedList<Promo> promociones = datos.listarPromociones("tests/datos/promosDIFERENTESatraccion.in", atracciones);
    }

    @Test
    public void leerPromo_CuandoSusNombres_NoCoiciden() throws LecturaDatosException {
        Map<String, Atraccion> atracciones = datos.listarAtracciones("atracciones.csv");
        LinkedList<Promo> promociones = datos.listarPromociones("tests/datos/promosDIFERENTESnombre.in", atracciones);
    }

    @Test
    public void seAgregaUn_TipoDePromo_QueNoExiste() throws LecturaDatosException {
        Map<String, Atraccion> atracciones = datos.listarAtracciones("atracciones.csv");
        LinkedList<Promo> promociones = datos.listarPromociones("tests/datos/promosDIFERENTEStipo.in", atracciones);
    }
}