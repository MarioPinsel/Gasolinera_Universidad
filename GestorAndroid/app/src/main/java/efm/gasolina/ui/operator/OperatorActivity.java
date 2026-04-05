package efm.gasolina.ui.operator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import efm.gasolina.R;

public class OperatorActivity extends AppCompatActivity {

    private String operatorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);

        operatorEmail = getIntent().getStringExtra("email");

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout  = findViewById(R.id.tabLayout);

        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return RegisterSaleFragment.newInstance(operatorEmail);
            }

            @Override
            public int getItemCount() { return 1; }
        });

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText("Registrar venta");
        }).attach();
    }
}