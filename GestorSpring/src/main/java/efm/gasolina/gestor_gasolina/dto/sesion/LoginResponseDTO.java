package efm.gasolina.gestor_gasolina.dto.sesion;

public record LoginResponseDTO(
        Long id,
        String role,
        String email,
        Long idStation,
        String name
) {
    public LoginResponseDTO(Long id, String role, String email, Long idStation, String name) {
        this.id = id;
        this.role = role;
        this.email = email;
        this.idStation = idStation;
        this.name = name;
    }
}