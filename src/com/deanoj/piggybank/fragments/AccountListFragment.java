package com.deanoj.piggybank.fragments;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.deanoj.piggybank.AccountActivity;
import com.deanoj.piggybank.R;
import com.deanoj.piggybank.data.AccountAdapter;

public class AccountListFragment extends AbstractServiceFragment {

	private static final String TAG = "AccountListFragment";

	private ListView listView;

	private AccountAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);

		listView = (ListView) rootView.findViewById(R.id.main_account_list);

		setHasOptionsMenu(true);
		// setRetainInstance(true);

		return rootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(getActivity(), AccountActivity.class);
				intent.putExtra("position", position);
				startActivity(intent);
			}

		});

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.account_list_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.new_account:
			addNewAccount();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void addNewAccount() {
		Log.d(TAG, "adding new account");
		
		AddAccountDialogFragment dialog = new AddAccountDialogFragment();
		dialog.show(getFragmentManager(), "add_account");
	}

	@Override
	public void onServiceConnectedCallback(ComponentName className,
			IBinder service) {
		
		adapter = new AccountAdapter(getActivity(),
				android.R.layout.simple_list_item_2, android.R.id.text1,
				mService.getAccountList());

		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
}
