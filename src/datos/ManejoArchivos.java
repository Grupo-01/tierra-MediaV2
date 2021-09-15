package datos;

import Excepciones.AccesoDatosException;
import Excepciones.EscrituraDatosException;
import Excepciones.LecturaDatosException;
import dominio.Atraccion;
import dominio.Promo;
import dominio.Usuario;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public interface ManejoArchivos {

    // agrega una cola de usarios con un archivo pasado por parametro
    Queue<Usuario> listarUsuarios(String nombreArchivo) throws LecturaDatosException;

    // agrega un lista de atracciones con un archivo pasado por parametro
    Map<String, Atraccion> listarAtracciones(String nombreArchivo) throws LecturaDatosException;

    // agrega una lista de promos con un archivo pasado por parametro
    LinkedList<Promo> listarPromociones(String nombreArchivo, Map<String, Atraccion> atracciones) throws LecturaDatosException;

    // crea un archivo en disco
    void crearArchivo(String nombreArchivo) throws AccesoDatosException;

    // Escribe en un archivo, 1 usuario con su respectivo itinerario
    void escribir(Usuario usuario, String nombreArchivo, double costoTotal, double duracionTotal) throws EscrituraDatosException;
}
