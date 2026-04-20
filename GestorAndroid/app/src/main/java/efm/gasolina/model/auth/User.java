package efm.gasolina.model.auth;

import com.google.gson.annotations.SerializedName;

public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String verified;

    @SerializedName("zone")
    private String zona;

    @SerializedName("brand")
    private String brand;

    public User(String name, String email,
                String password, String role,
                String zona, String brand) {
        this.name     = name;
        this.email    = email;
        this.password = password;
        this.role     = role;
        this.zona     = zona;
        this.brand    = brand;
    }

    public Long getId()         { return id; }
    public String getName()     { return name; }
    public String getEmail()    { return email; }
    public String getPassword() { return password; }
    public String getRole()     { return role; }
    public String getVerified() { return verified; }
    public String getZona()     { return zona; }
    public String getBrand()    { return brand; }
}