package efm.gasolina.ui.client;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import efm.gasolina.R;
import efm.gasolina.ui.client.history.MovementsHistoryActivity;
import efm.gasolina.ui.client.pqrs.PqrsActivity;
import efm.gasolina.ui.client.prices.GasPricesActivity;

public class ClientActivity extends AppCompatActivity {

    private MaterialButton btnRevisionPrecios, btnHistorialMovimientos, btnPqrs,btnBuscarEstacion;
    private String operatorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        operatorEmail = getIntent().getStringExtra("email");

        btnRevisionPrecios      = findViewById(R.id.btn_revision_precios);
        btnHistorialMovimientos = findViewById(R.id.btn_historial_movimientos);
        btnPqrs                 = findViewById(R.id.btn_pqrs);
        btnBuscarEstacion = findViewById(R.id.btn_buscar_estacion);

        btnRevisionPrecios.setOnClickListener(v -> {
            Intent intent = new Intent(this, GasPricesActivity.class);
            intent.putExtra("email", operatorEmail);
            startActivity(intent);
        });

        btnHistorialMovimientos.setOnClickListener(v -> {
            Intent intent = new Intent(this, MovementsHistoryActivity.class);
            intent.putExtra("email", operatorEmail);
            startActivity(intent);
        });

        btnPqrs.setOnClickListener(v ->
                startActivity(new Intent(this, PqrsActivity.class)));

        btnBuscarEstacion.setOnClickListener(v -> {
            Intent intent = new Intent(this, efm.gasolina.ui.client.stations.StationMapActivity.class);
            intent.putExtra("email", operatorEmail);
            startActivity(intent);
        });
    }
}