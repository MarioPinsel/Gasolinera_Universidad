package efm.gasolina.network;
import java.util.List;

import efm.gasolina.model.LoginRequest;
import efm.gasolina.model.NewPassword;
import efm.gasolina.model.LoginResponse;
import efm.gasolina.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @POST("/sesion/register")
    Call<Void> registerUser(@Body User user);


    @POST("api/session/forgotPassword")
    Call<Void> forgotPassword(@Body String request);

    @POST("api/session/codeVerifier")
    Call<Void> codeVerifier(@Body String request);

    @POST("api/session/changePassword")
    Call<NewPassword> changePassword(@Body NewPassword request);

    @POST("/sesion/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("admin/pending")
    Call<List<User>> getPendingUsers();

    @PUT("admin/approve/{id}")
    Call<Void> approveUser(@Path("id") Long id);

    @PUT("admin/reject/{id}")
    Call<Void> rejectUser(@Path("id") Long id);
}