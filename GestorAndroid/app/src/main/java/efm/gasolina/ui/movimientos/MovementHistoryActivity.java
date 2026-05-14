package efm.gasolina.ui.movimientos;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import efm.gasolina.R;

public class MovementHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_movimientos);

        String email    = getSharedPreferences("sesion", MODE_PRIVATE).getString("email", "");
        Long stationId  = getSharedPreferences("sesion", MODE_PRIVATE).getLong("stationId", -1L);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer,
                        MovementHistoryFragment.newInstance(email, stationId))
                .commit();
    }
}