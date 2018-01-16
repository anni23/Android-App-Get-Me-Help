package com.example.location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
public class Reg extends Activity 
{
	DBAdapterHelper d;
	Cursor c1;
	Button b1;
	Button b2;
	TextView t1;
	TextView t2;
	TextView t3;
	TextView t4;
	TextView t5;
	TextView t6;
	EditText e1;
	EditText e2;
	protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        d=new DBAdapterHelper(this);
        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);
        t1=(TextView)findViewById(R.id.textView1);
        t2=(TextView)findViewById(R.id.textView2);
        t3=(TextView)findViewById(R.id.textView3);
        t4=(TextView)findViewById(R.id.textView4);
        t5=(TextView)findViewById(R.id.textView5);
        t6=(TextView)findViewById(R.id.textView6);
        e1=(EditText)findViewById(R.id.editText1);
        e2=(EditText)findViewById(R.id.editText2);
        d.open();
        c1=d.getDetails();
        c1.moveToFirst();
        if(c1.moveToPosition(0))
        {
        t1.setText(c1.getString(0));
        t3.setText(c1.getString(1));
        }
        if(c1.moveToPosition(1))
        {
        t2.setText(c1.getString(0));
        t4.setText(c1.getString(1));
        }
        d.close();
    }
	public void click1(View v)
	{
		String n=e1.getText().toString();
		String no=e2.getText().toString();
		d.open();
		if(c1.getCount()<2)
		{
		if((n.equalsIgnoreCase(""))&&(no.equalsIgnoreCase("")))
		{
			Toast.makeText(this,"pls enter some data",Toast.LENGTH_SHORT).show();
		}
		else
		{
			d.insert(n,no);
		}
		}
		else
		{
			Toast.makeText(this,"only two entries allowed",Toast.LENGTH_SHORT).show();
		}
		e1.setText("");
		e2.setText("");
		c1=d.getDetails();
        c1.moveToFirst();
        if(c1.moveToPosition(0))
        {
        t1.setText(c1.getString(0));
        t3.setText(c1.getString(1));
        }
        if(c1.moveToPosition(1))
        {
        t2.setText(c1.getString(0));
        t4.setText(c1.getString(1));
        }
        d.close();
    }
	
	public void click2(View v)
	{
		d.open();
		d.delete();
		d.close();
		t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
        Toast.makeText(getApplicationContext(),"numbers deleted",Toast.LENGTH_SHORT).show();
    }
}
