package com.INFSeniorProject.courseplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.INFSeniorProject.navigationdrawer.HomeActivity;
import com.INFSeniorProject.navigationdrawer.R;

public class MainScreenActivity extends Activity {

	Button btnViewCourses;
	Button btnNewCourse;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.courseplanner_main_screen);
		setTitle("Course Planner");

		getActionBar().setDisplayHomeAsUpEnabled(true);

		// Buttons
		btnViewCourses = (Button) findViewById(R.id.btnViewCourses);
		btnNewCourse = (Button) findViewById(R.id.btnCreateCourse);

		// view courses click event
		btnViewCourses.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching All Courses Activity
				Intent i = new Intent(getApplicationContext(),
						AllCoursesActivity.class);
				startActivity(i);

			}
		});

		// view courses click event
		btnNewCourse.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching create new course activity
				Intent i = new Intent(getApplicationContext(),
						NewCourseActivity.class);
				startActivity(i);

			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			Intent int2 = new Intent(getApplicationContext(),
					HomeActivity.class);
			startActivity(int2);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent int2 = new Intent(getApplicationContext(),
				HomeActivity.class);
		startActivity(int2);
		finish();
	}	
}
