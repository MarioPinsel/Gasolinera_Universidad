package efm.gasolina.ui.station;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import efm.gasolina.R;
import efm.gasolina.model.Delivery;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevisionEntregasActivity extends AppCompatActivity {

    private RecyclerView rvDeliveries;
    private TextView tvEmpty;
    private SwipeRefreshLayout swipeRefresh;
    private StationDeliveryAdapter adapter;
    private final List<Delivery> deliveries = new ArrayList<>();
    private ApiService apiService;
    private Long stationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision_entregas);

        rvDeliveries = findViewById(R.id.rv_deliveries);
        tvEmpty      = findViewById(R.id.tv_empty);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        apiService   = ApiClient.getClient().create(ApiService.class);

        stationId = getSharedPreferences("sesion", MODE_PRIVATE)
                .getLong("stationId", -1L);

        if (stationId == -1L) {
            Toast.makeText(this, "Estación no identificada", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        adapter = new StationDeliveryAdapter(deliveries, new StationDeliveryAdapter.OnAction() {
            @Override
            public void onAccept(Delivery delivery) {
                confirmarAccion(delivery, true);
            }
            @Override
            public void onReject(Delivery delivery) {
                confirmarAccion(delivery, false);
            }
        });

        rvDeliveries.setLayoutManager(new LinearLayoutManager(this));
        rvDeliveries.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener(() -> {
            deliveries.clear();
            adapter.notifyDataSetChanged();
            cargarEntregas(stationId);
        });

        cargarEntregas(stationId);
    }

    private void cargarEntregas(Long stationId) {
        apiService.getPendingDeliveries(stationId).enqueue(new Callback<List<Delivery>>() {
            @Override
            public void onResponse(Call<List<Delivery>> call, Response<List<Delivery>> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    deliveries.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
                tvEmpty.setVisibility(deliveries.isEmpty() ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onFailure(Call<List<Delivery>> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(RevisionEntregasActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void confirmarAccion(Delivery delivery, boolean aceptar) {
        String accion = aceptar ? "aceptar" : "rechazar";
        new AlertDialog.Builder(this)
                .setTitle("Confirmar")
                .setMessage("¿Deseas " + accion + " la entrega del vehículo " + delivery.getVehicle() + "?")
                .setPositiveButton("Sí", (dialog, which) -> ejecutarAccion(delivery, aceptar))
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void ejecutarAccion(Delivery delivery, boolean aceptar) {
        Call<Void> call = aceptar
                ? apiService.acceptDelivery(delivery.getId())
                : apiService.rejectDelivery(delivery.getId());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    adapter.removeItem(delivery);
                    tvEmpty.setVisibility(deliveries.isEmpty() ? View.VISIBLE : View.GONE);
                    String msg = aceptar ? "✅ Entrega aceptada" : "❌ Entrega rechazada";
                    Toast.makeText(RevisionEntregasActivity.this, msg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RevisionEntregasActivity.this,
                            "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RevisionEntregasActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}