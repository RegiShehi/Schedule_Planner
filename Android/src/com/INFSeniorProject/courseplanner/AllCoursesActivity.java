package com.INFSeniorProject.courseplanner;

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
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.INFSeniorProject.navigationdrawer.R;

public class AllCoursesActivity extends ListActivity {

	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> coursesList;

	// url to get all courses list
	private static String url_all_courses = "http://10.251.26.2/android_connect/get_all_courses.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_COURSES = "courses";
	private static final String TAG_PID = "pid";
	private static final String TAG_NAME = "name";
	private static final String TAG_TIME = "time";
	private static final String TAG_ROOM = "room";
	private static final String TAG_PROF = "prof";

	// courses JSONArray
	JSONArray courses = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.courseplanner_all_courses);
		setTitle("All Courses");

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		// Hashmap for ListView
		coursesList = new ArrayList<HashMap<String, String>>();

		// Loading courses in Background Thread
		new LoadAllCourses().execute();

		// Get listview
		ListView lv = getListView();

		// on seleting single course
		// launching Edit Course Screen
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String pid = ((TextView) view.findViewById(R.id.pid)).getText()
						.toString();

				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						EditCourseActivity.class);
				// sending pid to next activity
				in.putExtra(TAG_PID, pid);

				// starting new activity and expecting some response back
				startActivityForResult(in, 100);
			}
		});

	}

	// Response from Edit Course Activity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// if result code 100
		if (resultCode == 100) {
			// if result code 100 is received
			// means user edited/deleted course
			// reload this screen again
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}

	}

	/**
	 * Background Async Task to Load all course by making HTTP Request
	 * */
	class LoadAllCourses extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(AllCoursesActivity.this);
			pDialog.setMessage("Loading courses. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All courses from url
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_all_courses, "GET",
					params);

			// Check your log cat for JSON reponse
			Log.d("All Courses: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// courses found
					// Getting Array of Courses
					courses = json.getJSONArray(TAG_COURSES);

					// looping through All Courses
					for (int i = 0; i < courses.length(); i++) {
						JSONObject c = courses.getJSONObject(i);

						// Storing each json item in variable
						String id = c.getString(TAG_PID);
						String name = c.getString(TAG_NAME);
						String time = c.getString(TAG_TIME);
						String room = c.getString(TAG_ROOM);
						String prof = c.getString(TAG_PROF);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_PID, id);
						map.put(TAG_NAME, name);
						map.put(TAG_TIME, time);
						map.put(TAG_ROOM, room);
						map.put(TAG_PROF, prof);

						// adding HashList to ArrayList
						coursesList.add(map);
					}
				} else {
					// no courses found
					// Launch Add New Course Activity
					Intent i = new Intent(getApplicationContext(),
							NewCourseActivity.class);
					// Closing all previous activities
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
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
			// dismiss the dialog after getting all courses
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							AllCoursesActivity.this, coursesList,
							R.layout.courseplanner_list_item, new String[] {
									TAG_PID, TAG_NAME, TAG_TIME, TAG_ROOM, TAG_PROF }, new int[] { R.id.pid,
									R.id.name, R.id.time, R.id.room, R.id.prof });
					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
}