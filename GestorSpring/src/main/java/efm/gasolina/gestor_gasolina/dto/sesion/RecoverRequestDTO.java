package efm.gasolina.gestor_gasolina.dto.sesion;

public class RecoverRequestDTO {
    private String token;
    private String value;
    
    public RecoverRequestDTO(){
        
    }

    public String getToken() { return token; }
    public String getValue() { return value; }
    public void setToken(String email) { this.token = email; }
    public void setValue(String password) { this.value = password; }
}
