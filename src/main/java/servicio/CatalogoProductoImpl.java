package servicio;

import excepciones.*;
import modelo.*;
import dao.*;

import java.util.*;

public class CatalogoProductoImpl implements CatalogoProducto {


    private final ManejoArchivos datos;
    private final Queue<Usuario> usuarios;
    private final Map<String, Atraccion> atracciones;
    private final LinkedList<Promo> promos;

    // constructor
    public CatalogoProductoImpl() throws LecturaDatosException {
        this.datos = new ManejoArchivosImpl();
        this.usuarios = datos.listarUsuarios("usuarios.csv");
        this.atracciones = datos.listarAtracciones("atracciones.csv");
        this.promos = datos.listarPromociones("promociones.csv", atracciones);
    }


    // getters
    private Queue<Usuario> getUsuarios() {
        return usuarios;
    }

    private Map<String, Atraccion> getAtracciones() {
        return atracciones;
    }

    private LinkedList<Promo> getPromos() {
        return promos;
    }


    @Override // para c/ usuario sugerir una atraccion que coincida con sus preferencias, costos y tiempos
    public void sugerir(Usuario usuario) {

        LinkedList<Ofertable> ofertas = this.crearOfertas();
        LinkedList<Ofertable> vendidos = new LinkedList<>();
        Scanner entrada = new Scanner(System.in);
        double tiempoDisponible = usuario.getTiempoActual();
        double presupuesto = usuario.getCostoActual();

        // ordena las atracciones
        ofertas.sort(new OfertablePorGusto(usuario));

        // recorre las ofertas disponibles y si tiene saldo/tiempo o mientras haya cupo lo agrega a una lista de vendidos
        try {
            agregarProductos(usuario, ofertas, vendidos, entrada, tiempoDisponible, presupuesto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // crea una lista de ofertas con las promos y las atracciones registradas.
    private LinkedList<Ofertable> crearOfertas() {
        LinkedList<Ofertable> ofertas = new LinkedList<>();
        ofertas.addAll(this.getPromos());
        ofertas.addAll(this.getAtracciones().values());
        return ofertas;
    }


    // agrega un producto a su itinerario
    private void agregarProductos(Usuario usuario, LinkedList<Ofertable> ofertas, LinkedList<Ofertable> vendidos, Scanner entrada, double tiempoDisponible, double presupuesto) throws Exception {
        for (var oferta : ofertas) {
            // agrego los productos que compro el usuario, inicialmente vacia
            usuario.setCompradas(vendidos);
            // si la lista de vendidos no contiene la oferta
            // si tiene tiempo y dinero para la oferta
            try {
                if (usuario.tieneSaldo() && usuario.tieneTiempo() && oferta.hayCupo()) {

                    verificaExcepcionesPosibles(usuario, vendidos, tiempoDisponible, presupuesto, oferta);
                    // menu de ofertas
                    // si el usuario quiere la oferta("s")
                    // descuenta el tiempo, el dinero y el cupo de la oferta y agrega la oferta a la lista de vendidos
                    // si el usuario no quiere la oferta("n")
                    // sigue con la siguiente oferta
                    // si se ingresa otra opcion lanza una excepcion y se repite el proceso
                    if (menuOfertas(usuario, entrada, oferta).equalsIgnoreCase("s")) {
                        usuario.setTiempoActual(tiempoDisponible -= oferta.getDuracion());
                        usuario.setCostoActual(presupuesto -= oferta.getCosto());
                        oferta.reservarCupo();
                        vendidos.add(oferta);
                    }
                }
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
    }

    private void verificaExcepcionesPosibles(Usuario usuario, LinkedList<Ofertable> vendidos, double tiempoDisponible, double presupuesto, Ofertable oferta) throws Exception {
        if (tiempoDisponible < oferta.getDuracion())
            throw new Exception(usuario.getNombre() + ": No tiene mas tiempo suficiente para realizar el pedido " + oferta.getNombre());
        if (presupuesto < oferta.getCosto())
            throw new Exception(usuario.getNombre() + ": No tiene mas saldo suficiente para realizar el pedido " + oferta.getNombre());
        if (!oferta.hayCupo())
            throw new Exception(usuario.getNombre() + ": Sin cupos para el pedido " + oferta.getNombre());
        try {
            for (var vendido : vendidos) {
                if (oferta.yaSeCompro(vendido))
                    throw new Exception(usuario.getNombre() + ": la promocion " + oferta.getNombre() + " ya ha sido incluida anteriormente en su lista de productos");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // muestra en consola los datos de usuario
    // ofrece una oferta ya ordenada
    // retorna una respuesta entre ("s") o ("n")
    // sino lanza una excepcion y repite el proceso
    private String menuOfertas(Usuario usuario, Scanner entrada, Ofertable oferta) {
        String respuesta;
        do {
            System.out.println("**************************************");
            System.out.println("NOMBRE DE USUARIO Y PREFERENCIAS: " + usuario);
            System.out.println("**************************************");
            System.out.println(">> DESEA GUARDAR ESTA OFERTA(S/N): ");
            System.out.print("   " + oferta + " ");
            respuesta = entrada.nextLine();
            if (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"))
                try {
                    throw new Exception("Opcion no valida intente devuelta(S/N)...");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));
        return respuesta;
    }


    @Override // muestra y crea un archivo con su respectivo itinerario
    public void mostrarProductos(Usuario usuario) throws AccesoDatosException {
        double price = 0;
        double time = 0;

        if (!usuario.getCompradas().toString().equals("[]")) {
            System.out.println("**************************************");
            System.out.println("RESUMEN DE ITINERARIO DE: " + usuario.getNombre());
            System.out.println(">> DETALLES DE COMPRA: ");
            for (var compra : usuario.getCompradas()) {
                System.out.println(compra);
                price += compra.getCosto();
                time += compra.getDuracion();
            }
            System.out.println("---------");
            System.out.println(">> DETALLES DE FACTURACION: ");
            System.out.println("COSTO TOTAL DE SU ITINERARIO: " + price);
            System.out.println("DURACION TOTAL DE SU ITINERARIO: " + time);
            datos.crearArchivo(usuario.getNombre() + "_Out" + ".txt");
            datos.escribir(usuario, usuario.getNombre() + "_Out" + ".txt", price, time);
            System.out.println("FINALIZADO...");
        }
    }


    @Override
    public void iniciarCatalogoProducto() throws Exception {
        Queue<Usuario> usuarios = getUsuarios();

        for (var usr : usuarios) {
            sugerir(usr);
        }

        while (!usuarios.isEmpty()) {
            mostrarProductos(usuarios.poll());
        }
    }
}
