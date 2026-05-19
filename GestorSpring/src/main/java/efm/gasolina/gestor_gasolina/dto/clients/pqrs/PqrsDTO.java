package efm.gasolina.gestor_gasolina.dto.clients.pqrs;

public record PqrsDTO(
        String email,
        String brand,
        String tipo,
        String mensaje
) {}