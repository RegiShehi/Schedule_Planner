package com.INFSeniorProject.schedule;

import android.os.Parcel;
import android.os.Parcelable;

public class Course implements Parcelable {

	private int id;
	private String name;
	private String room;
	private String time;

	private Department department;

	public Course() {
		super();
	}

	private Course(Parcel in) {
		super();
		this.id = in.readInt();
		this.name = in.readString();
		this.room = in.readString();
		this.time = in.readString();

		this.department = in.readParcelable(Department.class.getClassLoader());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", room="
				+ room + ", time=" + time + ", department="
				+ department + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(getId());
		parcel.writeString(getName());
		parcel.writeString(getRoom());
		parcel.writeString(getTime());
		parcel.writeParcelable(getDepartment(), flags);
	}

	public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {
		public Course createFromParcel(Parcel in) {
			return new Course(in);
		}

		public Course[] newArray(int size) {
			return new Course[size];
		}
	};

}
