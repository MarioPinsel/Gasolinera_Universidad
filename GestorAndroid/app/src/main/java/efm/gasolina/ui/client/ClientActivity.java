package efm.gasolina.ui.client;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import efm.gasolina.R;
import efm.gasolina.service.PqrsNotificationService;
import efm.gasolina.ui.client.history.MovementsHistoryActivity;
import efm.gasolina.ui.client.pqrs.PqrsActivity;
import efm.gasolina.ui.client.prices.GasPricesActivity;
import efm.gasolina.ui.client.sales.ClientHistoryActivity;
import efm.gasolina.ui.client.sales.ClientSaleActivity;


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
    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1001);
        }
    }

    private void startPqrsNotificationService() {
        if (operatorEmail == null || operatorEmail.isEmpty()) return;

        Intent intent = new Intent(this, PqrsNotificationService.class);
        intent.putExtra(PqrsNotificationService.EXTRA_EMAIL, operatorEmail);
        startService(intent);
    }
}
