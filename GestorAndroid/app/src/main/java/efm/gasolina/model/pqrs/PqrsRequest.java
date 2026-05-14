package efm.gasolina.model.pqrs;

public class PqrsRequest {
    private String email;
    private String brand;
    private String tipo;
    private String mensaje;

    public PqrsRequest(String email, String brand, String tipo, String mensaje) {
        this.email   = email;
        this.brand   = brand;
        this.tipo    = tipo;
        this.mensaje = mensaje;
    }

    public String getEmail()   { return email; }
    public String getBrand()   { return brand; }
    public String getTipo()    { return tipo; }
    public String getMensaje() { return mensaje; }
}