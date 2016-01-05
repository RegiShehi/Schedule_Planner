package com.INFSeniorProject.eventtracker;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

public class EventLab {

	private static final String TAG = "EventLab";
	private static final String FILENAME = "events.json";

	private static EventLab sEventLab;
	private Context mAppContext;

	private ArrayList<Event> mEvents;
	private EventIntentJSONSerializer mSerializer;

	private EventLab(Context appContext) {
		mAppContext = appContext;

		try {
			mEvents = mSerializer.loadEvents();
		} catch (Exception e) {
			mEvents = new ArrayList<Event>();
			Log.e(TAG, "Error loading events: ", e);
		}
	}

	public void addEvent(Event e) {
		mEvents.add(e);
	}

	public void deleteEvent(Event e) {
		mEvents.remove(e);
	}

	public ArrayList<Event> getEvents() {
		return mEvents;
	}

	public static EventLab get(Context c) {
		if (sEventLab == null) {
			sEventLab = new EventLab(c.getApplicationContext());
		}
		return sEventLab;
	}

	public Event getEvent(UUID id) {
		for (Event e : mEvents) {
			if (e.getId().equals(id))
				return e;
		}
		return null;
	}

	public boolean saveEvents() {
		try {
			mSerializer.saveEvents(mEvents);
			Log.d(TAG, "events saved to file");
			return true;
		} catch (Exception e) {
			Log.e(TAG, "Error saving crimes: ", e);
			return false;
		}
	}
}
