package pojo;

public class Dieta {
    private Integer idDieta;
    private String nombreDieta;
    private Double caloriasTotales;
    private String descripcion;
    private String estatusEdicion;

    public Dieta() {
    }

    public Dieta(Integer idDieta, String nombreDieta, Double caloriasTotales, String descripcion,
            String estatusEdicion) {
        this.idDieta = idDieta;
        this.nombreDieta = nombreDieta;
        this.caloriasTotales = caloriasTotales;
        this.descripcion = descripcion;
        this.estatusEdicion = estatusEdicion;
    }

    public Integer getIdDieta() {
        return idDieta;
    }

    public void setIdDieta(Integer idDieta) {
        this.idDieta = idDieta;
    }

    public String getNombreDieta() {
        return nombreDieta;
    }

    public void setNombreDieta(String nombreDieta) {
        this.nombreDieta = nombreDieta;
    }

    public Double getCaloriasTotales() {
        return caloriasTotales;
    }

    public void setCaloriasTotales(Double caloriasTotales) {
        this.caloriasTotales = caloriasTotales;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstatusEdicion() {
        return estatusEdicion;
    }

    public void setEstatusEdicion(String estatusEdicion) {
        this.estatusEdicion = estatusEdicion;
    }
}