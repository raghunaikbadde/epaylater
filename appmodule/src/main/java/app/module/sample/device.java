package app.module.sample;

public class device {
    public String deviceType = "TABLET";
    public String deviceClient;
    public String deviceNumber;
    //MAC Address
    public String deviceId;
    //Ex: Apple IPhone,Samsung Galaxy
    public String deviceMake;
    //7 plus, nexus
    public String deviceModel;
    //ios 10.2.1
    public String osVersion;
    public String IMEINumber;


    public device() {
        deviceNumber = MyApplication.getsInstance().getDeviceIpAddress();
        deviceId = MyApplication.getsInstance().getDeviceMacAddress();
    }

}
