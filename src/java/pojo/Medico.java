package pojo;

public class Medico {
    private Integer idMedico;
    private Integer idUsuario;
    private Integer numPersonal;
    private String cedulaProfesional;
    private String nombreMedico;
    private String apellidosMedico;
    private String fechaNacimiento;
    private String genero;
    private String email;
    private String telefono;
    private String domicilio;
    private String fotografia;

    public Medico() {
    }

    public Medico(Integer idMedico, Integer idUsuario, Integer numPersonal, String cedulaProfesional,
            String nombreMedico, String apellidosMedico, String fechaNacimiento, String genero, String email,
            String telefono, String domicilio, String fotografia) {
        this.idMedico = idMedico;
        this.idUsuario = idUsuario;
        this.numPersonal = numPersonal;
        this.cedulaProfesional = cedulaProfesional;
        this.nombreMedico = nombreMedico;
        this.apellidosMedico = apellidosMedico;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.email = email;
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.fotografia = fotografia;
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getNumPersonal() {
        return numPersonal;
    }

    public void setNumPersonal(Integer numPersonal) {
        this.numPersonal = numPersonal;
    }

    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    public void setCedulaProfesional(String cedulaProfesional) {
        this.cedulaProfesional = cedulaProfesional;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getApellidosMedico() {
        return apellidosMedico;
    }

    public void setApellidosMedico(String apellidosMedico) {
        this.apellidosMedico = apellidosMedico;
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
}