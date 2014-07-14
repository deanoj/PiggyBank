package com.deanoj.piggybank.data;

import java.text.NumberFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AccountAdapter extends ArrayAdapter<AccountDto> {

	private Context context;
	
	public AccountAdapter(Context context, int resource,
			int textViewResourceId, List<AccountDto> objects) {
		
		super(context, resource, textViewResourceId, objects);
		
		this.context = context;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(android.R.layout.simple_list_item_2, null);
		}

		AccountDto item = getItem(position);
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		
		if (item != null) {
			TextView accountBalanceView = (TextView) view
					.findViewById(android.R.id.text1);
			
			TextView accountNameView = (TextView) view
					.findViewById(android.R.id.text2);

			if (accountNameView != null) {
				accountNameView.setText(item.getAccountName());
			}
			
			if (accountBalanceView != null) {
				accountBalanceView.setText(nf.format(item.getBalance().doubleValue()));
			}
		}

		return view;
	}
	
}
