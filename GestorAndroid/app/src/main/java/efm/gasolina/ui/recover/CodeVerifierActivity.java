package efm.gasolina.ui.recover;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import efm.gasolina.R;

public class CodeVerifierActivity extends AppCompatActivity {

    private EditText etCode;
    private Button btnVerificar;
    private CodeVerifierViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verifier);

        etCode = findViewById(R.id.etCode);
        btnVerificar = findViewById(R.id.btnVerificar);

        viewModel = new ViewModelProvider(this)
                .get(CodeVerifierViewModel.class);

        // Observer reacciona al resultado de la petición
        viewModel.getRequestRecoverResult().observe(this, result -> {
            if (result.equals("OK")) {
                Intent intent = new Intent(this, ChangePasswordActivity.class);
                startActivity(intent);
                finish();
            } else if (result.startsWith("ERROR:")) {
                Toast.makeText(this,
                        result.substring(6),
                        Toast.LENGTH_SHORT).show();
            }
        });


        btnVerificar.setOnClickListener(v -> {
            String codigo = etCode.getText().toString().trim();
            viewModel.sendCode(codigo);
        });
    }
}