package org.quittheprogram.benzin;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.SimpleCursorAdapter;


public class Fillings extends ListActivity {	
		
	private static final int EDIT_FILLING = 0;
	DatabaseHelper dbHelper;
	 

	@Override	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		// register all items in list for a context menu
		registerForContextMenu(getListView());
		//getListView().setOnCreateContextMenuListener(this);
		
		dbHelper = new DatabaseHelper(this);
	    Cursor cursor = dbHelper.getFillings();
        startManagingCursor(cursor);
        
       
        
       
		
		//setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lv_arr));
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.filling, cursor, DatabaseHelper.FROM, DatabaseHelper.TO);
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
			//startActivity(new Intent(this, AddFilling.class));
			editFilling(info.position);
			return true;
		case R.id.delete_filling:
			deleteFilling(info.position);
			return true;
		default: 
			return super.onContextItemSelected(item);
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
