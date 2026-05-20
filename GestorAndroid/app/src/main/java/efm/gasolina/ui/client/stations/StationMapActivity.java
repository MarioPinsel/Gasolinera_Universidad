package efm.gasolina.ui.client.stations;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private TextView tvUbicacion, tvEstacionCercana;
    private Button btnBuscarRuta, btnEstacionCercana;
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

        spinnerStations    = findViewById(R.id.spinnerStations);
        tvUbicacion        = findViewById(R.id.tvUbicacion);
        tvEstacionCercana  = findViewById(R.id.tvEstacionCercana);
        btnBuscarRuta      = findViewById(R.id.btnBuscarRuta);
        btnEstacionCercana = findViewById(R.id.btnEstacionCercana);

        apiService = ApiClient.getClient().create(ApiService.class);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        cargarEstaciones();
        pedirPermisoUbicacion();

        btnBuscarRuta.setOnClickListener(v -> buscarRutaSeleccionada());
        btnEstacionCercana.setOnClickListener(v -> buscarEstacionMasCercana());
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

                try {
                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(
                            currentLat, currentLng, 1);

                    if (addresses != null && !addresses.isEmpty()) {
                        Address addr = addresses.get(0);
                        String barrio = addr.getSubLocality();
                        String ciudad = addr.getLocality();

                        String texto = (barrio != null ? barrio : "")
                                + (barrio != null && ciudad != null ? ", " : "")
                                + (ciudad != null ? ciudad : "");

                        tvUbicacion.setText("📍 Ubicacion: " + texto);
                    } else {
                        tvUbicacion.setText("📍 Ubicacion: "
                                + String.format(Locale.getDefault(),
                                "%.4f, %.4f", currentLat, currentLng));
                    }
                } catch (IOException e) {
                    tvUbicacion.setText("📍 Ubicacion: "
                            + String.format(Locale.getDefault(),
                            "%.4f, %.4f", currentLat, currentLng));
                }

            } else {
                tvUbicacion.setText("📍 Ubicacion no disponible");
            }
        });
    }

    private void buscarRutaSeleccionada() {
        if (stationList.isEmpty()) {
            Toast.makeText(this, "No hay estaciones disponibles",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Station station = stationList.get(spinnerStations.getSelectedItemPosition());
        abrirGoogleMaps(station);
    }

    private void buscarEstacionMasCercana() {
        if (currentLat == 0 && currentLng == 0) {
            Toast.makeText(this,
                    "Esperando ubicacion, intenta de nuevo",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (stationList.isEmpty()) {
            Toast.makeText(this, "No hay estaciones disponibles",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Station masCercana = null;
        float menorDistancia = Float.MAX_VALUE;

        for (Station s : stationList) {
            if (s.getLatitude() == null || s.getLongitude() == null) continue;

            float[] results = new float[1];
            Location.distanceBetween(
                    currentLat, currentLng,
                    s.getLatitude(), s.getLongitude(),
                    results
            );
            float distanciaMetros = results[0];

            if (distanciaMetros < menorDistancia) {
                menorDistancia = distanciaMetros;
                masCercana = s;
            }
        }

        if (masCercana == null) {
            Toast.makeText(this, "No se pudo determinar la estacion mas cercana",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String distTexto;
        if (menorDistancia < 1000) {
            distTexto = String.format(Locale.getDefault(), "%.0f m", menorDistancia);
        } else {
            distTexto = String.format(Locale.getDefault(), "%.1f km", menorDistancia / 1000f);
        }

        tvEstacionCercana.setText("📍 Mas cercana: " + masCercana.getBrand()
                + " - " + masCercana.getZone()
                + " (" + distTexto + ")");

        abrirGoogleMaps(masCercana);
    }

    private void abrirGoogleMaps(Station station) {
        Uri uri;
        if (currentLat != 0 && currentLng != 0
                && station.getLatitude() != null
                && station.getLongitude() != null) {
            uri = Uri.parse(
                    "https://www.google.com/maps/dir/?api=1" +
                            "&origin=" + currentLat + "," + currentLng +
                            "&destination=" + station.getLatitude() + ","
                            + station.getLongitude() +
                            "&travelmode=driving"
            );
        }  else {
            Toast.makeText(this, "No hay informacion de ubicacion",
                    Toast.LENGTH_SHORT).show();
            return;
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
                tvUbicacion.setText("📍 Permiso denegado");
                Toast.makeText(this,
                        "Sin permiso la ruta se calculara solo con el destino",
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