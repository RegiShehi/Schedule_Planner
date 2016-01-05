package com.INFSeniorProject.navigationdrawer;

import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends BaseActivity {
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigationdrawer_activity_home);
		
		Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/athletic.ttf");

		// load titles from strings.xml
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		

		// load icons from strings.xml
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		set(navMenuTitles, navMenuIcons);
		setTitle("INF Senior Project");
		
		TextView tv = (TextView) findViewById(R.id.txtLabel);
        tv.setTypeface(tf);
        
        TextView tv1 = (TextView) findViewById(R.id.txtLabel1);
        tv1.setTypeface(tf);
	}
}