package pojo;

public class Consulta {
    private Integer idConsulta;
    private Integer idPaciente;
    private Integer idMedico;
    private Integer idCita;
    private Integer idDieta;
    private String fecha;
    private Double pesoCapturado;
    private Double tallaCapturada;
    private Double imcCalculado;
    private String observaciones;

    public Consulta() {
    }

    public Consulta(Integer idConsulta, Integer idPaciente, Integer idMedico, Integer idCita, Integer idDieta,
            String fecha, Double pesoCapturado, Double tallaCapturada, Double imcCalculado, String observaciones) {
        this.idConsulta = idConsulta;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.idCita = idCita;
        this.idDieta = idDieta;
        this.fecha = fecha;
        this.pesoCapturado = pesoCapturado;
        this.tallaCapturada = tallaCapturada;
        this.imcCalculado = imcCalculado;
        this.observaciones = observaciones;
    }

    public Integer getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
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

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public Integer getIdDieta() {
        return idDieta;
    }

    public void setIdDieta(Integer idDieta) {
        this.idDieta = idDieta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getPesoCapturado() {
        return pesoCapturado;
    }

    public void setPesoCapturado(Double pesoCapturado) {
        this.pesoCapturado = pesoCapturado;
    }

    public Double getTallaCapturada() {
        return tallaCapturada;
    }

    public void setTallaCapturada(Double tallaCapturada) {
        this.tallaCapturada = tallaCapturada;
    }

    public Double getImcCalculado() {
        return imcCalculado;
    }

    public void setImcCalculado(Double imcCalculado) {
        this.imcCalculado = imcCalculado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}