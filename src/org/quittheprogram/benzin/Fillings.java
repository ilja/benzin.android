package org.quittheprogram.benzin;


import java.util.ArrayList;
import java.util.Calendar;
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
import android.widget.ArrayAdapter;
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
	        
        ArrayList<Filling> fillings = dbHelper.getFillings2();
        FillingAdapter adapter3 = new FillingAdapter(this, R.layout.filling, fillings);
        
        setListAdapter(adapter3);
		//getListView().setTextFilterEnabled(true);		
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
		Filling filling = (Filling)getListView().getAdapter().getItem(id);
		
		dbHelper = new DatabaseHelper(this);
		dbHelper.deleteFilling(filling.getId());
		
		// refresh listview
		ArrayList<Filling> fillings = dbHelper.getFillings2();
		((FillingAdapter)getListView().getAdapter()).Update(fillings);
	}
	
	public void editFilling(int id){
		Filling filling = (Filling)getListView().getAdapter().getItem(id);
		
		Bundle bundle = new Bundle();
		bundle.putLong("filling_id", filling.getId());
		bundle.putString("Date", filling.getDate().toString());
		bundle.putInt("Odometer", filling.getOdometer());
		bundle.putString("Amount", Double.toString(filling.getAmount()));
		bundle.putString("Price", Double.toString(filling.getPrice()));
		Intent intent = new Intent();
		intent.setClass(this, AddFilling.class);
		intent.putExtras(bundle);
		
		startActivityForResult(intent, EDIT_FILLING);
	}
}
