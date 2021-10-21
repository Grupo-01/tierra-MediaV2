package dao;

import excepciones.*;
import modelo.*;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;


public class ManejoArchivosImpl implements ManejoArchivos {

    // para el recorrido del archivo
    // lee una linea del archivo
    private String[] datosDeLinea = null;


    public Queue<Usuario> listarUsuarios(String nombreArchivo) throws LecturaDatosException {

        Queue<Usuario> usuarios = new LinkedList<>();
        File archivo = new File(nombreArchivo);
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(archivo));

            String linea = br.readLine();
            int index = 1;

            while (linea != null) {
                crearUsuario(usuarios, linea, index, archivo);

                // continua con la suiguiente linea
                linea = br.readLine();
                // aumenta el numero de linea del archivo pasado por parametro
                index++;
            }
        } catch (FileNotFoundException e) {
            throw new LecturaDatosException("Problema al encontrar el archivo " + "'" + archivo + "'", e.getCause());
        } catch (IOException e) {
            throw new LecturaDatosException("Problema al cargar el archivo " + "'" + archivo + "'" + e.getCause());
        } finally {
            cerrarArchivo(br);
        }
        return usuarios;
    }


    private void crearUsuario(Queue<Usuario> usuarios, String linea, int index, File archivo) throws LecturaDatosException {

        TipoAtraccion gusto;
        String nombre;
        double tiempoDisponible;
        int presupuesto;

        // separa los datos
        datosDeLinea = linea.split(";");

        //si no coincide con las datos esperados por linea
        if (datosDeLinea.length != 4)
            throw new LecturaDatosException("Problema al leer datos(" + archivo + ":" + index + ")");


        try {
            // guardo en var tmp
            nombre = datosDeLinea[0];
            presupuesto = Integer.parseInt(datosDeLinea[1]);
            tiempoDisponible = Double.parseDouble(datosDeLinea[2]);
            gusto = TipoAtraccion.valueOf(datosDeLinea[3].toUpperCase());
        } catch (Exception e) {
            throw new LecturaDatosException("Problemas con el tipo de dato(" + archivo + ":" + index + ")", e.getCause());
        }

        // crea un usuario
        Usuario usuario = new Usuario(nombre, presupuesto, tiempoDisponible, gusto);
        //lo asigno a la lista
        usuarios.offer(usuario);
    }


    //*******************************************************************************************************//
    @Override
    public Map<String, Atraccion> listarAtracciones(String nombreArchivo) throws LecturaDatosException {

        Map<String, Atraccion> atracciones = new HashMap<>();
        File archivo = new File(nombreArchivo);
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(archivo));

            String linea = br.readLine();
            int index = 1;

            while (linea != null) {

                crearAtraccion(atracciones, linea, index, archivo);

                // continua con la suiguiente linea
                linea = br.readLine();
                // aumenta el numero de linea del archivo pasado por parametro
                index++;
            }
        } catch (FileNotFoundException e) {
            throw new LecturaDatosException("Problema al encontrar el archivo " + "'" + archivo + "'", e.getCause());
        } catch (IOException e) {
            throw new LecturaDatosException("Problema al cargar el archivo " + "'" + archivo + "'" + e.getCause());
        } finally {
            cerrarArchivo(br);
        }
        return atracciones;
    }


    private void crearAtraccion(Map<String, Atraccion> atracciones, String linea, int index, File archivo) throws LecturaDatosException {

        String nombre;
        int costo;
        TipoAtraccion tipo;
        double tiempo;
        int cupo;

        // separa los datos
        datosDeLinea = linea.split(";");

        // si no coincide con los datos esperados
        if (datosDeLinea.length != 5)
            throw new LecturaDatosException("Problema al leer datos(" + archivo + ":" + index + ")");

        try {
            // guardo en var tmp
            nombre = datosDeLinea[0];
            costo = Integer.parseInt(datosDeLinea[1]);
            tiempo = Double.parseDouble(datosDeLinea[2]);
            cupo = Integer.parseInt(datosDeLinea[3]);
            tipo = TipoAtraccion.valueOf(datosDeLinea[4].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new LecturaDatosException("Problemas con el tipo de dato(" + archivo + ":" + index + ")", e.getCause());
        }

        // crea una atraccion
        Atraccion atraccion = new Atraccion(nombre, costo, tiempo, cupo, tipo);
        //lo asigno a la lista (clave = nombre de cada atraccion, valor = objeto Atraccion)
        atracciones.put(nombre, atraccion);
    }


    //*******************************************************************************************************//
    @Override
    public LinkedList<Promo> listarPromociones(String nombreArchivo, Map<String, Atraccion> atracciones) throws LecturaDatosException {

        LinkedList<Promo> promociones = new LinkedList<>();
        File archivo = new File(nombreArchivo);
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(archivo));

            String linea = br.readLine();
            int index = 1;

            while (linea != null) {
                try {
                    crearPromo(atracciones, promociones, archivo, linea, index);
                } catch (NumberFormatException e) {
                    System.err.println("Problema al leer datos(" + archivo + ":" + index + ") \n" + e.getMessage());
                }

                // continua con la suiguiente linea(si esxistiera)
                linea = br.readLine();
                // aumenta el numero de linea del archivo pasado por parametro
                index++;
            }
        } catch (FileNotFoundException e) {
            throw new LecturaDatosException("Problema al encontrar el archivo " + "'" + archivo + "'", e.getCause());
        } catch (IOException e) {
            throw new LecturaDatosException("Problema al cargar el archivo " + "'" + archivo + "'" + e.getCause());
        } finally {
            cerrarArchivo(br);
        }
        return promociones;
    }


    private void crearPromo(Map<String, Atraccion> atracciones, LinkedList<Promo> promociones, File archivo, String linea, int index) throws LecturaDatosException, NumberFormatException {

        String nombrePromo;
        String[] nombresAtraccionesPromo;
        TipoAtraccion tipoAtraccionPromo;
        String TipoPromo;

        // separa los datos
        datosDeLinea = linea.split(";");

        // verificar si faltan o hay mas datos de los esperados en linea
        for (String dato : datosDeLinea) {
            verificaDatosDeLinea(archivo, index, dato);
        }

        try {
            nombrePromo = datosDeLinea[0];
            nombresAtraccionesPromo = datosDeLinea[1].split(",");
            tipoAtraccionPromo = TipoAtraccion.valueOf(datosDeLinea[2].toUpperCase());
            TipoPromo = datosDeLinea[3];
        } catch (IllegalArgumentException e) {
            throw new LecturaDatosException("Problemas con el tipo de dato(" + archivo + ":" + index + ")\n", e.getCause());
        }

        LinkedList<Atraccion> listaAtraccionesPromo = new LinkedList<>();

        // recorre los nombres de las atracciones pasadas por el archivo.csv
        // recorre la lista de atracciones pasadas por parametro
        // verifica si los nombres de las atracciones y el tipo de atraccion coincidan
        for (String nombre : nombresAtraccionesPromo) {
            for (Map.Entry<String, Atraccion> atraccion : atracciones.entrySet()) {
                if (atraccion.getKey().contains(nombre) && atraccion.getValue().getGenero().equals(tipoAtraccionPromo)) {
                    listaAtraccionesPromo.add(atraccion.getValue());
                }
            }
        }

        // recibe por parametro el tipo de promo sacado del arvhivo.csv
        // verifica los casos posibles
        verificaTipoPromo(promociones, archivo, nombrePromo, tipoAtraccionPromo, TipoPromo, listaAtraccionesPromo);
    }


    private void verificaTipoPromo(LinkedList<Promo> promociones, File archivo, String nombrePromo, TipoAtraccion tipoAtraccionPromo, String TipoPromo, LinkedList<Atraccion> listaAtraccionesPromo) throws LecturaDatosException {
        switch (TipoPromo.toLowerCase()) {
            case "porcentual":
                // creo una promo
                // si la lista de atracciones no esta vacia
                if (!listaAtraccionesPromo.isEmpty()) {
                    Promo promoPorcentual = new Porcentual(nombrePromo, listaAtraccionesPromo, tipoAtraccionPromo, Integer.parseInt(datosDeLinea[4]));
                    promociones.add(promoPorcentual); // se la asigno a lista de promociones
                }
                break;
            case "absoluta":
                if (!listaAtraccionesPromo.isEmpty()) {
                    Promo promoAbsoluta = new Absoluta(nombrePromo, listaAtraccionesPromo, tipoAtraccionPromo, Integer.parseInt(datosDeLinea[4]));
                    promociones.add(promoAbsoluta);
                }
                break;
            case "axb":
                // si la lista no esta vacia y al menos hay 2 atracciones en la promo
                if (!listaAtraccionesPromo.isEmpty() && listaAtraccionesPromo.size() > 1) {
                    Promo promoAxB = new AxB(nombrePromo, listaAtraccionesPromo, tipoAtraccionPromo);
                    promociones.add(promoAxB);
                }
                break;
            default:
                throw new LecturaDatosException("Valor inesperado, no existe el tipo de promo especificado: " + TipoPromo + ". " + archivo);
        }
    }


    private void verificaDatosDeLinea(File archivo, int index, String dato) throws LecturaDatosException {
        if (dato.equalsIgnoreCase("Porcentual") || dato.equalsIgnoreCase("Absoluta")) {
            if (datosDeLinea.length != 5)
                throw new LecturaDatosException("Problema al leer datos(" + archivo + ":" + index + ")");
        } else if (dato.equalsIgnoreCase("AxB")) {
            if (datosDeLinea.length != 4)
                throw new LecturaDatosException("Problema al leer datos(" + archivo + ":" + index + ")");
        } else if (datosDeLinea.length <= 3)
            throw new LecturaDatosException("Problema al leer datos(" + archivo + ":" + index + ")");
    }


    //*******************************************************************************************************//
    private void cerrarArchivo(BufferedReader br) {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void crearArchivo(String nombreArchivo) throws AccesoDatosException {
        File archivo = new File(nombreArchivo);

        try {
            PrintWriter salida = new PrintWriter(new FileWriter(archivo));
            salida.close();
            System.out.println("Archivo creado con su resumen de productos");
        } catch (IOException e) {
            throw new AccesoDatosException("Problema al crear el archivo " + e.getCause());
        }
    }


    @Override
    public void escribir(Usuario usuario, String nombreArchivo, double costoTotal, double duracionTotal) throws EscrituraDatosException {
        File archivo = new File(nombreArchivo);
        try {
            var salida = new PrintWriter(new FileWriter(archivo));
            imprimir(usuario, costoTotal, duracionTotal, salida);

            salida.close();
        } catch (IOException e) {
            throw new EscrituraDatosException("Problema al escribir el archivo " + "'" + archivo + "'", e.getCause());
        }
    }


    private void imprimir(Usuario usuario, double costoTotal, double duracionTotal, PrintWriter salida) {
        DecimalFormat df = new DecimalFormat("#0.00"); // para formatear los decimales a 2 cifras
        salida.println("------------------------------------------------------");
        salida.println("              RESUMEN DE ITINERARIO: ");
        salida.println("------------------------------------------------------");
        salida.println("DETALLES DE USUARIO: ");
        salida.println("Nombre de usuario: " + usuario.getNombre());
        salida.println("Presupuesto inicial: " + df.format(usuario.getCostoInicial()));
        salida.println("Presupuesto actual: " + df.format(usuario.getCostoActual()));
        salida.println("Tiempo disponible inicial: " + df.format(usuario.getTiempoInicial()));
        salida.println("Tiempo disponible Actual: " + df.format(usuario.getTiempoActual()));
        salida.println("Tipo de atraccion preferida: " + usuario.getGusto());
        salida.println("------------------------------------------------------");
        salida.println("DETALLES DE COMPRA: ");
        for (var compra : usuario.getCompradas()) {
            salida.println(compra);
        }
        salida.println("------------------------------------------------------");
        salida.println("DETALLES DE FACTURACION: ");
        salida.println("Costo total a pagar: " + df.format(costoTotal));
        salida.println("Duracion total promedio: " + df.format(duracionTotal));
    }
}
