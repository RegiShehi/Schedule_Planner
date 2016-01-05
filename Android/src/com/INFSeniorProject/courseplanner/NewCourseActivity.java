package com.INFSeniorProject.courseplanner;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.INFSeniorProject.navigationdrawer.R;

public class NewCourseActivity extends Activity {

	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	EditText inputName, inputTime, inputRoom, inputProf, inputDesc;
	TextView view1;

	// url to create new course
	private static String url_create_course = "http://10.251.26.2/android_connect/create_course.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.courseplanner_add_course);
		setTitle("New Course");

		// Edit Text
		inputName = (EditText) findViewById(R.id.inputName);
		inputTime = (EditText) findViewById(R.id.inputTime);
		inputRoom = (EditText) findViewById(R.id.inputRoom);
		inputProf = (EditText) findViewById(R.id.inputProf);
		inputDesc = (EditText) findViewById(R.id.inputDesc);
		view1 = (TextView) findViewById(R.id.textView1);

		// Create button
		Button btnCreateCourse = (Button) findViewById(R.id.btnCreateCourse);

		// button click event
		btnCreateCourse.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// creating new course in background thread
				if (inputName.getText().toString().isEmpty()) {
					view1.setVisibility(View.VISIBLE);
				} else
					new CreateNewCourse().execute();
			}
		});
	}

	/**
	 * Background Async Task to Create new course
	 * */
	class CreateNewCourse extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(NewCourseActivity.this);
			pDialog.setMessage("Creating Course..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating course
		 * */
		protected String doInBackground(String... args) {
			String name = inputName.getText().toString();
			String time = inputTime.getText().toString();
			String room = inputRoom.getText().toString();
			String prof = inputProf.getText().toString();
			String description = inputDesc.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("time", time));
			params.add(new BasicNameValuePair("room", room));
			params.add(new BasicNameValuePair("prof", prof));
			params.add(new BasicNameValuePair("description", description));

			// getting JSON Object
			// Note that create course url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_course,
					"POST", params);

			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully created course
					Intent i = new Intent(getApplicationContext(),
							AllCoursesActivity.class);
					startActivity(i);

					// closing this screen
					finish();
				} else {
					// failed to create course
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}

	}
}
