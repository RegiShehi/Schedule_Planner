package com.INFSeniorProject.eventtracker;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.INFSeniorProject.navigationdrawer.HomeActivity;

public class EventListActivity extends SingleFragmentActivity {

	@Override
	public void onBackPressed() {
			// TODO Auto-generated method stub
			Intent int2 = new Intent(EventListActivity.this, HomeActivity.class);
			startActivity(int2);
			finish();
	}

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new EventListFragment();
	}

}
