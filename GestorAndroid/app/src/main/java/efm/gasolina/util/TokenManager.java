package efm.gasolina.util;


import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static final String KEY_TOKEN = "token";
    private static final String PREFS_NAME = "sesion";
    private final SharedPreferences prefs;

    public TokenManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // Guardar token
    public void saveToken(String token) {
        prefs.edit().putString(KEY_TOKEN, token).apply();
    }

    // Obtener token
    public String getToken() {
        return prefs.getString(KEY_TOKEN, null);
    }

    // Eliminar token
    public void clearToken() {
        prefs.edit().remove(KEY_TOKEN).apply();
    }

    // Verificar si existe token
    public boolean hasToken() {
        return getToken() != null;
    }
}