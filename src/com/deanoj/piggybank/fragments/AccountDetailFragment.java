package com.deanoj.piggybank.fragments;

import java.text.NumberFormat;
import java.util.ArrayList;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.deanoj.piggybank.R;
import com.deanoj.piggybank.data.AccountDto;

public class AccountDetailFragment extends AbstractServiceFragment {

	private static final String TAG = "AccountDetailFragment";

	private ViewSwitcher nameViewSwitcher;

	private TextView nameTextView;

	private EditText nameEditText;

	private TextView accountBalance;

	private AccountDto dto;
	
	private NumberFormat nf;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_account_detail,
				container, false);

		nameViewSwitcher = (ViewSwitcher) rootView
				.findViewById(R.id.name_view_switcher);
		nameTextView = (TextView) rootView
				.findViewById(R.id.account_name_text_view);
		nameEditText = (EditText) rootView
				.findViewById(R.id.account_name_edit_text);
		accountBalance = (TextView) rootView.findViewById(R.id.account_balance);
		
		nf = NumberFormat.getCurrencyInstance();

		nameTextView.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				Log.d(TAG, "on long click listener");
				v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
				nameViewSwitcher.showNext();
				if (nameEditText.requestFocus()) {
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					
					imm.showSoftInput(nameEditText, InputMethodManager.SHOW_IMPLICIT);
					
					// place edit cursor at end of string
					int textLength = nameEditText.getText().length();
					nameEditText.setSelection(textLength);
				}
				return false;
			}
		});

		
		nameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				Log.d(TAG, "enter key pressed");
				nameTextView.setText(v.getText().toString());
				dto.setAccountName(v.getText().toString());
				nameViewSwitcher.showNext();
				return false;
			}
		});

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	public void onServiceConnectedCallback(ComponentName className,
			IBinder service) {
		Log.d(TAG, "service connected callback");
		if (getActivity() != null) {
			setAccountDto();
		}
	}

	private void setAccountDto() {
		int position = getActivity().getIntent().getIntExtra("position", 0);

		ArrayList<AccountDto> accounts = mService.getAccountList();
		dto = accounts.get(position);

		nameTextView.setText(dto.getAccountName());
		nameEditText.setText(dto.getAccountName());
		accountBalance.setText(nf.format(dto.getBalance().doubleValue()));
	}

	public void updateBalance() {
		accountBalance.setText(nf.format(dto.getBalance().doubleValue()));
	}
	
}
