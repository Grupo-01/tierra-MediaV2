package Excepciones;

public class LecturaDatosException extends AccesoDatosException {

    public LecturaDatosException(String message) {
        super(message);
    }

    public LecturaDatosException(String message, Throwable cause) {
        super(message, cause);
    }
}
