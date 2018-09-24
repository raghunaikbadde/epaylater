package app.module.sample;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

class PaymentCallBack {
    PaymentCallBack() {
    }
    @JavascriptInterface
    public void success() {
        // Handle success callback
        Log.d(MainActivity.TAG,"success ");

    }
    @JavascriptInterface
    public void failure() {
        // Handle failure callback
        Log.d(MainActivity.TAG,"failure ");

    }
    @JavascriptInterface
    public void success(String jsonPayload) {
        // Handle success callback with jsonPayload
        Log.d(MainActivity.TAG,"success payload"+jsonPayload);
    }
    @JavascriptInterface
    public void failure(String jsonPayload) {
        // Handle failure callback with jsonPayload
        Log.d(MainActivity.TAG,"failure payload"+jsonPayload);


    }
}