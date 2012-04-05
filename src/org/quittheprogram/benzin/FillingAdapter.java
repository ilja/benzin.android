package org.quittheprogram.benzin;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FillingAdapter extends BaseAdapter {
	// store the context (as an inflated layout)
    private LayoutInflater inflater;
    // store the resource (typically filling.xml)
    private int resource;
    // store (a reference to) the data
    private ArrayList<Filling> data;
	
	
    
	public FillingAdapter(Context context, int resource, ArrayList<Filling> data) {
		super();
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.data = data;		
	}

	public int getCount() {
		return this.data.size();
	}

	public Object getItem(int position) {
		return this.data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// reuse a given view, or inflate a new one from the xml
        View view;
         
        if (convertView == null) {
                view = this.inflater.inflate(resource, parent, false);
        } else {
                view = convertView;
        }
        
        // bind the data to the view object
        return this.bindData(view, position);
	}
	
	public View bindData(View view, int position){
		 // make sure it's worth drawing the view
        if (this.data.get(position) == null) {
                return view;
        }
        
        // pull out the object
        Filling filling = (Filling)this.data.get(position);
        
        // find id textview and set id 
        TextView idTextView = (TextView)view.findViewById(R.id.rowid); 
        idTextView.setText(Integer.toString(filling.getId()));
        
        // find odometer textview and set text 
        TextView odoMeterTextView = (TextView)view.findViewById(R.id.rowOdometer); 
        odoMeterTextView.setText(Integer.toString(filling.getOdometer()));
        
        // find amount textview and set text
        TextView amountTextView = (TextView)view.findViewById(R.id.rowAmount); 
        amountTextView.setText(Integer.toString(filling.getAmount()));
        
        // find price textview and set text
        TextView priceTextView = (TextView)view.findViewById(R.id.rowPrice); 
        priceTextView.setText(Double.toString(filling.getPrice()));
                
        // find date view and set text
        TextView dateTextView = (TextView)view.findViewById(R.id.rowDate); 
        dateTextView.setText(filling.getFormattedDate());
               
            
        // return the final view object		
		return view;
	}
	
	public void Update(ArrayList<Filling> fillings){
		this.data = fillings;
		this.notifyDataSetChanged();
	}

}
