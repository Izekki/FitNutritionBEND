package pojo;

public class Administrador {
    private Integer idAdministrador;
    private Integer idUsuario;

    public Administrador() {
    }

    public Administrador(Integer idAdministrador, Integer idUsuario) {
        this.idAdministrador = idAdministrador;
        this.idUsuario = idUsuario;
    }

    public Integer getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(Integer idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}