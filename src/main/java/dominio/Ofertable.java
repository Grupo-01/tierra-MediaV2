package dominio;

public interface Ofertable {

    String getNombre();

    double getCosto();

    TipoAtraccion getGenero();

    double getDuracion();

    boolean esPromo();

    boolean hayCupo();

    boolean yaSeCompro(Ofertable oferta);

    void reservarCupo();

}
