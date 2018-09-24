package app.module.sample.api;

import org.json.JSONObject;

import app.module.sample.PayLaterModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PayLaterApiService {
    @FormUrlEncoded
    @Headers({"Content-Type: application/json"})
    @POST("pos_reports_queue/token.php")
    Call<String> buyNowAndPayLater(@Field("data") String payLaterModel);

}
