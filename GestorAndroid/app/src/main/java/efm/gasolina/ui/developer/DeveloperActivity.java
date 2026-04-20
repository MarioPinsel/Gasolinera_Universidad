package efm.gasolina.ui.developer;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import efm.gasolina.R;
import efm.gasolina.model.auth.User;

public class DeveloperActivity extends AppCompatActivity {

    private DeveloperViewModel viewModel;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        // configura el RecyclerView
        RecyclerView recycler = findViewById(R.id.recyclerUsuarios);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        // crea el adapter con las acciones
        adapter = new UserAdapter(new ArrayList<>(),
                new UserAdapter.OnUserActionListener() {
                    @Override
                    public void onAceptar(User user) {
                        viewModel.aprobar(user.getId());
                    }
                    @Override
                    public void onRechazar(User user) {
                        viewModel.rechazar(user.getId());
                    }
                });

        recycler.setAdapter(adapter);

        viewModel = new ViewModelProvider(this)
                .get(DeveloperViewModel.class);

        // observa la lista y actualiza el adapter
        viewModel.getUsuarios().observe(this, usuarios -> {
            adapter.updateList(usuarios);
        });

        // observa resultados de aprobar/rechazar
        viewModel.getActionResult().observe(this, result -> {
            Toast.makeText(this,
                    result.substring(result.indexOf(":") + 1),
                    Toast.LENGTH_SHORT).show();
        });

        // inicia el polling cada 10 segundos
        viewModel.startPolling(10);
    }
}