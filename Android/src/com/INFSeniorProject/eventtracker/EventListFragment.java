package com.INFSeniorProject.eventtracker;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.format.DateFormat;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.INFSeniorProject.navigationdrawer.HomeActivity;
import com.INFSeniorProject.navigationdrawer.R;

public class EventListFragment extends ListFragment {
	private ArrayList<Event> mEvents;
	public static final String TAG = "EventListFragment";

	Button addEventBtn;

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_event_list, menu);
	}

	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View v = inflater.inflate(R.layout.eventtracker_fragment_empty_list,
				parent, false);
		ListView view = (ListView) v.findViewById(android.R.id.list);
		view.setEmptyView(v.findViewById(android.R.id.empty));

		addEventBtn = (Button) v.findViewById(R.id.initialEventButton);
		addEventBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) { // TODO Auto-generated method
				Event event = new Event();
				EventLab.get(getActivity()).addEvent(event);
				Intent i = new Intent(getActivity(), EventPagerActivity.class);
				i.putExtra(EventFragment.EXTRA_EVENT_ID, event.getId());
				startActivityForResult(i, 0);
			}
		});

		ListView listView = (ListView) v.findViewById(android.R.id.list);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			registerForContextMenu(listView);
		} else {
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
			listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
				@Override
				public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
					return false;
				}

				@Override
				public void onDestroyActionMode(ActionMode arg0) {

				}

				@Override
				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
					MenuInflater inflater = mode.getMenuInflater();
					inflater.inflate(R.menu.event_list_item_context, menu);
					return true;
				}

				@Override
				public boolean onActionItemClicked(ActionMode mode,
						MenuItem item) {
					switch (item.getItemId()) {
					case R.id.menu_item_delete_event:
						EventAdapter adapter = (EventAdapter) getListAdapter();
						EventLab eventlab = EventLab.get(getActivity());
						for (int i = adapter.getCount() - 1; i >= 0; i--) {
							if (getListView().isItemChecked(i)) {
								eventlab.deleteEvent(adapter.getItem(i));
							}
						}
						mode.finish();
						adapter.notifyDataSetChanged();
						return true;
					default:
						return false;
					}
				}

				@Override
				public void onItemCheckedStateChanged(ActionMode mode,
						int position, long id, boolean checked) {

				}
			});
		}
		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

		getActivity().setTitle("Events");
		mEvents = EventLab.get(getActivity()).getEvents();

		EventAdapter adapter = new EventAdapter(mEvents);
		setListAdapter(adapter);

		setRetainInstance(true);
	}

	@TargetApi(11)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(getActivity(), HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			getActivity().finish();
			startActivity(intent);
			
			return true;
		case R.id.menu_item_new_event:
			Event event = new Event();
			EventLab.get(getActivity()).addEvent(event);
			Intent i = new Intent(getActivity(), EventPagerActivity.class);
			i.putExtra(EventFragment.EXTRA_EVENT_ID, event.getId());
			startActivityForResult(i, 0);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		getActivity().getMenuInflater().inflate(R.menu.event_list_item_context,
				menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int position = info.position;
		EventAdapter adapter = (EventAdapter) getListAdapter();
		Event event = adapter.getItem(position);

		switch (item.getItemId()) {
		case R.id.menu_item_delete_event:
			EventLab.get(getActivity()).deleteEvent(event);
			adapter.notifyDataSetChanged();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		Event e = (Event) (getListAdapter()).getItem(position);
		Intent i = new Intent(getActivity(), EventPagerActivity.class);
		i.putExtra(EventFragment.EXTRA_EVENT_ID, e.getId());
		startActivity(i);
	}

	private class EventAdapter extends ArrayAdapter<Event> {
		public EventAdapter(ArrayList<Event> events) {
			super(getActivity(), 0, events);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.eventtracker_list_item_event, null);
			}

			Event e = getItem(position);

			TextView titleTextView = (TextView) convertView
					.findViewById(R.id.event_list_item_titleTextView);
			titleTextView.setText(e.getTitle());
			TextView dateTextView = (TextView) convertView
					.findViewById(R.id.event_list_item_dateTextView);
			dateTextView.setText(DateFormat.format("EEEE, MMMM dd, yyyy",
					e.getDate()));
			CheckBox solvedCheckBox = (CheckBox) convertView
					.findViewById(R.id.event_list_item_solvedCheckBox);
			solvedCheckBox.setChecked(e.isSolved());

			return convertView;
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((EventAdapter) getListAdapter()).notifyDataSetChanged();
	}

}
