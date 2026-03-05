package efm.gasolina.ui.decrees;
public class DecretoModel {

    private String numDecreto;
    private String fechaExpedicion;
    private String fechaVigencia;
    private String zona;
    private String precio;

    // Auditoría
    private String cargadoPor;
    private String fechaCarga;
    private String ipCarga;

    public DecretoModel(String numDecreto, String fechaExpedicion, String fechaVigencia,
                        String zona, String precio,
                        String cargadoPor, String fechaCarga, String ipCarga) {
        this.numDecreto      = numDecreto;
        this.fechaExpedicion = fechaExpedicion;
        this.fechaVigencia   = fechaVigencia;
        this.zona            = zona;
        this.precio          = precio;
        this.cargadoPor      = cargadoPor;
        this.fechaCarga      = fechaCarga;
        this.ipCarga         = ipCarga;
    }

    // Getters
    public String getNumDecreto()      { return numDecreto; }
    public String getFechaExpedicion() { return fechaExpedicion; }
    public String getFechaVigencia()   { return fechaVigencia; }
    public String getZona()            { return zona; }
    public String getPrecio()          { return precio; }
    public String getCargadoPor()      { return cargadoPor; }
    public String getFechaCarga()      { return fechaCarga; }
    public String getIpCarga()         { return ipCarga; }

    @Override
    public String toString() {
        return "DecretoModel{" +
                "numDecreto='"      + numDecreto      + '\'' +
                ", fechaExpedicion='" + fechaExpedicion + '\'' +
                ", fechaVigencia='"   + fechaVigencia   + '\'' +
                ", zona='"            + zona            + '\'' +
                ", precio='"          + precio          + '\'' +
                ", cargadoPor='"      + cargadoPor      + '\'' +
                ", fechaCarga='"      + fechaCarga      + '\'' +
                ", ipCarga='"         + ipCarga         + '\'' +
                '}';
    }
}