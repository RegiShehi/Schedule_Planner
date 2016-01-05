package com.INFSeniorProject.schedule;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.INFSeniorProject.navigationdrawer.R;

public class CustomCourseDialogFragment extends DialogFragment {

	// UI references
	private EditText courseNametxt;
	private EditText courseTimetxt;
	private EditText courseRoomtxt;
	private Spinner deptSpinner;
	private LinearLayout submitLayout;

	private Course course;

	CourseDAO courseDAO;
	ArrayAdapter<Department> adapter;

	public static final String ARG_ITEM_ID = "course_dialog_fragment";

	/*
	 * Callback used to communicate with EmpListFragment to notify the list
	 * adapter. MainActivity implements this interface and communicates with
	 * EmpListFragment.
	 */
	public interface CustomEmpDialogFragmentListener {
		void onFinishDialog();
	}

	public CustomCourseDialogFragment() {

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		courseDAO = new CourseDAO(getActivity());

		Bundle bundle = this.getArguments();
		course = bundle.getParcelable("selectedCourse");

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View customDialogView = inflater.inflate(
				R.layout.schedule_fragment_add_course, null);
		builder.setView(customDialogView);

		courseNametxt = (EditText) customDialogView
				.findViewById(R.id.etxt_name);
		courseTimetxt = (EditText) customDialogView
				.findViewById(R.id.etxt_time);
		courseRoomtxt = (EditText) customDialogView
				.findViewById(R.id.etxt_room);
		deptSpinner = (Spinner) customDialogView
				.findViewById(R.id.spinner_dept);
		submitLayout = (LinearLayout) customDialogView
				.findViewById(R.id.layout_submit);
		submitLayout.setVisibility(View.GONE);
		setValue();

		builder.setTitle(R.string.update_course);
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.update,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						course.setName(courseNametxt.getText().toString());
						course.setTime(courseTimetxt.getText().toString());
						course.setRoom(courseRoomtxt.getText().toString());
						Department dept = (Department) adapter
								.getItem(deptSpinner.getSelectedItemPosition());
						course.setDepartment(dept);
						long result = courseDAO.update(course);

						if (courseNametxt.getText().toString().isEmpty()
								|| courseRoomtxt.getText().toString().isEmpty()
								|| courseTimetxt.getText().toString().isEmpty()) {

							Toast.makeText(getActivity(),
									"Fill in all the fields",
									Toast.LENGTH_SHORT).show();

						} else if (result > 0) {
							MainActivity activity = (MainActivity) getActivity();
							activity.onFinishDialog();
						} else {
							Toast.makeText(getActivity(),
									"Unable to update course",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();

					}
				});

		AlertDialog alertDialog = builder.create();

		return alertDialog;
	}

	private void setValue() {
		DepartmentDAO departmentDAO = new DepartmentDAO(getActivity());

		List<Department> departments = departmentDAO.getDepartments();
		adapter = new ArrayAdapter<Department>(getActivity(),
				android.R.layout.simple_list_item_1, departments);
		deptSpinner.setAdapter(adapter);
		int pos = adapter.getPosition(course.getDepartment());

		if (course != null) {
			courseNametxt.setText(course.getName());
			courseTimetxt.setText(course.getTime());
			courseRoomtxt.setText(course.getRoom());
			deptSpinner.setSelection(pos);
		}
	}
}
