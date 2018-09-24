package app.module.sample;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okio.Utf8;

public class EpayLaterPaymentActivity extends AppCompatActivity {

    private static final String MCODE = "mcode=";
    private static final String CHECKSUM = "&checksum=";
    private static final String ENCDATA = "&encdata=";
    private static final String JAVASCRIPT_INTERFACE_NAME = "EpayLater";
    private WebView ePayWebView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        //All your other initialization
        ePayWebView = findViewById(R.id.webview_epaylater);
        setUpWebView(ePayWebView);
        String endData = getIntent().getExtras().getString("encData");
        String checkSum = getIntent().getExtras().getString("checkSum");
        String mCode = getIntent().getExtras().getString("mCode");
        String paymentUrl = getIntent().getExtras().getString("paymentUrl");

        try{
            byte[] data = getPostData(mCode,checkSum,endData);
            ePayWebView.postUrl(paymentUrl,data);
        }catch (UnsupportedEncodingException usoe){

        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setUpWebView(WebView webView) {
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true); //required
        webView.getSettings().setSupportMultipleWindows(true);
////required
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true); //required
        webView.getSettings().setSupportMultipleWindows(true);
////required
        if (Build.VERSION.SDK_INT >= 21) { //required
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
            CookieManager.getInstance().setAcceptThirdPartyCookies(ePayWebView,
                    true);
        }
        webView.addJavascriptInterface(new PaymentCallBack(),JAVASCRIPT_INTERFACE_NAME);
    }

    private byte[] getPostData(String mCode,String checkSum,String encData) throws UnsupportedEncodingException{
        String postDataBuilder =   MCODE + URLEncoder.encode(mCode, "UTF-8") + CHECKSUM + URLEncoder.encode(checkSum,"UTF-8")+ ENCDATA + URLEncoder.encode(encData,"UTF-8");
        return postDataBuilder.getBytes();
    }

}
