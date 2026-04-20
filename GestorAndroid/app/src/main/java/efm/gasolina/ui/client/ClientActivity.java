package efm.gasolina.ui.client;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import efm.gasolina.R;
import efm.gasolina.ui.client.history.MovementsHistoryActivity;
import efm.gasolina.ui.client.prices.GasPricesActivity;

public class ClientActivity extends AppCompatActivity {

    private MaterialButton btnRevisionPrecios, btnHistorialMovimientos;
    private String operatorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_client);

        operatorEmail = getIntent().getStringExtra("email");

        btnRevisionPrecios = findViewById(R.id.btn_revision_precios);
        btnHistorialMovimientos = findViewById(R.id.btn_historial_movimientos);

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
    }
}