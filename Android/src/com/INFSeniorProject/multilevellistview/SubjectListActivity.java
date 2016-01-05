package com.INFSeniorProject.multilevellistview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.INFSeniorProject.navigationdrawer.HomeActivity;
import com.INFSeniorProject.navigationdrawer.R;

public class SubjectListActivity extends ListActivity {
	// Connection detector
	ConnectionDetector cd;

	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();

	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	ArrayList<HashMap<String, String>> subjectsList;

	// tracks JSONArray
	JSONArray departments = null;

	// Department id
	String department_id, department_name;

	// tracks JSON url
	// id - should be posted as GET params to get track list (ex: id = 5)
	private static final String URL_DEPARTMENTS = "http://10.251.26.2/catalogue/department_subjects.php";

	// ALL JSON node names
	private static final String TAG_COURSES = "courses";
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_DEPARTMENT = "department";
	private static final String TAG_CREDITS = "credits";
	private static final String TAG_CODE = "code";
	private static final String TAG_DESCRIPTION = "description";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multilevel_activity_subjects);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(SubjectListActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}

		// Get department id
		Intent i = getIntent();
		department_id = i.getStringExtra("department_id");

		// Hashmap for ListView
		subjectsList = new ArrayList<HashMap<String, String>>();

		// Loading tracks in Background Thread
		new LoadTracks().execute();

		// get listview
		ListView lv = getListView();

		/**
		 * Listview on item click listener SingleTrackActivity will be lauched
		 * by passing department id, course id
		 * */
		lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// On selecting single subject get course information
				Intent i = new Intent(getApplicationContext(),
						SingleSubjectActivity.class);

				// to get course information
				// both department id and course is needed
				String department_id = ((TextView) view
						.findViewById(R.id.department_id)).getText().toString();
				String course_id = ((TextView) view
						.findViewById(R.id.course_id)).getText().toString();

				i.putExtra("department_id", department_id);
				i.putExtra("course_id", course_id);

				startActivity(i);
			}
		});
	}

	@Override
	public void onBackPressed() {
		Intent int2 = new Intent(SubjectListActivity.this,
				DepartmentsActivity.class);			
		startActivity(int2);
		finish();
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			Intent int2 = new Intent(SubjectListActivity.this,
					DepartmentsActivity.class);
			startActivity(int2);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Background Async Task to Load all subjects under one department
	 * */
	class LoadTracks extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SubjectListActivity.this);
			pDialog.setMessage("Loading courses ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting tracks json and parsing
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			// post department id as GET parameter
			params.add(new BasicNameValuePair(TAG_ID, department_id));

			// getting JSON string from URL
			String json = jsonParser.makeHttpRequest(URL_DEPARTMENTS, "GET",
					params);

			// Check your log cat for JSON reponse
			Log.d("Subject List JSON: ", json);

			try {
				JSONObject jObj = new JSONObject(json);
				if (jObj != null) {
					String department_id = jObj.getString(TAG_ID);
					department_name = jObj.getString(TAG_DEPARTMENT);
					departments = jObj.getJSONArray(TAG_COURSES);

					if (departments != null) {
						// looping through All courses
						for (int i = 0; i < departments.length(); i++) {
							JSONObject c = departments.getJSONObject(i);

							// Storing each json item in variable
							String course_id = c.getString(TAG_ID);
							// track no - increment i value
							String subject_no = String.valueOf(i + 1);
							String name = c.getString(TAG_NAME);
							String code = c.getString(TAG_CODE);
							String credits = c.getString(TAG_CREDITS);
							String description = c.getString(TAG_DESCRIPTION);

							// creating new HashMap
							HashMap<String, String> map = new HashMap<String, String>();

							// adding each child node to HashMap key => value
							map.put("department_id", department_id);
							map.put(TAG_ID, course_id);
							map.put("subject_no", subject_no + ".");
							map.put(TAG_NAME, name);
							map.put(TAG_CODE, code);
							map.put(TAG_CREDITS, credits);
							map.put(TAG_DESCRIPTION, description);

							// adding HashList to ArrayList
							subjectsList.add(map);
						}
					} else {
						Log.d("Departments: ", "null");
					}
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
			// dismiss the dialog after getting all subjects
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							SubjectListActivity.this, subjectsList,
							R.layout.multilevel_list_item_subjects,
							new String[] { "department_id", TAG_ID,
									TAG_CREDITS, "subject_no", TAG_NAME,
									TAG_CODE, TAG_DESCRIPTION }, new int[] {
									R.id.department_id, R.id.course_id,
									R.id.course_credits, R.id.subject_no,
									R.id.department_name, R.id.course_code,
									R.id.course_description });
					// updating listview
					setListAdapter(adapter);

					// Change Activity Title with Department name
					setTitle(department_name);
				}
			});

		}

	}
}