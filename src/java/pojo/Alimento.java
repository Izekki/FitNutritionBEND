package pojo;

public class Alimento {
    private Integer idAlimento;
    private String nombreAlimento;
    private Double calorias;
    private String porcion;
    private Double proteinas;
    private Double carbohidratos;
    private Double grasas;

    public Alimento() {
    }

    public Alimento(Integer idAlimento, String nombreAlimento, Double calorias, String porcion, Double proteinas,
            Double carbohidratos, Double grasas) {
        this.idAlimento = idAlimento;
        this.nombreAlimento = nombreAlimento;
        this.calorias = calorias;
        this.porcion = porcion;
        this.proteinas = proteinas;
        this.carbohidratos = carbohidratos;
        this.grasas = grasas;
    }

    public Integer getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(Integer idAlimento) {
        this.idAlimento = idAlimento;
    }

    public String getNombreAlimento() {
        return nombreAlimento;
    }

    public void setNombreAlimento(String nombreAlimento) {
        this.nombreAlimento = nombreAlimento;
    }

    public Double getCalorias() {
        return calorias;
    }

    public void setCalorias(Double calorias) {
        this.calorias = calorias;
    }

    public String getPorcion() {
        return porcion;
    }

    public void setPorcion(String porcion) {
        this.porcion = porcion;
    }

    public Double getProteinas() {
        return proteinas;
    }

    public void setProteinas(Double proteinas) {
        this.proteinas = proteinas;
    }

    public Double getCarbohidratos() {
        return carbohidratos;
    }

    public void setCarbohidratos(Double carbohidratos) {
        this.carbohidratos = carbohidratos;
    }

    public Double getGrasas() {
        return grasas;
    }

    public void setGrasas(Double grasas) {
        this.grasas = grasas;
    }
}