package efm.gasolina.gestor_gasolina.dto.station;

import jakarta.validation.constraints.NotNull;

public record StationRequestDTO(
        @NotNull String franchise,
        @NotNull String price
) {
    public StationRequestDTO(String franchise, Integer price) {
        this(franchise, "$" + price);
    }
}