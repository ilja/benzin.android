package org.quittheprogram.benzin;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddFilling extends Activity implements OnClickListener {
	DatabaseHelper dbHelper;
	private long filling_id = 0;
	
	static final int EDIT_FILLING = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_filling);
		
		dbHelper = new DatabaseHelper(this);
		
		//register save button handler
		View saveFilling = findViewById(R.id.btn_save_filling);
	    saveFilling.setOnClickListener(this);
	    
	    //are we trying to edit?
	    Bundle bundle = getIntent().getExtras();
	    if(bundle != null){ 
	    	    
		    this.filling_id = bundle.getLong("filling_id");
		    
		    Cursor c = dbHelper.getFilling(filling_id);
		    if (c.moveToFirst()){
		    //String dateString = bundle.getString("Date");
		    	
			    
			    DatePicker datePicker = (DatePicker)findViewById(R.id.date);
			    EditText odometer = (EditText)findViewById(R.id.odometer);
			    EditText amount = (EditText)findViewById(R.id.amount);
			    EditText price = (EditText)findViewById(R.id.price);
			    
			    	    
			    Date date = new Date(java.sql.Date.parse(c.getString(1)));
			    
			    Calendar calendar = Calendar.getInstance();			    
			    calendar.setTime(date);
			    
			    //odometer.setText(c.getInt(2));
			    //amount.setText(Double.toString(c.getDouble(3)));
			    //price.setText(bundle.getString("Price"));		    
				
			    datePicker.updateDate(calendar.get(Calendar.YEAR) -1900, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		    }
		   	   
	    }
	    
	}

	public void onClick(View v) {
		switch (v.getId()){
    	case R.id.btn_save_filling:
    		
    		DatePicker datePicker = (DatePicker)findViewById(R.id.date);
    		EditText odometer = (EditText)findViewById(R.id.odometer);
    		EditText amount = (EditText)findViewById(R.id.amount);
    		EditText price = (EditText)findViewById(R.id.price);
    		
    		dbHelper.saveFilling(
    				new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth()), 
    				parseInt(odometer), 
    				parseDouble(amount), 
    				parseDouble(price),
    				this.filling_id
    				);
    		
    		Intent i = new Intent(this, Home.class);
    		startActivity(i);
    		finish();
    		break;
    	}		
	}
		
	private int parseInt(EditText et){
		int num = 0;
		try {
		    num = Integer.parseInt(et.getText().toString());
		} catch(NumberFormatException nfe) {
		   System.out.println("Could not parse " + nfe);
		} 
		return num;
	}
	
	private double parseDouble(EditText et){
		double d = 0;
		try{
			d = Double.parseDouble(et.getText().toString());
		} catch (NumberFormatException nfe){
			Log.e("AddFilling", "Could not parse to double " + nfe);
		}
		return d;
	}
	

}
