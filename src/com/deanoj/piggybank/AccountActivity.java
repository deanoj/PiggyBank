package com.deanoj.piggybank;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.deanoj.piggybank.fragments.AccountDetailFragment;
import com.deanoj.piggybank.fragments.FundButtonBarFragment.OnBalanceChangeListener;

public class AccountActivity extends Activity implements
		OnBalanceChangeListener {

	private static final String TAG = "AccountActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBalanceChange() {
		Log.d(TAG, "balance has changed");

		AccountDetailFragment detailFragment = (AccountDetailFragment) getFragmentManager()
				.findFragmentById(R.id.account_detail_fragment);
		
		if (detailFragment != null) {
			detailFragment.updateBalance();
		}
	}

}
