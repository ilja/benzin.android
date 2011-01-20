package org.quittheprogram.benzin;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_filling);
		
		dbHelper = new DatabaseHelper(this);
		
		//save
		View saveFilling = findViewById(R.id.btn_save_filling);
	    saveFilling.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()){
    	case R.id.btn_save_filling:
    		
    		DatePicker datePicker = (DatePicker)findViewById(R.id.date);
    		EditText odometer = (EditText)findViewById(R.id.odometer);
    		EditText amount = (EditText)findViewById(R.id.amount);
    		EditText price = (EditText)findViewById(R.id.price);
    		
    		dbHelper.saveFilling(new Date(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth()), parseInt(odometer), parseDouble(amount), parseDouble(price));
    		
    		Intent i = new Intent(this, Home.class);
    		startActivity(i);
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
