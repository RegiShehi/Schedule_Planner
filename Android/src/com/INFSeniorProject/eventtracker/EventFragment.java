package com.INFSeniorProject.eventtracker;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.INFSeniorProject.navigationdrawer.R;

public class EventFragment extends Fragment {

	private Event mEvent;
	private EditText mTitleField;
	private EditText mDescriptionField;
	private Button mDateButton;
	private CheckBox mSolvedCheckBox;
	private ImageButton mPhotoButton;
	private ImageView mPhotoView;

	public static final String EXTRA_EVENT_ID = "com.INFSeniorProject.eventtracker.event_id";
	private static final String TAG = "EventFragment";
	public static final String DIALOG_DATE = "date";
	public static final String DIALOG_TIME = "time";
	private static final String DIALOG_IMAGE = "image";

	private static final int REQUEST_DATE = 0;
	private static final int REQUEST_PHOTO = 3;
	public static final int REQUEST_TIME = 1;
	public static final int REQUEST_CHOICE = 2;

	public void updateDate() {
		mDateButton.setText(DateFormat.format("MMM d, yyyy  hh:mm a",
				mEvent.getDate()));
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenu.ContextMenuInfo menuInfo) {
		getActivity().getMenuInflater()
				.inflate(R.menu.event_photo_delete, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_delete_photo:
			if (mEvent.getPhoto() != null) {
				String path = getActivity().getFileStreamPath(
						mEvent.getPhoto().getFileName()).getAbsolutePath();
				File f = new File(path);
				f.delete();
				mEvent.setPhoto(null);
				mPhotoView.setImageDrawable(null);
			}
			return true;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		UUID eventId = (UUID) getArguments().getSerializable(EXTRA_EVENT_ID);
		mEvent = EventLab.get(getActivity()).getEvent(eventId);
		setHasOptionsMenu(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.delete_fragment_event:
			EventLab.get(getActivity()).deleteEvent(mEvent);
			getActivity().finish();
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				NavUtils.navigateUpFromSameTask(getActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.eventtracker_fragment_event,
				container, false);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}

		mTitleField = (EditText) v.findViewById(R.id.event_title);
		mTitleField.setText(mEvent.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				mEvent.setTitle(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		mDescriptionField = (EditText) v.findViewById(R.id.event_description);
		mDescriptionField.setText(mEvent.getDescription());
		mDescriptionField.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				mEvent.setDescription(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		mDateButton = (Button) v.findViewById(R.id.event_date);
		updateDate();

		/*
		 * mDateButton.setText(DateFormat.format("EEEE, MMMM dd, yyyy h:mmaa",
		 * mEvent.getDate()));
		 */
		mDateButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fm = getActivity().getSupportFragmentManager();
				ChoiceDialogFragment dialog = ChoiceDialogFragment
						.newInstance(mEvent.getDate());
				dialog.setTargetFragment(EventFragment.this, REQUEST_CHOICE);
				dialog.show(fm, EXTRA_EVENT_ID);
			}
		});

		mSolvedCheckBox = (CheckBox) v.findViewById(R.id.event_solved);
		mSolvedCheckBox.setChecked(mEvent.isSolved());
		mSolvedCheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						mEvent.setSolved(isChecked);
					}
				});

		mPhotoButton = (ImageButton) v.findViewById(R.id.event_imageButton);
		mPhotoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), EventCameraActivity.class);
				startActivityForResult(i, REQUEST_PHOTO);
			}
		});

		mPhotoView = (ImageView) v.findViewById(R.id.event_imageView);
		registerForContextMenu(mPhotoView);
		mPhotoView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Photo p = mEvent.getPhoto();
				if (p == null)
					return;

				FragmentManager fm = getActivity().getSupportFragmentManager();
				String path = getActivity().getFileStreamPath(p.getFileName())
						.getAbsolutePath();
				int orientation = p.getOrientation();
				ImageFragment.newInstance(path, orientation).show(fm,
						DIALOG_IMAGE);
			}
		});

		// If camera in not available, disable camera functionality
		PackageManager pm = getActivity().getPackageManager();
		if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
				&& !pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
			mPhotoButton.setEnabled(false);
		}
		return v;
	}

	// Show Photo
	private void showPhoto() {
		// (Re)set the image button's image based on our photo
		Photo p = mEvent.getPhoto();
		BitmapDrawable b = null;
		if (p != null) {
			String path = getActivity().getFileStreamPath(p.getFileName())
					.getAbsolutePath();
			b = PictureUtils.getScaledDrawable(getActivity(), path);

			int orientation = p.getOrientation();
			if (orientation == EventCameraActivity.ORIENTATION_PORTRAIT_INVERTED
					|| orientation == EventCameraActivity.ORIENTATION_PORTRAIT_NORMAL) {
				b = PictureUtils.getPortraitDrawable(mPhotoView, b);
			}
		}
		mPhotoView.setImageDrawable(b);
	}

	public static EventFragment newInstance(UUID eventId) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_EVENT_ID, eventId);

		EventFragment fragment = new EventFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != Activity.RESULT_OK)
			return;
		if (requestCode == REQUEST_DATE) {
			Date date = (Date) data
					.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
			mEvent.setDate(date);
			updateDate();
		}

		if (requestCode == REQUEST_TIME) {
			Date time = (Date) data
					.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
			combineTime(time);
			updateDate();
		}
		if (requestCode == REQUEST_CHOICE) {
			int choice = data.getIntExtra(ChoiceDialogFragment.EXTRA_CHOICE, 0);
			if (choice == ChoiceDialogFragment.CHOICE_DATE) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				DatePickerFragment dialog = DatePickerFragment
						.newInstance(mEvent.getDate());
				dialog.setTargetFragment(EventFragment.this, REQUEST_DATE);
				dialog.show(fm, EXTRA_EVENT_ID);
			} else if (choice == ChoiceDialogFragment.CHOICE_TIME) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				TimePickerFragment dialog = TimePickerFragment
						.newInstance(mEvent.getDate());
				dialog.setTargetFragment(EventFragment.this, REQUEST_TIME);
				dialog.show(fm, EXTRA_EVENT_ID);
			}
		} else if (requestCode == REQUEST_PHOTO) {
			// Create a new Photo object and attach it to the crime
			String filename = data
					.getStringExtra(EventCameraFragment.EXTRA_PHOTO_FILENAME);
			int i = data.getIntExtra(
					EventCameraFragment.EXTRA_PHOTO_ORIENTATION, 0);
			if (filename != null) {
				Photo p = new Photo(filename, i);
				mEvent.setPhoto(p);
				showPhoto();
			}
		}
	}

	public void combineTime(Date time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(mEvent.getDate());
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(time);
		int hours = cal.get(Calendar.HOUR_OF_DAY);
		int mins = cal.get(Calendar.MINUTE);
		Date finalD = new GregorianCalendar(year, month, day, hours, mins)
				.getTime();
		mEvent.setDate(finalD);
	}

	public void combineDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(mEvent.getDate());
		int hours = cal.get(Calendar.HOUR_OF_DAY);
		int mins = cal.get(Calendar.MINUTE);

		Date finalD = new GregorianCalendar(year, month, day, hours, mins)
				.getTime();
		mEvent.setDate(finalD);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.delete_fragment_crime, menu);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		EventLab.get(getActivity()).saveEvents();
	}

	@Override
	public void onStart() {
		super.onStart();
		showPhoto();
	}

	@Override
	public void onStop() {
		super.onStop();
		PictureUtils.cleanImageView(mPhotoView);
	}
}
