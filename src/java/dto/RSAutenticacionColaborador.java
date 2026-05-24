package dto;

import pojo.Colaborador;

public class RSAutenticacionColaborador {
    private Boolean error;
    private String mensaje;
    private Colaborador colaborador;
    private String token;

    public RSAutenticacionColaborador() {
}

public RSAutenticacionColaborador(Boolean error, String mensaje, Colaborador colaborador, String token) {
        this.error = error;
        this.mensaje = mensaje;
        this.colaborador = colaborador;
        package dto;

import pojo.Usuario;

public class RSAutenticacionColaborador {

private boolean error;
private String mensaje;
private String token;
package dto
;

import pojo.Usuario;

public class RSAutenticacionColaborador {

    private boolean error;
    private String mensaje;
    private String token;
    private Usuario usuario;

    public RSAutenticacionColaborador() {
    }

    public RSAutenticacionColaborador(boolean error, String mensaje, String token, Usuario usuario) {
        this.error = error;
        this.mensaje = mensaje;
        this.token = token;
        this.usuario = usuario;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
