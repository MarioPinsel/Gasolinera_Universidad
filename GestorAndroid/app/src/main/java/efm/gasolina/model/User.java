package efm.gasolina.model;

public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String verified;

    public User(String name, String email,
                String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getVerified() { return verified; }
}