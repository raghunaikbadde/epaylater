package app.module.sample.api;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PayLaterApiClient {

    private static PayLaterApiClient mInstance;

    private String BASE_URL = "http://10.0.1.25/API/";

    private static PayLaterApiService mPayLaterApiService;

    private PayLaterApiClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
               // .addConverterFactory(GsonConverterFactory.create())
                .build();

        mPayLaterApiService = retrofit.create(PayLaterApiService.class);

    }

    public static PayLaterApiService getApiService() {

        if (mInstance == null) {
            mInstance = new PayLaterApiClient();
        }
        return mPayLaterApiService;
    }


}
