package efm.gasolina.ui.wholesaler;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import efm.gasolina.R;

public class WholesalerActivity extends AppCompatActivity {

    private String distributorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wholesaler);

        distributorEmail = getIntent().getStringExtra("email");

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0: return RegisterDeliveryFragment.newInstance(distributorEmail);
                    case 1: return DeliveryHistoryFragment.newInstance(distributorEmail);
                    default: return RegisterDeliveryFragment.newInstance(distributorEmail);
                }
            }

            @Override
            public int getItemCount() { return 2; }
        });

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0: tab.setText("Registro"); break;
                case 1: tab.setText("Historial"); break;
            }
        }).attach();
    }
}