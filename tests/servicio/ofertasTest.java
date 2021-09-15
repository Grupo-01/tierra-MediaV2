package servicio;

import dominio.Usuario;
import org.junit.Test;

import static dominio.TipoAtraccion.AVENTURA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ofertasTest {

    @Test
    public void sugiere_oferta_en_la_cual_el_usuario_no_pueda_costearse() throws Exception {
        CatalogoProducto catalogo = new CatalogoProductoImpl();

        // nuevo usuario
        var usr = new Usuario("Roberto", 0, 15, AVENTURA);
        assertFalse(usr.tieneSaldo());
//        catalogo.sugerir(usr);
    }

    @Test
    public void sugiere_oferta_en_la_cual_el_usuario_no_tiene_tiempo() throws Exception {
        CatalogoProducto catalogo = new CatalogoProductoImpl();
        // nuevo usuario
        var usr = new Usuario("Roberto", 40, 0, AVENTURA);
        assertFalse(usr.tieneTiempo());
//        catalogo.sugerir(usr);
    }
}
