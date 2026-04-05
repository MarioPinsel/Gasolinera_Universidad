package efm.gasolina.gestor_gasolina.dto.sesion;

public class LoginResponseDTO {
    private String role;
    private String email; 

    public LoginResponseDTO(String role, String email) {
        this.role = role;
        this.email = email; 
    }

    public String getRole() { return role; }
    public String getEmail() { return email; }
}
