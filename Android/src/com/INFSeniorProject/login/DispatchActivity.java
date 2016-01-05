package com.INFSeniorProject.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.INFSeniorProject.navigationdrawer.HomeActivity;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Activity which starts an intent for either the logged in (MainActivity) or
 * logged out (SignUpOrLoginActivity) activity.
 */
public class DispatchActivity extends Activity {
	

	public DispatchActivity() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Parse.initialize(this, "wkRubEssxJkpgFUh0vxLCj3xOksetpw7ybi6HEPL",
				"qaURSOsrsMg72GzRIY3CTDlXI36r7eZEGEcT0qlX");
		
		ParseObject testObject = new ParseObject("TestObject");
		testObject.put("foo", "bar");
		testObject.saveInBackground();
		
		// Check if there is current user info
		if (ParseUser.getCurrentUser() != null) {
			// Start an intent for the logged in activity
			startActivity(new Intent(this, HomeActivity.class));
		} else {
			// Start and intent for the logged out activity
			startActivity(new Intent(this, SignUpOrLogInActivity.class));
		}
	}
}