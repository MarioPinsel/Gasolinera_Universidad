package efm.gasolina.ui.developer;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import efm.gasolina.R;
import efm.gasolina.model.User;

public class DeveloperActivity extends AppCompatActivity {

    private DeveloperViewModel viewModel;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        RecyclerView recycler = findViewById(R.id.recyclerUsuarios);
        recycler.setLayoutManager(new LinearLayoutManager(this));

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

        viewModel.getUsuarios().observe(this, usuarios -> {
            adapter.updateList(usuarios);
        });

        viewModel.getActionResult().observe(this, result -> {
            Toast.makeText(this,
                    result.substring(result.indexOf(":") + 1),
                    Toast.LENGTH_SHORT).show();
        });

        viewModel.startPolling(10);
    }
}