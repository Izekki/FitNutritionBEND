package dto;

public class PeticionCodigoAcceso {
    private String codigoActual;
    private String codigoAcceso;

    public PeticionCodigoAcceso() {
    }

    public PeticionCodigoAcceso(String codigoActual, String codigoAcceso) {
        this.codigoActual = codigoActual;
        this.codigoAcceso = codigoAcceso;
    }

    public String getCodigoActual() {
        return codigoActual;
    }

    public void setCodigoActual(String codigoActual) {
        this.codigoActual = codigoActual;
    }

    public String getCodigoAcceso() {
        return codigoAcceso;
    }

    public void setCodigoAcceso(String codigoAcceso) {
        this.codigoAcceso = codigoAcceso;
    }
}
