package dto;

import pojo.Usuario;

public class RSAutenticacionUsuario {

    private boolean error;
    private String mensaje;
    private String token;
    private Usuario usuario;

    public RSAutenticacionUsuario() {
    }

    public RSAutenticacionUsuario(boolean error, String mensaje, String token, Usuario usuario) {
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