package testguiarduino;

import java.io.Serializable;

/**
 * Created by corperk on 03/07/17.
 */
public class Usuario implements Serializable {
    String nombre;
    String correo;

    public Usuario() {

    }

    public Usuario(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
