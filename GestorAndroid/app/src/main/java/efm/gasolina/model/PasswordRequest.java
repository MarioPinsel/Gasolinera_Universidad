package efm.gasolina.model;

public class PasswordRequest {
    private String token;
    private String value;

    public PasswordRequest(String token, String value) {
        this.token = token;
        this.value = value;
    }

    public String getToken() { return token; }
    public String getValue() { return value; }
}
