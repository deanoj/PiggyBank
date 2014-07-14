package com.deanoj.piggybank.service;

import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.deanoj.piggybank.data.AccountDto;
import com.deanoj.piggybank.data.DbStore;

public class AccountService extends Service {

	private final IBinder mBinder = new LocalBinder();

	private static final String TAG = "AccountService";

	private final ArrayList<AccountDto> accountList = new ArrayList<AccountDto>();

	private DbStore db;

	public class LocalBinder extends Binder {
		public AccountService getService() {
			return AccountService.this;
		}
	}

	@Override
	public void onCreate() {
		db = new DbStore(getApplicationContext());
		accountList.addAll(db.fetchAll());
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "service bound");

		return mBinder;
	}

	@Override
	public void onDestroy() {
		db.persistAll(accountList);
	}

	public ArrayList<AccountDto> getAccountList() {
		return accountList;
	}

	public void addAccount(AccountDto dto) {
		int rowId = (int) db.insert(dto);
		dto.setRowId(rowId);
		accountList.add(dto);
	}
	
	@Override
	public String toString() {
		return "AccountService [mBinder=" + mBinder + "]";
	}

}
