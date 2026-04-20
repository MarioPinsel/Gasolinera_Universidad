package efm.gasolina.ui.decrees;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.*;

import efm.gasolina.R;

public class PriceDecreesActivity extends AppCompatActivity {

    private TextInputEditText etNumDecreto, etFechaExpedicion, etValor;
    private TextInputLayout tilNumDecreto, tilFechaExpedicion;
    private Spinner spinnerCombustible;
    private Button btnGuardar, btnCancelar;

    private Calendar fechaExpedicionCal;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "CO"));

    private PriceDecreesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_decrees);

        bindViews();
        configurarSpinner();
        configurarDatePicker();
        configurarViewModel();
        configurarBotones();
    }

    private void bindViews() {
        etNumDecreto       = findViewById(R.id.et_num_decreto);
        etFechaExpedicion  = findViewById(R.id.et_fecha_expedicion);
        etValor            = findViewById(R.id.et_valor);

        tilNumDecreto      = findViewById(R.id.til_num_decreto);
        tilFechaExpedicion = findViewById(R.id.til_fecha_expedicion);

        spinnerCombustible = findViewById(R.id.spinner_combustible);
        btnGuardar         = findViewById(R.id.btn_guardar);
        btnCancelar        = findViewById(R.id.btn_cancelar);
    }

    private void configurarSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_combustible,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCombustible.setAdapter(adapter);
    }

    private void configurarDatePicker() {
        etFechaExpedicion.setOnClickListener(v -> {
            Calendar hoy = Calendar.getInstance();

            new DatePickerDialog(this, (view, year, month, day) -> {
                fechaExpedicionCal = Calendar.getInstance();
                fechaExpedicionCal.set(year, month, day);

                etFechaExpedicion.setText(sdf.format(fechaExpedicionCal.getTime()));
                tilFechaExpedicion.setError(null);

            },
                    hoy.get(Calendar.YEAR),
                    hoy.get(Calendar.MONTH),
                    hoy.get(Calendar.DAY_OF_MONTH)
            ).show();
        });
    }

    private void configurarViewModel() {
        viewModel = new ViewModelProvider(this).get(PriceDecreesViewModel.class);

        viewModel.getIsLoading().observe(this, isLoading -> {
            btnGuardar.setText(isLoading ? "GUARDANDO..." : "Guardar Decreto");
            btnGuardar.setEnabled(!isLoading);
        });

        viewModel.getStatusMessage().observe(this, mensaje -> {
            if (mensaje == null) return;

            if (mensaje.equals("OK")) {
                Toast.makeText(this, "Decreto guardado correctamente", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, mensaje.replace("ERROR: ", ""), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configurarBotones() {

        btnCancelar.setOnClickListener(v -> finish());

        btnGuardar.setOnClickListener(v -> {

            String numDecreto = etNumDecreto.getText().toString().trim();
            String valorStr = etValor.getText().toString().trim();

            String tipoCombustible = spinnerCombustible.getSelectedItem() != null
                    ? spinnerCombustible.getSelectedItem().toString()
                    : "";

            boolean valido = true;

            if (TextUtils.isEmpty(numDecreto)) {
                tilNumDecreto.setError("Campo obligatorio");
                valido = false;
            } else tilNumDecreto.setError(null);

            if (fechaExpedicionCal == null) {
                tilFechaExpedicion.setError("Seleccione la fecha");
                valido = false;
            } else tilFechaExpedicion.setError(null);

            if (TextUtils.isEmpty(tipoCombustible)) {
                Toast.makeText(this, "Seleccione combustible", Toast.LENGTH_SHORT).show();
                valido = false;
            }

            if (TextUtils.isEmpty(valorStr)) {
                Toast.makeText(this, "Ingrese el valor", Toast.LENGTH_SHORT).show();
                valido = false;
            }

            if (!valido) return;

            new AlertDialog.Builder(this)
                    .setTitle("Confirmar")
                    .setMessage(
                            "¿Guardar decreto " + numDecreto + "?\n\n" +
                                    "Combustible: " + tipoCombustible + "\n" +
                                    "Valor: " + valorStr
                    )
                    .setPositiveButton("Sí", (d, w) -> {

                        try {
                            Integer valor = Integer.parseInt(valorStr);

                            viewModel.guardarDecreto(
                                    numDecreto,
                                    tipoCombustible,
                                    valor
                            );

                        } catch (Exception e) {
                            Toast.makeText(this, "Error en datos", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}