/**
 * WordCamp Kanpur
 *
 * @package    WordCamp Kanpur
 * @author     Sanyog Shelar <sanyog@hotmail.com>
 * @copyright  Copyright (c) WHMCS Limited 2005-2013
 * @license    WordCamp is released under the GPL
 * @version    1.3
 * @link       https://2017.kanpur.wordcamp.org
 */

package com.version.next.wpkanpur.wpkanpur;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Logcat tag
	private static final String LOG = "DatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "WordCamp_mumbai";

	// Table Names
	private static final String TABLE_scan_contact = "ScanContact";
	private static final String TABLE_Notification = "Notification";


	// Column names for ScanContact
	private static final String KEY_MasterId = "MasterId";
	private static final String KEY_contact_name= "ContactName";
	private static final String KEY_contact_phone= "ContactPhone";
	private static final String KEY_contact_Email= "ContactEmail";
	private static final String KEY_contact_Twitter= "ContactTwitter";
	private static final String KEY_TimeStemp = "TimeStemp";

	// Column names for Notification
	private static final String KEY_MasterId_noti = "MasterId";
	private static final String KEY_Notification_time = "NotificationTime";
	private static final String KEY_Notification_message= "NotificationMessage";
	private static final String KEY_Notification_title= "NotificationTitle";

	// Table Create Statements

	private static final String CREATE_TABLE_ScanContact = "CREATE TABLE "
			+ TABLE_scan_contact + "("
			+ KEY_MasterId + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ KEY_contact_name  + " TEXT ,"
			+ KEY_contact_phone  + " TEXT ,"
			+ KEY_contact_Email  + " TEXT ,"
			+ KEY_contact_Twitter  + " TEXT ,"
			+ KEY_TimeStemp + " TEXT " +  ")";

	private static final String CREATE_TABLE_TABLE_Notification = "CREATE TABLE "
			+ TABLE_Notification + "("
			+ KEY_MasterId_noti + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ KEY_Notification_time  + " TEXT ,"
			+ KEY_Notification_message  + " TEXT ,"
			+ KEY_Notification_title  +  ")";


	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// creating required tables
		db.execSQL(CREATE_TABLE_ScanContact);
		db.execSQL(CREATE_TABLE_TABLE_Notification);
		//db.execSQL(CREATE_TABLE_InventoryDetail);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_scan_contact);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Notification);
		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_InventoryDetail);

		// create new tables
		onCreate(db);
	}

	// ------------------------ table methods ----------------//

	public long Insert_ScanContact(String contact_name,String contact_phone , String contact_email,String contact_twitter , String TimeStemp)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		int i;
		ContentValues values = new ContentValues();

		values.put(KEY_contact_name,contact_name);
		values.put(KEY_contact_phone,contact_phone);
		values.put(KEY_contact_Email,contact_email);
		values.put(KEY_contact_Twitter,contact_twitter);
		values.put(KEY_TimeStemp,TimeStemp);

		String query = "SELECT * FROM " + TABLE_scan_contact + " WHERE " + KEY_contact_phone + "='"+contact_phone+"'";

		Log.d("query", "query->" + query);

		Cursor c = db.rawQuery(query, null);

		if (c.getCount()>0)
		{

			i = 1;
			db.execSQL("UPDATE "+TABLE_scan_contact+ " SET " + KEY_contact_name+ "='"+contact_name+"',"+ KEY_contact_Email + "='"+contact_email+"'," +
					KEY_contact_Twitter + "='"+contact_twitter+"'," + KEY_TimeStemp + "='"+TimeStemp+"' WHERE "+KEY_contact_phone+"='"+contact_phone+"'");
		}
		else
		{
			i = 0;
			long insert = db.insert(TABLE_scan_contact, null, values);

		}

		return i;
	}


	public long Insert_Notification(String notification_time,String notification_title,String notification_message)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		int i;
		ContentValues values = new ContentValues();


		values.put(KEY_Notification_time,notification_time);
		values.put(KEY_Notification_message,notification_message);
		values.put(KEY_Notification_title,notification_title);

		String query = "SELECT * FROM " + TABLE_Notification + " WHERE " + KEY_Notification_time + "='"+notification_time+"'";

		Log.d("query", "query->" + query);

		Cursor c = db.rawQuery(query, null);

		if (c.getCount()>0)
		{

			i = 1;
			db.execSQL("UPDATE "+TABLE_Notification+ " SET " +  KEY_Notification_title + "='"+notification_title+"'," +
					 KEY_Notification_message + "='"+notification_message+"' WHERE "+KEY_Notification_time+"='"+notification_time+"'");
		}
		else
		{
			i = 0;
			long insert = db.insert(TABLE_Notification, null, values);

		}

		return i;
	}



	public Cursor Get_ScanContact()
	{

		SQLiteDatabase db = this.getReadableDatabase();

		String query = "SELECT * FROM " + TABLE_scan_contact;

		Log.d("Query", "Query->" + query);

		Cursor c = db.rawQuery(query, null);

		return c;

	}

	public Cursor Get_Notification()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT * FROM " + TABLE_Notification +" ORDER BY "+KEY_MasterId_noti + " DESC";
		Log.d("Query", "Query->" + query);
		Cursor c = db.rawQuery(query, null);

		return c;
	}


	public Cursor Get_Master_Id()
	{
		SQLiteDatabase db = this.getReadableDatabase();

		String query = "SELECT ifnull(max(MasterId),0)+1 FROM " + TABLE_scan_contact;

		Log.d("Query", "Query->" + query);
		Cursor c = db.rawQuery(query, null);

		return c;
	}

	public void Delete_Inventory_Master_by_Id(String Masterid)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		db.execSQL("DELETE FROM "+ TABLE_scan_contact +" where "+ KEY_MasterId +"='" + Masterid + "'");
	}

	public void Delete_All_Tables()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		db.delete(TABLE_scan_contact, null, null);
	}

	public void Truncate_All_Tables()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		db.execSQL("DELETE FROM "+TABLE_scan_contact);
	}

	// closing database
	public void closeDB()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}
}
