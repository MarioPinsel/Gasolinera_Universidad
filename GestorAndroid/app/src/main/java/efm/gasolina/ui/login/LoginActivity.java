package efm.gasolina.ui.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import efm.gasolina.R;
import efm.gasolina.ui.recover.RecoverByEmailActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail    = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin   = findViewById(R.id.btnLogin);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        viewModel.getLoginSuccess().observe(this, user -> {
            Toast.makeText(this, "Welcome, role: " + user.getRol(), Toast.LENGTH_SHORT).show();
            // TODO: redirigir según rol
        });

        viewModel.getLoginError().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
      
        btnLogin.setOnClickListener(v -> viewModel.login(
                etEmail.getText().toString().trim(),
                etPassword.getText().toString().trim()
        ));
      
              TextView texto = findViewById(R.id.tvEnlace);
        texto.setOnClickListener(v ->
                startActivity(new Intent(this, RecoverByEmailActivity.class)));

    }
}