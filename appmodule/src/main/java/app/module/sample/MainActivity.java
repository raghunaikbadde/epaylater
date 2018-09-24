package app.module.sample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import app.module.sample.api.PayLaterApiClient;
import app.module.sample.confirm.ConfirmOrder;
import app.module.sample.refund.Refund;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.button_submit)
    Button button;

    @BindView(R.id.button_refund)
    Button refundButton;

    @BindView(R.id.button_confirm)
    Button confirmButton;

    String APIKEY = "EE477A3941E3BC7AC6AF0971ED7CD5D5";
    String VI_KEY = "40EF81357620E19A";

    String AuthKey= "secret_ac4ad2d4-f047-4009-95c5-2bec6c1d1ef2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.button_submit)
    public void submitForEncryptUtil(View view) {

        Toast.makeText(MainActivity.this, "Pay Button clicked", Toast.LENGTH_SHORT).show();
        callPayLaterApi();

    }

    @OnClick(R.id.button_confirm)
    public void confirmOrder(View view){
        Toast.makeText(MainActivity.this, "Confirm Button clicked", Toast.LENGTH_SHORT).show();
        ConfirmOrder confirmOrder = new ConfirmOrder();
        SharedPreferences sp = getApplicationContext().getSharedPreferences("paylater",0);
        String marketplaceOrderId = sp.getString("marketplaceOrderId", String.valueOf(new Date()));
        String orderId = sp.getString("order",OrderHistory.orderId);
        long amount = sp.getLong("amount", 30000);
        confirmOrder.setMarketPlaceOrderId(marketplaceOrderId);
        confirmOrder.setAmount(amount);
        confirmOrder.setId(orderId);
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        String format = df.format(new Date());
        confirmOrder.setDate(format);
        confirmOrder.setStatus("delivered");

        String json = new Gson().toJson(confirmOrder);
        new HitForConfirm(json,marketplaceOrderId,orderId).execute();
    }

    @OnClick(R.id.button_refund)
    public void submitForRefund(View view){
        Toast.makeText(MainActivity.this, "Refund Button clicked", Toast.LENGTH_SHORT).show();
        Refund refund = new Refund();
        SharedPreferences sp = getApplicationContext().getSharedPreferences("paylater",0);
        String marketplaceOrderId = sp.getString("marketplaceOrderId", String.valueOf(new Date()));
        long amount = sp.getLong("amount", 30000);

        refund.setMarketplaceOrderId(marketplaceOrderId);
        refund.setReturnAmount(amount);
        refund.setReturnType("full");
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        //date = "2017-04-27T11:06:46Z";//df.format(new Date());
        String date = df.format(new Date());
        refund.setReturnAcceptedDate(date);
        refund.setReturnShipmentReceivedDate(date);
        refund.setRefundDate(date);
        String jsonObject = new Gson().toJson(refund);
        new HitForRefund(jsonObject,marketplaceOrderId).execute();


    }

    private void callPayLaterApi() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("paylater",0);

        PayLaterModel payLaterModel = new PayLaterModel();
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("marketplaceOrderId",payLaterModel.marketplaceOrderId);
        edit.putLong("amount",payLaterModel.amount);
        edit.putString("orderid",OrderHistory.orderId);
        edit.commit();

        String jsonObject = new Gson().toJson(payLaterModel);
        new HitForcheckSum(jsonObject).execute();
        }

    private class HitForcheckSum extends AsyncTask {
        String data;

        HitForcheckSum(String data) {
            this.data = data;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String response = "";
            String text = "";
            try {

                String data = URLEncoder.encode("data", "UTF-8")
                        + "=" + URLEncoder.encode(this.data, "UTF-8");

                URL url = new URL("http://10.0.1.31/API/pos_reports_queue/token1.php");
                BufferedReader reader = null;

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                }


                text = sb.toString();
            } catch (UnsupportedEncodingException use) {

            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return text;

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            try {
                JSONObject jsonObject = new JSONObject(o.toString());
                Intent intent = new Intent(MainActivity.this, EpayLaterPaymentActivity.class);
                intent.putExtra("encData", jsonObject.optString("encdata"));
                intent.putExtra("checkSum", jsonObject.optString("checksum"));
                intent.putExtra("mCode", "nukkadshops");
                intent.putExtra("paymentUrl", "https://payment-sandbox.epaylater.in/web/process-transaction");
                startActivity(intent);
            } catch (JSONException jsoe) {

            }

            Log.d(TAG, o.toString());
        }
    }
        public String convert(InputStream inputStream, Charset charset) throws IOException {

            StringBuilder stringBuilder = new StringBuilder();
            String line = null;

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }

            return stringBuilder.toString();
        }

    private class HitForConfirm extends AsyncTask{
        String data;
        String marketPlaceOrderId;
        String orderId;
        HitForConfirm(String data,String marketPlaceOrderId,String orderId){
            this.data = data;
            this.marketPlaceOrderId = marketPlaceOrderId;
            this.orderId = orderId;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String response = "";
            String text = "";
            try {
                URL url = new URL("https://payment-sandbox.epaylater.in/transaction/v2/"+orderId+"/confirmed/"+marketPlaceOrderId+"?delivered=true");
                BufferedReader reader=null;
                HttpURLConnection conn = (HttpsURLConnection)url.openConnection();
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Content-Type","application/json");
                conn.setRequestProperty("Authorization","Bearer "+AuthKey);
                conn.setRequestProperty("Accept","application/json");

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    // Append server response in string
                    sb.append(line + "\n");
                }


                text = sb.toString();
            } catch (UnsupportedEncodingException use){

            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return text;

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Log.d(TAG,String.valueOf(o));
        }



    }


        private class HitForRefund extends AsyncTask{
            String data;
            String marketPlaceOrderId;
            HitForRefund(String data,String marketPlaceOrderId){
                this.data = data;
                this.marketPlaceOrderId = marketPlaceOrderId;
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                String response = "";
                String text = "";
                try {
                    URL url = new URL("https://payment-sandbox.epaylater.in/transaction/v2/marketplaceorderid/"+marketPlaceOrderId+"/returned");
                    BufferedReader reader=null;
                    HttpURLConnection conn = (HttpsURLConnection)url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-Type","application/json");
                    conn.setRequestProperty("Authorization",AuthKey);
                    conn.setRequestProperty("Accept","application/json");

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();

                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        // Append server response in string
                        sb.append(line + "\n");
                    }


                    text = sb.toString();
                } catch (UnsupportedEncodingException use){

                } catch (Exception e) {
                    Log.d(TAG, e.getLocalizedMessage());
                }
                return text;

            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Log.d(TAG,String.valueOf(o));
            }



    }
}
