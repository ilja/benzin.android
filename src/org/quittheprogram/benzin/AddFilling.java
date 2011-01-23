package org.quittheprogram.benzin;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddFilling extends Activity implements OnClickListener {
	DatabaseHelper dbHelper;
	
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
	    if(bundle == null) return; 
	    
	    String dateString = bundle.getString("Date");
	    
	    
	    DatePicker datePicker = (DatePicker)findViewById(R.id.date);
	    EditText odometer = (EditText)findViewById(R.id.odometer);
	    EditText amount = (EditText)findViewById(R.id.amount);
	    EditText price = (EditText)findViewById(R.id.price);
	    
	    	    
	    Date date = new Date(java.sql.Date.parse(dateString));
	    
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    
		
	    datePicker.updateDate(calendar.get(Calendar.YEAR) -1900, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
	    
	    
	    //odometer.setText(dateString);
	    
	}

	public void onClick(View v) {
		switch (v.getId()){
    	case R.id.btn_save_filling:
    		
    		DatePicker datePicker = (DatePicker)findViewById(R.id.date);
    		EditText odometer = (EditText)findViewById(R.id.odometer);
    		EditText amount = (EditText)findViewById(R.id.amount);
    		EditText price = (EditText)findViewById(R.id.price);
    		
    		dbHelper.saveFilling(new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth()), parseInt(odometer), parseDouble(amount), parseDouble(price));
    		
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
