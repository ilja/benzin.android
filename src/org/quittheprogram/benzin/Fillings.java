package org.quittheprogram.benzin;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.*;

public class Fillings extends ListActivity {	
	private String lv_arr[]={"filling1","filling2","filling3","filling4", "filling5", "filling6", "filling7", "filling8", "filling9"};
	
	@Override	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setListAdapter(new ArrayAdapter<String>(this,
		          android.R.layout.simple_list_item_1, lv_arr));
		  getListView().setTextFilterEnabled(true);		
	}
}
