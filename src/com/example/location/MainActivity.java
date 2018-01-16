package com.example.location;
import java.io.IOException;
import java.util.List;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
public class MainActivity extends Activity implements LocationListener 
{
	LocationManager lm;
	String locationprovider;
	Criteria c;
	DBAdapterHelper d;
	Cursor c1;
	double longitude;
	double latitude;
	String number1;
	String number2;
	TextView t1;
	TextView t2;
	TextView t3;
	Geocoder g;
	List<Address> a;
	Location l;
	SmsManager sm;
	StringBuilder sb;
	Button b1;
	Intent i;
	Button b2;
	protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        t1=(TextView)findViewById(R.id.textView3);
        t2=(TextView)findViewById(R.id.textView4);
        t3=(TextView)findViewById(R.id.textView5);
        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);
        lm=(LocationManager)getSystemService(LOCATION_SERVICE);
        c=new Criteria();
        sb=new StringBuilder();
        d=new DBAdapterHelper(this);
        i=new Intent(this,Reg.class);
        g=new Geocoder(getApplicationContext());
        //locationprovider=lm.getBestProvider(c,true);
        sm=SmsManager.getDefault();
        locationprovider=LocationManager.NETWORK_PROVIDER;
        l=lm.getLastKnownLocation(locationprovider);
        if(l!=null)
        onLocationChanged(l);
        lm.requestLocationUpdates(locationprovider,0,0,this);
	}
	public void onClick(View v)
	{
		d.open();
        c1=d.getDetails();
        c1.moveToFirst();
        if(c1.moveToPosition(0))
        {
        number1=(c1.getString(1));
        }
        if(c1.moveToPosition(1))
        {
        number2=(c1.getString(1));
        }
        
        if(sb.toString().equalsIgnoreCase(""))
		{
        	Toast.makeText(getApplicationContext(),"location not available",Toast.LENGTH_SHORT).show();
		}
        else
        {
        	if(c1.moveToPosition(0)||c1.moveToPosition(1))
    		{
    		if(c1.moveToPosition(0))	
    		{
    			sm.sendTextMessage(number1,null,sb.toString(),null,null);
    			Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
    		}
    		if(c1.moveToPosition(1))
    		{
    		sm.sendTextMessage(number2,null,sb.toString(),null,null);
    		Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
    		}
    		}
    		else
    		{
    			Toast.makeText(getApplicationContext(),"register atleast one number",Toast.LENGTH_SHORT).show();
    		}
        }
        
		d.close();
	}
	public void Click(View v)
	{
		startActivity(i);
	}
	public void onLocationChanged(Location loc)
	{
		latitude=loc.getLatitude();
        longitude=loc.getLongitude();
        sb=new StringBuilder();
        String x=""+longitude;
        String y=""+latitude;
        t1.setText(x);
        t2.setText(y);
        try 
        {
			a=g.getFromLocation(latitude,longitude,1);
		}
        catch (IOException e)
        {
			e.printStackTrace();
		}
        if(a.size()>0)
        {
        Address address = a.get(0);
        for (int i=0;i<address.getMaxAddressLineIndex();i++)
        sb.append(address.getAddressLine(i)).append("\n");
        sb.append(address.getLocality()).append("\n");
        sb.append(address.getPostalCode()).append("\n");
        sb.append(address.getCountryName());
        String addressString=sb.toString();
        t3.setText(addressString);
        }
    }
	public void onProviderDisabled(String arg0)
	{
		Toast.makeText(getApplicationContext(),"GPS Disabled",Toast.LENGTH_SHORT).show();
	}
	public void onProviderEnabled(String arg0) 
	{
		Toast.makeText(getApplicationContext(),"GPS Enabled",Toast.LENGTH_SHORT).show();
	}
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) 
	{
		
	}
}
