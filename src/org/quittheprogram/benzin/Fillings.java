package org.quittheprogram.benzin;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;

public class Fillings extends ListActivity {	
	private String lv_arr[]={"filling1","filling2","filling3","filling4", "filling5", "filling6", "filling7", "filling8", "filling9"};
	
	@Override	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		DatabaseHelper dbHelper = new DatabaseHelper(this);
	    Cursor cursor = dbHelper.getFillings();
        startManagingCursor(cursor);
        
        
       
		
		//setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lv_arr));
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.filling, cursor, dbHelper.FROM, dbHelper.TO);
        setListAdapter(adapter);
		getListView().setTextFilterEnabled(true);		
	}
}
