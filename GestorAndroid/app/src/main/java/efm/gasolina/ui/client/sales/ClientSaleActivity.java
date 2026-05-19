package efm.gasolina.ui.client.sales;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import efm.gasolina.R;

public class ClientSaleActivity extends AppCompatActivity {

    private AutoCompleteTextView spinnerZona, spinnerBrand, spinnerFuel, spinnerVehicle;
    private TextInputEditText etVolume;
    private MaterialButton btnComprar;
    private ClientSaleViewModel viewModel;
    private String email;
    private Long clientId;

    private final Map<String, List<String>> zonaBrands = new LinkedHashMap<String, List<String>>() {{
        put("Ciudad Bolivar", Arrays.asList("Terpel", "Primax", "Biomax", "Petrobras"));
        put("Tunjuelito",     Arrays.asList("Terpel", "Primax", "Biomax"));
        put("Kennedy",        Arrays.asList("Terpel", "Primax", "Biomax"));
        put("Suba",           Arrays.asList("Terpel", "Primax", "Biomax", "Petrobras"));
        put("Bosa",           Arrays.asList("Terpel", "Biomax", "Petrobras"));
        put("Chapinero",      Arrays.asList("Terpel", "Primax", "Biomax", "Petrobras"));
        put("Teusaquillo",    Arrays.asList("Terpel", "Primax", "Biomax"));
    }};

    private final List<String> zonas     = new ArrayList<>();
    private final String[] fuels         = {"Corriente", "Diesel"};
    private final String[] vehicleTypes  = {"Moto", "Carro", "Camion"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_sale);

        spinnerZona    = findViewById(R.id.spinnerZona);
        spinnerBrand   = findViewById(R.id.spinnerBrand);
        spinnerFuel    = findViewById(R.id.spinnerFuel);
        spinnerVehicle = findViewById(R.id.spinnerVehicle);
        etVolume       = findViewById(R.id.etVolume);
        btnComprar     = findViewById(R.id.btnComprar);

        email    = getSharedPreferences("sesion", MODE_PRIVATE).getString("email", "");
        clientId = getSharedPreferences("sesion", MODE_PRIVATE).getLong("clientId", -1L);

        viewModel = new ViewModelProvider(this).get(ClientSaleViewModel.class);

        zonas.add("Seleccione una zona");
        zonas.addAll(zonaBrands.keySet());

        configurarSpinners();

        viewModel.getResult().observe(this, result -> {
            if (result.startsWith("OK:")) {
                Toast.makeText(this, result.substring(3), Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, result.substring(7), Toast.LENGTH_SHORT).show();
            }
        });

        btnComprar.setOnClickListener(v -> {
            String zona    = spinnerZona.getText().toString();
            String brand   = spinnerBrand.getText().toString();
            String fuel    = spinnerFuel.getText().toString();
            String vehicle = spinnerVehicle.getText().toString();
            String volStr  = etVolume.getText() != null ? etVolume.getText().toString() : "";

            if (zona.isEmpty() || zona.equals("Seleccione una zona")) {
                Toast.makeText(this, "Seleccione una zona", Toast.LENGTH_SHORT).show();
                return;
            }
            if (brand.isEmpty()) {
                Toast.makeText(this, "Seleccione una franquicia", Toast.LENGTH_SHORT).show();
                return;
            }
            if (fuel.isEmpty()) {
                Toast.makeText(this, "Seleccione el tipo de combustible", Toast.LENGTH_SHORT).show();
                return;
            }
            if (vehicle.isEmpty()) {
                Toast.makeText(this, "Seleccione el tipo de vehículo", Toast.LENGTH_SHORT).show();
                return;
            }
            if (volStr.isEmpty()) {
                Toast.makeText(this, "Ingrese el volumen", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.realizarCompra(email, brand, zona, fuel, vehicle,
                    Integer.parseInt(volStr));
        });
    }

    private void configurarSpinners() {
        spinnerZona.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, zonas));

        spinnerZona.setOnItemClickListener((parent, view, pos, id) -> {
            if (pos == 0) {
                spinnerBrand.setAdapter(null);
                spinnerBrand.setText("");
                return;
            }
            List<String> brands = zonaBrands.get(zonas.get(pos));
            spinnerBrand.setAdapter(new ArrayAdapter<>(this,
                    android.R.layout.simple_dropdown_item_1line, brands));
            spinnerBrand.setText("");
        });

        spinnerFuel.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, fuels));

        spinnerVehicle.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, vehicleTypes));
    }
}
