package app;

import excepciones.LecturaDatosException;
import servicio.CatalogoProducto;
import servicio.CatalogoProductoImpl;

public class Parque {
    public static void main(String[] args) throws Exception {
        CatalogoProducto producto = new CatalogoProductoImpl();

        System.out.println("**************************************");
        System.out.println("BIENVENIDO AL PARQUE TIERRA MEDIA");
        producto.iniciarCatalogoProducto();
        System.out.println("GRACIAS POR UTILIZAR EL SERVICIO DE ATRACCIONES DE TIERRA MEDIA");
        System.out.println("**************************************");
    }
}

