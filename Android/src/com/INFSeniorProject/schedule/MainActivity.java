package com.INFSeniorProject.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.INFSeniorProject.navigationdrawer.HomeActivity;
import com.INFSeniorProject.navigationdrawer.R;
import com.INFSeniorProject.schedule.CustomCourseDialogFragment.CustomEmpDialogFragmentListener;

public class MainActivity extends FragmentActivity implements
		CustomEmpDialogFragmentListener {

	private Fragment contentFragment;
	private CourseListFragment courseListFragment;
	private CourseAddFragment courseAddFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_activity_main);

		FragmentManager fragmentManager = getSupportFragmentManager();

		DepartmentDAO deptDAO = new DepartmentDAO(this);
		setTitle("Course Schedule");

		getActionBar().setDisplayHomeAsUpEnabled(true);

		// Initially loads departments
		if (deptDAO.getDepartments().size() <= 0)
			deptDAO.loadDepartments();

		/*
		 * This is called when orientation is changed.
		 */
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey("content")) {
				String content = savedInstanceState.getString("content");
				if (content.equals(CourseAddFragment.ARG_ITEM_ID)) {
					if (fragmentManager
							.findFragmentByTag(CourseAddFragment.ARG_ITEM_ID) != null) {
						setFragmentTitle(R.string.add_course);
						contentFragment = fragmentManager
								.findFragmentByTag(CourseAddFragment.ARG_ITEM_ID);
					}
				}
			}
			if (fragmentManager
					.findFragmentByTag(CourseListFragment.ARG_ITEM_ID) != null) {
				courseListFragment = (CourseListFragment) fragmentManager
						.findFragmentByTag(CourseListFragment.ARG_ITEM_ID);
				contentFragment = courseListFragment;
			}
		} else {
			courseListFragment = new CourseListFragment();
			setFragmentTitle(R.string.app_name);
			switchContent(courseListFragment, CourseListFragment.ARG_ITEM_ID);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add:
			setFragmentTitle(R.string.add_course);
			courseAddFragment = new CourseAddFragment();
			switchContent(courseAddFragment, CourseAddFragment.ARG_ITEM_ID);

			return true;

		case android.R.id.home:
			Intent int2 = new Intent(MainActivity.this, HomeActivity.class);
			startActivity(int2);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if (contentFragment instanceof CourseAddFragment) {
			outState.putString("content", CourseAddFragment.ARG_ITEM_ID);
		} else {
			outState.putString("content", CourseListFragment.ARG_ITEM_ID);
		}
		super.onSaveInstanceState(outState);
	}

	/*
	 * We consider CourseListFragment as the home fragment and it is not added
	 * to the back stack.
	 */
	public void switchContent(Fragment fragment, String tag) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		while (fragmentManager.popBackStackImmediate())
			;

		if (fragment != null) {
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			transaction.replace(R.id.content_frame, fragment, tag);
			// Only CourseAddFragment is added to the back stack.
			if (!(fragment instanceof CourseListFragment)) {
				transaction.addToBackStack(tag);
			}
			transaction.commit();
			contentFragment = fragment;
		}
	}

	protected void setFragmentTitle(int resourseId) {
		setTitle(resourseId);
		getActionBar().setTitle(resourseId);

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent int2 = new Intent(MainActivity.this, HomeActivity.class);
		startActivity(int2);
		finish();
	}

	/*
	 * Callback used to communicate with CourseListFragment to notify the list
	 * adapter. Communication between fragments goes via their Activity class.
	 */
	@Override
	public void onFinishDialog() {
		if (courseListFragment != null) {
			courseListFragment.updateView();
		}
	}
}
