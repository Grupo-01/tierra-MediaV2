package Excepciones;

public class AccesoDatosException extends Exception {

    public AccesoDatosException(String message) {
        super(message);
    }

    public AccesoDatosException(String message, Throwable cause) {
        super(message, cause);
    }
}
