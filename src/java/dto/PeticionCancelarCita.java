package dto;

public class PeticionCancelarCita {
    private String motivo;

    public PeticionCancelarCita() {
    }

    public PeticionCancelarCita(String motivo) {
        this.motivo = motivo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
