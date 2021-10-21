package servicio;

import excepciones.*;
import modelo.Usuario;

public interface CatalogoProducto {

    void sugerir(Usuario usuario) throws Exception; // sugiere atraccion que coincida con sus preferencias

    void mostrarProductos(Usuario usuario) throws AccesoDatosException; // muestra itinerario

    void iniciarCatalogoProducto() throws Exception; // inicia el proograma
}
