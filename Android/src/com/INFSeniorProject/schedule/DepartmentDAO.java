package com.INFSeniorProject.schedule;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class DepartmentDAO extends CourseDBDAO {

	private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
			+ " =?";

	public DepartmentDAO(Context context) {
		super(context);
	}

	public long save(Department department) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NAME_COLUMN, department.getName());

		return database.insert(DataBaseHelper.DEPARTMENT_TABLE, null, values);
	}

	public long update(Department department) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NAME_COLUMN, department.getName());

		long result = database.update(DataBaseHelper.DEPARTMENT_TABLE, values,
				WHERE_ID_EQUALS,
				new String[] { String.valueOf(department.getId()) });
		Log.d("Update Result:", "=" + result);
		return result;

	}

	public int deleteDept(Department department) {
		return database.delete(DataBaseHelper.DEPARTMENT_TABLE,
				WHERE_ID_EQUALS, new String[] { department.getId() + "" });
	}

	public List<Department> getDepartments() {
		List<Department> departments = new ArrayList<Department>();
		Cursor cursor = database.query(DataBaseHelper.DEPARTMENT_TABLE,
				new String[] { DataBaseHelper.ID_COLUMN,
						DataBaseHelper.NAME_COLUMN }, null, null, null, null,
				null);

		while (cursor.moveToNext()) {
			Department department = new Department();
			department.setId(cursor.getInt(0));
			department.setName(cursor.getString(1));
			departments.add(department);
		}
		return departments;
	}

	public void loadDepartments() {
		Department department = new Department(
				"Arts, Languages, and Literature ");
		Department department1 = new Department("Business");
		Department department2 = new Department("Computer Science");
		Department department3 = new Department("Economics");
		Department department4 = new Department("History and Civilizations");
		Department department5 = new Department(
				"Journalism and Mass Communication");
		Department department6 = new Department("Mathematics and Science");
		Department department7 = new Department("Politics and European Studies");

		List<Department> departments = new ArrayList<Department>();
		departments.add(department);
		departments.add(department1);
		departments.add(department2);
		departments.add(department3);
		departments.add(department4);
		departments.add(department5);
		departments.add(department6);
		departments.add(department7);
		for (Department dept : departments) {
			ContentValues values = new ContentValues();
			values.put(DataBaseHelper.NAME_COLUMN, dept.getName());
			database.insert(DataBaseHelper.DEPARTMENT_TABLE, null, values);
		}
	}

}
