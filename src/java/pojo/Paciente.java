package pojo;

public class Paciente {
    private Integer idPaciente;
    private Integer idMedico;
    private String nombrePaciente;
    private String apellidosPaciente;
    private String fechaNacimiento;
    private String genero;
    private Double peso;
    private Double estatura;
    private Double talla;
    private String email;
    private String telefono;
    private String domicilio;
    private String fotografia;
    private String codigoAcceso;
    private String estatus;

    public Paciente() {
        this.estatus = "Activo";
    }

    public Paciente(Integer idPaciente, Integer idMedico, String nombrePaciente,
            String apellidosPaciente, String fechaNacimiento, String genero, Double peso, Double estatura, Double talla,
            String email, String telefono, String domicilio, String fotografia, String codigoAcceso) {
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.nombrePaciente = nombrePaciente;
        this.apellidosPaciente = apellidosPaciente;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.peso = peso;
        this.estatura = estatura;
        this.talla = talla;
        this.email = email;
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.fotografia = fotografia;
        this.codigoAcceso = codigoAcceso;
        this.estatus = "Activo";
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

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getApellidosPaciente() {
        return apellidosPaciente;
    }

    public void setApellidosPaciente(String apellidosPaciente) {
        this.apellidosPaciente = apellidosPaciente;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getEstatura() {
        return estatura;
    }

    public void setEstatura(Double estatura) {
        this.estatura = estatura;
    }

    public Double getTalla() {
        return talla;
    }

    public void setTalla(Double talla) {
        this.talla = talla;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    public String getCodigoAcceso() {
        return codigoAcceso;
    }

    public void setCodigoAcceso(String codigoAcceso) {
        this.codigoAcceso = codigoAcceso;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}