package com.deanoj.piggybank.fragments;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.deanoj.piggybank.service.AccountService;
import com.deanoj.piggybank.service.AccountService.LocalBinder;

public abstract class AbstractServiceFragment extends Fragment {

	boolean mBound = false;

	protected AccountService mService;
	
	abstract public void onServiceConnectedCallback(ComponentName className, IBinder service);
	
	@Override
	public void onStart() {
		super.onStart();
		Intent intent = new Intent(getActivity(), AccountService.class);
		getActivity()
				.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mBound && mConnection != null) {
			getActivity().unbindService(mConnection);
			mBound = false;
		}
	}

	protected ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			LocalBinder binder = (LocalBinder) service;
			mService = binder.getService();
			mBound = true;
			
			onServiceConnectedCallback(className, service);
		}

		@Override
		public void onServiceDisconnected(ComponentName className) {
			mBound = false;
		}
	};
}
