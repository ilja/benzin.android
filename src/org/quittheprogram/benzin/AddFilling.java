package org.quittheprogram.benzin;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddFilling extends Activity implements OnClickListener {
	DatabaseHelper dbHelper;
	private long filling_id = 0;
	
	static final int EDIT_FILLING = 0;
	
	private int pickedYear;
	private int pickedMonth;
	private int pickedDay;
	static final int DATE_DIALOG_ID = 0;
	private Button pickDate;
	private EditText dateDisplay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_filling);
		
		dbHelper = new DatabaseHelper(this);
		
		//date display and click handler
		dateDisplay = (EditText) findViewById(R.id.dateDisplay);		
		dateDisplay.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
		
		//current date
		final Calendar calendar = Calendar.getInstance();
		pickedYear = calendar.get(Calendar.YEAR);
		pickedMonth = calendar.get(Calendar.MONTH);
		pickedDay = calendar.get(Calendar.DAY_OF_MONTH);
		
		updateDateDisplay();
		
		//register save button handler
		View saveFilling = findViewById(R.id.btn_save_filling);
	    saveFilling.setOnClickListener(this);
	    
	    //are we trying to edit?
	    Bundle bundle = getIntent().getExtras();
	    if(bundle != null){ 
	    	
	    	// set id of object, so we can save changes to the same object
		    this.filling_id = bundle.getLong("filling_id");
		    
		    Cursor c = dbHelper.getFilling(filling_id);
		    if (c.moveToFirst()){
			    
			    EditText odometer = (EditText)findViewById(R.id.odometer);
			    EditText amount = (EditText)findViewById(R.id.amount);
			    EditText price = (EditText)findViewById(R.id.price);
			    
			    		    	    
			    Date date = new Date(java.sql.Date.parse(c.getString(1)));
			    			    
			    // add values to form
			    odometer.setText(Integer.toString(c.getInt(2)));
			    amount.setText(Double.toString(c.getDouble(3)));
			    price.setText(Double.toString(c.getDouble(4)));
			    pickedYear = date.getYear()+1900;
			    pickedMonth = date.getMonth();
			    pickedDay = date.getDate();
			    
		    }		   	   
	    }	    
	}

	public void onClick(View v) {
		switch (v.getId()){
    	case R.id.btn_save_filling:
    		
    		
    		EditText odometer = (EditText)findViewById(R.id.odometer);
    		EditText amount = (EditText)findViewById(R.id.amount);
    		EditText price = (EditText)findViewById(R.id.price);
    		
    		dbHelper.saveFilling(
    				new Date(pickedYear - 1900, pickedMonth, pickedDay), 
    				parseInt(odometer), 
    				parseDouble(amount), 
    				parseDouble(price),
    				this.filling_id
    				);
    		
    		//Intent i = new Intent(this, Home.class);
    		//startActivity(i);
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
	
	private void updateDateDisplay(){
		dateDisplay.setText(new StringBuilder()
			.append(pickedDay).append('-')
			.append(pickedMonth + 1).append('-')
			.append(pickedYear));
	}
	
	private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {		
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			pickedYear = year;
			pickedMonth = monthOfYear;
			pickedDay = dayOfMonth;
			updateDateDisplay();
		}
	};
	
	@Override
	protected Dialog onCreateDialog(int id){
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, dateSetListener, pickedYear, pickedMonth, pickedDay);
		}
		return null;
	}

}
