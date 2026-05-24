package efm.gasolina.model.pqrs;

public class PqrsResponseRequest {
    private String respuesta;

    public PqrsResponseRequest(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getRespuesta() { return respuesta; }
}
