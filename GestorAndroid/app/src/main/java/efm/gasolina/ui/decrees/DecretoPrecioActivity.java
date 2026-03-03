package efm.gasolina.ui.decrees;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;
import java.util.*;

import efm.gasolina.R;

public class DecretoPrecioActivity extends AppCompatActivity {

    // --- UI ---
    private TextInputEditText etNumDecreto, etFechaExpedicion, etFechaVigencia, etPrecio;
    private TextInputLayout tilNumDecreto, tilFechaVigencia, tilPrecio;
    private Spinner spinnerZona;
    private TextView tvAudit, tvErrorVigencia, tvPrecioActual, tvAvisoReemplazo;
    private Button btnGuardar, btnCancelar;

    // --- Estado ---
    private Calendar fechaExpedicionCal;
    private Calendar fechaVigenciaCal;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "CO"));

    // Zonas y precios actuales (simulados — reemplazar con BD real)
    private final String[] zonas = {"Seleccione una zona", "Zona Centro", "Zona Norte", "Zona Sur", "Zona Oriente", "Zona Occidente"};
    private final Map<String, String> preciosActuales = new HashMap<String, String>() {{
        put("Zona Centro",    "15.800");
        put("Zona Norte",     "16.200");
        put("Zona Sur",       "15.500");
        put("Zona Oriente",   "16.000");
        put("Zona Occidente", "15.700");
    }};

    // Auditoría (simulada — en producción obtener de sesión real)
    private String usuarioActual = "admin@gasolinera.co";
    private String ipActual      = "192.168.1.10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ── Guard: solo administradores ──────────────────────────────
        // Reemplaza esta lógica con tu sistema de sesión real
        String rolUsuario = obtenerRolSesion();
        if (!"ADMIN".equals(rolUsuario)) {
            Toast.makeText(this, "Acceso denegado: solo administradores", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        // ─────────────────────────────────────────────────────────────

        setContentView(R.layout.activity_decreto_precio);
        bindViews();
        configurarAuditBanner();
        configurarSpinnerZona();
        configurarDatePickers();
        configurarBotones();
    }

    // ── Binding ──────────────────────────────────────────────────────

    private void bindViews() {
        etNumDecreto      = findViewById(R.id.et_num_decreto);
        etFechaExpedicion = findViewById(R.id.et_fecha_expedicion);
        etFechaVigencia   = findViewById(R.id.et_fecha_vigencia);
        etPrecio          = findViewById(R.id.et_precio);
        tilNumDecreto     = findViewById(R.id.til_num_decreto);
        tilFechaVigencia  = findViewById(R.id.til_fecha_vigencia);
        tilPrecio         = findViewById(R.id.til_precio);
        spinnerZona       = findViewById(R.id.spinner_zona);
        tvAudit           = findViewById(R.id.tv_audit);
        tvErrorVigencia   = findViewById(R.id.tv_error_vigencia);
        tvPrecioActual    = findViewById(R.id.tv_precio_actual);
        tvAvisoReemplazo  = findViewById(R.id.tv_aviso_reemplazo);
        btnGuardar        = findViewById(R.id.btn_guardar);
        btnCancelar       = findViewById(R.id.btn_cancelar);
    }

    // ── Auditoría ────────────────────────────────────────────────────

    private void configurarAuditBanner() {
        String ahora = sdf.format(new Date());
        tvAudit.setText(
                "👤 Usuario: " + usuarioActual + "\n" +
                        "🕐 Fecha carga: " + ahora + "\n" +
                        "🌐 IP: " + ipActual
        );
    }

    // ── Spinner de zonas ─────────────────────────────────────────────

    private void configurarSpinnerZona() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, zonas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerZona.setAdapter(adapter);

        spinnerZona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String zona = zonas[pos];
                if (pos == 0) {
                    tvPrecioActual.setVisibility(View.GONE);
                    tvAvisoReemplazo.setVisibility(View.GONE);
                } else {
                    mostrarPrecioActual(zona);
                    actualizarAvisoReemplazo(zona);
                }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void mostrarPrecioActual(String zona) {
        String precio = preciosActuales.get(zona);
        tvPrecioActual.setText("ℹ Precio actual en " + zona + ": $" + precio + " COP/galón");
        tvPrecioActual.setVisibility(View.VISIBLE);
    }

    private void actualizarAvisoReemplazo(String zona) {
        String precio = preciosActuales.get(zona);
        String vigencia = etFechaVigencia.getText() != null ? etFechaVigencia.getText().toString() : "—";
        tvAvisoReemplazo.setText(
                "⚠ Al guardar, el precio actual ($" + precio + " COP/galón) de " + zona +
                        " será reemplazado a partir del " + vigencia + "."
        );
        tvAvisoReemplazo.setVisibility(View.VISIBLE);
    }

    // ── DatePickers ──────────────────────────────────────────────────

    private void configurarDatePickers() {
        etFechaExpedicion.setOnClickListener(v -> mostrarDatePicker(false));
        etFechaVigencia.setOnClickListener(v -> mostrarDatePicker(true));
    }

    private void mostrarDatePicker(boolean esVigencia) {
        Calendar hoy = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, year, month, day) -> {
                    Calendar seleccionado = Calendar.getInstance();
                    seleccionado.set(year, month, day);

                    if (esVigencia) {
                        fechaVigenciaCal = seleccionado;
                        etFechaVigencia.setText(sdf.format(seleccionado.getTime()));
                        validarFechaVigencia();
                        // Actualizar aviso con nueva fecha
                        int pos = spinnerZona.getSelectedItemPosition();
                        if (pos > 0) actualizarAvisoReemplazo(zonas[pos]);
                    } else {
                        fechaExpedicionCal = seleccionado;
                        etFechaExpedicion.setText(sdf.format(seleccionado.getTime()));
                    }
                },
                hoy.get(Calendar.YEAR),
                hoy.get(Calendar.MONTH),
                hoy.get(Calendar.DAY_OF_MONTH)
        );

        // Para vigencia: mínimo hoy
        if (esVigencia) {
            dialog.getDatePicker().setMinDate(hoy.getTimeInMillis());
        }
        dialog.show();
    }

    // ── Validaciones ─────────────────────────────────────────────────

    /** Retorna true si la fecha de vigencia es válida (hoy o futura). */
    private boolean validarFechaVigencia() {
        if (fechaVigenciaCal == null) return false;

        Calendar hoy = Calendar.getInstance();
        hoy.set(Calendar.HOUR_OF_DAY, 0);
        hoy.set(Calendar.MINUTE, 0);
        hoy.set(Calendar.SECOND, 0);
        hoy.set(Calendar.MILLISECOND, 0);

        if (fechaVigenciaCal.before(hoy)) {
            tvErrorVigencia.setVisibility(View.VISIBLE);
            tilFechaVigencia.setError(" ");
            return false;
        }
        tvErrorVigencia.setVisibility(View.GONE);
        tilFechaVigencia.setError(null);
        return true;
    }

    private boolean validarFormulario() {
        boolean valido = true;

        if (TextUtils.isEmpty(etNumDecreto.getText())) {
            tilNumDecreto.setError("Campo obligatorio");
            valido = false;
        } else {
            tilNumDecreto.setError(null);
        }

        if (fechaExpedicionCal == null) {
            Toast.makeText(this, "Seleccione la fecha de expedición", Toast.LENGTH_SHORT).show();
            valido = false;
        }

        if (!validarFechaVigencia()) {
            valido = false;
        }

        if (spinnerZona.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Seleccione una zona", Toast.LENGTH_SHORT).show();
            valido = false;
        }

        if (TextUtils.isEmpty(etPrecio.getText())) {
            tilPrecio.setError("Campo obligatorio");
            valido = false;
        } else {
            tilPrecio.setError(null);
        }

        return valido;
    }

    // ── Botones ──────────────────────────────────────────────────────

    private void configurarBotones() {
        btnCancelar.setOnClickListener(v -> finish());
        btnGuardar.setOnClickListener(v -> {
            if (validarFormulario()) {
                mostrarConfirmacion();
            }
        });
    }

    private void mostrarConfirmacion() {
        String zona   = zonas[spinnerZona.getSelectedItemPosition()];
        String precio = etPrecio.getText().toString();
        String decreto = etNumDecreto.getText().toString();
        String vigencia = etFechaVigencia.getText().toString();

        new AlertDialog.Builder(this)
                .setTitle("Confirmar Decreto")
                .setMessage(
                        "¿Confirma cargar el decreto " + decreto + "?\n\n" +
                                "Zona: " + zona + "\n" +
                                "Nuevo precio: $" + precio + " COP/galón\n" +
                                "Vigente desde: " + vigencia + "\n\n" +
                                "Esta acción reemplazará el precio anterior para esta zona."
                )
                .setPositiveButton("Sí, guardar", (dialog, which) -> guardarDecreto())
                .setNegativeButton("Revisar", null)
                .show();
    }

    private void guardarDecreto() {
        // ── Modelo del decreto ────────────────────────────────────────
        DecretoModel decreto = new DecretoModel(
                etNumDecreto.getText().toString(),
                sdf.format(fechaExpedicionCal.getTime()),
                sdf.format(fechaVigenciaCal.getTime()),
                zonas[spinnerZona.getSelectedItemPosition()],
                etPrecio.getText().toString(),
                usuarioActual,
                sdf.format(new Date()),   // fecha/hora de carga
                ipActual
        );

        // TODO: enviar `decreto` a tu repositorio / API REST / BD local
        // Ej: viewModel.guardarDecreto(decreto);

        Toast.makeText(this,
                "✅ Decreto " + decreto.getNumDecreto() + " guardado correctamente",
                Toast.LENGTH_LONG).show();
        finish();
    }

    // ── Sesión (reemplazar con tu lógica real) ───────────────────────

    private String obtenerRolSesion() {
        // Ejemplo con SharedPreferences o token JWT decodificado
        return getSharedPreferences("sesion", MODE_PRIVATE)
                .getString("rol", "");
    }
}