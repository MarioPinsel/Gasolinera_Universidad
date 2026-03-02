package efm.gasolina.gestor_gasolina.dto.sesion;

public class LoginResponseDTO {
    
    private String role;

    public LoginResponseDTO(String role) {
        this.role = role;
    }

    public String getRol() { return role; }
}
