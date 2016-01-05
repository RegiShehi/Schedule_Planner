package com.INFSeniorProject.eventtracker;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.INFSeniorProject.navigationdrawer.R;

public class TimePickerFragment extends DialogFragment {
	public static final String EXTRA_TIME = "com.INFSeniorProjecr.eventtracker.time";
	private Date mDate;

	public static TimePickerFragment newInstance(Date date) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_TIME, date);

		TimePickerFragment fragment = new TimePickerFragment();
		fragment.setArguments(args);

		return fragment;
	}

	public void sendReult(int resultCode) {
		if (getTargetFragment() == null)
			return;

		Intent i = new Intent();
		i.putExtra(EXTRA_TIME, mDate);

		getTargetFragment().onActivityResult(getTargetRequestCode(),
				resultCode, i);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mDate = (Date) getArguments().getSerializable(EXTRA_TIME);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mDate);
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		// int am = calendar.get(Calendar.PM);

		View v = getActivity().getLayoutInflater().inflate(
				R.layout.eventtracker_dialog_time, null);

		TimePicker timePicker = (TimePicker) v
				.findViewById(R.id.time_dialog_timePicker);
		timePicker.setCurrentHour(hour);
		timePicker.setCurrentMinute(minute);
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			@SuppressWarnings("deprecation")
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				mDate.setHours(hourOfDay);
				mDate.setMinutes(minute);
				getArguments().putSerializable(EXTRA_TIME, mDate);
			}
		});
		return new AlertDialog.Builder(getActivity())
				.setView(v)
				.setTitle("Time of event: ")
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								sendReult(Activity.RESULT_OK);
							}
						}).create();
	}
}
