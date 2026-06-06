package pojo;

public class DietaAlimento {
    private Integer idDietaAlimento;
    private Integer idDieta;
    private Integer idAlimento;
    private String porcion;
    private Double caloriasPorcion;
    private String tiempoComida;

    public DietaAlimento() {
    }

    public DietaAlimento(Integer idDietaAlimento, Integer idDieta, Integer idAlimento, String porcion,
            Double caloriasPorcion) {
        this.idDietaAlimento = idDietaAlimento;
        this.idDieta = idDieta;
        this.idAlimento = idAlimento;
        this.porcion = porcion;
        this.caloriasPorcion = caloriasPorcion;
    }

    public DietaAlimento(Integer idDietaAlimento, Integer idDieta, Integer idAlimento, String porcion,
            Double caloriasPorcion, String tiempoComida) {
        this.idDietaAlimento = idDietaAlimento;
        this.idDieta = idDieta;
        this.idAlimento = idAlimento;
        this.porcion = porcion;
        this.caloriasPorcion = caloriasPorcion;
        this.tiempoComida = tiempoComida;
    }

    public Integer getIdDietaAlimento() {
        return idDietaAlimento;
    }

    public void setIdDietaAlimento(Integer idDietaAlimento) {
        this.idDietaAlimento = idDietaAlimento;
    }

    public Integer getIdDieta() {
        return idDieta;
    }

    public void setIdDieta(Integer idDieta) {
        this.idDieta = idDieta;
    }

    public Integer getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(Integer idAlimento) {
        this.idAlimento = idAlimento;
    }

    public String getPorcion() {
        return porcion;
    }

    public void setPorcion(String porcion) {
        this.porcion = porcion;
    }

    public Double getCaloriasPorcion() {
        return caloriasPorcion;
    }

    public void setCaloriasPorcion(Double caloriasPorcion) {
        this.caloriasPorcion = caloriasPorcion;
    }

    public String getTiempoComida() {
        return tiempoComida;
    }

    public void setTiempoComida(String tiempoComida) {
        this.tiempoComida = tiempoComida;
    }
}