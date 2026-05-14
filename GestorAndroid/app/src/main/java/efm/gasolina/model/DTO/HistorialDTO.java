package efm.gasolina.model.DTO;

public class HistorialDTO {
    private String tipo;
    private String placa;
    private Integer volumen;
    private Integer total;
    private String fecha;

    public String getTipo()     { return tipo; }
    public String getPlaca()    { return placa; }
    public Integer getVolumen() { return volumen; }
    public Integer getTotal()   { return total; }
    public String getFecha()    { return fecha; }
}