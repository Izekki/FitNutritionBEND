package dto;

public class RSImcApi {

    private boolean error;
    private String mensaje;
    private Double imc;
    private String clasificacion;

    public RSImcApi() {
    }

    public RSImcApi(boolean error, String mensaje, Double imc, String clasificacion) {
        this.error = error;
        this.mensaje = mensaje;
        this.imc = imc;
        this.clasificacion = clasificacion;
    }

    public boolean getError() {
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

    public Double getImc() {
        return imc;
    }

    public void setImc(Double imc) {
        this.imc = imc;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
}