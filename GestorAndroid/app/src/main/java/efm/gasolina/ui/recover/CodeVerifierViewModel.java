package efm.gasolina.ui.recover;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import efm.gasolina.util.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CodeVerifierViewModel extends AndroidViewModel {

    private final ApiService apiService;

    private final TokenManager tokenManager;
    private final MutableLiveData<String> requestRecoverResult = new MutableLiveData<>();

    public CodeVerifierViewModel(@NonNull Application app) {
        super(app);
        apiService = ApiClient.getClient().create(ApiService.class);
        tokenManager = new TokenManager(app.getApplicationContext());
    }

    public LiveData<String> getRequestRecoverResult() {
        return requestRecoverResult;
    }

    public void sendCode(String codigo) {

        if (codigo.isEmpty()) {
            requestRecoverResult.setValue("ERROR:Ingresa codigo para cambiar contraseña");
            return;
        }

        Map request = new HashMap<String, String>();
        request.put("value", codigo);
        request.put("token", tokenManager.getToken());
        // primero creas el objeto LoginRequest
        // luego se lo mandas a la API

        apiService.codeVerifier(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call,
                                   Response<Void> response) {
                if (response.isSuccessful() ) {
                    requestRecoverResult.setValue("OK");
                } else if (response.code() == 404) {
                    requestRecoverResult.setValue("ERROR:Correo no encontrado");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                requestRecoverResult.setValue("ERROR:Sin conexión");
            }
        });
    }
}
