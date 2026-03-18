package efm.gasolina.gestor_gasolina.handler;

public class ExceedsCapacityException extends RuntimeException {
    public ExceedsCapacityException() {
        super("Volume exceeds station capacity");
    }
}
