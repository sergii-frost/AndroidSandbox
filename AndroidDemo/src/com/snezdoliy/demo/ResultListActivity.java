/**
 * 
 */
package com.snezdoliy.demo;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author sergiynezdoliy
 *
 */
public class ResultListActivity extends Activity {
	
	private ListView devicesList;
	private List<String> devices;
	private boolean useToasts, useDialogs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_list_activity);		
		int mobile_os_selection = getIntent().getIntExtra(AndroidDemoActivity.MOBILE_OS_EXTRA, 0);
		useToasts = getIntent().getBooleanExtra(AndroidDemoActivity.USE_TOASTS_FLAG, false);
		useDialogs = getIntent().getBooleanExtra(AndroidDemoActivity.USE_DIALOGS_FLAG, false);
		devices = getDevicesArray(mobile_os_selection);
		devicesList = (ListView)findViewById(R.id.resultListView);		
		devicesList.setAdapter(new DeviceArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, devices));
		devicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> paramAdapterView,
					View paramView, int paramInt, long paramLong) {
				String value = ((TextView)paramView.findViewById(android.R.id.text1)).getText().toString();
				if(useToasts){
					Toast.makeText(getApplicationContext(), getString(R.string.toast_prefix) + value, 1000).show();
				}
				if(useDialogs){
					getSimpleAlert(getString(R.string.dialog_prefix) + value).show();
				}
			}
		});
	}
	
	private List<String> getDevicesArray(int os_selection){
		TypedArray all_devices = getResources().obtainTypedArray(R.array.devices_lists_array);		
		int n = all_devices.length();
		String[][] array = new String[n][];
		for (int i = 0; i < n; ++i) {
		    int id = all_devices.getResourceId(i, 0);
		    if (id > 0) {
		        array[i] = getResources().getStringArray(id);
		    } else {
		        // something wrong with the XML
		    }
		}
		all_devices.recycle(); // Important!			
		return Arrays.asList(array[os_selection]);
	}
	
	
	private class DeviceArrayAdapter extends ArrayAdapter<String> {
		private List<String> devicesList;
		int resId;
		
		public DeviceArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            devicesList = objects;
            resId = textViewResourceId;
        }

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {			
			// A ViewHolder keeps references to children views to avoid unnecessary calls
            // to findViewById() on each row.
            ViewHolder holder;
            
            View row = convertView;
            if(row == null){
            	LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = vi.inflate(resId, null);
                
                // Creates a ViewHolder and store references to the two children views
                // we want to bind data to.
                holder = new ViewHolder();
                holder.deviceTitle = (TextView)row.findViewById(android.R.id.text1);
                row.setTag(holder);
            } else {
                // Get the ViewHolder back to get fast access to the fields.
                holder = (ViewHolder) row.getTag();
            }    
            String item = devicesList.get(position);
            if(item != null){
            	holder.deviceTitle.setText(item);
            }
            
			return row;
		}
		
	}

	static class ViewHolder {
		TextView deviceTitle;
    }

	private AlertDialog getSimpleAlert(String msgString){
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
		alertBuilder.setMessage(msgString)
					.setPositiveButton(R.string.ok_button_title, new AlertDialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface paramDialogInterface, int paramInt) {
							paramDialogInterface.dismiss();
						}
					});
		return alertBuilder.create();
	}
	
}
