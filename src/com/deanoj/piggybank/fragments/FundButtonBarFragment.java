package com.deanoj.piggybank.fragments;

import java.math.BigDecimal;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ComponentName;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.deanoj.piggybank.R;
import com.deanoj.piggybank.data.AccountDto;

public class FundButtonBarFragment extends AbstractServiceFragment {

	private static final String TAG = "FundButtonBarFragment";
	
	private AccountDto dto;
	
	private Button addTenMinor;

	private Button addFiftyMinor;

	private Button addOneMajor;
	
	private OnBalanceChangeListener listener;
	
	public interface OnBalanceChangeListener {
		public void onBalanceChange();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_fund_button_bar, container,
				false);

		addTenMinor = (Button) rootView.findViewById(R.id.fund_add_ten_minor);
		addFiftyMinor = (Button) rootView
				.findViewById(R.id.fund_add_fifty_minor);
		addOneMajor = (Button) rootView.findViewById(R.id.fund_add_one_major);

		addTenMinor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setFunds(new BigDecimal("0.10"));
			}
		});

		addFiftyMinor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setFunds(new BigDecimal("0.50"));
			}
		});

		addOneMajor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setFunds(new BigDecimal("1.00"));
			}
		});

		return rootView;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (OnBalanceChangeListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnBalanceChangeListener");
		}
	}
	
	@Override
	public void onServiceConnectedCallback(ComponentName className,
			IBinder service) {

		int position = getActivity().getIntent().getIntExtra("position", 0);
		
		ArrayList<AccountDto> accounts = mService.getAccountList();
		dto = accounts.get(position);
	}
	
	private void setFunds(BigDecimal payment) {
		
		Log.d(TAG, "setting funds " + payment.toString());
		dto.setBalance(dto.getBalance().add(payment));
		Log.d(TAG, "new balance " + dto.getBalance().toString());
		
		listener.onBalanceChange();
	}
	
}
