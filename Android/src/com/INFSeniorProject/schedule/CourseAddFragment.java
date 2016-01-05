package com.INFSeniorProject.schedule;

import java.lang.ref.WeakReference;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.INFSeniorProject.navigationdrawer.R;

public class CourseAddFragment extends Fragment implements OnClickListener {

	// UI references
	private EditText courseNametxt;
	private EditText courseTimetxt;
	private EditText courseRoomtxt;
	private Spinner deptSpinner;
	private Button addButton;
	private Button resetButton;

	private TextView view1, view2, view3;

	Course course = null;
	private CourseDAO courseDAO;
	private DepartmentDAO departmentDAO;
	private GetDeptTask task;
	private AddEmpTask addEmpTask;

	public static final String ARG_ITEM_ID = "course_add_fragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		courseDAO = new CourseDAO(getActivity());
		departmentDAO = new DepartmentDAO(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.schedule_fragment_add_course,
				container, false);

		findViewsById(rootView);

		setListeners();

		// asynchronously retrieves department from table and sets it in Spinner
		task = new GetDeptTask(getActivity());
		task.execute((Void) null);

		return rootView;
	}

	private void setListeners() {
		addButton.setOnClickListener(this);
		resetButton.setOnClickListener(this);
	}

	protected void resetAllFields() {
		courseNametxt.setText("");
		courseTimetxt.setText("");
		courseRoomtxt.setText("");
		if (deptSpinner.getAdapter().getCount() > 0)
			deptSpinner.setSelection(0);
	}

	private void setCourse() {
		course = new Course();
		course.setName(courseNametxt.getText().toString());
		course.setTime(courseTimetxt.getText().toString());
		course.setRoom(courseRoomtxt.getText().toString());
		Department selectedDept = (Department) deptSpinner.getSelectedItem();
		course.setDepartment(selectedDept);
	}

	@Override
	public void onResume() {
		getActivity().setTitle("Add Course");
		getActivity().getActionBar().setTitle("Add Course");
		super.onResume();
	}

	private void findViewsById(View rootView) {
		courseNametxt = (EditText) rootView.findViewById(R.id.etxt_name);
		courseTimetxt = (EditText) rootView.findViewById(R.id.etxt_time);
		courseRoomtxt = (EditText) rootView.findViewById(R.id.etxt_room);

		view1 = (TextView) rootView.findViewById(R.id.textView1);
		view2 = (TextView) rootView.findViewById(R.id.textView2);
		view3 = (TextView) rootView.findViewById(R.id.textView3);

		courseRoomtxt.setInputType(InputType.TYPE_NULL);

		deptSpinner = (Spinner) rootView.findViewById(R.id.spinner_dept);
		addButton = (Button) rootView.findViewById(R.id.button_add);
		resetButton = (Button) rootView.findViewById(R.id.button_reset);
	}

	@Override
	public void onClick(View view) {
		if (courseNametxt.getText().toString().isEmpty()
				|| courseRoomtxt.getText().toString().isEmpty()
				|| courseTimetxt.getText().toString().isEmpty()) {
			view1.setVisibility(View.VISIBLE);
			view2.setVisibility(View.VISIBLE);
			view3.setVisibility(View.VISIBLE);

			Toast.makeText(getActivity(), "Fill in all the *Required fields",
					Toast.LENGTH_SHORT).show();
		} else if (view == addButton) {
			view1.setVisibility(View.INVISIBLE);
			view2.setVisibility(View.INVISIBLE);
			view3.setVisibility(View.INVISIBLE);
			setCourse();
			addEmpTask = new AddEmpTask(getActivity());
			addEmpTask.execute((Void) null);
			Intent i = new Intent(getActivity(), MainActivity.class);
			startActivity(i);
		} else if (view == resetButton) {
			resetAllFields();
		}
	}

	public class GetDeptTask extends AsyncTask<Void, Void, Void> {

		private final WeakReference<Activity> activityWeakRef;
		private List<Department> departments;

		public GetDeptTask(Activity context) {
			this.activityWeakRef = new WeakReference<Activity>(context);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			departments = departmentDAO.getDepartments();
			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			if (activityWeakRef.get() != null
					&& !activityWeakRef.get().isFinishing()) {

				ArrayAdapter<Department> adapter = new ArrayAdapter<Department>(
						activityWeakRef.get(),
						android.R.layout.simple_list_item_1, departments);
				deptSpinner.setAdapter(adapter);

				addButton.setEnabled(true);
			}
		}
	}

	public class AddEmpTask extends AsyncTask<Void, Void, Long> {

		private final WeakReference<Activity> activityWeakRef;

		public AddEmpTask(Activity context) {
			this.activityWeakRef = new WeakReference<Activity>(context);
		}

		@Override
		protected Long doInBackground(Void... arg0) {
			long result = courseDAO.save(course);
			return result;
		}

		@Override
		protected void onPostExecute(Long result) {
			if (activityWeakRef.get() != null
					&& !activityWeakRef.get().isFinishing()) {
				if (result != -1)
					Toast.makeText(activityWeakRef.get(), "Course Saved",
							Toast.LENGTH_LONG).show();
			}
		}
	}
}
