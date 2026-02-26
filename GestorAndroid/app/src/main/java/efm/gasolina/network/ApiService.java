package efm.gasolina.network;

import efm.gasolina.model.LoginRequest;
import efm.gasolina.model.NewPassword;
import efm.gasolina.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/users/register")
    Call<Void> registerUser(@Body User user);

    @POST("api/users/login")
    Call<User> login(@Body LoginRequest request);

    @POST("api/session/forgotPassword")
    Call<String> forgotPassword(@Body String request);

    @POST("api/session/codeVerifier")
    Call<Void> codeVerifier(@Body String request);

    @POST("api/session/changePassword")
    Call<NewPassword> changePassword(@Body NewPassword request);
}