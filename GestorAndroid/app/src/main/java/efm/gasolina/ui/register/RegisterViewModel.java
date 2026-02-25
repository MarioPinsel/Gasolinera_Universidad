package efm.gasolina.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import efm.gasolina.model.User;
import efm.gasolina.repository.UserRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {

    private final UserRepository repository;
    private final MutableLiveData<String> registerResult = new MutableLiveData<>();

    public RegisterViewModel() {
        repository = new UserRepository();
    }

    public LiveData<String> getRegisterResult() {
        return registerResult;
    }

    public void register(String nombre, String correo,
                         String password, String rol) {

        // Validaciones básicas antes de llamar la API
        if (nombre.isEmpty() || correo.isEmpty() ||
                password.isEmpty()) {
            registerResult.setValue("ERROR:Completa todos los campos");
            return;
        }

        User user = new User(nombre, correo, password, rol);

        repository.register(user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call,
                                   Response<Void> response) {
                if (response.isSuccessful()) {
                    registerResult.setValue("OK:Registro exitoso, espera aprobación del admin");
                } else if (response.code() == 409) {
                    registerResult.setValue("ERROR:El correo ya está registrado");
                } else {
                    registerResult.setValue("ERROR:Error del servidor " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                registerResult.setValue("ERROR:Sin conexión: " + t.getMessage());
            }
        });
    }
}