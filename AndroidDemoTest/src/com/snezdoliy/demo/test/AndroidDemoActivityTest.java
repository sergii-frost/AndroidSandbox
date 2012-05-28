package com.snezdoliy.demo.test;

import com.jayway.android.robotium.solo.Solo;
import com.snezdoliy.demo.AndroidDemoActivity;
import com.snezdoliy.demo.R;
import com.snezdoliy.demo.ResultListActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class AndroidDemoActivityTest extends
		ActivityInstrumentationTestCase2<AndroidDemoActivity> {

	public static final String EMAIL = "user@domain.com";
	public static final String PASSWORD = "qwe123:)";
	
	public AndroidDemoActivityTest() throws ClassNotFoundException{
		super(AndroidDemoActivity.class);
	}
	
	private Solo solo;
	
	@Override
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void test001EnterEmailAndPassword(){
		//given
		//when
		solo.typeText(0, EMAIL);
		solo.typeText(1, PASSWORD);
		//then
		assertEquals("Email is equal to what was input", EMAIL, solo.getEditText(0).getText().toString());
		assertEquals("Password is equal to what was input", PASSWORD, solo.getEditText(1).getText().toString());
	}
	
	public void test002WorkWithChekboxes(){
		//given
		//when
		solo.clickOnCheckBox(0);
		boolean currentValue = ((CheckBox)solo.getView(R.id.checkBoxToast)).isChecked();
		//then
		assertEquals("First check box is checked", true, currentValue);
	}
	
	public void test003CheckToggleAndGoButton(){
		//given
		boolean toggleIsInDisabledState = solo.searchToggleButton(solo.getString(R.string.text_off));
		//when
		if(toggleIsInDisabledState){
			solo.clickOnToggleButton(solo.getString(R.string.text_off));
		}
		//then
		boolean buttonIsEnabled = solo.getButton(solo.getString(R.string.go_further_button)).isEnabled();
		assertEquals("Button becomes enabled when toggle is set to enabled state", solo.searchToggleButton(solo.getString(R.string.text_on)), buttonIsEnabled);
		
		//when		
		solo.clickOnToggleButton(solo.getString(R.string.text_on));
		//then
		boolean buttonIsDisabled = !solo.getButton(solo.getString(R.string.go_further_button)).isEnabled();
		assertEquals("Button becomes disabled when toggle is set to disabled state", solo.searchToggleButton(solo.getString(R.string.text_off)), buttonIsDisabled);

	}
	
	public void test004checkSpinnerField(){
		//given
		String[] itemsArray = this.getActivity().getResources().getStringArray(R.array.mobile_os_array);
		Spinner spinnerField = (Spinner)solo.getView(R.id.mobile_os_spinner);
		//when
		solo.pressSpinnerItem(0, 2);
		//then
		assertEquals("Spinner value should be equal to selected one", itemsArray[2], spinnerField.getSelectedItem().toString());
	}
	
	public void test005validationMessageOnEmptyEmail(){
		//given
		EditText emailEditText = (EditText)solo.getView(R.id.emailEditText);
		//when
		solo.typeText(emailEditText, "test");
		solo.sleep(300);
		solo.clearEditText(emailEditText);
		if(solo.searchToggleButton(solo.getString(R.string.text_off))){
			solo.clickOnToggleButton(solo.getString(R.string.text_off));
		}
		solo.clickOnButton(solo.getString(R.string.go_further_button));
		//then		
		assertEquals("Message is displayed", true, solo.waitForText(solo.getString(R.string.empty_email_message), 1, 100));
		
	}
	
	public void test006validationMessageOnEmptyPassword(){
		//given
		EditText emailEditText = (EditText)solo.getView(R.id.emailEditText);
		EditText passwordEditText = (EditText)solo.getView(R.id.passwordEditText);
		//when
		solo.typeText(emailEditText, "test");
		solo.typeText(passwordEditText, "password test");
		solo.sleep(300);
		solo.clearEditText(passwordEditText);
		if(solo.searchToggleButton(solo.getString(R.string.text_off))){
			solo.clickOnToggleButton(solo.getString(R.string.text_off));
		}
		solo.clickOnButton(solo.getString(R.string.go_further_button));
		//then		
		assertEquals("Message is displayed", true, solo.waitForText(solo.getString(R.string.empty_password_message), 1, 100));
	}
	
	public void test007validateToastsMessage(){
		//given
		String valueToTypeOn = getActivity().getResources().getStringArray(R.array.ios_devices)[1];
		solo.typeText(0, "test@test.com");
		solo.typeText(1, "password test");
		solo.clickOnCheckBox(0);
		solo.pressSpinnerItem(0, 1);//iOS devices list
		if(solo.searchToggleButton(solo.getString(R.string.text_off))){
			solo.clickOnToggleButton(solo.getString(R.string.text_off));
		}				
		//when
		solo.clickOnButton(solo.getString(R.string.go_further_button));
		solo.sleep(100);
		//then
		solo.assertCurrentActivity("Current activity should be list", ResultListActivity.class);
		
		//when		
		solo.clickOnText(valueToTypeOn);
		//then
		assertEquals("Toast will be displayed", true, solo.waitForText(solo.getString(R.string.toast_prefix) + valueToTypeOn));
	}
	
	//this test will fail as it won't check appropriate checkbox to display dialogs
	public void test008validateDialogsMessage(){
		//given
		String valueToTypeOn = getActivity().getResources().getStringArray(R.array.blackberry_devices)[2];
		solo.typeText(0, "test@test.com");
		solo.typeText(1, "password test");
		//change this part to have pass result: 0 --> 1
		solo.clickOnCheckBox(0);
		solo.pressSpinnerItem(0, 2);//Blackberry devices list
		if(solo.searchToggleButton(solo.getString(R.string.text_off))){
			solo.clickOnToggleButton(solo.getString(R.string.text_off));
		}				
		//when
		solo.clickOnButton(solo.getString(R.string.go_further_button));
		solo.sleep(100);
		//then
		solo.assertCurrentActivity("Current activity should be list", ResultListActivity.class);
		
		//when		
		solo.clickOnText(valueToTypeOn);
		//then
		assertEquals("Dialog will be displayed", true, solo.waitForText(solo.getString(R.string.dialog_prefix) + valueToTypeOn));
	}

	
	@Override
	public void tearDown() throws Exception {
        solo.finishOpenedActivities();
	}	
}
