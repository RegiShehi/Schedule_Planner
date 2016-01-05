package com.INFSeniorProject.schedule;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.INFSeniorProject.navigationdrawer.R;

public class CourseListAdapter extends ArrayAdapter<Course> {

	private Context context;
	List<Course> courses;

	public CourseListAdapter(Context context, List<Course> courses) {
		super(context, R.layout.schedule_list_item, courses);
		this.context = context;
		this.courses = courses;
	}

	private class ViewHolder {
		TextView courseNameTxt;
		TextView courseRoomTxt;
		TextView courseTimeTxt;
		TextView courseDeptNameTxt;
	}

	@Override
	public int getCount() {
		return courses.size();
	}

	@Override
	public Course getItem(int position) {
		return courses.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.schedule_list_item, null);
			holder = new ViewHolder();

			holder.courseNameTxt = (TextView) convertView
					.findViewById(R.id.txt_course_name);
			holder.courseRoomTxt = (TextView) convertView
					.findViewById(R.id.txt_course_room);
			holder.courseTimeTxt = (TextView) convertView
					.findViewById(R.id.txt_course_time);
			holder.courseDeptNameTxt = (TextView) convertView
					.findViewById(R.id.txt_course_dept);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Course course = (Course) getItem(position);
		holder.courseNameTxt.setText(course.getName());
		holder.courseTimeTxt.setText(course.getTime());
		holder.courseDeptNameTxt.setText(course.getDepartment().getName());

		holder.courseRoomTxt.setText(course.getRoom());

		return convertView;
	}

	@Override
	public void add(Course course) {
		courses.add(course);
		notifyDataSetChanged();
		super.add(course);
	}

	@Override
	public void remove(Course course) {
		courses.remove(course);
		notifyDataSetChanged();
		super.remove(course);
	}
}
