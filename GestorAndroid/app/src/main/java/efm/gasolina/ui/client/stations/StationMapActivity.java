package efm.gasolina.ui.client.stations;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import efm.gasolina.R;
import efm.gasolina.model.sation.Station;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationMapActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_CODE = 100;

    private Spinner spinnerStations;
    private TextView tvUbicacion;
    private Button btnBuscarRuta;
    private FusedLocationProviderClient fusedLocationClient;
    private ApiService apiService;
    private List<Station> stationList = new ArrayList<>();
    private double currentLat = 0;
    private double currentLng = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_map);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        spinnerStations = findViewById(R.id.spinnerStations);
        tvUbicacion     = findViewById(R.id.tvUbicacion);
        btnBuscarRuta   = findViewById(R.id.btnBuscarRuta);

        apiService = ApiClient.getClient().create(ApiService.class);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        cargarEstaciones();
        pedirPermisoUbicacion();

        btnBuscarRuta.setOnClickListener(v -> buscarRuta());
    }

    private void cargarEstaciones() {
        apiService.getAllStations().enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call,
                                   Response<List<Station>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    stationList = response.body();
                    List<String> nombres = new ArrayList<>();
                    for (Station s : stationList) {
                        nombres.add(s.getBrand() + " - " + s.getZone());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            StationMapActivity.this,
                            android.R.layout.simple_spinner_item,
                            nombres
                    );
                    adapter.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item
                    );
                    spinnerStations.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                Toast.makeText(StationMapActivity.this,
                        "Error cargando estaciones", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pedirPermisoUbicacion() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            obtenerUbicacion();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_CODE
            );
        }
    }

    private void obtenerUbicacion() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) return;

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                currentLat = location.getLatitude();
                currentLng = location.getLongitude();
                tvUbicacion.setText(" Ubicación actual: " +
                        String.format("%.4f", currentLat) + ", " +
                        String.format("%.4f", currentLng));
            } else {
                tvUbicacion.setText(" Ubicación: no disponible");
            }
        });
    }

    private void buscarRuta() {
        if (stationList.isEmpty()) {
            Toast.makeText(this, "No hay estaciones disponibles",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int pos = spinnerStations.getSelectedItemPosition();
        Station station = stationList.get(pos);

        if (station.getAddress() == null || station.getAddress().isEmpty()) {
            Toast.makeText(this, "Esta estación no tiene dirección registrada",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String destino = station.getAddress().replace(" ", "+");

        Uri uri;
        if (currentLat != 0 && currentLng != 0) {
            uri = Uri.parse(
                    "https://www.google.com/maps/dir/?api=1" +
                            "&origin=" + currentLat + "," + currentLng +
                            "&destination=" + destino +
                            "&travelmode=driving"
            );
        } else {
            uri = Uri.parse(
                    "https://www.google.com/maps/search/?api=1&query=" + destino
            );
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            intent.setPackage(null);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerUbicacion();
            } else {
                tvUbicacion.setText(" Permiso de ubicación denegado");
                Toast.makeText(this,
                        "Sin permiso de ubicación la ruta se calculará solo con el destino",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}