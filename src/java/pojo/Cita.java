package pojo;

public class Cita {
    private Integer idCita;
    private Integer idPaciente;
    private Integer idMedico;
    private Integer idMedicoAnterior;
    private String fecha;
    private String hora;
    private String estado;
    private String observaciones;

    public Cita() {
    }

    public Cita(Integer idCita, Integer idPaciente, Integer idMedico, String fecha, String hora, String estado,
            String observaciones) {
        this.idCita = idCita;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    public Cita(Integer idCita, Integer idPaciente, Integer idMedico, Integer idMedicoAnterior, String fecha, String hora, String estado,
            String observaciones) {
        this.idCita = idCita;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.idMedicoAnterior = idMedicoAnterior;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public Integer getIdMedicoAnterior() {
        return idMedicoAnterior;
    }

    public void setIdMedicoAnterior(Integer idMedicoAnterior) {
        this.idMedicoAnterior = idMedicoAnterior;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}