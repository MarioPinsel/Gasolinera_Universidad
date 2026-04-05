package efm.gasolina.ui.register;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import efm.gasolina.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNombre, etCorreo, etPassword;
    private Spinner spinnerRoles;
    private Button btnRegistrar;
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        spinnerRoles = findViewById(R.id.spinnerRoles);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        viewModel = new ViewModelProvider(this)
                .get(RegisterViewModel.class);

        // Observa el resultado y muestra Toast
        viewModel.getRegisterResult().observe(this, result -> {
            if (result.startsWith("OK:")) {
                Toast.makeText(this,
                        result.substring(3), Toast.LENGTH_LONG).show();
                finish(); // Regresa al MainActivity
            } else {
                Toast.makeText(this,
                        result.substring(7), Toast.LENGTH_SHORT).show();
            }
        });

        btnRegistrar.setOnClickListener(v -> {
            viewModel.register(
                    etNombre.getText().toString().trim(),
                    etCorreo.getText().toString().trim(),
                    etPassword.getText().toString().trim(),
                    spinnerRoles.getSelectedItem().toString()
            );
        });
    }
}