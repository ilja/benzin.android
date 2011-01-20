package org.quittheprogram.benzin;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Home extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        View addFilling = findViewById(R.id.add_filling);
        addFilling.setOnClickListener(this);
        
        TextView tv = (TextView)findViewById(R.id.home_text);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor cursor = dbHelper.getFillings();
        startManagingCursor(cursor);
        tv.setText("Total fillings: " + cursor.getCount());
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
}