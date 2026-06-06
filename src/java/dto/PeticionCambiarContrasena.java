package dto;

public class PeticionCambiarContrasena {
    private Integer id;
    private String rol;
    private String contrasenaActual;
    private String contrasenaNueva;

    public PeticionCambiarContrasena() {
    }

    public PeticionCambiarContrasena(Integer id, String rol, String contrasenaActual, String contrasenaNueva) {
        this.id = id;
        this.rol = rol;
        this.contrasenaActual = contrasenaActual;
        this.contrasenaNueva = contrasenaNueva;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getContrasenaActual() {
        return contrasenaActual;
    }

    public void setContrasenaActual(String contrasenaActual) {
        this.contrasenaActual = contrasenaActual;
    }

    public String getContrasenaNueva() {
        return contrasenaNueva;
    }

    public void setContrasenaNueva(String contrasenaNueva) {
        this.contrasenaNueva = contrasenaNueva;
    }
}
