package efm.gasolina.gestor_gasolina.dto.sesion;

public record LoginResponseDTO(
        String role,
        String email,
        Long idStation
) {
    public LoginResponseDTO(String role, String email, Long idStation) {
        this.role = role;
        this.email = email;
        this.idStation = idStation;
    }
}