package efm.gasolina.ui.prices;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import efm.gasolina.R;
import efm.gasolina.ui.login.LoginActivity;
import efm.gasolina.ui.recover.ChangePasswordViewModel;

public class GasPricesActivity extends AppCompatActivity {

    private Spinner zoneSpinner;
    private Spinner typeSpinner;
    private Button launchBtn;
    private TextView results;

    private GasPriceViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_prices);

        zoneSpinner = (Spinner) findViewById(R.id.zoneSpinner);
        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        results = (TextView)findViewById(R.id.textView4);
        launchBtn = findViewById(R.id.button);

        viewModel = new ViewModelProvider(this).get(GasPriceViewModel.class);

        viewModel.getGasPricesResult().observe(this, result -> {
            if (result.equals("OK")) {
                results.setText(viewModel.getResult().getValue());
            } else if (result.startsWith("ERROR")) {
                Toast.makeText(this,
                        result.substring(6),
                        Toast.LENGTH_SHORT).show();
            }
        });

        launchBtn.setOnClickListener(v -> {
            String zone = zoneSpinner.getSelectedItem().toString().trim();
            String type = typeSpinner.getSelectedItem().toString().trim();
            viewModel.getPricesByZoneAndType(zone, type);
        });
    }
}