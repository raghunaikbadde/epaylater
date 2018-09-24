package app.module.sample;

import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

public class MyApplication extends Application {

    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }


    public static MyApplication getsInstance() {
        return sInstance;
    }


    public String getDeviceIpAddress() {
        String ipAddress = "";
        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        ipAddress = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        return ipAddress;

    }

    public String getDeviceMacAddress() {
        String deviceMacAddress = "";
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        deviceMacAddress = wInfo.getMacAddress();

        return deviceMacAddress;

    }
}
