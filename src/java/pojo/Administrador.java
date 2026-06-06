package pojo;

public class Administrador {
    private Integer idAdministrador;
    private String email;
    private String contrasena;
    private String nombreAdmin;

    public Administrador() {
    }

    public Administrador(Integer idAdministrador, String email, String contrasena, String nombreAdmin) {
        this.idAdministrador = idAdministrador;
        this.email = email;
        this.contrasena = contrasena;
        this.nombreAdmin = nombreAdmin;
    }

    public Integer getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(Integer idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombreAdmin() {
        return nombreAdmin;
    }

    public void setNombreAdmin(String nombreAdmin) {
        this.nombreAdmin = nombreAdmin;
    }
}