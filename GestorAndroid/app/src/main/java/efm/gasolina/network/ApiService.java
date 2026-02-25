package efm.gasolina.network;

import efm.gasolina.model.LoginRequest;
import efm.gasolina.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/users/register")
    Call<Void> registerUser(@Body User user);

    @POST("api/users/login")
    Call<User> login(@Body LoginRequest request);
}