package com.INFSeniorProject.schedule;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.INFSeniorProject.navigationdrawer.R;

public class CourseListFragment extends Fragment implements
		OnItemClickListener, OnItemLongClickListener {

	public static final String ARG_ITEM_ID = "course_list";
	public static final String YES_NO = "modify";
	public static final String POSITION = "position";

	Activity activity;
	ListView courseListView;
	ArrayList<Course> courses;

	CourseListAdapter courseListAdapter;
	CourseDAO courseDAO;

	private GetEmpTask task;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		courseDAO = new CourseDAO(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.schedule_fragment_course_list,
				container, false);
		findViewsById(view);

		task = new GetEmpTask(activity);
		task.execute((Void) null);

		courseListView.setOnItemClickListener(this);
		courseListView.setOnItemLongClickListener(this);
		return view;
	}

	private void findViewsById(View view) {
		courseListView = (ListView) view.findViewById(R.id.list_course);
	}

	@Override
	public void onItemClick(AdapterView<?> list, View view, int position,
			long id) {
		Course course = (Course) list.getItemAtPosition(position);

		if (course != null) {
			Bundle arguments = new Bundle();
			arguments.putParcelable("selectedCourse", course);
			CustomCourseDialogFragment customEmpDialogFragment = new CustomCourseDialogFragment();
			customEmpDialogFragment.setArguments(arguments);
			customEmpDialogFragment.show(getFragmentManager(),
					CustomCourseDialogFragment.ARG_ITEM_ID);
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {

		final Course employee = (Course) parent.getItemAtPosition(position);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
		.setTitle("Do you want to delete?")
		.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub
						courseDAO.deleteEmployee(employee);
						courseListAdapter.remove(employee);
					}
				})
		.setNegativeButton(android.R.string.cancel,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
		builder.create().show();

		return true;
	}

	public class GetEmpTask extends AsyncTask<Void, Void, ArrayList<Course>> {

		private final WeakReference<Activity> activityWeakRef;

		public GetEmpTask(Activity context) {
			this.activityWeakRef = new WeakReference<Activity>(context);
		}

		@Override
		protected ArrayList<Course> doInBackground(Void... arg0) {
			ArrayList<Course> courseList = courseDAO.getCourses();
			return courseList;
		}

		@Override
		protected void onPostExecute(ArrayList<Course> empList) {
			if (activityWeakRef.get() != null
					&& !activityWeakRef.get().isFinishing()) {
				courses = empList;
				if (empList != null) {
					if (empList.size() != 0) {
						courseListAdapter = new CourseListAdapter(activity,
								empList);
						courseListView.setAdapter(courseListAdapter);
					} else {
						Toast.makeText(activity, "No Course Records",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		}
	}

	/*
	 * This method is invoked from MainActivity onFinishDialog() method. It is
	 * called from CustomEmpDialogFragment when an employee record is updated.
	 * This is used for communicating between fragments.
	 */
	public void updateView() {
		task = new GetEmpTask(activity);
		task.execute((Void) null);
	}

	@Override
	public void onResume() {
		getActivity().setTitle("Course Schedule");
		getActivity().getActionBar().setTitle("Course Schedule");
		super.onResume();
	}
}
