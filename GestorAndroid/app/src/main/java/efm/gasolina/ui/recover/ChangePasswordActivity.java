package efm.gasolina.ui.recover;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import efm.gasolina.R;
import efm.gasolina.ui.login.LoginActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText passwordA;
    private EditText passwordB;
    private Button btnVerificar;
    private ChangePasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        passwordA = findViewById(R.id.password);
        passwordB = findViewById(R.id.confirmPassword);

        btnVerificar = findViewById(R.id.btnVerificar);

        viewModel = new ViewModelProvider(this)
                .get(ChangePasswordViewModel.class);

        // Observer reacciona al resultado de la petición
        viewModel.getRequestRecoverResult().observe(this, result -> {
            if (result.equals("OK")) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else if (result.startsWith("ERROR:")) {
                Toast.makeText(this,
                        result.substring(6),
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnVerificar.setOnClickListener(v -> {
            String password = passwordA.getText().toString().trim();
            String confirmPassword = passwordB.getText().toString().trim();
            viewModel.changePassword(password, confirmPassword);
        });
    }
}
