package efm.gasolina.ui.station;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import efm.gasolina.R;

public class StationActivity extends AppCompatActivity {

    private MaterialButton btnRevisionEntregas, btnRealizarVenta,
            btnDisponibilidad, btnHistorialMovimientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        btnRevisionEntregas     = findViewById(R.id.btn_revision_entregas);
        btnRealizarVenta        = findViewById(R.id.btn_realizar_venta);
        btnDisponibilidad       = findViewById(R.id.btn_disponibilidad);
        btnHistorialMovimientos = findViewById(R.id.btn_historial_movimientos);

//        btnRevisionEntregas.setOnClickListener(v ->
//                startActivity(new Intent(this, RevisionEntregasActivity.class)));
//
//        btnRealizarVenta.setOnClickListener(v ->
//                startActivity(new Intent(this, RealizarVentaActivity.class)));
//
//        btnDisponibilidad.setOnClickListener(v ->
//                startActivity(new Intent(this, DisponibilidadActivity.class)));
//
//        btnHistorialMovimientos.setOnClickListener(v ->
//                startActivity(new Intent(this, HistorialMovimientosActivity.class)));
    }
}