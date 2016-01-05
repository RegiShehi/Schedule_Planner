package com.INFSeniorProject.multilevellistview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
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

public class DepartmentsActivity extends ListActivity {
	// Connection detector
	ConnectionDetector cd;

	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();

	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	ArrayList<HashMap<String, String>> departmentsList;

	// departments JSONArray
	JSONArray departments = null;

	// departments JSON url
	private static final String URL_DEPARTMENTS = "http://10.251.26.2/catalogue/departments.php";

	// ALL JSON node names
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_COURSES_COUNT = "courses_count";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multilevel_activity_departments);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		cd = new ConnectionDetector(getApplicationContext());

		// Check for internet connection
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(DepartmentsActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}

		// Hashmap for ListView
		departmentsList = new ArrayList<HashMap<String, String>>();

		// Loading Departments JSON in Background Thread
		new LoadDepartments().execute();

		// get listview
		ListView lv = getListView();

		/**
		 * Listview item click listener TrackListActivity will be lauched by
		 * passing department id
		 * */
		lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// on selecting a single department
				// TrackListActivity will be launched to show courses inside the
				// department
				Intent i = new Intent(getApplicationContext(),
						SubjectListActivity.class);

				// send department id to tracklist activity to get list courses
				// under that department
				String department_id = ((TextView) view
						.findViewById(R.id.department_id)).getText().toString();
				i.putExtra("department_id", department_id);

				startActivity(i);
				finish();
			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent int2 = new Intent(DepartmentsActivity.this,
				HomeActivity.class);			
		startActivity(int2);
		finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			Intent int2 = new Intent(DepartmentsActivity.this,
					HomeActivity.class);			
			startActivity(int2);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	/**
	 * Background Async Task to Load all Departments by making http request
	 * */
	class LoadDepartments extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DepartmentsActivity.this);
			pDialog.setMessage("Listing Departments ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting Departments JSON
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			// getting JSON string from URL
			String json = jsonParser.makeHttpRequest(URL_DEPARTMENTS, "GET",
					params);

			// Check your log cat for JSON reponse
			Log.d("Departments JSON: ", "> " + json);

			try {
				departments = new JSONArray(json);

				if (departments != null) {
					// looping through All Departments
					for (int i = 0; i < departments.length(); i++) {
						JSONObject c = departments.getJSONObject(i);

						// Storing each json item values in variable
						String id = c.getString(TAG_ID);
						String name = c.getString(TAG_NAME);
						String courses_count = c.getString(TAG_COURSES_COUNT);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_ID, id);
						map.put(TAG_NAME, name);
						map.put(TAG_COURSES_COUNT, courses_count);

						// adding HashList to ArrayList
						departmentsList.add(map);
					}
				} else {
					Log.d("Departments: ", "null");
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
			// dismiss the dialog after getting all Departments
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							DepartmentsActivity.this,
							departmentsList,
							R.layout.multilevel_list_item_departments,
							new String[] { TAG_ID, TAG_NAME, TAG_COURSES_COUNT },
							new int[] { R.id.department_id,
									R.id.department_name, R.id.courses_count });

					// updating listview
					setListAdapter(adapter);
					setTitle("Majors");
				}
			});

		}

	}
}