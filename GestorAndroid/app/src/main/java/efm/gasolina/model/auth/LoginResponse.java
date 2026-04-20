package efm.gasolina.model.auth;

public class LoginResponse {

    private String role;
    private String email;
    private Long idStation;

    public String getRol() { return role; }
    public String getEmail() { return email; }
    public Long getIdStation() {
        return idStation;
    }
}