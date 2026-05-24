package efm.gasolina.ui.operator;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import efm.gasolina.model.pqrs.Pqrs;
import efm.gasolina.model.pqrs.PqrsResponseRequest;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PqrsOperatorActivity extends AppCompatActivity {

    private RecyclerView rvPqrs;
    private TextView tvEmpty;
    private SwipeRefreshLayout swipeRefresh;
    private PqrsAdapter adapter;
    private ApiService apiService;
    private final List<Pqrs> pqrsList = new ArrayList<>();
    private String brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pqrs_operator);

        rvPqrs = findViewById(R.id.rv_pqrs);
        tvEmpty = findViewById(R.id.tv_empty);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        apiService = ApiClient.getClient().create(ApiService.class);

        brand = getSharedPreferences("sesion", MODE_PRIVATE).getString("brand", "");
        if (brand == null || brand.isEmpty()) {
            Toast.makeText(this, "Franquicia no identificada", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        adapter = new PqrsAdapter(pqrsList, this::mostrarDialogoRespuesta);
        rvPqrs.setLayoutManager(new LinearLayoutManager(this));
        rvPqrs.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener(this::cargarPendientes);
        cargarPendientes();
    }

    private void cargarPendientes() {
        swipeRefresh.setRefreshing(true);
        apiService.getPendingPqrs(brand).enqueue(new Callback<List<Pqrs>>() {
            @Override
            public void onResponse(Call<List<Pqrs>> call, Response<List<Pqrs>> response) {
                swipeRefresh.setRefreshing(false);
                pqrsList.clear();
                if (response.isSuccessful() && response.body() != null) {
                    pqrsList.addAll(response.body());
                }
                adapter.notifyDataSetChanged();
                tvEmpty.setVisibility(pqrsList.isEmpty() ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onFailure(Call<List<Pqrs>> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(PqrsOperatorActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarDialogoRespuesta(Pqrs pqrs) {
        EditText input = new EditText(this);
        input.setMinLines(4);
        input.setGravity(android.view.Gravity.TOP);
        input.setHint("Respuesta al cliente");

        new AlertDialog.Builder(this)
                .setTitle("Responder PQRS")
                .setMessage("Cliente: " + pqrs.getEmail() + "\n\n" + pqrs.getMensaje())
                .setView(input)
                .setPositiveButton("Enviar", (dialog, which) -> {
                    String respuesta = input.getText().toString().trim();
                    if (respuesta.isEmpty()) {
                        Toast.makeText(this, "La respuesta no puede estar vacía", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    responderPqrs(pqrs, respuesta);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void responderPqrs(Pqrs pqrs, String respuesta) {
        apiService.respondPqrs(pqrs.getId(), new PqrsResponseRequest(respuesta))
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            adapter.removeItem(pqrs);
                            tvEmpty.setVisibility(pqrsList.isEmpty() ? View.VISIBLE : View.GONE);
                            Toast.makeText(PqrsOperatorActivity.this,
                                    "Respuesta enviada", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PqrsOperatorActivity.this,
                                    "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(PqrsOperatorActivity.this,
                                "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
