package com.INFSeniorProject.schedule;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "employeedb";
	private static final int DATABASE_VERSION = 1;

	public static final String COURSE_TABLE = "employee";
	public static final String DEPARTMENT_TABLE = "department";

	public static final String ID_COLUMN = "id";
	public static final String NAME_COLUMN = "name";
	public static final String COURSE_ROOM = "dob";
	public static final String COURSE_TIME = "salary";
	public static final String COURSE_DEPARTMENT_ID = "dept_id";

	public static final String CREATE_COURSE_TABLE = "CREATE TABLE "
			+ COURSE_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
			+ NAME_COLUMN + " TEXT, " + COURSE_TIME + " TEXT, "
			+ COURSE_ROOM + " TEXT, " + COURSE_DEPARTMENT_ID + " INT, "
			+ "FOREIGN KEY(" + COURSE_DEPARTMENT_ID + ") REFERENCES "
			+ DEPARTMENT_TABLE + "(id) " + ")";

	public static final String CREATE_DEPARTMENT_TABLE = "CREATE TABLE "
			+ DEPARTMENT_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY,"
			+ NAME_COLUMN + ")";

	private static DataBaseHelper instance;

	public static synchronized DataBaseHelper getHelper(Context context) {
		if (instance == null)
			instance = new DataBaseHelper(context);
		return instance;
	}

	private DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			// Enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_DEPARTMENT_TABLE);
		db.execSQL(CREATE_COURSE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
