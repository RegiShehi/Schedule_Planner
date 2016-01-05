package com.INFSeniorProject.courseplanner;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
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

import com.INFSeniorProject.navigationdrawer.R;

public class EditCourseActivity extends Activity {

	EditText txtName, txtTime, txtRoom, txtProf, txtDesc, txtCreatedAt;
	Button btnSave, btnDelete;

	String pid;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// single course url
	private static final String url_course_detials = "http://10.251.26.2/android_connect/get_course_details.php";

	// url to update course
	private static final String url_update_course = "http://10.251.26.2/android_connect/update_course.php";
	
	// url to delete course
	private static final String url_delete_course = "http://10.251.26.2/android_connect/delete_course.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_COURSE = "course";
	private static final String TAG_PID = "pid";
	private static final String TAG_NAME = "name";
	private static final String TAG_TIME = "time";
	private static final String TAG_ROOM = "room";
	private static final String TAG_PROF = "prof";
	private static final String TAG_DESCRIPTION = "description";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.courseplanner_edit_course);
		setTitle("Edit Course");

		// save button
		btnSave = (Button) findViewById(R.id.btnSave);
		btnDelete = (Button) findViewById(R.id.btnDelete);

		// getting course details from intent
		Intent i = getIntent();
		
		// getting course id (pid) from intent
		pid = i.getStringExtra(TAG_PID);

		// Getting complete course details in background thread
		new GetCourseDetails().execute();

		// save button click event
		btnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// starting background task to update course
				new SaveCourseDetails().execute();
			}
		});

		// Delete button click event
		btnDelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// deleting course in background thread
				new DeleteCourse().execute();
			}
		});

	}

	/**
	 * Background Async Task to Get complete course details
	 * */
	class GetCourseDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditCourseActivity.this);
			pDialog.setMessage("Loading course details. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Getting course details in background thread
		 * */
		protected String doInBackground(String... params) {

			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					// Check for success tag
					int success;
					try {
						// Building Parameters
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("pid", pid));

						// getting course details by making HTTP request
						// Note that course details url will use GET request
						JSONObject json = jsonParser.makeHttpRequest(
								url_course_detials, "GET", params);

						// check your log for json response
						Log.d("Single Course Details", json.toString());
						
						// json success tag
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							// successfully received course details
							JSONArray courseObj = json
									.getJSONArray(TAG_COURSE); // JSON Array
							
							// get first course object from JSON Array
							JSONObject course = courseObj.getJSONObject(0);

							// course with this pid found
							// Edit Text
							txtName = (EditText) findViewById(R.id.inputName);
							txtTime = (EditText) findViewById(R.id.inputTime);
							txtRoom = (EditText) findViewById(R.id.inputRoom);
							txtProf = (EditText) findViewById(R.id.inputProf);
							txtDesc = (EditText) findViewById(R.id.inputDesc);

							// display course data in EditText
							txtName.setText(course.getString(TAG_NAME));
							txtTime.setText(course.getString(TAG_TIME));
							txtRoom.setText(course.getString(TAG_ROOM));
							txtProf.setText(course.getString(TAG_PROF));
							txtDesc.setText(course.getString(TAG_DESCRIPTION));

						}else{
							// course with pid not found
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});

			return null;
		}


		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once got all details
			pDialog.dismiss();
		}
	}

	/**
	 * Background Async Task to  Save course Details
	 * */
	class SaveCourseDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditCourseActivity.this);
			pDialog.setMessage("Saving course ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Saving course
		 * */
		protected String doInBackground(String... args) {

			// getting updated data from EditTexts
			String name = txtName.getText().toString();
			String time = txtTime.getText().toString();
			String room = txtRoom.getText().toString();
			String prof = txtProf.getText().toString();
			String description = txtDesc.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_PID, pid));
			params.add(new BasicNameValuePair(TAG_NAME, name));
			params.add(new BasicNameValuePair(TAG_TIME, time));
			params.add(new BasicNameValuePair(TAG_ROOM, room));
			params.add(new BasicNameValuePair(TAG_PROF, prof));
			params.add(new BasicNameValuePair(TAG_DESCRIPTION, description));

			// sending modified data through http request
			// Notice that update course url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_update_course,
					"POST", params);

			// check json success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				
				if (success == 1) {
					// successfully updated
					Intent i = getIntent();
					// send result code 100 to notify about course update
					setResult(100, i);
					finish();
				} else {
					// failed to update course
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
			// dismiss the dialog once course updated
			pDialog.dismiss();
		}
	}

	/*****************************************************************
	 * Background Async Task to Delete Course
	 * */
	class DeleteCourse extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditCourseActivity.this);
			pDialog.setMessage("Deleting Course...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Deleting course
		 * */
		protected String doInBackground(String... args) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("pid", pid));

				// getting course details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(
						url_delete_course, "POST", params);

				// check your log for json response
				Log.d("Delete Course", json.toString());
				
				// json success tag
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					// course successfully deleted
					// notify previous activity by sending code 100
					Intent i = getIntent();
					// send result code 100 to notify about course deletion
					setResult(100, i);
					finish();
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
			// dismiss the dialog once course deleted
			pDialog.dismiss();

		}

	}
}
