package org.quittheprogram.benzin;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;


public class Fillings extends ListActivity {	
		
	private static final int EDIT_FILLING = 0;
	DatabaseHelper dbHelper;
	 

	@Override	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		registerForContextMenu(getListView());
		getActionBar().setHomeButtonEnabled(true);
				
		dbHelper = new DatabaseHelper(this);
	    Cursor cursor = dbHelper.getFillings();
        startManagingCursor(cursor);
             
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.filling, cursor, DatabaseHelper.FROM, DatabaseHelper.TO);
        
        ViewBinder viewBinder = new ViewBinder() {
			
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

				if(view.getId() == R.id.rowDate){
					
					Long createDateInMillis = cursor.getLong(cursor.getColumnIndex("Date"));
				    final Calendar calendar = Calendar.getInstance();
				    try {
				    	calendar.setTimeInMillis(createDateInMillis);
				    	
				    	String formattedDate  = new StringBuilder()
						.append(calendar.get(Calendar.DAY_OF_MONTH)).append('-')
						.append(calendar.get(Calendar.MONTH) + 1).append('-')
						.append(calendar.get(Calendar.YEAR)).toString();
					
				    	((TextView) view).setText(formattedDate);
					} catch (Exception e) {
						((TextView) view).setText("niet te parsen date:" + createDateInMillis);
					}
				    
				    
				    
					return true;
				}
				return false;
			}
		};
        adapter.setViewBinder(viewBinder);
        
        setListAdapter(adapter);
		getListView().setTextFilterEnabled(true);		
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.filling_context_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item){
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		
		switch (item.getItemId()){
		case R.id.edit_filling:
			editFilling(info.position);
			return true;
		case R.id.delete_filling:
			deleteFilling(info.position);
			return true;
		default: 
			return super.onContextItemSelected(item);
		}
		
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu){
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case android.R.id.home:
				Intent intent = new Intent(this, Home.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}		
	}
	
	public void deleteFilling(int id){
		Cursor c =  (Cursor)getListView().getAdapter().getItem(id);
		dbHelper = new DatabaseHelper(this);
		dbHelper.deleteFilling(c.getLong(0));
		
		((SimpleCursorAdapter)getListView().getAdapter()).getCursor().requery();		
	}
	
	public void editFilling(int id){
		Cursor c = (Cursor)getListView().getAdapter().getItem(id);
		
		Bundle bundle = new Bundle();
		bundle.putLong("filling_id", c.getLong(0));
		bundle.putString("Date", c.getString(1));
		bundle.putInt("Odometer", c.getInt(2));
		bundle.putString("Amount",c.getString(3));
		bundle.putString("Price",c.getString(4));
		Intent intent = new Intent();
		intent.setClass(this, AddFilling.class);
		intent.putExtras(bundle);
		
		startActivityForResult(intent, EDIT_FILLING);
	}
}
