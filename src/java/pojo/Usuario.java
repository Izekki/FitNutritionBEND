package pojo;

public class Usuario {
    private Integer idUsuario;
    private String password;
    private String rol;
    private Boolean estatus;

    public Usuario() {
    }

    public Usuario(Integer idUsuario, String password, String rol, Boolean estatus) {
        this.idUsuario = idUsuario;
        this.password = password;
        this.rol = rol;
        this.estatus = estatus;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(Boolean estatus) {
        this.estatus = estatus;
    }
}