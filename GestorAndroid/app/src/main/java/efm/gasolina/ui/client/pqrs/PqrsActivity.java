package efm.gasolina.ui.client.pqrs;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import efm.gasolina.R;

public class PqrsActivity extends AppCompatActivity {

    private AutoCompleteTextView spinnerBrand, spinnerTipo;
    private EditText etMensaje;
    private Button btnEnviar;
    private PqrsViewModel viewModel;
    private String email;

    private final String[] brands = {"Terpel", "Primax", "Biomax", "Petrobras"};
    private final String[] tipos  = {"Petición", "Queja", "Reclamo", "Sugerencia"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pqrs);

        spinnerBrand = findViewById(R.id.spinnerBrand);
        spinnerTipo  = findViewById(R.id.spinnerTipo);
        etMensaje    = findViewById(R.id.etMensaje);
        btnEnviar    = findViewById(R.id.btnEnviar);

        email = getSharedPreferences("sesion", MODE_PRIVATE)
                .getString("email", "");

        viewModel = new ViewModelProvider(this).get(PqrsViewModel.class);

        configurarSpinners();

        viewModel.getResult().observe(this, result -> {
            if (result.startsWith("OK:")) {
                Toast.makeText(this, result.substring(3), Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, result.substring(7), Toast.LENGTH_SHORT).show();
            }
        });

        btnEnviar.setOnClickListener(v -> {
            String brand   = spinnerBrand.getText().toString();
            String tipo    = spinnerTipo.getText().toString();
            String mensaje = etMensaje.getText().toString().trim();

            if (brand.isEmpty()) {
                Toast.makeText(this, "Seleccione una franquicia", Toast.LENGTH_SHORT).show();
                return;
            }
            if (tipo.isEmpty()) {
                Toast.makeText(this, "Seleccione el tipo", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.enviarPqrs(email, brand, tipo, mensaje);
        });
    }

    private void configurarSpinners() {
        spinnerBrand.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, brands));

        spinnerTipo.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, tipos));
    }
}