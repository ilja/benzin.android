package org.quittheprogram.benzin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Home extends Activity implements OnClickListener {
	DatabaseHelper dbHelper; 	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        View addFilling = findViewById(R.id.add_filling);
        addFilling.setOnClickListener(this);
        
        dbHelper = new DatabaseHelper(this);
        Cursor cursor = dbHelper.getFillings();
        startManagingCursor(cursor);
        updateStatistics(cursor);
    }
    
    @Override
    protected void onDestroy(){    	
		super.onDestroy();
		if(dbHelper != null){
			dbHelper.close();
		}   	
    }

	private void updateStatistics(Cursor cursor) {
		TextView tv = (TextView)findViewById(R.id.home_text);               
        ArrayList<Filling> fillings = dbHelper.getFillings2();
        
        for(int i = 1; i < fillings.size(); i ++){        	
        	Filling previousfilling = fillings.get(i-1);        	
        	Filling filling = fillings.get(i);
        	previousfilling.setEndOdometer(filling.getOdometer());
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Total fillings: " + fillings.size()+"\n");
        
        Collections.reverse(fillings); //we wan't the newest at the top
           
        for(Filling filling: fillings){
        	
        	sb.append("Distance: "+ filling.getDistance() +"km, l: "+ filling.getAmount() +", km/l: "+ filling.calculateLitresPerKilometer()+"\n");
        }
        
        tv.setText(sb.toString());
        
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
    	switch(item.getItemId()){
    	case android.R.id.home:
			Intent intent = new Intent(this, Home.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
    	case R.id.settings:
    		startActivity(new Intent(this, Prefs.class));
    		return true;
    	case R.id.show_all:
    		startActivity(new Intent(this, Fillings.class));
    		return true;
    	}
    	return false;
    }
    
    public void onClick(View v){
    	switch (v.getId()){
    	case R.id.add_filling:
    		Intent i = new Intent(this, AddFilling.class);
    		startActivity(i);
    		break;
    	}
    }    
    
    @Override
    public void onResume(){
    	super.onResume();
    	updateStatistics(dbHelper.getFillings());    	
    }
}