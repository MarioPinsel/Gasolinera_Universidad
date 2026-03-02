package efm.gasolina.ui.recover;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import efm.gasolina.R;

public class RecoverByEmailActivity extends AppCompatActivity {
    private EditText textFiel;
    private Button btnEnviar;
    private RecoverByEmailViewModel recoverModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_by_email_code);

        textFiel = findViewById(R.id.etTexto);
        btnEnviar = findViewById(R.id.btnEnviar);
        recoverModel = new ViewModelProvider(this)
                .get(RecoverByEmailViewModel.class);

        recoverModel.getRequestRecoverResult().observe(this, result -> {
            if (result.startsWith("OK")) {
                startActivity(new Intent(this, CodeVerifierActivity.class));
                finish();
            } else {
                Toast.makeText(this,
                        result.substring(6), Toast.LENGTH_SHORT).show();
            }
        });

        btnEnviar.setOnClickListener(v -> {
            recoverModel.sendMail(
                    textFiel.getText().toString().trim()
            );
        });
    }
}
