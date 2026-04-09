package efm.gasolina.ui.station;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import efm.gasolina.R;
import efm.gasolina.ui.operator.OperatorActivity;
import efm.gasolina.ui.sales.HistorialMovimientos;
import efm.gasolina.ui.sales.HistorialMovimientosActivity;
import efm.gasolina.ui.sales.HistorialMovimientosFragment;

public class StationActivity extends AppCompatActivity {

    private MaterialButton btnRevisionEntregas, btnRealizarVenta, btnDisponibilidad, btnHistorialMovimientos;
    private String operatorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);
        operatorEmail = getIntent().getStringExtra("email");

        btnRevisionEntregas     = findViewById(R.id.btn_revision_entregas);
        btnRealizarVenta        = findViewById(R.id.btn_realizar_venta);
        btnDisponibilidad       = findViewById(R.id.btn_disponibilidad);
        btnHistorialMovimientos = findViewById(R.id.btn_historial_movimientos);

        btnRevisionEntregas.setOnClickListener(v ->
                startActivity(new Intent(this, RevisionEntregasActivity.class)));

        btnRealizarVenta.setOnClickListener(v -> {
            Intent intent = new Intent(this, OperatorActivity.class);
            intent.putExtra("email", operatorEmail);
            startActivity(intent);
        });

        btnHistorialMovimientos.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistorialMovimientosActivity.class);
            intent.putExtra("email", operatorEmail);
            startActivity(intent);
        });
    }
}