package efm.gasolina.model;

public class User {

    private String nombre;
    private String correo;
    private String password;
    private String rol;

    public User(String nombre, String correo,
                String password, String rol) {

        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
    }

    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getPassword() { return password; }
    public String getRol() { return rol; }
}