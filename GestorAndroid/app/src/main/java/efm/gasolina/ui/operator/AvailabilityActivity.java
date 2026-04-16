package efm.gasolina.ui.operator;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import efm.gasolina.R;

public class AvailabilityActivity extends AppCompatActivity {

    private String operatorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);

        operatorEmail = getIntent().getStringExtra("email");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer,
                        AvailabilityFragment.newInstance(operatorEmail))
                .commit();
    }
}