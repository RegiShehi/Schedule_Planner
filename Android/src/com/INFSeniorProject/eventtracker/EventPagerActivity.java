package com.INFSeniorProject.eventtracker;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.INFSeniorProject.navigationdrawer.R;

public class EventPagerActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private ArrayList<Event> mEvents;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);

		mEvents = EventLab.get(this).getEvents();
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			@Override
			public int getCount() {
				return mEvents.size();
			}

			@Override
			public Fragment getItem(int pos) {
				Event event = mEvents.get(pos);
				return EventFragment.newInstance(event.getId());
			}
		});

		mViewPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageSelected(int pos) {
						Event event = mEvents.get(pos);
						if (event.getTitle() != null) {
							setTitle(event.getTitle());
						}
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {

					}

					@Override
					public void onPageScrollStateChanged(int arg0) {

					}
				});

		UUID eventId = (UUID) getIntent().getSerializableExtra(
				EventFragment.EXTRA_EVENT_ID);
		for (int i = 0; i < mEvents.size(); i++) {
			if (mEvents.get(i).getId().equals(eventId)) {
				mViewPager.setCurrentItem(i);
				break;
			}
		}
	}
}
