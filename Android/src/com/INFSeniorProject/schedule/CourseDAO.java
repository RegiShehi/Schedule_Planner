package com.INFSeniorProject.schedule;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

//import android.database.sqlite.SQLiteQueryBuilder;

public class CourseDAO extends CourseDBDAO {

	public static final String COURSE_ID_WITH_PREFIX = "course.id";
	public static final String COURSE_NAME_WITH_PREFIX = "course.name";
	public static final String DEPT_NAME_WITH_PREFIX = "dept.name";

	private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
			+ " =?";

	public CourseDAO(Context context) {
		super(context);
	}

	public long save(Course course) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NAME_COLUMN, course.getName());
		values.put(DataBaseHelper.COURSE_ROOM, course.getRoom());
		values.put(DataBaseHelper.COURSE_TIME, course.getTime());
		values.put(DataBaseHelper.COURSE_DEPARTMENT_ID, course.getDepartment()
				.getId());

		return database.insert(DataBaseHelper.COURSE_TABLE, null, values);
	}

	public long update(Course course) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NAME_COLUMN, course.getName());
		values.put(DataBaseHelper.COURSE_ROOM, course.getRoom());
		values.put(DataBaseHelper.COURSE_TIME, course.getTime());
		values.put(DataBaseHelper.COURSE_DEPARTMENT_ID, course.getDepartment()
				.getId());

		long result = database.update(DataBaseHelper.COURSE_TABLE, values,
				WHERE_ID_EQUALS,
				new String[] { String.valueOf(course.getId()) });
		Log.d("Update Result:", "=" + result);
		return result;
	}

	public int deleteEmployee(Course course) {
		return database.delete(DataBaseHelper.COURSE_TABLE, WHERE_ID_EQUALS,
				new String[] { course.getId() + "" });
	}

	// METHOD 1
	// Uses rawQuery() to query multiple tables
	public ArrayList<Course> getCourses() {
		ArrayList<Course> courses = new ArrayList<Course>();
		String query = "SELECT " + COURSE_ID_WITH_PREFIX + ","
				+ COURSE_NAME_WITH_PREFIX + "," + DataBaseHelper.COURSE_ROOM
				+ "," + DataBaseHelper.COURSE_TIME + ","
				+ DataBaseHelper.COURSE_DEPARTMENT_ID + ","
				+ DEPT_NAME_WITH_PREFIX + " FROM "
				+ DataBaseHelper.COURSE_TABLE + " course, "
				+ DataBaseHelper.DEPARTMENT_TABLE + " dept WHERE course."
				+ DataBaseHelper.COURSE_DEPARTMENT_ID + " = dept."
				+ DataBaseHelper.ID_COLUMN;

		Log.d("query", query);
		Cursor cursor = database.rawQuery(query, null);
		while (cursor.moveToNext()) {
			Course course = new Course();
			course.setId(cursor.getInt(0));
			course.setName(cursor.getString(1));
			course.setRoom(cursor.getString(2));
			course.setTime(cursor.getString(3));

			Department department = new Department();
			department.setId(cursor.getInt(4));
			department.setName(cursor.getString(5));

			course.setDepartment(department);

			courses.add(course);
		}
		return courses;
	}
}
