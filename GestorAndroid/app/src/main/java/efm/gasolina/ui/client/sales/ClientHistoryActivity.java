package efm.gasolina.ui.client.sales;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import efm.gasolina.R;
import efm.gasolina.model.clients.ClientHistoryDTO;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientHistoryActivity extends AppCompatActivity {

    private RecyclerView rvHistorial;
    private TextView tvEmpty;
    private ClientHistoryAdapter adapter;
    private final List<ClientHistoryDTO> historial = new ArrayList<>();
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_history);

        rvHistorial = findViewById(R.id.rv_historial);
        tvEmpty     = findViewById(R.id.tv_empty);
        apiService  = ApiClient.getClient().create(ApiService.class);

        String email = getSharedPreferences("sesion", MODE_PRIVATE)
                .getString("email", "");

        adapter = new ClientHistoryAdapter(historial);
        rvHistorial.setLayoutManager(new LinearLayoutManager(this));
        rvHistorial.setAdapter(adapter);

        apiService.getClientHistory(email).enqueue(new Callback<List<ClientHistoryDTO>>() {
            @Override
            public void onResponse(Call<List<ClientHistoryDTO>> call,
                                   Response<List<ClientHistoryDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    historial.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
                tvEmpty.setVisibility(historial.isEmpty() ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onFailure(Call<List<ClientHistoryDTO>> call, Throwable t) {
                Toast.makeText(ClientHistoryActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}