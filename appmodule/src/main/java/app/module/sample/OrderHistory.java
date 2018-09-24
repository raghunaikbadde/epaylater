package app.module.sample;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class OrderHistory {
    public static String orderId = "110012";
    public long amount = 30000;
    public String date;
    public String category = "RETAIL";
    public String currencyCode = "INR";
    public PaymentMethod paymentMethod;
    public boolean returned = false;
    public ReturnReason returnReason;


    public OrderHistory() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
       // date = "2017-04-27T11:06:46Z";df.format(new Date());

        date = df.format(new Date());
        Log.d(MainActivity.TAG,date);
    }
}
