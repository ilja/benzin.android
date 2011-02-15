package org.quittheprogram.benzin;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	static final String dbName = "benzinDB";
	static final String fillingsTable = "Fillings";
	static final String _ID = "_id";	
	static final String colDate = "Date";
	static final String colOdometer = "Odometer";
	static final String colAmount = "Amount";
	static final String colPrice = "Price";
	
	
	static final String ORDER_BY = "Date DESC";
	
	//static final String[] FROM = { colID, colDate, colOdometer, colAmount, colPrice, };
	static final String[] FROM = { _ID, colDate, colOdometer, colAmount, colPrice };
	static final int[] TO = { R.id.rowid, R.id.rowDate };

	public DatabaseHelper(Context context) {
		super(context, dbName, null, 2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + fillingsTable + " ("+ 
				_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + 
				colDate + " TEXT NOT NULL, " +
				colOdometer + " INTEGER NOT NULL, " +
				colAmount + " REAL NOT NULL, " +
				colPrice + " REAL NOT NULL);");		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Alter table yadda yadda
		  db.execSQL("DROP TABLE IF EXISTS "+fillingsTable);		  
		  onCreate(db);
	}
	
	public void saveFilling(Date date, int odometer, double amount, double price, long _id){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(colDate, date.toString());
		cv.put(colOdometer, odometer);
		cv.put(colAmount, amount);
		cv.put(colPrice, price);
		
		if(_id == 0) {
			db.insert(fillingsTable, _ID, cv);
		}
		else {		
			db.update(fillingsTable, cv, _ID+"=?", new String[] {String.valueOf(_id)});
		}
		
		db.close();
	}
	
	public Cursor getFilling(long id){
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(fillingsTable, FROM, _ID+"=?", new String[]{Long.toString(id)}, null, null, null);
		cursor.moveToFirst();
		return cursor;
	}
	
	public void deleteFilling(long id){
		SQLiteDatabase db = this.getReadableDatabase();
		
		int c = db.delete(fillingsTable, _ID+"=?", new String[]{Long.toString(id)});
		//todo check c = 1
		db.close();
	}
	
	public Cursor getFillings(){
		// Perform a managed query. The Activity will handle closing
		// and re-querying the cursor when needed.
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(fillingsTable, FROM, null, null, null, null, ORDER_BY);
		
		return cursor;
	}
}
