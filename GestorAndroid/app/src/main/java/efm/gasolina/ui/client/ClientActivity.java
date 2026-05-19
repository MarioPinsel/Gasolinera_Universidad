package efm.gasolina.ui.client;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import efm.gasolina.R;
import efm.gasolina.ui.client.history.MovementsHistoryActivity;
import efm.gasolina.ui.client.pqrs.PqrsActivity;
import efm.gasolina.ui.client.prices.GasPricesActivity;
import efm.gasolina.ui.client.sales.ClientHistoryActivity;
import efm.gasolina.ui.client.sales.ClientSaleActivity;

public class ClientActivity extends AppCompatActivity {

    private MaterialButton btnRevisionPrecios, btnHistorialMovimientos, btnPqrs, btnComprar;
    private String operatorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        operatorEmail = getSharedPreferences("sesion", MODE_PRIVATE)
                .getString("email", "");

        btnRevisionPrecios      = findViewById(R.id.btn_revision_precios);
        btnHistorialMovimientos = findViewById(R.id.btn_historial_movimientos);
        btnPqrs                 = findViewById(R.id.btn_pqrs);
        btnComprar              = findViewById(R.id.btn_comprar);

        btnRevisionPrecios.setOnClickListener(v -> {
            Intent intent = new Intent(this, GasPricesActivity.class);
            intent.putExtra("email", operatorEmail);
            startActivity(intent);
        });

        btnHistorialMovimientos.setOnClickListener(v ->
                startActivity(new Intent(this, ClientHistoryActivity.class)));

        btnPqrs.setOnClickListener(v ->
                startActivity(new Intent(this, PqrsActivity.class)));

        btnComprar.setOnClickListener(v ->
                startActivity(new Intent(this, ClientSaleActivity.class)));
    }
}