package com.deanoj.piggybank.fragments;

import java.math.BigDecimal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.deanoj.piggybank.R;
import com.deanoj.piggybank.data.AccountDto;
import com.deanoj.piggybank.service.AccountService;
import com.deanoj.piggybank.service.AccountService.LocalBinder;

public class AddAccountDialogFragment extends DialogFragment {
	
	private static final String TAG = "AddAccountDialogFragment";
	
	private View dialogView;
	
	boolean mBound = false;

	protected AccountService mService;

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		builder.setMessage(R.string.title_dialog_add_account);
		dialogView = inflater.inflate(R.layout.dialog_add_account, null);
		builder.setView(dialogView);
		
		builder.setPositiveButton(R.string.button_done, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.d(TAG, "dialog onclick");
				if (mBound) {
					
					EditText textView = (EditText)dialogView.findViewById(R.id.dialog_add_account_name);
					String accountName = textView.getText().toString();
					Log.d(TAG, "dialog service is connected with name " + accountName);
					
					mService.addAccount(new AccountDto(accountName, new BigDecimal("0.00")));
					
				}
			}
		})
		.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				//AddAccountDialogFragment.this.getDialog().cancel();
			}
		});
		
		return builder.create();
	}
	
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
			
		}

		@Override
		public void onServiceDisconnected(ComponentName className) {
			mBound = false;
		}
	};
}
