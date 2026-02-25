package efm.gasolina.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import efm.gasolina.R;

public class LoginActivity extends AppCompatActivity {

    EditText etCorreo, etPassword;
    Button btnLogin;
    LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        viewModel = new ViewModelProvider(this)
                .get(LoginViewModel.class);

        viewModel.getLoginResult().observe(this, result -> {
            if (result.startsWith("OK:")) {
                String rol = result.substring(3);
                // Aquí navegas a la pantalla según el rol
                Toast.makeText(this,
                        "Bienvenido, rol: " + rol, Toast.LENGTH_SHORT).show();
                // TODO: redirigir según rol
            } else {
                Toast.makeText(this,
                        result.substring(7), Toast.LENGTH_SHORT).show();
            }
        });

        btnLogin.setOnClickListener(v -> {
            viewModel.login(
                    etCorreo.getText().toString().trim(),
                    etPassword.getText().toString().trim()
            );
        });
    }
}