package efm.gasolina.ui.register;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import efm.gasolina.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNombre, etCorreo, etPassword;
    private AutoCompleteTextView spinnerRoles, spinnerZona, spinnerBrand;
    private Button btnRegistrar;
    private RegisterViewModel viewModel;

    private final Map<String, List<String>> zonaBrands = new LinkedHashMap<String, List<String>>() {{
        put("Ciudad Bolivar", Arrays.asList("Terpel", "Primax", "Biomax", "Petrobras"));
        put("Tunjuelito",     Arrays.asList("Terpel", "Primax", "Biomax"));
        put("Kennedy",        Arrays.asList("Terpel", "Primax", "Biomax"));
        put("Suba",           Arrays.asList("Terpel", "Primax", "Biomax", "Petrobras"));
        put("Bosa",           Arrays.asList("Terpel", "Biomax", "Petrobras"));
        put("Chapinero",      Arrays.asList("Terpel", "Primax", "Biomax", "Petrobras"));
        put("Teusaquillo",    Arrays.asList("Terpel", "Primax", "Biomax"));
    }};

    private final List<String> zonas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNombre     = findViewById(R.id.etNombre);
        etCorreo     = findViewById(R.id.etEmail);
        etPassword   = findViewById(R.id.etPassword);
        spinnerRoles = findViewById(R.id.spinnerRoles);
        spinnerZona  = findViewById(R.id.spinnerZona);
        spinnerBrand = findViewById(R.id.spinnerBrand);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        zonas.add("Seleccione una zona");
        zonas.addAll(zonaBrands.keySet());

        configurarSpinnerRoles();
        configurarSpinnerZona();

        viewModel.getRegisterResult().observe(this, result -> {
            if (result.startsWith("OK:")) {
                Toast.makeText(this, result.substring(3), Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, result.substring(7), Toast.LENGTH_SHORT).show();
            }
        });

        btnRegistrar.setOnClickListener(v -> {
            String rol = spinnerRoles.getText().toString();

            if (rol.isEmpty()) {
                Toast.makeText(this, "Seleccione un puesto de trabajo", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean requiereEstacion = !rol.equals("DISTRIBUIDOR") && !rol.equals("ADMINISTRADORLEGAL");

            if (requiereEstacion) {
                if (spinnerZona.getText().toString().isEmpty() ||
                        spinnerZona.getText().toString().equals("Seleccione una zona")) {
                    Toast.makeText(this, "Seleccione una zona", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (spinnerBrand.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Seleccione una franquicia", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            viewModel.register(
                    etNombre.getText().toString().trim(),
                    etCorreo.getText().toString().trim(),
                    etPassword.getText().toString().trim(),
                    rol,
                    spinnerZona.getText().toString(),
                    spinnerBrand.getText().toString()
            );
        });
    }

    private void configurarSpinnerRoles() {
        String[] roles = getResources().getStringArray(R.array.roles_usuario);
        ArrayAdapter<String> rolesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, roles);
        spinnerRoles.setAdapter(rolesAdapter);

        spinnerRoles.setOnItemClickListener((parent, view, pos, id) -> {
            String rolSeleccionado = roles[pos];
            if (rolSeleccionado.equals("DISTRIBUIDOR") || rolSeleccionado.equals("ADMINISTRADORLEGAL")) {
                spinnerZona.setVisibility(View.GONE);
                spinnerBrand.setVisibility(View.GONE);
                spinnerZona.setText("");
                spinnerBrand.setText("");
            } else {
                spinnerZona.setVisibility(View.VISIBLE);
                spinnerBrand.setVisibility(View.VISIBLE);
            }
        });
    }

    private void configurarSpinnerZona() {
        ArrayAdapter<String> zonaAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, zonas);
        spinnerZona.setAdapter(zonaAdapter);

        spinnerZona.setOnItemClickListener((parent, view, pos, id) -> {
            if (pos == 0) {
                spinnerBrand.setAdapter(null);
                spinnerBrand.setText("");
                return;
            }
            String zonaSeleccionada = zonas.get(pos);
            List<String> brands = zonaBrands.get(zonaSeleccionada);
            ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(
                    RegisterActivity.this,
                    android.R.layout.simple_dropdown_item_1line,
                    brands
            );
            spinnerBrand.setAdapter(brandAdapter);
            spinnerBrand.setText("");
        });
    }
}