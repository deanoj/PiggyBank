package com.deanoj.piggybank.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DbStore {

	private SQLiteDatabase db;

	public DbStore(Context context) {
		MyOpenHelper openHelper = new MyOpenHelper(context);
		this.db = openHelper.getWritableDatabase();
	}
	
	public List<AccountDto> fetchAll() {
		
		ArrayList<AccountDto> items = new ArrayList<AccountDto>();
    	Cursor query = db.query(AccountEntry.TABLE_NAME, null, null, null, null, null, null);
    	
    	while (query.moveToNext()) {
    		
    		int rowId = query.getInt(query.getColumnIndex(AccountEntry._ID));
    		String name = query.getString(query.getColumnIndex(AccountEntry.COLUMN_NAME_ACCOUNT_NAME));
    		double balance = query.getDouble(query.getColumnIndex(AccountEntry.COLUMN_NAME_BALANCE));
    		
    		AccountDto dto = new AccountDto(rowId, name, new BigDecimal(String.valueOf(balance)));
    		items.add(dto);
    	}
    	return items;
	}
	
	public long insert(AccountDto dto) {
		
		ContentValues values = new ContentValues();
        values.put(AccountEntry.COLUMN_NAME_ACCOUNT_NAME, dto.getAccountName());
        values.put(AccountEntry.COLUMN_NAME_BALANCE, 0);
        
        return db.insert(AccountEntry.TABLE_NAME, null, values);
	}
	
	public int update(AccountDto dto) {
		ContentValues values = new ContentValues();
        values.put(AccountEntry.COLUMN_NAME_ACCOUNT_NAME, dto.getAccountName());
        values.put(AccountEntry.COLUMN_NAME_BALANCE, dto.getBalance().doubleValue());
        
        String selection = AccountEntry._ID + " LIKE ?";
        String selectionArgs[] = { String.valueOf(dto.getRowId()) };
        
        return db.update(AccountEntry.TABLE_NAME, values, selection, selectionArgs);
	}
	
	public void persistAll(ArrayList<AccountDto> accountList) {
		
		for (AccountDto dto : accountList)
		{
			update(dto);
		}
	}
	
	public static abstract class AccountEntry implements BaseColumns {
		
		public static final String TABLE_NAME = "account";

		public static final String COLUMN_NAME_ACCOUNT_NAME = "account_name";

		public static final String COLUMN_NAME_BALANCE = "balance";
	}

	private class MyOpenHelper extends SQLiteOpenHelper {

		private static final String dbName = "accounts";

		private static final int dbVersion = 1;

		private static final String TEXT_TYPE = " TEXT";

		private static final String REAL_TYPE = " REAL";

		private static final String COMMA_SEP = ",";

		public static final String DATABASE_CREATE = "CREATE TABLE "
				+ AccountEntry.TABLE_NAME + " (" + AccountEntry._ID
				+ " INTEGER PRIMARY KEY,"
				+ AccountEntry.COLUMN_NAME_ACCOUNT_NAME + TEXT_TYPE + COMMA_SEP
				+ AccountEntry.COLUMN_NAME_BALANCE + REAL_TYPE + " )";

		public static final String DATABASE_DROP = "DROP TABLE IF EXISTS "
				+ AccountEntry.TABLE_NAME;

		public MyOpenHelper(Context context) {
			super(context, dbName, null, dbVersion);
		}

		@Override
		public void onCreate(SQLiteDatabase sqLiteDatabase) {
			sqLiteDatabase.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
			sqLiteDatabase.execSQL(DATABASE_DROP);
		}
	}

	

}
