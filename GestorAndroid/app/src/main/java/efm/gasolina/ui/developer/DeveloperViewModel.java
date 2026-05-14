package efm.gasolina.ui.developer;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import efm.gasolina.model.auth.User;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeveloperViewModel extends ViewModel {

    private final ApiService apiService;
    private final MutableLiveData<List<User>> usuarios = new MutableLiveData<>();
    private final MutableLiveData<String> actionResult = new MutableLiveData<>();
    private ScheduledExecutorService scheduler;

    public DeveloperViewModel() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public LiveData<List<User>> getUsuarios() { return usuarios; }
    public LiveData<String> getActionResult() { return actionResult; }

    public void startPolling(int segundos) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(
                this::cargarPendientes,
                0,
                segundos,
                TimeUnit.SECONDS
        );
    }

    private void cargarPendientes() {
        apiService.getPendingUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call,
                                   Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    usuarios.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                actionResult.postValue("ERROR:Sin conexión");
            }
        });
    }

    public void aprobar(Long id, String role) {
        apiService.approveUser(id, role).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    actionResult.postValue("OK:Usuario aprobado");
                    cargarPendientes();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                actionResult.postValue("ERROR:Sin conexión");
                Log.e("------------", t.getMessage());

            }
        });
    }

    public void rechazar(Long id, String role) {
        apiService.rejectUser(id, role).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    actionResult.postValue("OK:Usuario rechazado");
                    cargarPendientes();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                actionResult.postValue("ERROR:Sin conexión");
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (scheduler != null) scheduler.shutdown();
    }
}