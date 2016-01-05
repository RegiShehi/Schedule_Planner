package com.INFSeniorProject.multilevellistview;

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
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.INFSeniorProject.navigationdrawer.R;

public class SingleSubjectActivity extends Activity {
	// Connection detector
	ConnectionDetector cd;

	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();

	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	// subjects JSONArray
	JSONArray departments = null;

	// Department id
	String department_id = null;
	String course_id = null;

	String department_name, course_name, credits, course_code,
			course_description;

	// single course JSON url
	// GET parameters department, course
	private static final String URL_COURSE = "http://10.251.26.2/catalogue/subject.php";

	// ALL JSON node names
	private static final String TAG_NAME = "name";
	private static final String TAG_CREDITS = "credits";
	private static final String TAG_DEPARTMENT = "department";
	private static final String TAG_CODE = "code";
	private static final String TAG_DESCRIPTION = "description";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multilevel_activity_single_subject);

		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(SingleSubjectActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}

		// Get department id, course id
		Intent i = getIntent();
		department_id = i.getStringExtra("department_id");
		course_id = i.getStringExtra("course_id");

		// calling background thread
		new LoadSingleTrack().execute();
	}

	/**
	 * Background Async Task to get single course information
	 * */
	class LoadSingleTrack extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SingleSubjectActivity.this);
			pDialog.setMessage("Loading course ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting course json and parsing
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			// post department id, course id as GET parameters
			params.add(new BasicNameValuePair("department", department_id));
			params.add(new BasicNameValuePair("course", course_id));

			// getting JSON string from URL
			String json = jsonParser.makeHttpRequest(URL_COURSE, "GET", params);

			// Check your log cat for JSON reponse
			Log.d("Single Subject JSON: ", json);

			try {
				JSONObject jObj = new JSONObject(json);
				if (jObj != null) {
					course_name = jObj.getString(TAG_NAME);
					department_name = jObj.getString(TAG_DEPARTMENT);
					credits = jObj.getString(TAG_CREDITS);
					course_code = jObj.getString(TAG_CODE);
					course_description = jObj.getString(TAG_DESCRIPTION);
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
			// dismiss the dialog after getting course information
			pDialog.dismiss();

			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {

					TextView txt_course_title = (TextView) findViewById(R.id.course_title);
					TextView txt_department_name = (TextView) findViewById(R.id.department_name);
					TextView txt_credits = (TextView) findViewById(R.id.credits);
					TextView txt_code = (TextView) findViewById(R.id.course_code);
					TextView txt_description = (TextView) findViewById(R.id.description);

					// displaying course data in view
					txt_course_title.setText(course_name);
					txt_code.setText(Html.fromHtml(course_code));
					txt_department_name.setText(Html.fromHtml(department_name));
					txt_credits.setText(Html.fromHtml(credits));
					txt_description.setText(Html.fromHtml(course_description));

					// Change Activity Title with Course title
					setTitle(course_name);
				}
			});

		}

	}
}
