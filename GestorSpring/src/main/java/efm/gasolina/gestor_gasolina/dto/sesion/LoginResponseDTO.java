package efm.gasolina.gestor_gasolina.dto.sesion;

public class LoginResponseDTO {
    
    private String rol;

    public LoginResponseDTO(String rol) {
        this.rol = rol;
    }

    public String getRol() { return rol; }
}
