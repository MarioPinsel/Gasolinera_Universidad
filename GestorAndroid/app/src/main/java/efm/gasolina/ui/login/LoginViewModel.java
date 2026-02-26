package efm.gasolina.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import efm.gasolina.model.LoginRequest;
import efm.gasolina.model.LoginResponse;
import efm.gasolina.repository.UserRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private final UserRepository repository;
    private final MutableLiveData<LoginResponse> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> loginError = new MutableLiveData<>();

    public LoginViewModel() {
        repository = new UserRepository();
    }

    public LiveData<LoginResponse> getLoginSuccess() { return loginSuccess; }
    public LiveData<String> getLoginError() { return loginError; }

    public void login(String email, String password) {

        if (email.isEmpty() && password.isEmpty()) {
            loginError.setValue("All fields are required");
            return;
        }

        if (email.isEmpty()) {
            loginError.setValue("Email is required");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginError.setValue("Enter a valid email");
            return;
        }

        if (password.isEmpty()) {
            loginError.setValue("Password is required");
            return;
        }

        repository.login(new LoginRequest(email, password)).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    loginSuccess.setValue(response.body());
                } else if (response.code() == 401) {
                    loginError.setValue("Incorrect credentials");
                } else if (response.code() == 403) {
                    loginError.setValue("Account pending approval");
                } else {
                    loginError.setValue("Server error " + response.code());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginError.setValue("No internet connection");
            }
        });
    }
}