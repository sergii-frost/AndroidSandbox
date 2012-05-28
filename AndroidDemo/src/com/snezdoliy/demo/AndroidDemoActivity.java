package com.snezdoliy.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class AndroidDemoActivity extends Activity {
    
	public static final String MOBILE_OS_EXTRA = "mobileOSExtra";
	public static final String USE_TOASTS_FLAG = "useToastsBoolean";
	public static final String USE_DIALOGS_FLAG = "useDialogsBoolean";
	
	private Button goFurtherButton;
	private ToggleButton toggleButton;
	private EditText emailEditText;
	private EditText passwordEditText;
	private Spinner mobileOsSpinner;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        mobileOsSpinner = (Spinner)findViewById(R.id.mobile_os_spinner);
        initButton();
        initToggleButton();
    }
    
    /**
     * Allows to init button to invoke next activity
     */
    private void initButton(){
    	goFurtherButton = (Button)findViewById(R.id.go_further_button);
    	goFurtherButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(emailEditText.length() == 0){					
					getSimpleAlert(R.string.empty_email_message).show();
				} else if(passwordEditText.length() == 0){
					getSimpleAlert(R.string.empty_password_message).show();
				} else {
					Intent resultListIntent = new Intent(v.getContext(), ResultListActivity.class);
					resultListIntent.putExtra(MOBILE_OS_EXTRA, mobileOsSpinner.getSelectedItemPosition());
					resultListIntent.putExtra(USE_TOASTS_FLAG, ((CheckBox)findViewById(R.id.checkBoxToast)).isChecked());
					resultListIntent.putExtra(USE_DIALOGS_FLAG, ((CheckBox)findViewById(R.id.checkBoxDialog)).isChecked());
					resultListIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(resultListIntent);
				}
			}
		});
    }
    
    /**
     * Allows to init button to enable/disable main button
     */
    private void initToggleButton(){
    	toggleButton = (ToggleButton)findViewById(R.id.toggleButton1);
    	toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				goFurtherButton.setEnabled(isChecked);
			}
		});
    	toggleButton.setChecked(true);
    	toggleButton.toggle();
    }
    
	private AlertDialog getSimpleAlert(int msgSrcId){
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
		alertBuilder.setMessage(msgSrcId)
					.setPositiveButton(R.string.ok_button_title, new AlertDialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface paramDialogInterface, int paramInt) {
							paramDialogInterface.dismiss();
						}
					});
		return alertBuilder.create();
	}
}