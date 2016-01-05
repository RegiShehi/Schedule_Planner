package com.INFSeniorProject.eventtracker;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.INFSeniorProject.navigationdrawer.R;

public class ChoiceDialogFragment extends DialogFragment {
	public static final String EXTRA_CHOICE = "com.INFSeniorProject.eventtracker";

	private int mChoice = 0;

	public static final int CHOICE_DATE = 1;
	public static final int CHOICE_TIME = 2;

	public static ChoiceDialogFragment newInstance(Date date) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_CHOICE, date);

		ChoiceDialogFragment fragment = new ChoiceDialogFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle(R.string.date_time_picker_title)
				.setPositiveButton(R.string.date_change,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(
									DialogInterface dialogInterface, int i) {
								mChoice = CHOICE_DATE;
								sendResult(Activity.RESULT_OK);
							}
						})
				.setNegativeButton(R.string.time_change,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(
									DialogInterface dialogInterface, int i) {
								mChoice = CHOICE_TIME;
								sendResult(Activity.RESULT_OK);
							}
						});
		return builder.create();
	}

	private void sendResult(int resultCode) {
		if (getTargetFragment() == null)
			return;
		Intent i = new Intent();
		i.putExtra(EXTRA_CHOICE, mChoice);

		getTargetFragment().onActivityResult(getTargetRequestCode(),
				resultCode, i);
	}
}
