package efm.gasolina.model;

public class NewPassword {
    private String pqasswordA;
    private String passwordB;

    public NewPassword(String passwordA, String passwordB) {
        this.pqasswordA = passwordA;
        this.passwordB = passwordB;
    }

    public String getPqasswordA() { return pqasswordA; }
    public String getPasswordB() { return passwordB; }
}
