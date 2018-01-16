package com.example.location;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
public class DBAdapterHelper extends SQLiteOpenHelper 
{
	Context c;
	SQLiteDatabase db;
	static String DATABASE_NAME="contact";
	static int DATABASE_VERSION=1;
	static String CREATE="create table register(name text primary key,number text not null);";
	static String ROW_NAME="name";
	static String ROW_NUMBER="number";
	//static String ROW_FIRSTNAME="firstname";
	//static String ROW_EMAIL="email";
	static String DATABASE_TABLE="register";
	public DBAdapterHelper(Context ctx)
	{
		super(ctx,DATABASE_NAME,null,DATABASE_VERSION);
		c=ctx;
	}
	public void onCreate(SQLiteDatabase d)
	{
		d.execSQL(CREATE);
	}
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) 
	{
	}
	public void open()throws SQLException
	{
		db=getWritableDatabase();
	}
	public Cursor getDetails()
	{
		Cursor c;
		c=db.query(DATABASE_TABLE, new String[]{ROW_NAME,ROW_NUMBER},null,null,null,null,null);
		return c;
	}
	public void delete()
	{
		db.delete(DATABASE_TABLE,null,null);
	}
	public void insert(String n,String no)
	{
		ContentValues cv=new ContentValues();
		cv.put(ROW_NAME,n);
		cv.put(ROW_NUMBER,no);
		//cv.put(ROW_FIRSTNAME, nm);
		//cv.put(ROW_EMAIL, email);
		long row=db.insertWithOnConflict(DATABASE_TABLE,null,cv,db.CONFLICT_REPLACE);
		if(row!=-1)
		{
			Toast.makeText(c,"RECORD ADDED SUCCESSFULLY",Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(c,"ERROR",Toast.LENGTH_SHORT).show();
		}
	}
}
