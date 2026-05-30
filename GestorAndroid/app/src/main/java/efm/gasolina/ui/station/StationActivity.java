package efm.gasolina.ui.station;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import efm.gasolina.R;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import efm.gasolina.ui.operator.AvailabilityActivity;
import efm.gasolina.ui.operator.OperatorActivity;
import efm.gasolina.ui.operator.PqrsOperatorActivity;
import efm.gasolina.ui.movimientos.MovementHistoryActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationActivity extends AppCompatActivity {

    private MaterialButton btnRevisionEntregas, btnRealizarVenta, btnDisponibilidad, btnHistorialMovimientos, btnPqrs, btnReporte;
    private String operatorEmail;
    private Long stationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);
        operatorEmail = getIntent().getStringExtra("email");

        btnRevisionEntregas     = findViewById(R.id.btn_revision_entregas);
        btnRealizarVenta        = findViewById(R.id.btn_realizar_venta);
        btnDisponibilidad       = findViewById(R.id.btn_disponibilidad);
        btnHistorialMovimientos = findViewById(R.id.btn_historial_movimientos);
        btnPqrs                 = findViewById(R.id.btn_pqrs);
        btnReporte              = findViewById(R.id.btn_reporte);

        stationId = getSharedPreferences("sesion", MODE_PRIVATE)
                .getLong("stationId", -1L);

        btnRevisionEntregas.setOnClickListener(v ->
                startActivity(new Intent(this, RevisionDeliveriesActivity.class)));

        btnRealizarVenta.setOnClickListener(v -> {
            Intent intent = new Intent(this, OperatorActivity.class);
            intent.putExtra("email", operatorEmail);
            startActivity(intent);
        });

        btnHistorialMovimientos.setOnClickListener(v -> {
            Intent intent = new Intent(this, MovementHistoryActivity.class);
            intent.putExtra("email", operatorEmail);
            startActivity(intent);
        });

        btnDisponibilidad.setOnClickListener(v -> {
            Intent intent = new Intent(this, AvailabilityActivity.class);
            intent.putExtra("email", operatorEmail);
            startActivity(intent);
        });

        btnPqrs.setOnClickListener(v ->
                startActivity(new Intent(this, PqrsOperatorActivity.class)));

        btnReporte.setOnClickListener(v -> generarReporte());
    }

    private void generarReporte() {
        if (stationId == -1L) {
            Toast.makeText(this, "Estación no identificada", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.generateStationReport(stationId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(StationActivity.this,
                            "✅ Reporte enviado al correo exitosamente", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(StationActivity.this,
                            "Error al generar reporte: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(StationActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
