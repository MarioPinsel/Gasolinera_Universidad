package efm.gasolina.ui.movimientos;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import efm.gasolina.R;

public class HistorialMovimientosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_movimientos);

        String email = getIntent().getStringExtra("email");

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer,
                        HistorialMovimientosFragment.newInstance(email))
                .commit();
    }
}
