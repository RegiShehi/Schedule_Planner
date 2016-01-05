package com.INFSeniorProject.eventtracker;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class Event {

	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_SOLVED = "solved";
	private static final String JSON_DATE = "date";
	private static final String JSON_DESCRIPTION = "description";
	private static final String JSON_PHOTO = "photo";

	private UUID mId;
	private String mTitle;
	private Date mDate;
	private boolean mSolved;
	private String mDescription;
	private Photo mPhoto;

	public Event() {
		mId = UUID.randomUUID(); // generate random identifier
		mDate = new Date();
	}

	public Event(JSONObject json) throws JSONException {
		mId = UUID.fromString(json.getString(JSON_ID));
		mTitle = json.getString(JSON_TITLE);
		mDescription = json.getString(JSON_DESCRIPTION);
		mSolved = json.getBoolean(JSON_SOLVED);
		mDate = new Date(json.getLong(JSON_DATE));
		if (json.has(JSON_PHOTO))
			mPhoto = new Photo(json.getJSONObject(JSON_PHOTO));
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_TITLE, mTitle);
		json.put(JSON_DESCRIPTION, mDescription);
		json.put(JSON_SOLVED, mSolved);
		json.put(JSON_DATE, mDate.getTime());
		if (mPhoto != null) {
			json.put(JSON_PHOTO, mPhoto.toJSON());
		}
		return json;
	}

	public UUID getId() {
		return mId;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String Title) {
		this.mTitle = Title;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String Description) {
		this.mDescription = Description;
	}

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date date) {
		mDate = date;
	}

	public boolean isSolved() {
		return mSolved;
	}

	public void setSolved(boolean solved) {
		mSolved = solved;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mTitle;
	}

	public Photo getPhoto() {
		return mPhoto;
	}

	public void setPhoto(Photo p) {
		mPhoto = p;
	}
}
