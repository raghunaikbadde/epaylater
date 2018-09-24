package app.module.sample;

import android.content.SharedPreferences;
import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class PayLaterModel {
    public String redirectType = "WEBPAGE";
    public String marketplaceOrderId = "abc234";//String.valueOf(new Date());


    public String mCode = "nukkadshops";
    public String callbackUrl ="https://www.nukkadshops.com/ku.php";
    public boolean customerEmailVerified = true;
    public boolean customerTelephoneNumberVerified = false;
    public boolean customerLoggedin = false;
    public long amount = 30000;
    public String currencyCode = "INR";
    public String date;
    public String category = "RETAIL";
    public Customer customer;
    public Address address;
    public device device;
    public ArrayList<OrderHistory> orderHistory;
    public MarketplaceSpecificSection marketplaceSpecificSection;

    public PayLaterModel() {

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        //date = "2017-04-27T11:06:46Z";//df.format(new Date());
        date = df.format(new Date());

        customer = new Customer();
        address = new Address();
        device = new device();
        orderHistory = new ArrayList<OrderHistory>();
        orderHistory.add(new OrderHistory());
        marketplaceSpecificSection = new MarketplaceSpecificSection();

    }
}
